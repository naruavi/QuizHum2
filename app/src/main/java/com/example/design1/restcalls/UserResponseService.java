package com.example.design1.restcalls;

import com.example.design1.models.ContestDefinition;
import com.example.design1.ScoreCard;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


//TODO after gateway change the query --> userId to userToken && remove username applied anywhere
public interface UserResponseService {


    @GET("/usercontest/useractive")
    Call<List<ContestDefinition>> getIncompletedContests(@Query("userId") String userToken);

    @GET("/usercontest/userresult")
    Call<ScoreCard> getContestResult(@Query("contestId") String contestId, @Query("userId") String userToken, @Query("username") String username);

    @POST("/response/user")
    Call<String> newResponseToQuestion(@Body RequestBody requestBody);

    @PUT("/response/user")
    Call<String> updateResponseOfSkipped(@Body RequestBody requestBody);


}
