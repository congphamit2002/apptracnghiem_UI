package com.example.testpbl4.Constant;

import android.content.SharedPreferences;

import com.example.testpbl4.Payload.ShareData;

public class Constant {
    public static final String BASE_URL = "http://192.168.1.147:8080";
    public static final String ARG_PAGE = "page";
    public static final String ARG_CHECK = "check";
    public static final String ARG_QUESTION_GR_DETAIL_ID = "questionGrDetailId";
    public static final String ARG_REFRESH = "refresh";

    SharedPreferences preferences;

    public void clearStorage(SharedPreferences preferences) {
        ShareData.userLogin = null;
         preferences.edit().clear().commit();
    }
}
