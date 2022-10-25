package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testpbl4.Payload.LoginRequest;
import com.example.testpbl4.Payload.LoginRespone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static LoginRespone userLogin;
    EditText txtUsername, txtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GUI();

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPassword.getText().toString().equals("")
                || txtUsername.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đủ tài khoản, mật khẩu", Toast.LENGTH_LONG).show();
                } else {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsername(txtUsername.getText().toString());
                    loginRequest.setPassword(txtPassword.getText().toString());

                    loginHandle(loginRequest);
                }
            }
        });
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

    private void loginHandle(LoginRequest loginRequest) {
        Call<LoginRespone> loginResponeCall = APIClient.getUserService().loginUser(loginRequest);
        loginResponeCall.enqueue(new Callback<LoginRespone>() {
            @Override
            public void onResponse(Call<LoginRespone> call, Response<LoginRespone> response) {
                if(response.isSuccessful()) {
                     userLogin = response.body();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else  {
                    String message = "Đăng nhập thất bại, vui lòng thử lại";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRespone> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


}