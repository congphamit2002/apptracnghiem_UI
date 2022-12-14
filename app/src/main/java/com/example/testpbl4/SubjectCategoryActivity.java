package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.testpbl4.Adapter.QuestionGroupAdapter;
import com.example.testpbl4.Payload.QuestionGroupRespone;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectCategoryActivity extends AppCompatActivity {

    private RecyclerView rlcQGr;
    ArrayList<QuestionGroupRespone> listQGr;
    QuestionGroupAdapter questionGRAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_category);

        rlcQGr = findViewById(R.id.rlcQGrs);

        int subjectID = getIntent().getExtras().getInt("subjectID");
        getQRByID(subjectID);
    }

    public void getQRByID(int subjectID) {
        Call<ArrayList<QuestionGroupRespone>> getSubjectResponeCall = APIClient.getQRService().getQGBySubjectId(subjectID);
        getSubjectResponeCall.enqueue(new Callback<ArrayList<QuestionGroupRespone>>() {
            @Override
            public void onResponse(Call<ArrayList<QuestionGroupRespone>> call, Response<ArrayList<QuestionGroupRespone>> response) {
                if (response.isSuccessful()) {
                    listQGr = response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SubjectCategoryActivity.this, LinearLayoutManager.VERTICAL, false);
                    rlcQGr.setLayoutManager(linearLayoutManager);
                    questionGRAdapter = new QuestionGroupAdapter( listQGr, SubjectCategoryActivity.this);

                    questionGRAdapter.setOnItemClickListener(new QuestionGroupAdapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            int questionGrID = listQGr.get(position).getId();
                            Intent intent = new Intent(SubjectCategoryActivity.this, QuestionGrDetailActivity.class);
                            intent.putExtra("questionGrID", questionGrID);
                            startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(int position, View v) {

                        }
                    });
                    rlcQGr.setAdapter(questionGRAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuestionGroupRespone>> call, Throwable t) {
                Toast.makeText(SubjectCategoryActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }
}