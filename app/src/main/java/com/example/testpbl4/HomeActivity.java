package com.example.testpbl4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testpbl4.Adapter.SubjectAdapter;
import com.example.testpbl4.Payload.ShareData;
import com.example.testpbl4.Payload.SubjectRespone;
import com.example.testpbl4.Service.SubjectService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rlcSubject;
    ArrayList<SubjectRespone> listSubjects = new ArrayList<>();
    TextView nameAccount;
    SubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rlcSubject = findViewById(R.id.rlcSubject);
        nameAccount = findViewById(R.id.nameAccount);
        nameAccount.setText(ShareData.userLogin.getUsername());

        getAllSubject();



    }

    private void getAllSubject() {
        Log.e("\t\tToken: ", ShareData.userLogin.getToken());
        Call<ArrayList<SubjectRespone>> getSubjectCall = APIClient.getSubjectService().getAllSubject("Bearer " +ShareData.userLogin.getToken());
        getSubjectCall.enqueue(new Callback<ArrayList<SubjectRespone>>() {
            @Override
            public void onResponse(Call<ArrayList<SubjectRespone>> call, Response<ArrayList<SubjectRespone>> response) {
                if(response.isSuccessful()) {
                    listSubjects = response.body();
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(HomeActivity.this, 3);
                    rlcSubject.setLayoutManager(linearLayoutManager);
                     subjectAdapter = new SubjectAdapter( listSubjects, HomeActivity.this);

                    subjectAdapter.setOnItemClickListener(new SubjectAdapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            Intent sendActivity = new Intent(HomeActivity.this, SubjectCategoryActivity.class);
                            sendActivity.putExtra("subjectID", listSubjects.get(position).getId());
                            startActivity(sendActivity);
                        }

                        @Override
                        public void onItemLongClick(int position, View v) {
                            Toast.makeText(HomeActivity.this, "on click " + listSubjects.get(position).getId(), Toast.LENGTH_LONG).show();
                        }
                    });

                    rlcSubject.setAdapter(subjectAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SubjectRespone>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }
}