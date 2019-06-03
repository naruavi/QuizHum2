package com.example.design1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.example.design1.ApiRetrofitClass;
import com.example.design1.AuthToken;
import com.example.design1.BaseActivity;
import com.example.design1.CONSTANTS;
import com.example.design1.R;
import com.example.design1.adapter.ViewPagerAdapter;
import com.example.design1.models.ContestDefinition;
import com.example.design1.models.ContestTotal;
import com.example.design1.models.QuestionDefinition;
import com.example.design1.restcalls.ContestService;
import com.example.design1.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlayStaticContest1 extends BaseActivity {


    private int contestId;
    List<QuestionDefinition> listOfQuestion;
    Button nextButton;
    Button skipbutton;
    Button previousButton;
    Button submit_btn;
    ContestDefinition contestDefinition;
    List<Integer> skipList;
    List<Integer> answered;
    int skipCount;
    HashMap<Integer, String> stateResponse;
    HashMap<Integer, Integer> state;
    FrameLayout scoreCardHolder;
    String VIDEO = "Video-Based";
    String AUDIO = "Audio-Based";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_play_static_contest);
//
//        listOfQuestion = new ArrayList<>();
//        nextButton = findViewById(R.id.next_btn);
//        submit_btn = findViewById(R.id.submit_btn);
//        skipbutton =findViewById(R.id.skip_btn);
//        previousButton = findViewById(R.id.previous_btn);
//        skipList = new ArrayList<>();
//        answered= new ArrayList<>();
//        stateResponse = new HashMap<>();
//        scoreCardHolder = findViewById(R.id.scoreCardHolder);
//
//        Intent intent = getIntent();
//        contestId = intent.getIntExtra("contestId",1);
//        //TODO getting list of questions
//
//        Retrofit retrofit= ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);
//        final ContestService contestService=retrofit.create(ContestService.class);
//        Log.d("gotthecontestId", contestId+" and key" + AuthToken.getToken(PlayStaticContest.this));
//        contestService.getTotalContest(contestId, AuthToken.getToken(PlayStaticContest.this))   //TODO ADD TOKEN HERE
//                .enqueue(new Callback<ContestTotal>() {
//                    @Override
//                    public void onResponse(Call<ContestTotal> call, Response<ContestTotal> response) {
//                        if(response.code()/100 == 2){
//                            if(response.body()!=null){
//                                listOfQuestion.addAll(response.body().getQuestionList());
//                                contestDefinition = response.body().getContestDefinition();
//                                stateResponse = response.body().getUserResponse();
//                                Log.d("questions", response.body().getQuestionList().toString()
//                                        +response.body().getContestDefinition().toString() +
//                                        "userresponse" +response.body().getUserResponse());
//                                if(stateResponse!=null){
//                                    Log.d("stateResponse", stateResponse.toString());
//                                    for(Map.Entry entry : stateResponse.entrySet()){
//                                        if(entry.getValue().equals("s")){
//                                            skipList.add((Integer) entry.getKey());
//                                        }
//                                        else{
//                                            answered.add((Integer) entry.getKey());
//                                        }
//                                    }
//                                }
//                                else{
//                                    Log.d("stateResponse", "state null");
//                                }
//                                Log.d("just", skipList.toString() + " answered" + answered);
//                                //fragment
//                                Integer questions= response.body().getContestDefinition().getTotalQuestionsInContest();
//                                Integer skips=response.body().getContestDefinition().getSkipsAllowed();
//                                Integer hard=0,easy=0,medium=0;
//
//                                Log.e("in response", response.body().getQuestionList().toString());
//                                for(int i=0;i<listOfQuestion.size();i++){
//                                    Log.e("in list of questions", listOfQuestion.get(i).getDifficultyLevel());
//
//                                    if(listOfQuestion.get(i).getDifficultyLevel().toLowerCase().equals("hard"))
//                                        hard++;
//                                    else if(listOfQuestion.get(i).getDifficultyLevel().toLowerCase().equals("easy"))
//                                        easy++;
//                                    else if(listOfQuestion.get(i).getDifficultyLevel().toLowerCase().equals("medium"))
//                                        medium++;
//
//                                }
//                                Fragment fragment= rules.newInstance(PlayStaticContest.this,questions,skips,hard,medium,easy);
//                                FragmentManager fragmentManager = getSupportFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                fragmentTransaction.replace(R.id.RulesHolder, fragment);
//
//                                //((rules) fragment).attachThings(questions,skips,hard,medium,easy);
//                                fragmentTransaction.commit();
//
//                                viewPager = findViewById(R.id.PlayStaticViewPager);
//                                pagerAdapter = new ViewPagerAdapter(PlayStaticContest.this, listOfQuestion);
//                                viewPager.setAdapter(pagerAdapter);
//                                viewPager.setCurrentItem(skipList.size() + answered.size());
//
//
//                                nextButton.setEnabled(false);
//                                if(viewPager.getCurrentItem() == 0){
//                                    previousButton.setEnabled(false);
//                                }
//
//                                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//                                    @Override
//                                    public void onPageScrolled(int i, float v, int i1) {
//
//                                    }
//                                    @Override
//                                    public void onPageSelected(int i) {
//                                        VideoView vi= findViewById(R.id.myVideo);
//                                        vi.stopPlayback();
//                                        if(i == 0){
//                                            previousButton.setEnabled(false);
//                                        }
//                                        else{
//                                            previousButton.setEnabled(true);
//                                        }
//
//                                        if(skipList.contains(listOfQuestion.get(i).getQuestionId())){
//                                            Log.d("onpageselected","in skiplist");
//                                            nextButton.setEnabled(true);
//                                            submit_btn.setEnabled(true);
//                                            skipbutton.setEnabled(false);
//                                        }
//                                        else if(answered.contains(listOfQuestion.get(i).getQuestionId())){
//                                            Log.d("onpageselected","in answered");
//                                            skipbutton.setEnabled(false);
//                                            nextButton.setEnabled(true);
//                                            submit_btn.setEnabled(false);
//                                        }
//                                        else{
//                                            Log.d("onpageselected","in nothing");
//                                            nextButton.setEnabled(false);
//                                            skipbutton.setEnabled(true);
//                                            submit_btn.setEnabled(true);
//                                        }
//
//                                        if(i == listOfQuestion.size()-1){
//                                            nextButton.setEnabled(false);
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onPageScrollStateChanged(int i) {
//
//                                    }
//                                });
//                            }
//                            else{
//                                Log.d("contestResponse", "No data");
//                            }
//                        }
//                        else{
//                            Log.d("contestResponse", response.code()+"");
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<ContestTotal> call, Throwable t) {
//                        Log.d("contestResponse", t.getMessage());
//                    }
//                });
//
//
//
//
//    }
    }
}






































