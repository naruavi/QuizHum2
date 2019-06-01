package com.example.design1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.Transition;
import android.view.View;

import com.example.design1.BaseActivity;
import com.example.design1.R;
import com.example.design1.adapter.RecyclerAdapterForHome;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Scene scene1, scene2,scene3;
    private Transition transition;
    private boolean start1, start2;

    public static final String EXTRA_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rec1);
        List<String> l1 = new ArrayList<>();

        for(int i=0;i<10;i++) {
            l1.add("Category"+i);
        }

        RecyclerAdapterForHome recyclerAdapterForHome = new RecyclerAdapterForHome(l1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recyclerAdapterForHome);
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
