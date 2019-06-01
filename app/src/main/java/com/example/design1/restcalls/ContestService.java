package com.example.design1.restcalls;

import com.example.design1.Pojo.DetailsOfContest;
import com.example.design1.models.CategoryDefinition;
import com.example.design1.models.ContestDefinition;
import com.example.design1.models.ContestTotal;
import com.example.design1.models.QuestionDefinition;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContestService {

    @GET("/contest/contestofuser")
    Call<ContestTotal> getTotalContest(@Query("contestId") int contestId, @Query("userId") int userToken);

    @GET("/contest/getquestionofcontest")
    Call<List<QuestionDefinition>> getContestResult(@Query("contestId") int contestId, @Query("userId") int userToken, @Query("username") String username);

    @GET("/contest/getallcategory")
    Call<List<CategoryDefinition>> getCategories();

    @GET("/contest/getbycategory/{category}")
    Call<List<ContestDefinition>> getContestsByCategory(@Path("category") String category);

    @GET("/contest/getbytype/{type}")
    Call<List<ContestDefinition>> getContestsByType(@Path("type") String type);


}
