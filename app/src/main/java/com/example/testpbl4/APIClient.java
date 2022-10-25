package com.example.testpbl4;

import com.example.testpbl4.Constant.Constant;
import com.example.testpbl4.Service.ProvinceService;
import com.example.testpbl4.Service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClient {
    public static Retrofit getRetrofi() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static UserService getUserService() {
        UserService userService = getRetrofi().create(UserService.class);
        return userService;
    }

    public static ProvinceService getProvinceService() {
        ProvinceService provinceService = getRetrofi().create(ProvinceService.class);
        return provinceService;
    }
}

