package com.example.testpbl4.Service;

import com.example.testpbl4.model.Question;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionService {

    @GET("/api/questions/questionDetailID/{id}")
    Call<ArrayList<Question>> getQuestionsByGrDetailId(@Path("id") int id);
}
