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
import com.example.design1.Pojo.SubmittedResponseAck;
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

public class DynamicContestActivity extends BaseActivity {

    private static final String TAG = "DynamicContestActivity";

    List<QuestionDefinition> questions;
    LayoutInflater layoutInflater;
    private Context mContext;
    private Activity mActivity;

    private TextView textView, textView1;
    private RadioGroup radioGroup;
    private RadioButton radio1, radio2, radio3;
    private CheckBox box1, box2, box3;
    private ImageView imageView;

    private ConstraintLayout mRootLayout;
    private Button mButtonPlay,submitButton;

    public static MediaPlayer mPlayer;
    public static MediaController mediaController;
    private VideoView videoView;


    AlertDialog.Builder notificationPopUpBuilder;
    private long timeLeft = 0;
    private TextView timer;
<<<<<<< HEAD
    Bundle bundle;
    long endTime, startTime;
    int questionId;
=======
    long endTime,startTime;
    int dynamicContestId;
    int cqid,questionId; // cqid = contest question id for start time and end time matching
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83
    View view;
    View handlerLayout;

    @Override
<<<<<<< HEAD
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (!isConnected()) buildDialog().show();
        setContentView(R.layout.question_activity);
        bundle = getIntent().getExtras();
=======
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected()) buildDialog().show();
        setContentView(R.layout.question_activity);
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83

        Intent intent = getIntent();

        dynamicContestId = intent.getIntExtra("contestId",-1);

        if(dynamicContestId < 0 ){
            endActivity();
        }

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_session_id), Context.MODE_PRIVATE);
        cqid = sharedPreferences.getInt("cqid",-1);
        endTime = sharedPreferences.getLong("endTime",-1);
        startTime = sharedPreferences.getLong("startTime",-1);

        if(cqid ==-1 || endTime ==-1|| startTime==-1)
            endActivity();

        getQuestion();

        submitButton = findViewById(R.id.dynamic_activity_submit_button);
        view = findViewById(R.id.question_activity_included);
        handlerLayout = findViewById(R.id.question_activity_empty_handler);
        handlerLayout.setVisibility(View.VISIBLE);
        handlerLayout.findViewById(R.id.handling_empty_layouts_progress_bar).setVisibility(View.VISIBLE);




<<<<<<< HEAD
        Log.e(TAG, "On Create - End time" + endTime + " Start Time " + startTime + " QuestionId" + questionId);

        if (questionId != -1 && bundle.getInt("questionId") != questionId) {
            showAlert("Question has expired", "Please try out the current question if available");
        }
        if (questionId != -1 && endTime != -1 && startTime != -1) {
=======

>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83


    }

<<<<<<< HEAD
            contestService.getQuestionById(questionId)
                    .enqueue(new Callback<QuestionDefinition>() {
                        @Override
                        public void onResponse(Call<QuestionDefinition> call, Response<QuestionDefinition> response) {

                            if (response.code() == 401) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else if (response.code() / 100 == 5) {
                                Toast.makeText(getApplicationContext(), "Internal Server Error, please come back later.",
                                        Toast.LENGTH_LONG).show();

                            } else {
                                if (response.body() != null) {
                                    handlerLayout.setVisibility(View.GONE);
                                    assigningQuestion(response.body());
                                } else {
                                    handlerLayout.setVisibility(View.VISIBLE);
                                    TextView textView = handlerLayout.findViewById(R.id.handling_empty_layouts_text);
                                    textView.setText("No dynamic question available right now\n\nPlease come back later");
                                    textView.setVisibility(View.VISIBLE);
                                }
                            }
                            handlerLayout.findViewById(R.id.handling_empty_layouts_progress_bar).setVisibility(View.GONE);
                        }

            @Override
            public void onFailure (Call < QuestionDefinition > call, Throwable t){
                //Toast.makeText(getApplicationContext(),"Server Response Failed", Toast.LENGTH_LONG).show();
            }
        });
