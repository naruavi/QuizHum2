package com.example.design1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.design1.ApiRetrofitClass;
import com.example.design1.BaseActivity;
import com.example.design1.CONSTANTS;
import com.example.design1.R;
import com.example.design1.adapter.RecyclerAdapterForContest;
import com.example.design1.models.ContestDefinition;
import com.example.design1.restcalls.ContestService;
import com.example.design1.restcalls.UserResponseService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ContestActivity extends BaseActivity {
    String categoryName;
    Integer userId;
    List<ContestDefinition> contestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);

        RecyclerView recyclerView = findViewById(R.id.rec2);
        /*for(int i=1;i<=10;i++) {
            l1.add("Contest "+i);
        }*/

        final RecyclerAdapterForContest recyclerAdapterForContest = new RecyclerAdapterForContest(contestList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ContestActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapterForContest);

        categoryName = getIntent().getStringExtra("Category");
        Log.e("in contest", categoryName +"" );

        userId=getIntent().getIntExtra("userId", 0);
        Log.e("in contest userId", userId + "");

        if(categoryName!=null) {

            Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);

            ContestService contestService = retrofit.create(ContestService.class);

            contestService.getContestsByCategory(categoryName)
                    .enqueue(new Callback<List<ContestDefinition>>() {
                        @Override
                        public void onResponse(Call<List<ContestDefinition>> call, Response<List<ContestDefinition>> response) {
                            Log.e("In contest activity", response.body().toString());
                            contestList.addAll(response.body());
                            recyclerAdapterForContest.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<ContestDefinition>> call, Throwable t) {
                            Log.e("In contest activity", "failure");

                        }
                    });
        }
        else if(userId!=0){
            Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);
            UserResponseService userResponseService=retrofit.create(UserResponseService.class);

            userResponseService.getIncompletedContests(4)
                    .enqueue(new Callback<List<ContestDefinition>>() {
                        @Override
                        public void onResponse(Call<List<ContestDefinition>> call, Response<List<ContestDefinition>> response) {
                            if(response.body()!=null) {
                                Log.e("in incomplete contest", response.body().toString());
                                contestList.addAll(response.body());
                                recyclerAdapterForContest.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ContestDefinition>> call, Throwable t) {
                            Log.e("in complete contest", "failure");
                        }
                    });
        }
    }

    public void openContest(View view) {
        Intent intent = new Intent(this, PlayStaticContest.class);
        startActivity(intent);
    }
}
