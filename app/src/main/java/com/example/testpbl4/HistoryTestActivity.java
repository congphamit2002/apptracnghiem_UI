package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.SavedStateHandle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.testpbl4.Adapter.HistoryTestAdapter;
import com.example.testpbl4.Adapter.SubjectAdapter;
import com.example.testpbl4.Payload.HistoryTestRespone;
import com.example.testpbl4.Payload.ShareData;
import com.example.testpbl4.Service.HistoryTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rlcListHistory;
    private HistoryTestAdapter historyTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_test);

        rlcListHistory = findViewById(R.id.rlcListHistory);
        toolbar = findViewById(R.id.toolBar);
        actionToolBar();
        renderHistoryTest();
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void renderHistoryTest() {
        Call<ArrayList<Map<String, String>>> getHistoryCall = APIClient.getHistoryTestService()
                .getHistoryTestByUserID(ShareData.userLogin.getId(),
                        "Bearer " + ShareData.userLogin.getToken());
        getHistoryCall.enqueue(new Callback<ArrayList<Map<String, String>>>() {
            @Override
            public void onResponse(Call<ArrayList<Map<String, String>>> call, Response<ArrayList<Map<String, String>>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Map<String, String>> listResult = response.body();
                    ArrayList<HistoryTestRespone> listHistory = new ArrayList<>();
                    Log.e("\t\tList history size ", "" + listResult.size());
                    for (Map<String, String> item : listResult) {
                        HistoryTestRespone historyTestRespone = new HistoryTestRespone();
                        historyTestRespone.setCorrect_answer(Integer.parseInt(item.get("correct_answer")));
                        historyTestRespone.setIncorrect_answer(Integer.parseInt(item.get("incorrect_answer")));
                        historyTestRespone.setScore(Double.parseDouble(String.format("%.1f", Double.parseDouble(item.get("score"))).replace(",",".")));
                        historyTestRespone.setName_gr_detail(item.get("name_gr_detail"));
                        historyTestRespone.setId(Integer.parseInt(item.get("id")));
                        listHistory.add(historyTestRespone);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryTestActivity.this, LinearLayoutManager.VERTICAL, false);
                    rlcListHistory.setLayoutManager(linearLayoutManager);
                    historyTestAdapter = new HistoryTestAdapter(listHistory, HistoryTestActivity.this);
                    Log.e("\t\tJson data ", "" + listHistory.get(0).getName_gr_detail());
                    historyTestAdapter.setOnItemClickListener(new HistoryTestAdapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            new AlertDialog.Builder(HistoryTestActivity.this)
                                    .setTitle("Xóa lịch sử")
                                    .setMessage("Bạn có muốn xóa lịch sử bài thi này không?")
                                    .setPositiveButton( "Xác nhận", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    Call<String> deleteHistoryCall = APIClient.getHistoryTestService()
                                            .deleteHistoryTest(listHistory.get(position).getId(),
                                                    "Bearer " + ShareData.userLogin.getToken());
                                    deleteHistoryCall.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if(response.isSuccessful()) {
                                                Toast.makeText(HistoryTestActivity.this, "Xóa lịch sử thi thành công", Toast.LENGTH_LONG).show();
                                                HistoryTestActivity.this.recreate();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            }).setNegativeButton( "Hủy", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing

                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }

                        @Override
                        public void onItemLongClick(int position, View v) {

                        }
                    });

                    rlcListHistory.setAdapter(historyTestAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Map<String, String>>> call, Throwable t) {

            }
        });
    }
}