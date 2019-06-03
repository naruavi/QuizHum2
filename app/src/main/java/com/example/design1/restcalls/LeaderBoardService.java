package com.example.design1.restcalls;

import com.example.design1.models.LeaderBoardListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LeaderBoardService {

    @GET("/leaderboard/daily")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardDaily();

    @GET("/leaderboard/weekly")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardWeekly();

    @GET("/leaderboard/monthly")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardMonthly();

    @GET("/leaderboard/contest")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardByContest(@Query("contestId") int contestId);


    @GET("/leaderboard/static")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardStaticContest(@Query("userId") int userToken, @Query("contestId") int contestId,@Query("noOfRecords") int noOfRecords);

    @GET("/leaderboard/dynamic")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardDynamicContest(@Query("userId") int userToken, @Query("contestId") int contestId,@Query("noOfRecords") int noOfRecords);

}
