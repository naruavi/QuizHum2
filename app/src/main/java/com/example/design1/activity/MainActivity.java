package com.example.design1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.design1.ApiRetrofitClass;
import com.example.design1.BaseActivity;
import com.example.design1.CONSTANTS;
import com.example.design1.R;
import com.example.design1.adapter.RecyclerAdapterForHome;
import com.example.design1.models.CategoryDefinition;
import com.example.design1.models.ContestDefinition;
import com.example.design1.restcalls.ContestService;
import com.example.design1.restcalls.UserResponseService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Scene scene1, scene2,scene3;
    private Transition transition;
    private boolean start1, start2;
    private Button incompleteContests;
    //private int userToken;
    List<CategoryDefinition> categoryList = new ArrayList<>();

    public static final String EXTRA_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        incompleteContests=findViewById(R.id.incomplete_contests);
        incompleteContests.setOnClickListener(this);


        final RecyclerView recyclerView = findViewById(R.id.rec1);
        final RecyclerAdapterForHome recyclerAdapterForHome = new RecyclerAdapterForHome(categoryList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recyclerAdapterForHome);

        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);
        ContestService contestService=retrofit.create(ContestService.class);
        contestService.getCategories()
                .enqueue(new Callback<List<CategoryDefinition>>() {
                    @Override
                    public void onResponse(Call<List<CategoryDefinition>> call, Response<List<CategoryDefinition>> response) {
                        if(response.body()!=null){
                            Log.e("In get all categories", response.body().toString());

                            categoryList.addAll(response.body());
                            recyclerAdapterForHome.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<CategoryDefinition>> call, Throwable t) {
                        Log.e("In get all catoegories", "failed"+t.getMessage());

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.incomplete_contests){
           /* Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);
            UserResponseService userResponseService=retrofit.create(UserResponseService.class);

            userResponseService.getIncompletedContests(4)
                    .enqueue(new Callback<List<ContestDefinition>>() {
                        @Override
                        public void onResponse(Call<List<ContestDefinition>> call, Response<List<ContestDefinition>> response) {
                            if(response.body()!=null) {
                                Log.e("in incomple contest", response.body().toString());
                                //categoryList.addAll(response.body());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ContestDefinition>> call, Throwable t) {
                            Log.e("in complete contest", "failure");
                        }
                    });*/
        }
    }

    public void seeContests(View view) {
        Intent intent = new Intent(this, ContestActivity.class);
        startActivity(intent);
    }

    public void goToLeaderBoard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }
}