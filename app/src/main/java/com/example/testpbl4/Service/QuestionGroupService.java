package com.example.testpbl4.Service;

import com.example.testpbl4.Payload.QuestionGroupRespone;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface QuestionGroupService {

    @GET("/api/questionGroups/getAllQGBySubjectId/{id}")
    Call<ArrayList<QuestionGroupRespone>> getQGBySubjectId(@Path("id") int id, @Header("Authorization") String token);
}
