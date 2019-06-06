package com.example.design1.activity;

import android.os.Bundle;
;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.design1.ApiRetrofitClass;
import com.example.design1.CONSTANTS;
import com.example.design1.R;
import com.example.design1.adapter.RecyclerAdapterForLeaderboard;
import com.example.design1.models.LeaderBoardListItem;
import com.example.design1.restcalls.ApiResponse;
import com.example.design1.restcalls.LeaderBoardService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DailyLeaderBoard extends Fragment {

    RecyclerView dailyLeaderboardRecyclerView;
    RecyclerAdapterForLeaderboard recyclerAdapterForLeaderboard;
    List<LeaderBoardListItem> leaderBoardListItemArrayList = new ArrayList<>();
    View handlerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dailyLeaderboard = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        recyclerAdapterForLeaderboard = new RecyclerAdapterForLeaderboard(leaderBoardListItemArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());



        dailyLeaderboardRecyclerView = dailyLeaderboard.findViewById(R.id.leaderboard_recycler_view);
        dailyLeaderboardRecyclerView.setLayoutManager(layoutManager);
        dailyLeaderboardRecyclerView.setAdapter(recyclerAdapterForLeaderboard);
        getDailyLeaderboard();

        return dailyLeaderboard;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        handlerLayout = getView().findViewById(R.id.fragement_leaderboard_empty_handler);
        handlerLayout.setVisibility(View.VISIBLE);
        handlerLayout.findViewById(R.id.handling_empty_layouts_progress_bar).setVisibility(View.VISIBLE);

        super.onViewCreated(view, savedInstanceState);

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

                            leaderBoardListItemArrayList.addAll(response.body().getData());
                            recyclerAdapterForLeaderboard.notifyDataSetChanged();
                            handlerLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<LeaderBoardListItem>>> call, Throwable t) {
                        FragmentActivity fragmentActivity = getActivity();
                        if(fragmentActivity != null)  {
                            Toast.makeText(fragmentActivity.getApplicationContext(),"Server Response Failed - get leaderboard daily", Toast.LENGTH_LONG).show();
                            Log.e("Response LeaderBoard","Failure response");
                        }
                    }
                });

    }
}
