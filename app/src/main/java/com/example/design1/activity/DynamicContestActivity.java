package com.example.design1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.design1.ApiRetrofitClass;
import com.example.design1.AuthToken;
import com.example.design1.BaseActivity;
import com.example.design1.CONSTANTS;
import com.example.design1.CustomViewPager;
import com.example.design1.Main3Activity;
import com.example.design1.Pojo.Question;
import com.example.design1.R;
import com.example.design1.ScoreCard;
import com.example.design1.adapter.ViewPagerAdapter;
import com.example.design1.models.ContestDefinition;
import com.example.design1.models.ContestTotal;
import com.example.design1.models.NewResponse;
import com.example.design1.models.QuestionDefinition;
import com.example.design1.restcalls.ContestService;
import com.example.design1.restcalls.UserResponseService;
import com.example.design1.rules;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DynamicContestActivity extends BaseActivity{

    private static final String TAG = "DynamicContestActivity";

    AlertDialog.Builder notificationPopUpBilder;
    private long timeLeft = 0;
    private TextView timer;

    @Override
    public void onCreate(Bundle savedInstanceState,PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.question_activity);


        int questionId  = getSharedPreferences(getString(R.string.shared_pref_session_id), Context.MODE_PRIVATE).getInt("questionId",-1);


        if(questionId != -1){
            Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);

            ContestService contestService = retrofit.create(ContestService.class);

            contestService.getQuestionById(questionId)
                    .enqueue(new Callback<QuestionDefinition>() {
                        @Override
                        public void onResponse(Call<QuestionDefinition> call, Response<QuestionDefinition> response) {

                        }

                        @Override
                        public void onFailure(Call<QuestionDefinition> call, Throwable t) {

                        }
                    });



            //getQuestionById();
        }



    }

    public void timer(long diffEndandPresentTime){
        timer = findViewById(R.id.timer);
        //timer.setText(diffEndandPresent+"");
        timeLeft = diffEndandPresentTime;
        CountDownTimer downTimer = new CountDownTimer(timeLeft, 1) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void updateTimer(){
        String timeText= String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(timeLeft),
                TimeUnit.MILLISECONDS.toMinutes(timeLeft) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft)),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft)));

        timer.setText(timeText);
    }

    // Shows an informative pop up on the screen
    private void showAlert(String title, String message) {
        // build notificaiton
        notificationPopUpBilder
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", null);

        AlertDialog alert = notificationPopUpBilder.create();
        // show pop up alert
        alert.show();
    }
}