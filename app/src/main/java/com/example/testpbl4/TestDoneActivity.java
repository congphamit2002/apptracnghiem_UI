package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testpbl4.Constant.Constant;
import com.example.testpbl4.Payload.HistoryTestRequest;
import com.example.testpbl4.Payload.ShareData;
import com.example.testpbl4.model.Question;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestDoneActivity extends AppCompatActivity {

    private TextView txtNumCorr, txtNumIncorr, txtNumNoCheck, txtNumScore;
    Button btnRestart, btnSaveTest, btnExit;
    private ArrayList<Question> listQuestion;
    private int numIncorr, numCorr, numNoCheck, questionGrDetailId;
    private double numScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_done);

        txtNumCorr = findViewById(R.id.txtNumCorr);
        txtNumIncorr = findViewById(R.id.txtNumIncorr);
        txtNumNoCheck = findViewById(R.id.txtNumNoCheck);
        txtNumScore = findViewById(R.id.txtNumScore);
        btnRestart = findViewById(R.id.btnRestart);
        btnSaveTest = findViewById(R.id.btnSaveTest);
        btnExit = findViewById(R.id.btnExit);

        Bundle b = getIntent().getExtras();
        listQuestion = (ArrayList<Question>) b.getSerializable("listQuestion");
        questionGrDetailId = b.getInt(Constant.ARG_QUESTION_GR_DETAIL_ID);
        Log.e("questionGrDetailId  ", ""+questionGrDetailId);

        renderResult();

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listQuestion = refreshListQuestion(listQuestion);
                Intent intent = new Intent(TestDoneActivity.this, ScreenSlidePagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listQuestion",(Serializable) listQuestion);
                bundle.putInt(Constant.ARG_QUESTION_GR_DETAIL_ID, questionGrDetailId);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát hay không?");
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        btnSaveTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn lưu điểm hay không?");
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveHistoryTest(questionGrDetailId);
                    }
                });
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    public void saveHistoryTest(int id) {
        HistoryTestRequest historyTestRequest = new HistoryTestRequest();
        historyTestRequest.setAccountId(ShareData.userLogin.getId());
        historyTestRequest.setQgrDetailId(id);
        historyTestRequest.setCorrectAnswer(numCorr);
        historyTestRequest.setInCorrectAnswer(numIncorr);
        numScore = Double.parseDouble(String.format("%.1f", numScore).replace(",","."));
        Log.e("\t\tNumscore ", ""+ numScore);
        historyTestRequest.setScore(numScore );
        Call<String> saveHistoryTestCall = APIClient.getHistoryTestService().saveHistoryTest(historyTestRequest, "Bearer " +ShareData.userLogin.getToken());
        saveHistoryTestCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(TestDoneActivity.this, "Lưu điểm thành công", Toast.LENGTH_LONG).show();

                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(TestDoneActivity.this, "SAVE ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void renderResult() {
        for (Question item : listQuestion) {
            if(item.getAnswer().equals("")) {
                numNoCheck++;
                numIncorr++;
            } else if(item.getCorrectAnswer().equals(item.getAnswer())) {
                numCorr++;
            } else {
                numIncorr++;
            }
        }

        txtNumCorr.setText(""+numCorr);
        txtNumIncorr.setText(""+numIncorr);
        txtNumNoCheck.setText(""+numNoCheck);
        numScore = numCorr*1.0 / listQuestion.size() *10;
        txtNumScore.setText(String.format("%.1f", numScore));
    }

    public ArrayList<Question> refreshListQuestion(ArrayList<Question> listQuestion) {
        for(Question item : listQuestion) {
            item.setAnswer("");
            item.setCheckedID(-1);
        }
        return listQuestion;
    }

}