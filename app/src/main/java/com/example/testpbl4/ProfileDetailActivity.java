package com.example.testpbl4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testpbl4.Payload.AccountUpRequest;
import com.example.testpbl4.Payload.AccountUpRespone;
import com.example.testpbl4.Payload.Provinces;
import com.example.testpbl4.Payload.ShareData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText txtFullName, txtEmail, txtPhone;
    private Spinner spinnerProvince;
    private RadioButton rdbMale, rdbFemale;
    private Button btnUpdate;
    private DatePicker datePicker;
    private SharedPreferences preferences;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        toolbar = findViewById(R.id.toolBar);

        preferences = getSharedPreferences("accountLogin", MODE_PRIVATE);
        token = ShareData.userLogin.getToken();

        actionToolbar();
        renderProvince();

        txtFullName = findViewById(R.id.txtFullName);
        datePicker = findViewById(R.id.datePicker);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        spinnerProvince = findViewById(R.id.spinnerProvince);
        rdbMale = findViewById(R.id.rbdMale);
        rdbFemale = findViewById(R.id.rbdFeMale);
        btnUpdate = findViewById(R.id.btnUpdate);

        renderUser();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountUpRequest accountUpRequest = new AccountUpRequest();
                accountUpRequest.setFullname(txtFullName.getText().toString());
                accountUpRequest.setEmail(txtEmail.getText().toString());
                accountUpRequest.setGender(rdbMale.isChecked() ? 0 : 1);
                accountUpRequest.setPhone(txtPhone.getText().toString());
                accountUpRequest.setProvince_id(spinnerProvince.getSelectedItemPosition() + 1);
                Log.e("Position " , "" + spinnerProvince.getSelectedItemPosition());
                int   day  = datePicker.getDayOfMonth();
                int   month= datePicker.getMonth();
                int   year = datePicker.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = sdf.format(calendar.getTime());
                accountUpRequest.setDate_of_birth(formatedDate);
                accountUpRequest.setId(Integer.parseInt(preferences.getString("id","")));
                Call<String> updateCall = APIClient.getUserService().updateAccount(accountUpRequest, "Bearer " + token);
                updateCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(ProfileDetailActivity.this, HomeActivity.class);
                            Toast.makeText(ProfileDetailActivity.this, "Thay đổi thông tin thành công", Toast.LENGTH_LONG).show();
                            ProfileDetailActivity.this.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    }
                });

            }
        });
    }

    private void actionToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    public void renderProvince() {
        Call<List<Provinces>> provincesCall = APIClient.getProvinceService().getAllProvinces();
        provincesCall.enqueue(new Callback<List<Provinces>>() {
            @Override
            public void onResponse(Call<List<Provinces>> call, Response<List<Provinces>> response) {
                if(response.isSuccessful()) {
                    List<Provinces> listData = response.body();
                    Provinces[] arr = listData.toArray(new Provinces[0]);
                    ArrayAdapter<Provinces> adapter = new ArrayAdapter<Provinces>(ProfileDetailActivity.this,
                            android.R.layout.simple_spinner_item, arr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProvince.setAdapter(adapter);
                } else {
                    Toast.makeText(ProfileDetailActivity.this, "Lỗi hiển thị T/TP", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Provinces>> call, Throwable t) {
                Toast.makeText(ProfileDetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void renderUser(){
        Call<AccountUpRespone> accountUpResponeCall = APIClient.getUserService().getAccountUpResp(Integer.parseInt(preferences.getString("id","")),
                "Bearer " + token
        );
        accountUpResponeCall.enqueue(new Callback<AccountUpRespone>() {
            @Override
            public void onResponse(Call<AccountUpRespone> call, Response<AccountUpRespone> response) {
                AccountUpRespone accountUpRespone = response.body();
                if(accountUpRespone != null ) {
                    txtFullName.setText(accountUpRespone.getFullname());
                    String dateApi = accountUpRespone.getDate_of_birth();
                    String[] dateSplit = dateApi.split("-", 0);
                    int year = Integer.parseInt(dateSplit[0]);
                    int month = Integer.parseInt(dateSplit[1]);
                    int day = Integer.parseInt(dateSplit[2]);
                    Log.e("\t\tYEAR ", "" + year);
                    Log.e("\t\tMONTH ", "" + month);
                    Log.e("\t\tDAY ", "" + day);
                    datePicker.init(year, month - 1 , day, null);
                    txtEmail.setText(accountUpRespone.getEmail());
                    txtPhone.setText(accountUpRespone.getPhone());
                    spinnerProvince.setSelection(accountUpRespone.getProvince_id() - 1);
                    if(accountUpRespone.getGender() == 0) {
                        rdbMale.setChecked(true);
                    } else {
                        rdbFemale.setChecked(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountUpRespone> call, Throwable t) {
                Toast.makeText(ProfileDetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}