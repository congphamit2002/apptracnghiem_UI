package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testpbl4.Adapter.QuestionGrDetailAdapter;
import com.example.testpbl4.Constant.Constant;
import com.example.testpbl4.Payload.QuestionGrDetailRespone;
import com.example.testpbl4.model.Question;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionGrDetailActivity extends AppCompatActivity  {

    private RecyclerView rlcListExam;
    private ArrayList<QuestionGrDetailRespone> listQGrDetail = new ArrayList<>();
    private QuestionGrDetailAdapter lisQuestionGrDetailAdapter;
    private ArrayList<Question> listQuestion = new ArrayList<>();
    private TextView txtKiemTra;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_gr_detail);

        rlcListExam = findViewById(R.id.rlcListExam);
        toolbar = findViewById(R.id.toolBar);

        int questionGrID = getIntent().getExtras().getInt("questionGrID");
        getQRDByID(questionGrID);
        actionToolbar();
    }

    public void getQRDByID(int questionGrID) {
        Call<ArrayList<QuestionGrDetailRespone>> getQGrDetaialResponeCall = APIClient.getQRDService().getQGrDetailByQGrId(questionGrID);
        getQGrDetaialResponeCall.enqueue(new Callback<ArrayList<QuestionGrDetailRespone>>() {
            @Override
            public void onResponse(Call<ArrayList<QuestionGrDetailRespone>> call, Response<ArrayList<QuestionGrDetailRespone>> response) {
                if (response.isSuccessful()) {
                    listQGrDetail = response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuestionGrDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                    rlcListExam.setLayoutManager(linearLayoutManager);
                    lisQuestionGrDetailAdapter = new QuestionGrDetailAdapter( listQGrDetail, QuestionGrDetailActivity.this);

                    lisQuestionGrDetailAdapter.setOnItemClickListener(new QuestionGrDetailAdapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            getQuestionByGrDetailId(listQGrDetail.get(position).getId());
                        }

                        @Override
                        public void onItemLongClick(int position, View v) {

                        }
                    });

                    rlcListExam.setAdapter(lisQuestionGrDetailAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuestionGrDetailRespone>> call, Throwable t) {
                Toast.makeText(QuestionGrDetailActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getQuestionByGrDetailId(int questionGrDetailId) {

        Call<ArrayList<Question>> getQuesitonCall = APIClient.getQuetionService().getQuestionsByGrDetailId(questionGrDetailId);
        getQuesitonCall.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(QuestionGrDetailActivity.this, ScreenSlidePagerActivity.class);
                    listQuestion = response.body();
                    Log.e("\t\tList size  " , "" +listQuestion.size());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("listQuestion",(Serializable) listQuestion);
                    bundle.putInt(Constant.ARG_QUESTION_GR_DETAIL_ID, questionGrDetailId);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                Toast.makeText(QuestionGrDetailActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void actionToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
}