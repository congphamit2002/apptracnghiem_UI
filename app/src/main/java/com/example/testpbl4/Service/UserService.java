package com.example.testpbl4.Service;

import com.example.testpbl4.Payload.AccountUpRequest;
import com.example.testpbl4.Payload.AccountUpRespone;
import com.example.testpbl4.Payload.ChangePasswordRequest;
import com.example.testpbl4.Payload.LoginRequest;
import com.example.testpbl4.Payload.LoginRespone;
import com.example.testpbl4.Payload.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("/api/account/changePassword")
    Call<String> changePassword(@Body ChangePasswordRequest changePasswordRequest, @Header("Authorization") String token);

    @POST("/api/account/insertAccount")
    Call<String> insertUser(@Body RegisterRequest registerRequest, @Header("Authorization") String token);

    @POST("/api/v1/login")
    Call<LoginRespone> loginUser(@Body LoginRequest loginRequest);

    @GET("/api/account/update/{id}")
    Call<AccountUpRespone> getAccountUpResp(@Path("id") int id, @Header("Authorization") String token);

    @POST("/api/account/updateAccount")
    Call<String> updateAccount(@Body AccountUpRequest accountUpRequest, @Header(("Authorization")) String token);
}