=======
    private void endActivity(){
        Toast.makeText(getApplicationContext(),"No valid Dynamic Questions Available",Toast.LENGTH_LONG).show();
        finish();
    }


    private void getQuestion(){
        long presentTime = new Date().getTime();
        if(endTime - presentTime > 0 && startTime - presentTime < 0) {

            Log.e(TAG, "On Create - End time" + endTime + " Start Time " + startTime + " cqid" + cqid);

            if (cqid != -1 && endTime != -1 && startTime != -1) {

                Retrofit retrofit = ApiRetrofitClass.getNewRetrofit("http://10.177.7.130:8080/");
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83

                ContestService contestService = retrofit.create(ContestService.class);

<<<<<<< HEAD
        //getQuestionById();
    } else

    {
        //TODO no questions available page visible
        handlerLayout.setVisibility(View.VISIBLE);
    }


}
=======
                contestService.getCQId(cqid)
                        .enqueue(new Callback<QuestionDefinition>() {
                            @Override
                            public void onResponse(Call<QuestionDefinition> call, Response<QuestionDefinition> response) {
                                if (response != null) {
                                    if (response.body() != null) {
                                        questionId = response.body().getQuestionId();
                                        handlerLayout.setVisibility(View.GONE);
                                        assigningQuestion(response.body());
                                    } else {
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
            } else {
                //TODO no questions available page visible
                handlerLayout.setVisibility(View.VISIBLE);
            }
        }else{
            endActivity();
        }

    }
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83

    private void assigningQuestion(final QuestionDefinition questionDefinition) {

        view.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.VISIBLE);

        videoView = findViewById(R.id.myVideo);
<<<<<<< HEAD
        TextView textView = findViewById(R.id.textView2);
        TextView textView1 = findViewById(R.id.textView3);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radio1 = findViewById(R.id.textView4);
        RadioButton radio2 = findViewById(R.id.textView5);
        RadioButton radio3 = findViewById(R.id.textView6);
        CheckBox box1 = findViewById(R.id.textView4c);
        CheckBox box2 = findViewById(R.id.textView5c);
        CheckBox box3 = findViewById(R.id.textView6c);
        ImageView imageView = findViewById(R.id.questionImage);
        mRootLayout = findViewById(R.id.textlayout);
        mButtonPlay = findViewById(R.id.playbutton);
        VideoView vi = imageView.findViewById(R.id.myVideo);
=======
        textView=findViewById(R.id.textView2);
        textView1=findViewById(R.id.textView3);
        radioGroup = findViewById(R.id.radioGroup);
        radio1 = findViewById(R.id.textView4);
        radio2 = findViewById(R.id.textView5);
        radio3 = findViewById(R.id.textView6);
        box1 = findViewById(R.id.textView4c);
        box2 = findViewById(R.id.textView5c);
        box3 = findViewById(R.id.textView6c);
        imageView = findViewById(R.id.questionImage);
        mRootLayout = findViewById(R.id.textlayout);
        mButtonPlay = findViewById(R.id.playbutton);
        //VideoView vi= imageView.findViewById(R.id.myVideo);
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83

        Log.d("DynamicContestActivity", questionDefinition.toString());
        textView.setText("Q");
        textView1.setText(questionDefinition.getQuestionText());


        timer(endTime - new Date().getTime());

<<<<<<< HEAD
        if (questionDefinition.getAnswerType().equals("single")) {
            Log.e(TAG, "Single Correct Type");
=======
        if(questionDefinition.getAnswerType().equals("Single-Correct")) {
            Log.e(TAG,"Single Correct Type");
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83
            radio1.setVisibility(View.VISIBLE);
            radio2.setVisibility(View.VISIBLE);
            radio3.setVisibility(View.VISIBLE);
            radio1.setText(questionDefinition.getOptionA());
            radio2.setText(questionDefinition.getOptionB());
            radio3.setText(questionDefinition.getOptionC());
<<<<<<< HEAD
        } else if (questionDefinition.getAnswerType().equals("multiple")) {
=======
        }else if(questionDefinition.getAnswerType().equals("Multiple-Correct"))
        {
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83
            box1.setVisibility(View.VISIBLE);
            box2.setVisibility(View.VISIBLE);
            box3.setVisibility(View.VISIBLE);
            box1.setText(questionDefinition.getOptionA());
            box2.setText(questionDefinition.getOptionB());
            box3.setText(questionDefinition.getOptionC());
        }
        else{
            Log.e(TAG,"Single Correct Type");
            radio1.setVisibility(View.VISIBLE);
            radio2.setVisibility(View.VISIBLE);
            radio3.setVisibility(View.VISIBLE);
            radio1.setText(questionDefinition.getOptionA());
            radio2.setText(questionDefinition.getOptionB());
            radio3.setText(questionDefinition.getOptionC());
        }

<<<<<<< HEAD
        if (questionDefinition.getQuestionType().equals("video")) {
=======
        if(questionDefinition.getQuestionType().equals("Video-Based")){
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83
            videoView.setVisibility(View.VISIBLE);
            Uri vidUri = Uri.parse(questionDefinition.getBinaryFilePath());
            videoView.setVideoURI(vidUri);
            mediaController = new MediaController(DynamicContestActivity.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.start();
            Log.d("video", videoView.isPlaying() + "..");

            // Log.d("video", vi.isPlaying()+"..");
<<<<<<< HEAD
        } else if (questionDefinition.getQuestionType().equals("image")) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(DynamicContestActivity.this)
                    .load(questionDefinition.getBinaryFilePath())
                    .into(imageView);
        } else if (questionDefinition.getQuestionType().equals("audio")) {
=======
        }
        else if(questionDefinition.getQuestionType().equals("Image-Based")){
            imageView.setVisibility(View.VISIBLE);
            Glide.with(DynamicContestActivity.this)
                    .load(questionDefinition.getBinaryFilePath())
                        .into(imageView);
        }else if(questionDefinition.getQuestionType().equals("Audio-Based")){
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83
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
                    try {

                        mPlayer.setDataSource(audioUrl);

                        mPlayer.prepare();

                        // Start playing audio from http url
                        mPlayer.start();

                        // Inform user for audio streaming
                        Toast.makeText(DynamicContestActivity.this, "Playing", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        // Catch the exception
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
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
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dynamic



                String userResponse = new String();
                if (radioGroup!=null) {
                    if (radio1.isChecked()) {
                        userResponse = "a";
                    }
                    if (radio2.isChecked()) {
                        userResponse = "b";
                    }
                    if (radio3.isChecked()) {
                        userResponse = "c";
                    }
                }
//                if(listOfQuestion.get(viewPager.getCurrentItem()).getAnswerType().equals("multiple")){
//                    CheckBox checkBox1 = view.findViewById(R.id.textView4c);
//                    CheckBox checkBox2 = view.findViewById(R.id.textView5c);
//                    CheckBox checkBox3 = view.findViewById(R.id.textView6c);
//                    if(checkBox1.isChecked())
//                        userResponse = userResponse + "a";
//                    if(checkBox2.isChecked())
//                        userResponse = userResponse + "b";
//                    if(checkBox3.isChecked())
//                        userResponse = userResponse + "c";
//                }

                final String temp = userResponse;

                Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_AUTH_URL);

                UserResponseService userResponseService = retrofit.create(UserResponseService.class);

                HashMap<String, Object> jsonParams = new HashMap<>();
                jsonParams.put("questionId",questionId);
                jsonParams.put("contestId", dynamicContestId);
                //jsonParams.put("userId", 4);
                jsonParams.put("response", userResponse);
                //jsonParams.put("username","test");

                Log.d("ApiRetrofitClass","Json Value "+jsonParams.toString());

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

                if(!userResponse.isEmpty()){
                    userResponseService.newResponseToQuestion(body, AuthToken.getToken(DynamicContestActivity.this))
                            .enqueue(new Callback<SubmittedResponseAck>() {
                                @Override
                                public void onResponse(Call<SubmittedResponseAck> call, Response<SubmittedResponseAck> response) {
                                    if(response.code()/100 == 2 && response.body() != null){
                                        Log.d("dynamic response done", "success");
                                        Toast.makeText(getApplicationContext(), "Response Submitted successfully",
                                                Toast.LENGTH_SHORT).show();
                                        submitButton.setEnabled(false);
                                    }
                                    else {
                                        Log.d(TAG, response.code() + TAG + "response code");
                                    }
                                }

                                @Override
                                public void onFailure(Call<SubmittedResponseAck> call, Throwable t) {
                                    Log.d(TAG, t.getMessage());
                                }
                            });
                }
                else{
                    Toast.makeText(getApplicationContext(), "please select a response", Toast.LENGTH_LONG).show();
                }


                }
        });

    }

/*

    private void handleNotificationIntent() {
        // TODO duplicate
        Bundle bundle = getIntent().getExtras();

        Long endTimeInLong = bundle.getLong("endTime");
        Long startTimeInLong = bundle.getLong("startTime");


        Log.e(TAG,"end, start time in long "+ endTimeInLong + "  " + startTimeInLong);
        Date presentDate = new Date();

        long diffEndandPresentTime = (endTimeInLong - presentDate.getTime());
        long diffPresentandStartTime = (presentDate.getTime() - startTimeInLong);

        if(bundle.getString("type").equals("contest")) {
            if (diffEndandPresentTime < 0) {
                //expired
                Log.e(TAG,"In if Question slot has expired");
                //showAlert("Contest Expired", "Sorry, Contest Expired. Please try again next time.");
                Toast.makeText(getApplicationContext(), "Contest Expired", Toast.LENGTH_SHORT).show();
            } else if (diffEndandPresentTime > 0 && diffPresentandStartTime > 0) {
                //contest going on
                Log.e(TAG,"In if contest slot has opened");
                showAlert("Contest is going on", "Hurry up to the dynamic contest section to start playing!");
                //Toast.makeText(getApplicationContext(), "Contest going on", Toast.LENGTH_LONG).show();

                //timer(diffEndandPresentTime);

            } else if (diffPresentandStartTime < 0) {
                //contest not yet started
                Log.e(TAG,"In if contest slot has not yet opened");
                showAlert("Contest will start soon", "The contest will begin shortly.");
                //Toast.makeText(getApplicationContext(), "Contest will start", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (diffEndandPresentTime < 0) {
                //expired
                Log.e(TAG,"In else Question slot has expired");
                showAlert("Question Expired", "Sorry, Contest Expired. Please try again next time.");
                //Toast.makeText(getApplicationContext(), "Contest Expired", Toast.LENGTH_SHORT).show();

            } else if (diffEndandPresentTime > 0 && diffPresentandStartTime > 0) {
                //contest going on

                Log.e(TAG,"In else Question slot opened");
                showAlert("Question slot is opened", "Hurry up to the dynamic contest section to start playing!");
                getQuestion();
                //Toast.makeText(getApplicationContext(), "Contest going on", Toast.LENGTH_LONG).show();

//                timer(diffEndandPresentTime);

            } else if (diffPresentandStartTime < 0) {
                //contest not yet started
                Log.e(TAG,"In else Question slot didn't open");
                showAlert("Question slot didn't open", "The question will be available shortly.");
                //Toast.makeText(getApplicationContext(), "Contest will start", Toast.LENGTH_SHORT).show();
            }
        }
    }

<<<<<<< HEAD
    public void timer(long diffEndandPresentTime) {
        timer = findViewById(R.id.timer);
=======
*/

    public void timer(long diffEndandPresentTime){
        timer = findViewById(R.id.timer_dynamic_contest);
>>>>>>> 9db0bce7adfd716a0efd0cf67be3f95dd7bb9b83
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
                submitButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), "The time has ended you cannot answer questions"
                ,Toast.LENGTH_LONG).show();
                finish();
            }
        }.start();
    }

    public void updateTimer() {
        String timeText = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(timeLeft),
                TimeUnit.MILLISECONDS.toMinutes(timeLeft) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft)),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft)));

        timer.setText(timeText);
    }

    // Shows an informative pop up on the screen
    /*private void showAlert(String title, String message) {
        // build notificaiton
        AlertDialog.Builder notificationPopUpBuilder = new AlertDialog.Builder(this);
        notificationPopUpBuilder
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", null);

        AlertDialog alert = notificationPopUpBuilder.create();
        // show pop up alert
        alert.show();
    }*/
}