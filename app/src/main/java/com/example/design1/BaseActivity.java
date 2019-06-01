package com.example.design1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.design1.activity.LeaderboardActivity;

public class BaseActivity extends AppCompatActivity {

    public void goToLeaderBoard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }
}
