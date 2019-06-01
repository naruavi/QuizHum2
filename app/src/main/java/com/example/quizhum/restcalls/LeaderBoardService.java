package com.example.quizhum.restcalls;

import com.example.quizhum.models.LeaderBoardListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LeaderBoardService {

    @GET("/leaderboard/daily")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardDaily();

    @GET("/leaderboard/weekly")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardWeekly();

    @GET("/leaderboard/monthly")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardMonthly();

}
