package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GUI();
    }

    private void GUI(){

        // set event click register
        TextView textViewRegister = findViewById(R.id.textViewCreateAccount2);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

        // set event click forgot password
        TextView textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForgotPass = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intentForgotPass);
            }
        });


    }
}