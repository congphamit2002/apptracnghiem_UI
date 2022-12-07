package com.example.testpbl4.Service;

import com.example.testpbl4.Payload.Provinces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProvinceService {
    @GET("/api/province/getAllProvince")
    Call<List<Provinces>> getAllProvinces();
}
