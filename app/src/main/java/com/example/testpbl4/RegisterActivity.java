package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testpbl4.Payload.ChangePasswordRequest;
import com.example.testpbl4.Payload.Provinces;
import com.example.testpbl4.Payload.RegisterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword ,txtCfPassword, txtFullName, txtEmail, txtPhone;
    RadioButton rbtnMale, rbtnFemale;
    Spinner spinnerProvince;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtCfPassword = findViewById(R.id.txtCfPassword);
        txtFullName = findViewById(R.id.txtFullName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        rbtnMale = findViewById(R.id.rbtnMale);
        rbtnFemale = findViewById(R.id.rbtnFemale);
        btnRegister = findViewById(R.id.btnRegister);
        spinnerProvince = findViewById(R.id.spinnerProvince);

        renderProvince();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsername.getText().toString().equals("")
                        || txtPassword.getText().toString().equals("")
                        || txtCfPassword.getText().toString().equals("")
                        || txtFullName.getText().toString().equals("")
                        || txtPhone.getText().toString().equals("")
                        || txtEmail.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đủ thông tin!!", Toast.LENGTH_LONG).show();
                } else if (txtPassword.getText().toString().equals(txtCfPassword.getText().toString()) == false) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không chính xác!!", Toast.LENGTH_LONG).show();
                } else {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setUsername(txtUsername.getText().toString());
                    registerRequest.setPassword(txtPassword.getText().toString());
                    registerRequest.setFullname(txtFullName.getText().toString());
                    registerRequest.setPhone(txtPhone.getText().toString());
                    registerRequest.setEmail(txtEmail.getText().toString());
                    registerRequest.setGender(rbtnMale.isChecked() ? 1 : 0);
                    registerRequest.setProvince((Provinces) spinnerProvince.getSelectedItem());

                    registerUser(registerRequest);
                }
            }

        });
    }

    public void renderProvince() {
        Call<List<Provinces>> provincesCall = APIClient.getProvinceService().getAllProvinces();
        provincesCall.enqueue(new Callback<List<Provinces>>() {
            @Override
            public void onResponse(Call<List<Provinces>> call, Response<List<Provinces>> response) {
                if(response.isSuccessful()) {
                    List<Provinces> listData = response.body();
                    Provinces[] arr = listData.toArray(new Provinces[0]);
                    ArrayAdapter<Provinces> adapter = new ArrayAdapter<Provinces>(RegisterActivity.this,
                            android.R.layout.simple_spinner_item, arr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProvince.setAdapter(adapter);
                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi hiển thị T/TP", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Provinces>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void registerUser(RegisterRequest registerRequest) {
        Call<String> insertUserCall = APIClient.getUserService().insertUser(registerRequest);
        insertUserCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "Không thể đăng ký tài khoản", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}