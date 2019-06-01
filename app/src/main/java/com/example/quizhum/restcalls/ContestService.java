package com.example.quizhum.restcalls;

import com.example.quizhum.Pojo.DetailsOfContest;
import com.example.quizhum.Pojo.Question;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ContestService {

    @GET("/contest/contestofuser")
    Call<ApiResponse<List<DetailsOfContest>>> getIncompletedContests(@Query("contestId") int contestId,@Query("userId") int userToken);

    @GET("/contest/getquestionofcontest")
    Call<List<Question>> getContestResult(@Query("contestId") String contestId, @Query("userId") String userToken, @Query("username") String username);




    @POST("/contest/user")
    Call<ApiResponse<String>> newResponseToQuestion(@Body RequestBody requestBody);

}
