package com.example.testpbl4.Service;

import com.example.testpbl4.Payload.SubjectRespone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SubjectService {
    @GET("/api/subject/getAllSubject")
    Call<ArrayList<SubjectRespone>> getAllSubject(@Header("Authorization") String token);
}
