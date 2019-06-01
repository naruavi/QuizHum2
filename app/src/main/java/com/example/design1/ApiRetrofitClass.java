package com.example.design1;

import android.util.Log;

import com.example.design1.Pojo.ContestObject;
import com.example.design1.Pojo.DetailsOfContest;
import com.example.design1.models.ContestDefinition;
import com.example.design1.models.ContestTotal;
import com.example.design1.models.LeaderBoardListItem;
import com.example.design1.restcalls.ApiResponse;
import com.example.design1.restcalls.ContestService;
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

    public List<DetailsOfContest> getIncompleteContests(int userToken){
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

    public void getContestById(int contestId, int userId){
        String CONTEST_BY_ID = "/contest/contests/contestbyid";
        //response, contest details and the list of questions of a particular contest
        Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);
        ContestService contestService=retrofit.create(ContestService.class);
        contestService.getTotalContest(contestId,userId)
                .enqueue(new Callback<ContestTotal>() {
                    @Override
                    public void onResponse(Call<ContestTotal> call, Response<ContestTotal> response) {

                    }

                    @Override
                    public void onFailure(Call<ContestTotal> call, Throwable t) {

                    }
                });

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
    public void submitContest(int userToken,int contestId, String userName){
        String SUBMIT_CONTEST_URL = "/contest/userresult";

        Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);
        UserResponseService responseService=retrofit.create(UserResponseService.class);

        responseService.getContestResult(contestId,userToken,userName)
                .enqueue(new Callback<ScoreCard>() {
                    @Override
                    public void onResponse(Call<ScoreCard> call, Response<ScoreCard> response) {

                    }

                    @Override
                    public void onFailure(Call<ScoreCard> call, Throwable t) {

                    }
                });

    }

    //TODO implement leaderboard api calls
    public void getDynamicLeaderboard(int contestId, int userToken, int length){
        //list of user score rank for leaderboard
        String DYNAMIC_LEADERBOARD_URL = "/leaderboard/dynamic";

        Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.LEADER_BOARD_URL);
        LeaderBoardService leaderBoardService=retrofit.create(LeaderBoardService.class);

        leaderBoardService.getLeaderBoardDynamicContest(userToken,contestId,length)
                .enqueue(new Callback<ApiResponse<List<LeaderBoardListItem>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<LeaderBoardListItem>>> call, Response<ApiResponse<List<LeaderBoardListItem>>> response) {

                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<LeaderBoardListItem>>> call, Throwable t) {

                    }
                });
    }
   /* @GET("/leaderboard/dynamic")
    Call<ApiResponse<List<LeaderBoardListItem>>> getLeaderBoardDynamicContest(@Query("userId") String userToken, @Query("contestId") String contestId, @Query("noOfRecords") int noOfRecords);
*/

    public void getStaticLeaderboard(int contestId,int userToken, int length){
        //list of user score rank for leaderboard
        String CATEGORIES_URL = "/leaderboard/static";

        Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.LEADER_BOARD_URL);
        LeaderBoardService leaderBoardService=retrofit.create(LeaderBoardService.class);

        leaderBoardService.getLeaderBoardStaticContest(userToken,contestId,length)
                .enqueue(new Callback<ApiResponse<List<LeaderBoardListItem>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<LeaderBoardListItem>>> call, Response<ApiResponse<List<LeaderBoardListItem>>> response) {

                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<LeaderBoardListItem>>> call, Throwable t) {

                    }
                });
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
