package com.example.testpbl4.Service;

import com.example.testpbl4.Payload.HistoryTestRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HistoryTestService {

    @POST("/api/historyTest/save")
    Call<String> saveHistoryTest(@Body HistoryTestRequest historyTestRequest, @Header("Authorization") String token);
}
