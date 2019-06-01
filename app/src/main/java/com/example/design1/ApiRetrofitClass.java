package com.example.design1;

import android.util.Log;

import com.example.design1.Pojo.ContestObject;
import com.example.design1.Pojo.DetailsOfContest;
import com.example.design1.models.LeaderBoardListItem;
import com.example.design1.restcalls.ApiResponse;
import com.example.design1.restcalls.LeaderBoardService;
import com.example.design1.restcalls.UserResponseService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofitClass {
    public static Retrofit getNewRetrofit(String BASE_URL){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .build();
    }


    //TODO functionalities

    public List<String> getAllCategories(){
        String CATEGORIES_URL = "";
        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CATEGORIES_URL);


        return null;
    }

    public List<DetailsOfContest> getContestsByCategrory(String category, String userToken){
        String CATEGORY_CONTEST_URL = "No api endpoint yet";
        return null;
    }

    public List<DetailsOfContest> getIncompleteContests(String userToken){
        String INCOMPLETE_CONTESTS_URL = "/contest/useractive";
        return null;
    }

    public ContestObject getContestById(String contestId, String userId){
        String CONTEST_BY_ID = "/contest/contests/contestbyid";
        //response, contest details and the list of questions of a particular contest
        return null;
    }

    public String submitResponseSingleQuestion(String questionId,String contestId,String userToken,String response){
        String RESPONSE_URL = "/response/user";

        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);

        UserResponseService userResponseService = retrofit.create(UserResponseService.class);

        HashMap<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("questionId",questionId);
        jsonParams.put("contestId", contestId);
        jsonParams.put("userToken", userToken);
        jsonParams.put("response", response);

        Log.d("ApiRetrofitClass","Json Value "+jsonParams.toString());

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        /*userResponseService.newResponseToQuestion(body)
                .enqueue(new Callback<ApiResponse<String>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                        //TODO what is the response and
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<String>> call, Throwable t) {

                    }
                });*/

        return null;

    }

    //TODO after the last question is submitted successfully
    public String submitContest(String userToken,String contestId){
        String SUBMIT_CONTEST_URL = "/contest/userresult";
        //TODO response from api contains scorecard details
        return null;
    }






    //TODO implement leaderboard api calls
    public void getDynamicLeaderboard(String contestId, String userToken, int length){
        //list of user score rank for leaderboard
        String DYNAMIC_LEADERBOARD_URL = "/leaderboard/dynamic";
    }

    public void getStaticLeaderboard(String contestId,String userToken, int length){
        //list of user score rank for leaderboard
        String CATEGORIES_URL = "/leaderboard/static";
    }




    //TODO assign this model
    public void getDailyLeaderboard(){
        //list of user score rank for leaderboard

        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.LEADER_BOARD_URL);

        LeaderBoardService leaderBoardService = retrofit.create(LeaderBoardService.class);

        leaderBoardService.getLeaderBoardDaily()
                .enqueue(new Callback<ApiResponse<List<LeaderBoardListItem>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<LeaderBoardListItem>>> call, Response<ApiResponse<List<LeaderBoardListItem>>> response) {
                        if (response != null){
                            Log.e("Response LeaderBoard", response.body().getMessage());
                            Log.e("Response LeaderBoard", response.body().getData().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<LeaderBoardListItem>>> call, Throwable t) {
                        Log.e("Response LeaderBoard","Failure response");
                    }
                });

    }




    public void getWeeklyLeaderboard(){
        //list of user score rank for leaderboard
        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.LEADER_BOARD_URL);

        LeaderBoardService leaderBoardService = retrofit.create(LeaderBoardService.class);

        leaderBoardService.getLeaderBoardWeekly()
                .enqueue(new Callback<ApiResponse<List<LeaderBoardListItem>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<LeaderBoardListItem>>> call, Response<ApiResponse<List<LeaderBoardListItem>>> response) {
                        if (response != null){
                            Log.e("Response LeaderBoard", response.body().getMessage());
                            Log.e("Response LeaderBoard", response.body().getData().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<LeaderBoardListItem>>> call, Throwable t) {
                        Log.e("Response LeaderBoard","Failure response");
                    }
                });
    }

    public void getMonthlyLeaderboard(){
        //list of user score rank for leaderboard
        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.LEADER_BOARD_URL);

        LeaderBoardService leaderBoardService = retrofit.create(LeaderBoardService.class);

        leaderBoardService.getLeaderBoardMonthly()
                .enqueue(new Callback<ApiResponse<List<LeaderBoardListItem>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<LeaderBoardListItem>>> call, Response<ApiResponse<List<LeaderBoardListItem>>> response) {
                        if (response != null){
                            Log.e("Response LeaderBoard", response.body().getMessage());
                            Log.e("Response LeaderBoard", response.body().getData().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<LeaderBoardListItem>>> call, Throwable t) {
                        Log.e("Response LeaderBoard","Failure response");
                    }
                });
    }

    //TODO diiscuss the api contract of dynamic contest
    public ContestObject getDynamicContest(){
        return null;
    }



}
