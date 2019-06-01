package com.example.quizhum.restcalls;

import com.example.quizhum.Pojo.DetailsOfContest;

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
    Call<ApiResponse<List<DetailsOfContest>>> getIncompletedContests(@Query("userId") String userToken);

    @GET("/usercontest/useractive")
    Call<ApiResponse<List<DetailsOfContest>>> getContestResult(@Query("contestId") String contestId, @Query("userId") String userToken,@Query("username") String username);

    @POST("/response/user")
    Call<ApiResponse<String>> newResponseToQuestion(@Body RequestBody requestBody);

    @PUT("/response/user")
    Call<ApiResponse<String>> updateResponseOfSkipped(@Body RequestBody requestBody);


}
