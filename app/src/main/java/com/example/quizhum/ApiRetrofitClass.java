package com.example.quizhum;

import android.util.ArrayMap;
import android.util.Log;

import com.example.quizhum.Pojo.ContestObject;
import com.example.quizhum.Pojo.DetailsOfContest;
import com.example.quizhum.models.ContestDefinition;
import com.example.quizhum.models.LeaderBoardListItem;
import com.example.quizhum.restcalls.ApiResponse;
import com.example.quizhum.restcalls.ContestService;
import com.example.quizhum.restcalls.LeaderBoardService;
import com.example.quizhum.restcalls.UserResponseService;

import org.json.JSONObject;

import java.lang.invoke.ConstantCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void getContestsByCategrory(String category){

        String CATEGORY_CONTEST_URL = "/contest/getbycategory";
        Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);

        ContestService contestService=retrofit.create(ContestService.class);

        contestService.getContestsByCategory(category)
                .enqueue(new Callback<List<ContestDefinition>>() {
                    @Override
                    public void onResponse(Call<List<ContestDefinition>> call, Response<List<ContestDefinition>> response) {

                    }

                    @Override
                    public void onFailure(Call<List<ContestDefinition>> call, Throwable t) {

                    }
                });
    }

    public List<DetailsOfContest> getIncompleteContests(String userToken){
        String INCOMPLETE_CONTESTS_URL = "/contest/useractive";

        Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);
        UserResponseService userResponseService=retrofit.create(UserResponseService.class);
        userResponseService.getIncompletedContests(userToken)
                .enqueue(new Callback<List<ContestDefinition>>() {
                    @Override
                    public void onResponse(Call<List<ContestDefinition>> call, Response<List<ContestDefinition>> response) {

                    }

                    @Override
                    public void onFailure(Call<List<ContestDefinition>> call, Throwable t) {

                    }
                });
        return null;
    }

    public void getContestById(String contestId, String userId){
        String CONTEST_BY_ID = "/contest/contests/contestbyid";
        //response, contest details and the list of questions of a particular contest
        Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);
        ContestService contestService=retrofit.create(ContestService.class);

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

        userResponseService.newResponseToQuestion(body)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //TODO what is the response and
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

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
