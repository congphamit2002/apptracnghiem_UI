package com.example.testpbl4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testpbl4.Adapter.MenuNavAdapter;
import com.example.testpbl4.Adapter.SubjectAdapter;
import com.example.testpbl4.Payload.ShareData;
import com.example.testpbl4.Payload.SubjectRespone;
import com.example.testpbl4.Service.SubjectService;
import com.example.testpbl4.model.ItemMenu;
import com.google.android.material.navigation.NavigationView;

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

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rlcSubject = findViewById(R.id.rlcSubject);
        nameAccount = findViewById(R.id.nameAccount);
        nameAccount.setText(ShareData.userLogin.getUsername());

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        listView = (ListView) findViewById(R.id.listView);

        getAllSubject();
        actionToolbar();
        actionMenuNav();
    }

    private void getAllSubject() {
        Call<ArrayList<SubjectRespone>> getSubjectCall = APIClient.getSubjectService().getAllSubject();
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

    private void actionToolbar(){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void actionMenuNav(){
        // set item
        List<ItemMenu> listItemMenu = new ArrayList<>();
        listItemMenu.add(new ItemMenu("Quản lý thông tin cá nhân", R.drawable.ic_baseline_person_24));
        listItemMenu.add(new ItemMenu("Lịch sử làm bài", R.drawable.ic_baseline_history_24));
        listItemMenu.add(new ItemMenu("Đổi mật khẩu", R.drawable.ic_baseline_lock_24));
        listItemMenu.add(new ItemMenu("Đăng xuất", R.drawable.ic_baseline_logout_24));

        MenuNavAdapter adapter = new MenuNavAdapter(this, listItemMenu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                    {
                        Intent intent = new Intent(HomeActivity.this, ProfileDetailActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:
                    {
                        Intent intent = new Intent(HomeActivity.this, HistoryTestActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2:
                    {
                        Intent intent = new Intent(HomeActivity.this, ChangePasswordActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3:
                    {
                        // destroy session here
                        //

                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }
}