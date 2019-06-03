package com.example.design1.restcalls;

import com.example.design1.Pojo.SubmittedResponseAck;
import com.example.design1.models.ContestDefinition;
import com.example.design1.ScoreCard;
import com.example.design1.models.NewResponse;
import com.example.design1.models.ScoreCardObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


//TODO after gateway change the query --> userId to userToken && remove username applied anywhere
public interface UserResponseService {


    @GET("/usercontest/useractive")
    Call<List<ContestDefinition>> getIncompletedContests(@Header("cookie") String cookie);

    @POST("/usercontest/userresult")
    Call<ScoreCardObject> getContestResult(@Body RequestBody requestBody,@Header("cookie") String cookie);

    @POST("/response/user")
    Call<SubmittedResponseAck> newResponseToQuestion(@Body RequestBody requestBody, @Header("cookie") String cookie);

    @PUT("/response/user")
    Call<SubmittedResponseAck> updateResponseOfSkipped(@Body RequestBody requestBody, @Header("cookie") String cookie);

}
