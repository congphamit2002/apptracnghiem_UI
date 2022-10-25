package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testpbl4.Payload.ChangePasswordRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText txtOldPassword, txtNewPassword,txtCFNewPassword;
    Button btnChangePW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        txtOldPassword = findViewById(R.id.txtOldPassword);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtCFNewPassword = findViewById(R.id.txtCFNewPassword);
        btnChangePW = findViewById(R.id.btnChangePW);

        btnChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtOldPassword.getText().toString().equals("")
                || txtNewPassword.getText().toString().equals("")
                || txtCFNewPassword.getText().toString().equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập đủ thông tin!!", Toast.LENGTH_LONG).show();
                } else if (txtNewPassword.getText().toString().equals(txtCFNewPassword.getText().toString()) == false) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu xác nhận không chính xác!!", Toast.LENGTH_LONG).show();
                } else {
                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
                    changePasswordRequest.setId(1);
                    changePasswordRequest.setNewPassword(txtNewPassword.getText().toString());
                    changePasswordRequest.setOldPassword(txtOldPassword.getText().toString());
                    changePassword(changePasswordRequest);
                }
            }

        });
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Call<String> changePasswordCall = APIClient.getUserService().changePassword(changePasswordRequest);
        changePasswordCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String message = "Mật khẩu đã được thay đổi thành công!!";
                    Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                } else {
                    String message = "Mật khẩu cũ không chính xác";
                    Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}