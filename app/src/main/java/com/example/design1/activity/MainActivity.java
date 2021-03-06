package com.example.design1.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.design1.ApiRetrofitClass;
import com.example.design1.AuthToken;
import com.example.design1.BaseActivity;
import com.example.design1.CONSTANTS;
import com.example.design1.R;
import com.example.design1.adapter.RecyclerAdapterForHome;
import com.example.design1.models.CategoryDefinition;
import com.example.design1.models.ContestDefinition;
import com.example.design1.restcalls.ApiLoginSignUp;
import com.example.design1.restcalls.ContestService;
import com.example.design1.restcalls.UserResponseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "message";
    private static final String TAG = "MainActivity.class Log ";
    boolean doubleBackToExitPressedOnce = false;
    TextView toolbarHeader;
    AlertDialog.Builder notificationPopUpBilder;
    //private int userToken;
    List<CategoryDefinition> categoryList = new ArrayList<>();
    List<ContestDefinition> contestList = new ArrayList<>();
    //TODO remove unnecessary variable declaration
    private Scene scene1, scene2, scene3;
    private Transition transition;
    private boolean start1, start2;
    private Button incompleteContests, dynamicContest;
    private int dynamicContestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isConnected()) buildDialog().show();

        setContentView(R.layout.activity_main);

        incompleteContests = findViewById(R.id.incomplete_contests);
        incompleteContests.setOnClickListener(this);

        TextView toolbarHeader = findViewById(R.id.toolbar_header_text);
        //TODO move to string file
        toolbarHeader.setText("QuizHum");

        createNotificationChannel();

        notificationPopUpBilder = new AlertDialog.Builder(this);

        dynamicContest = findViewById(R.id.button);
        dynamicContest.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            Log.d("remoteservice",getIntent().getExtras().toString());
            handleNotificationIntent();
        }

        final RecyclerView recyclerView = findViewById(R.id.rec1);
        final RecyclerAdapterForHome recyclerAdapterForHome = new RecyclerAdapterForHome(categoryList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recyclerAdapterForHome);

        //TODO only one instance or retrofit
        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit("http://10.177.7.130:8080");
        ContestService contestService = retrofit.create(ContestService.class);
        contestService.getCategories()
                .enqueue(new Callback<List<CategoryDefinition>>() {
                    @Override
                    public void onResponse(Call<List<CategoryDefinition>> call, Response<List<CategoryDefinition>> response) {
                        Log.d("login: main:", response.code()+" code");
                        Log.d("login: main:", response.body()+" body");
                        Log.d("login: main:", "response body bool: " + (response.body() == null));
                        if(response.code()==401){
                            Log.d("login: main: ", "reached 1");
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            //Todo toast
                            finish();
                        }
                        else if(response.code()/100 ==  5){
                            Log.d("login: main: ", "reached 2");
                            Toast.makeText(getApplicationContext(),"Internal Server Error, please come back later.",
                                    Toast.LENGTH_LONG).show();

                        }
                        else if(response.body()!=null){
                            Log.d("login: main: ", "reached 3");
                            Log.e("In get all categories", response.body().toString());
                            categoryList.addAll(response.body());
                            recyclerAdapterForHome.notifyDataSetChanged();
                        }

                        Log.d("login: main: ", "reached here");
                    }

                    @Override
                    public void onFailure(Call<List<CategoryDefinition>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Get Categories Server Response Failed", Toast.LENGTH_LONG).show();
                        Log.e("In get all catoegories", "failed" + t.getMessage());

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (AuthToken.getToken(this) != null)
            Log.d("tokenauth", AuthToken.getToken(MainActivity.this));
        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_AUTH_URL);
        ApiLoginSignUp apiLoginSignUp = retrofit.create(ApiLoginSignUp.class);

        apiLoginSignUp.verifyUserSession(AuthToken.getToken(MainActivity.this))
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response != null && response.code() / 100 == 2 && response.body() != null) {
                            //TODO when the user is valid

                        } else {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });

        dynamicContest.setEnabled(false);
        getDynamicContest();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.incomplete_contests) {
            /*Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);
            UserResponseService userResponseService=retrofit.create(UserResponseService.class);

            userResponseService.getIncompletedContests(4)
                    .enqueue(new Callback<List<ContestDefinition>>() {
                        @Override
                        public void onResponse(Call<List<ContestDefinition>> call, Response<List<ContestDefinition>> response) {
                            if(response.body()!=null) {
                                Log.e("in incomplete contest", response.body().toString());
                                contestList.addAll(response.body());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ContestDefinition>> call, Throwable t) {
                            Log.e("in complete contest", "failure");
                        }
                    });*/
            Intent intent = new Intent(view.getContext(), ContestActivity.class);
            intent.putExtra("userId", 4);
            view.getContext().startActivity(intent);
        }

        if (view.getId() == R.id.button) {

            Intent intent = new Intent(view.getContext(), DynamicContestActivity.class);
            intent.putExtra("contestId", dynamicContestId);
            Log.e(TAG, "" + dynamicContestId);
            //ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(MainActivity.this,0,R.anim.slideup_animation);
            view.getContext().startActivity(intent);
        }
    }

    private void getDynamicContest() {

        Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL_WITHOUT_GATEWAY);


        ContestService contestService = retrofit.create(ContestService.class);

        contestService.getContestsByType("dynamic")
                .enqueue(new Callback<List<ContestDefinition>>() {
                    @Override
                    public void onResponse(Call<List<ContestDefinition>> call, Response<List<ContestDefinition>> response) {

                        if(response.code()==401){
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else if(response.code()/100 ==  5){
                            Toast.makeText(getApplicationContext(),"Internal Server Error, please come back later.",
                                    Toast.LENGTH_LONG).show();

                        }

                        else if(response.body() !=  null){
                            if(response.body().size()!=0) {
                                //checking if the last dynamic contest slot is right now
                                Log.e(TAG,response.body().toString());
                                long presentTime = new Date().getTime();
                                long endTime = response.body().get(response.body().size() - 1).getEndTimeOfContest();
                                long startTime = response.body().get(response.body().size() - 1).getStartTimeOfContest();
                                if (startTime - presentTime < 0 && endTime - presentTime > 0) {
                                    dynamicContestId = response.body().get(response.body().size() - 1).getContestId();
                                    dynamicContestId = response.body().get(response.body().size() - 1).getContestId();
                                    dynamicContest.setEnabled(true);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ContestDefinition>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Server Response Failed - get contest by type", Toast.LENGTH_LONG).show();

                    }
                });
    }


    //todo move is helper class
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.dynamic_contest_notif_channel_name);
            String description = getString(R.string.dynamic_contest_notif_channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            String channelId = getString(R.string.dynamic_contest_notif_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void seeContests(View view) {
        Intent intent = new Intent(this, ContestActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void handleNotificationIntent() {
        // TODO duplicate
        Bundle bundle = getIntent().getExtras();

        Long endTimeInLong = bundle.getLong("endTime");
        Long startTimeInLong = bundle.getLong("startTime");


        Log.e(TAG, "end, start time in long " + endTimeInLong + "  " + startTimeInLong);
        Date presentDate = new Date();

        long diffEndandPresentTime = (endTimeInLong - presentDate.getTime());
        long diffPresentandStartTime = (presentDate.getTime() - startTimeInLong);
        try {
            if (bundle.getString("type").equals("contest")) {
                if (diffEndandPresentTime < 0) {
                    //expired
                    Log.e(TAG, "In if Question slot has expired");
                    //showAlert("Contest Expired", "Sorry, Contest Expired. Please try again next time.");
                    Toast.makeText(getApplicationContext(), "Contest Expired", Toast.LENGTH_SHORT).show();
                } else if (diffEndandPresentTime > 0 && diffPresentandStartTime > 0) {
                    //contest going on
                    Log.e(TAG, "In if contest slot has opened");
                    showAlert("Contest is going on", "Hurry up to the dynamic contest section to start playing!");
                    //Toast.makeText(getApplicationContext(), "Contest going on", Toast.LENGTH_LONG).show();

                    //timer(diffEndandPresentTime);

                } else if (diffPresentandStartTime < 0) {
                    //contest not yet started
                    Log.e(TAG, "In if contest slot has not yet opened");
                    showAlert("Contest will start soon", "The contest will begin shortly.");
                    //Toast.makeText(getApplicationContext(), "Contest will start", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (diffEndandPresentTime < 0) {
                    //expired
                    Log.e(TAG, "In else Question slot has expired");
                    showAlert("Question Expired", "Sorry, Contest Expired. Please try again next time.");
                    //Toast.makeText(getApplicationContext(), "Contest Expired", Toast.LENGTH_SHORT).show();

                } else if (diffEndandPresentTime > 0 && diffPresentandStartTime > 0) {
                    //contest going on

                    Log.e(TAG, "In else Question slot opened");
                    showAlert("Question slot is opened", "Hurry up to the dynamic contest section to start playing!");
                    //Toast.makeText(getApplicationContext(), "Contest going on", Toast.LENGTH_LONG).show();

//                timer(diffEndandPresentTime);

                } else if (diffPresentandStartTime < 0) {
                    //contest not yet started
                    Log.e(TAG, "In else Question slot didn't open");
                    showAlert("Question slot didn't open", "The question will be available shortly.");
                    //Toast.makeText(getApplicationContext(), "Contest will start", Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            Log.d("handlenotification","something found null" );
        }
    }

    private void showAlert(String title, String message) {
        // build notificaiton
        AlertDialog.Builder notificationPopUpBilder = new AlertDialog.Builder(this);

        notificationPopUpBilder
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", null);

        AlertDialog alert = notificationPopUpBilder.create();
        // show pop up alert
        alert.show();

    }

    public void goToDynamicLeaderBoard(View view){
        Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
        intent.putExtra("contestId", dynamicContestId);
        startActivity(intent);
    }
}
