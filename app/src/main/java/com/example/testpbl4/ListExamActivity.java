package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.testpbl4.Adapter.ListExamAdapter;
import com.example.testpbl4.Payload.QuestionGrDetailRespone;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListExamActivity extends AppCompatActivity {

    private RecyclerView rlcListExam;
    ArrayList<QuestionGrDetailRespone> listQGrDetail;
    ListExamAdapter questionGRDAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exam);

        rlcListExam = findViewById(R.id.rlcListExam);

        int questionGrID = getIntent().getExtras().getInt("questionGrID");
        getQRDByID(questionGrID);
    }

    public void getQRDByID(int questionGrID) {
        Call<ArrayList<QuestionGrDetailRespone>> getQGrDetaialResponeCall = APIClient.getQRDService().getQGrDetailByQGrId(questionGrID);
        getQGrDetaialResponeCall.enqueue(new Callback<ArrayList<QuestionGrDetailRespone>>() {
            @Override
            public void onResponse(Call<ArrayList<QuestionGrDetailRespone>> call, Response<ArrayList<QuestionGrDetailRespone>> response) {
                if (response.isSuccessful()) {
                    listQGrDetail = response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListExamActivity.this, LinearLayoutManager.VERTICAL, false);
                    rlcListExam.setLayoutManager(linearLayoutManager);
                    questionGRDAdapter = new ListExamAdapter( listQGrDetail, ListExamActivity.this);
                    rlcListExam.setAdapter(questionGRDAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuestionGrDetailRespone>> call, Throwable t) {
                Toast.makeText(ListExamActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }
}