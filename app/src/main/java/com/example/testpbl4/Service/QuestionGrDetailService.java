package com.example.testpbl4.Service;

import com.example.testpbl4.Payload.QuestionGrDetailRespone;
import com.example.testpbl4.Payload.QuestionGroupRespone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionGrDetailService {
    @GET("/api/qGrDetail/getQGrDetailByQGrId/{id}")
    Call<ArrayList<QuestionGrDetailRespone>> getQGrDetailByQGrId(@Path("id") int id);
}
