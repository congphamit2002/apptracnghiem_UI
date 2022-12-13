package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.testpbl4.Payload.ShareData;

public class ForgotPasswordActivity extends AppCompatActivity {


    private Toolbar toolbar;

    private SharedPreferences preferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        preferences = getSharedPreferences("accountLogin", MODE_PRIVATE);
        token = ShareData.userLogin.getToken();
        actionToolbar();
    }

    private void actionToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
}