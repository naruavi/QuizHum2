package com.example.design1.restcalls;

import com.example.design1.Pojo.SignupUserData;
import com.example.design1.models.HttpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiLoginSignUp {


    @FormUrlEncoded
    @POST("/login")
    Call<HttpResponse> userLogin(@Field("username") String userName, @Field("password") String password);


    @POST("/signup")
    Call<Object> userSignUp(@Body SignupUserData userData);

}
