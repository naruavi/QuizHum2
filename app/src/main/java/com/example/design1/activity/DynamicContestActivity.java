package com.example.design1.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    List<QuestionDefinition> questions;
    LayoutInflater layoutInflater;
    private Context mContext;
    private Activity mActivity;

    private ConstraintLayout mRootLayout;
    private Button mButtonPlay;

    public static MediaPlayer mPlayer;
    public static MediaController mediaController;
    private VideoView videoView;


    AlertDialog.Builder notificationPopUpBuilder;
    private long timeLeft = 0;
    private TextView timer;
    Bundle bundle;
    long endTime,startTime;
    int questionId;
    View view;
    View handlerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState,PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if(!isConnected()) buildDialog().show();
        setContentView(R.layout.question_activity);
        bundle =  getIntent().getExtras();

        questionId = bundle.getInt("questionId");
        endTime = bundle.getLong("endTime");
        startTime = bundle.getLong("endTime");

        view = findViewById(R.id.question_activity_included);
        handlerLayout = findViewById(R.id.question_activity_empty_handler);
        handlerLayout.setVisibility(View.VISIBLE);
        handlerLayout.findViewById(R.id.handling_empty_layouts_progress_bar).setVisibility(View.VISIBLE);

/*

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_session_id), Context.MODE_PRIVATE);
        questionId = sharedPreferences.getInt("questionId",-1);

        endTime = sharedPreferences.getLong("endTime",-1);
        startTime = sharedPreferences.getLong("startTime",-1);
*/

        Log.e(TAG,"On Create - End time"+endTime+" Start Time "+startTime+" QuestionId"+questionId);

        if(questionId != -1 && bundle.getInt("questionId")!= questionId){
            showAlert("Question has expired","Please try out the current question if available");
        }
        if(questionId != -1 && endTime != -1 && startTime != -1){

            Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);

            ContestService contestService = retrofit.create(ContestService.class);

            contestService.getCQId(questionId)
                    .enqueue(new Callback<QuestionDefinition>() {
                        @Override
                        public void onResponse(Call<QuestionDefinition> call, Response<QuestionDefinition> response) {
                            if(response != null) {
                                if(response.body() != null){
                                    handlerLayout.setVisibility(View.GONE);
                                    assigningQuestion(response.body());
                                }
                                else{
                                    handlerLayout.setVisibility(View.VISIBLE);
                                    TextView textView = handlerLayout.findViewById(R.id.handling_empty_layouts_text);
                                    textView.setText("No dynamic question available right now\n\nPlease come back later");
                                    textView.setVisibility(View.VISIBLE);
                                }

                            }

                            handlerLayout.findViewById(R.id.handling_empty_layouts_progress_bar).setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<QuestionDefinition> call, Throwable t) {
                            //Toast.makeText(getApplicationContext(),"Server Response Failed", Toast.LENGTH_LONG).show();
                        }
                    });



            //getQuestionById();
        }else{
            //TODO no questions available page visible
            handlerLayout.setVisibility(View.VISIBLE);
        }



    }

    private void assigningQuestion(final QuestionDefinition questionDefinition) {

        view.setVisibility(View.VISIBLE);


        videoView = findViewById(R.id.myVideo);
        TextView textView=findViewById(R.id.textView2);
        TextView textView1=findViewById(R.id.textView3);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radio1 = findViewById(R.id.textView4);
        RadioButton radio2 = findViewById(R.id.textView5);
        RadioButton radio3 = findViewById(R.id.textView6);
        CheckBox   box1 = findViewById(R.id.textView4c);
        CheckBox   box2 = findViewById(R.id.textView5c);
        CheckBox   box3 = findViewById(R.id.textView6c);
        ImageView imageView = findViewById(R.id.questionImage);
        mRootLayout = findViewById(R.id.textlayout);
        mButtonPlay = findViewById(R.id.playbutton);
        VideoView vi= imageView.findViewById(R.id.myVideo);

        Log.d("DynamicContestActivity", questionDefinition.toString());
        textView.setText("Q");
        textView1.setText(questionDefinition.getQuestionText());


        timer(endTime- new Date().getTime());

        if(questionDefinition.getAnswerType().equals("single")) {
            Log.e(TAG,"Single Correct Type");
            radio1.setVisibility(View.VISIBLE);
            radio2.setVisibility(View.VISIBLE);
            radio3.setVisibility(View.VISIBLE);
            radio1.setText(questionDefinition.getOptionA());
            radio2.setText(questionDefinition.getOptionB());
            radio3.setText(questionDefinition.getOptionC());
        }else if(questionDefinition.getAnswerType().equals("multiple"))
        {
            box1.setVisibility(View.VISIBLE);
            box2.setVisibility(View.VISIBLE);
            box3.setVisibility(View.VISIBLE);
            box1.setText(questionDefinition.getOptionA());
            box2.setText(questionDefinition.getOptionB());
            box3.setText(questionDefinition.getOptionC());
        }

        if(questionDefinition.getQuestionType().equals("video")){
            videoView.setVisibility(View.VISIBLE);
            Uri vidUri = Uri.parse(questionDefinition.getBinaryFilePath());
            videoView.setVideoURI(vidUri);
            mediaController = new MediaController(DynamicContestActivity.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.start();
            Log.d("video", videoView.isPlaying()+"..");

            // Log.d("video", vi.isPlaying()+"..");
        }
        else if(questionDefinition.getQuestionType().equals("image")){
            imageView.setVisibility(View.VISIBLE);
            Glide.with(DynamicContestActivity.this)
                    .load(questionDefinition.getBinaryFilePath())
                        .into(imageView);
        }else if(questionDefinition.getQuestionType().equals("audio")){
            mButtonPlay.setVisibility(View.VISIBLE);
            mButtonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Disable the play button
                    mButtonPlay.setEnabled(false);

                    // The audio url to play
                    String audioUrl = questionDefinition.getBinaryFilePath();

                    // Initialize a new media player instance
                    mPlayer = new MediaPlayer();

                    // Set the media player audio stream type
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    //Try to play music/audio from url
                    try{

                        mPlayer.setDataSource(audioUrl);

                        mPlayer.prepare();

                        // Start playing audio from http url
                        mPlayer.start();

                        // Inform user for audio streaming
                        Toast.makeText(DynamicContestActivity.this,"Playing",Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        // Catch the exception
                        e.printStackTrace();
                    }catch (IllegalArgumentException e){
                        e.printStackTrace();
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }catch (IllegalStateException e){
                        e.printStackTrace();
                    }

                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mButtonPlay.setEnabled(true);
                        }
                    });
                }
            });
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
        notificationPopUpBuilder
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", null);

        AlertDialog alert = notificationPopUpBuilder.create();
        // show pop up alert
        alert.show();
    }
}