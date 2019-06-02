package com.example.design1;

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitAuthClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CONSTANTS.USER_AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        Log.d("RetrofitStatus", "Retrofit client built");
    return retrofit;
    }
}
