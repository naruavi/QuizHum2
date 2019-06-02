package com.example.design1.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.design1.ApiRetrofitClass;
import com.example.design1.CONSTANTS;
import com.example.design1.CustomViewPager;
import com.example.design1.Pojo.Question;
import com.example.design1.R;
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

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlayStaticContest extends AppCompatActivity {

    private CustomViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_static_contest);

        listOfQuestion = new ArrayList<>();
        nextButton = findViewById(R.id.next_btn);
        submit_btn = findViewById(R.id.submit_btn);
        skipbutton =findViewById(R.id.skip_btn);
        previousButton = findViewById(R.id.previous_btn);
        skipList = new ArrayList<>();
        answered= new ArrayList<>();
        stateResponse = new HashMap<>();


        Intent intent = getIntent();
        contestId = intent.getIntExtra("constestId",1);
        //TODO getting list of questions

        Retrofit retrofit= ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);
        final ContestService contestService=retrofit.create(ContestService.class);
        contestService.getTotalContest(contestId, 1)   //TODO ADD TOKEN HERE
                .enqueue(new Callback<ContestTotal>() {
                    @Override
                    public void onResponse(Call<ContestTotal> call, Response<ContestTotal> response) {
                        if(response.code()/100 == 2){
                            if(response.body()!=null){
                                listOfQuestion.addAll(response.body().getQuestionList());
                                contestDefinition = response.body().getContestDefinition();
                                stateResponse = response.body().getUserResponse();
                                Log.d("questions",response.body().getQuestionList().toString()
                                + response.body().getContestDefinition().toString() + response.body().getUserResponse());
                                if(stateResponse!=null){
                                    for(Map.Entry entry : stateResponse.entrySet()){
                                        if(entry.getValue().equals("S")){
                                            skipList.add((Integer) entry.getKey() - 1);
                                        }
                                        else{
                                            answered.add((Integer) entry.getKey() - 1);
                                        }
                                    }
                                }
                                //fragment
                                Integer questions= response.body().getContestDefinition().getTotalQuestionsInContest();
                                Integer skips=response.body().getContestDefinition().getSkipsAllowed();
                                Integer hard=0,easy=0,medium=0;

                                Log.e("in response", response.body().getQuestionList().toString());
                                for(int i=0;i<listOfQuestion.size();i++){
                                    Log.e("in list of questions", listOfQuestion.get(i).getDifficultyLevel());

                                    if(listOfQuestion.get(i).getDifficultyLevel().equals("hard"))
                                        hard++;
                                    else if(listOfQuestion.get(i).getDifficultyLevel().equals("easy"))
                                        easy++;
                                    else if(listOfQuestion.get(i).getDifficultyLevel().equals("medium"))
                                        medium++;

                                }
                                Fragment fragment= rules.newInstance(PlayStaticContest.this,questions,skips,hard,medium,easy);
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.RulesHolder, fragment);

                                //((rules) fragment).attachThings(questions,skips,hard,medium,easy);
                                fragmentTransaction.commit();

                                viewPager = findViewById(R.id.PlayStaticViewPager);
                                pagerAdapter = new ViewPagerAdapter(PlayStaticContest.this, listOfQuestion);
                                viewPager.setAdapter(pagerAdapter);
                                viewPager.setCurrentItem(skipList.size() + answered.size());
                            }
                            else{
                                Log.d("contestResponse", "No data");
                            }
                        }
                        else{
                            Log.d("contestResponse", response.code()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<ContestTotal> call, Throwable t) {
                        Log.d("contestResponse", t.getMessage());
                    }
                });

//        HashMap<Integer, String> a = new HashMap<>();
//        a.put(1, "A");
//        a.put(2, "B");
//        a.put(3, "AB");
//        a.put(4,"S");
//        a.put(5,"S");
//        a.put(6, "A");
        skipCount = 0;
//        Collections.sort(skipList);
//        Collections.sort(answered);
//        Log.d("answerandskip", skipList.toString() + answered.toString());
//
//        liq = new ArrayList<>();
//        Question q1 = new Question(1, 1, "this is the first questions1",
//                "text","", "i have","this is it", "yes got it"
//        ,"sports", "hard","single");
//        Question q2 = new Question(1, 2, "this is the second questions1",
//                "text","", "i have","this is it", "yes got it"
//                ,"sports", "hard","single");
//        Question q3 = new Question(1, 3, "this is the third questions1",
//                "text","", "i have","this is it", "yes got it"
//                ,"sports", "hard","single");
//        Question q4 = new Question(1, 4, "this is the fourth questions1",
//                "text","", "i have","this is it", "yes got it"
//                ,"sports", "hard","single");
//        Question q5 = new Question(1, 5, "this is the fifth questions1",
//                "text","", "i have","this is it", "yes got it"
//                ,"sports", "hard","single");
//        Question q6 = new Question(1, 6, "this is the first questions1",
//                "text","", "i have","this is it", "yes got it"
//                ,"sports", "hard","single");
//        Question q7 = new Question(1, 7, "this is the first questions1",
//                "text","", "i have","this is it", "yes got it"
//                ,"sports", "hard","single");
//        Question q8 = new Question(1, 8, "this is the first questions1",
//                "text","", "i have","this is it", "yes got it"
//                ,"sports", "hard","single");
//        Question q9 = new Question(1, 9, "this is the first questions1",
//                "text","", "i have","this is it", "yes got it"
//                ,"sports", "hard","single");
//        Question q10 = new Question(1, 10, "this is the first questions1",
//                "text","", "i have","this is it", "yes got it"
//                ,"sports", "hard","single");
//
//
//        liq.add(q1);
//        liq.add(q2);
//        liq.add(q3);
//        liq.add(q4);
//        liq.add(q5);
//        liq.add(q6);
//        liq.add(q7);
//        liq.add(q8);
//        liq.add(q9);
//        liq.add(q10);

        allOnClickListeners();

    }

    @Override
    protected void onStart() {
        super.onStart();

        viewPager = findViewById(R.id.PlayStaticViewPager);
        pagerAdapter = new ViewPagerAdapter(PlayStaticContest.this, listOfQuestion);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(skipList.size() + answered.size());
//        viewPager.setCurrentItem();

        nextButton.setEnabled(false);
        if(viewPager.getCurrentItem() == 0){
            previousButton.setEnabled(false);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                VideoView vi= findViewById(R.id.myVideo);
                vi.stopPlayback();


                if(i == 0){
                    previousButton.setEnabled(false);
                }
                else{
                    previousButton.setEnabled(true);
                }

                if(skipList.contains(i)){
                    Log.d("onpageselected","in skiplist");
                    nextButton.setEnabled(true);
                    submit_btn.setEnabled(true);
                    skipbutton.setEnabled(false);
                }
                else if(answered.contains(i)){
                    Log.d("onpageselected","in answered");
                    skipbutton.setEnabled(false);
                    nextButton.setEnabled(true);
                    submit_btn.setEnabled(false);
                }
                else{
                    Log.d("onpageselected","in nothing");
                    nextButton.setEnabled(false);
                    skipbutton.setEnabled(true);
                    submit_btn.setEnabled(true);
                }

                if(i == listOfQuestion.size()-1){
                    nextButton.setEnabled(false);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    public void allOnClickListeners(){

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals("video")){
//                    if(ViewPagerAdapter.videoView.isPlaying()) {
//                        Log.d("vidio", "stopped");
//                        ViewPagerAdapter.videoView.stopPlayback();
//
//                    }
                }
                else if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals("audio")){
                    Log.d("audio", "stopped");
                    if(ViewPagerAdapter.mPlayer!=null && ViewPagerAdapter.mPlayer.isPlaying()){
                        ViewPagerAdapter.mPlayer.stop();
                        ViewPagerAdapter.mPlayer.release();
                        ViewPagerAdapter.mPlayer = null;
                    }
                }
                //TODO write retrofit call for return of response
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answered.add(viewPager.getCurrentItem());
                if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals("video")){
//                    if(ViewPagerAdapter.videoView.isPlaying()) {
//                        Log.d("vidio", "stopped");
//                        VideoView vi= findViewById(R.id.myVideo);
//                        vi.stopPlayback();
//
//                    }
                }
                else if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals("audio")){
                    Log.d("audio", "stopped");
                    if(ViewPagerAdapter.mPlayer!=null && ViewPagerAdapter.mPlayer!=null && ViewPagerAdapter.mPlayer.isPlaying()){
                        ViewPagerAdapter.mPlayer.stop();
                        ViewPagerAdapter.mPlayer.release();
                        ViewPagerAdapter.mPlayer = null;
                    }
                }
                Collections.sort(answered);
                Log.d("skiplistbtn",String.valueOf(viewPager.getCurrentItem()));
                if(skipList.contains(viewPager.getCurrentItem()))
                {
                    int indexOfskip = skipList.indexOf(viewPager.getCurrentItem());
                    try{
                        skipList.remove(indexOfskip);
                        Collections.sort(skipList);
                        Log.d("indexremoved",indexOfskip +"currentpage " + viewPager.getCurrentItem() + skipList.toString());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                skipbutton.setEnabled(false);
                submit_btn.setEnabled(false);
                if(viewPager.getCurrentItem() == listOfQuestion.size() - 1){
                    nextButton.setEnabled(false);
                }
                else{
                    nextButton.setEnabled(true);
                }
                if((answered.size() + skipList.size()) == listOfQuestion.size()){
                    if(!skipList.isEmpty()){
                        Log.d("onsubmit","new page " + answered.size() + skipList.size() + skipList.get(0));
                        viewPager.setCurrentItem(skipList.get(0));
                    }
                }
            //make response api call
                String userResponse = "";
                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                if (radioGroup!=null) {
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    if (radioId == R.id.textView4) {
                        userResponse = "a";
                    } else if (radioId == R.id.textView5) {
                        userResponse = "b";
                    } else if (radioId == R.id.textView6) {
                        userResponse = "c";
                    }
                }
                if(listOfQuestion.get(viewPager.getCurrentItem()).getAnswerType().equals("multiple")){
                    CheckBox checkBox1 = findViewById(R.id.textView4c);
                    CheckBox checkBox2 = findViewById(R.id.textView5c);
                    CheckBox checkBox3 = findViewById(R.id.textView6c);
                    if(checkBox1.isChecked())
                        userResponse = userResponse + "a";
                    if(checkBox2.isChecked())
                        userResponse = userResponse + "b";
                    if(checkBox3.isChecked())
                        userResponse = userResponse + "c";
                }


                //Submit response

                Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);

                UserResponseService userResponseService = retrofit.create(UserResponseService.class);

                HashMap<String, Object> jsonParams = new HashMap<>();
                jsonParams.put("questionId",listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
                jsonParams.put("contestId", contestId);
                jsonParams.put("userId", 4);
                jsonParams.put("response", userResponse);
                jsonParams.put("username","test");

                Log.d("ApiRetrofitClass","Json Value "+jsonParams.toString());
//                NewResponse newResponse = new NewResponse();
//                newResponse.setQuestionId(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
//                newResponse.setContestId(contestId);
//                newResponse.setUserId(1);
//                newResponse.setResponse(userResponse);
//                newResponse.setUsername("test");
//
//                Log.d("newResponse",newResponse.toString());

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

                userResponseService.newResponseToQuestion(body)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                //TODO what is the response and
                                if(response.code()/100 == 2){
                                    Toast.makeText(PlayStaticContest.this, "Submitted successfully",
                                            Toast.LENGTH_SHORT);
                                }
                                else{
                                    Log.d("responsenotsend",response.code() + "");
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("responsenotsend",t.getMessage() + "");
                            }
                        });
            }
        });

        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NO of skipped question instead of 3
                if(skipList.size() < contestDefinition.getSkipsAllowed()){
                    if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals("video")){
//                        if(ViewPagerAdapter.videoView.isPlaying()) {
//                            Log.d("video", "stopped");
//                            VideoView vi= findViewById(R.id.myVideo);
//                            vi.stopPlayback();
//                            Log.e("ID IS PLAYING: ", vi.isPlaying() + "");
//                            //ViewPagerAdapter.videoView.stopPlayback();
//                            Log.d("video", ViewPagerAdapter.videoView.isPlaying()+"..");
//                        }
                    }
                    else if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals("audio")){
                        Log.d("audio", "stopped");
                        if(ViewPagerAdapter.mPlayer!=null && ViewPagerAdapter.mPlayer.isPlaying()){
                            ViewPagerAdapter.mPlayer.stop();
                            ViewPagerAdapter.mPlayer.release();
                            ViewPagerAdapter.mPlayer = null;
                        }
                    }
                    skipList.add(viewPager.getCurrentItem());
                    Collections.sort(skipList);
                    skipbutton.setEnabled(false);
                    Toast toast = Toast.makeText(PlayStaticContest.this, " added " + skipList.toString(), Toast.LENGTH_SHORT);
                    toast.show();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }
                else {
                    Toast toast = Toast.makeText(PlayStaticContest.this, " no more skip ", Toast.LENGTH_LONG);
                    toast.show();
                }

                //make api call
                Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);

                UserResponseService userResponseService = retrofit.create(UserResponseService.class);

                HashMap<String, Object> jsonParams = new HashMap<>();
                jsonParams.put("questionId",listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
                jsonParams.put("contestId", contestId);
                jsonParams.put("userId", 4);
                jsonParams.put("username","test");
                jsonParams.put("response", "s");

                Log.d("ApiRetrofitClass","Json Value "+jsonParams.toString());

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

//                NewResponse newResponse = new NewResponse();
//                newResponse.setQuestionId(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
//                newResponse.setContestId(contestId);
//                newResponse.setUserId(1);
//                newResponse.setUsername("test");
//                newResponse.setResponse("S");

                userResponseService.newResponseToQuestion(body)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                //TODO what is the response and
                                if(response.code()/100 == 2){
                                    Toast.makeText(PlayStaticContest.this, "Submitted successfully",
                                            Toast.LENGTH_SHORT);
                                }
                                else {
                                    Log.d("responsenotsend",response.code() + "");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("responsenotsend",t.getMessage() + "");
                            }
                        });
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("video", listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType());
                if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals("video")){

                    VideoView vi= findViewById(R.id.myVideo);
                    vi.stopPlayback();
                    Log.e("ID IS PLAYING: ", vi.isPlaying() + "");


                }
                else if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals("audio")){
                    if(ViewPagerAdapter.mPlayer!=null && ViewPagerAdapter.mPlayer.isPlaying()){
                        Log.d("audio", "stopped");
                        ViewPagerAdapter.mPlayer.stop();
                        ViewPagerAdapter.mPlayer.release();
                        ViewPagerAdapter.mPlayer = null;
                    }
                }
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
    }
}
