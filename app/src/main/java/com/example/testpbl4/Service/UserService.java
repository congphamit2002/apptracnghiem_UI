package com.example.testpbl4.Service;

import com.example.testpbl4.Payload.ChangePasswordRequest;
import com.example.testpbl4.Payload.LoginRequest;
import com.example.testpbl4.Payload.LoginRespone;
import com.example.testpbl4.Payload.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("/api/account/changePassword")
    Call<String> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST("/api/account/insertAccount")
    Call<String> insertUser(@Body RegisterRequest registerRequest);

    @POST("/api/account/login")
    Call<LoginRespone> loginUser(@Body LoginRequest loginRequest);
}
