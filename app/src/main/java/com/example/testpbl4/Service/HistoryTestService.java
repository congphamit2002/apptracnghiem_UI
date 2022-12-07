package com.example.testpbl4.Service;

import com.example.testpbl4.Payload.HistoryTestRequest;
import com.example.testpbl4.Payload.HistoryTestRespone;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HistoryTestService {

    @POST("/api/historyTest/save")
    Call<String> saveHistoryTest(@Body HistoryTestRequest historyTestRequest, @Header("Authorization") String token);

    @GET("/api/historyTest/{accountID}")
    Call<ArrayList<Map<String, String>>> getHistoryTestByUserID(@Path("accountID") int accountID, @Header("Authorization") String token);

    @GET("/api/historyTest/delete/{id}")
    Call<String> deleteHistoryTest(@Path("id") int id, @Header("Authorization") String token);
}
