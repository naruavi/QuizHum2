package com.example.design1.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
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
import com.example.design1.Pojo.SubmittedResponseAck;
import com.example.design1.R;
import com.example.design1.ScoreCard;
import com.example.design1.adapter.ViewPagerAdapter;
import com.example.design1.models.ContestDefinition;
import com.example.design1.models.ContestTotal;
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

public class PlayStaticContest extends BaseActivity {

    private CustomViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private int contestId;
    private String contestName;
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
    TextView contestToolbarHeader;
    String VIDEO = "Video-Based";
    String AUDIO = "Audio-Based";
    View handlerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected()) buildDialog().show();
        setContentView(R.layout.activity_play_static_contest);
        enableBackToolbar(R.id.static_contest_toolbar);

        listOfQuestion = new ArrayList<>();
        nextButton = findViewById(R.id.next_btn);
        submit_btn = findViewById(R.id.submit_btn);
        skipbutton =findViewById(R.id.skip_btn);
        submit_btn.setEnabled(false);
        skipbutton.setEnabled(false);
        previousButton = findViewById(R.id.previous_btn);
        skipList = new ArrayList<>();
        answered= new ArrayList<>();
        stateResponse = new HashMap<>();
        scoreCardHolder = findViewById(R.id.scoreCardHolder);

        contestToolbarHeader = findViewById(R.id.toolbar_header_text);
        contestToolbarHeader.setText("Play Contest");

        //For progress bar and empty handler
        handlerLayout = findViewById(R.id.static_contest_empty_handler);
        handlerLayout.setVisibility(View.VISIBLE);
        handlerLayout.findViewById(R.id.handling_empty_layouts_progress_bar).setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        contestId = intent.getIntExtra("contestId",1);
        contestName = intent.getStringExtra("contestName");
        if(contestName == null)
            contestName = "Play Contest";
        contestToolbarHeader.setText(contestName);

        //TODO getting list of questions

        Retrofit retrofit= ApiRetrofitClass.getNewRetrofit(CONSTANTS.CONTEST_RESPONSE_URL);
        final ContestService contestService=retrofit.create(ContestService.class);
        Log.d("gotthecontestId", contestId+" and key" + AuthToken.getToken(PlayStaticContest.this));
        contestService.getTotalContest(contestId, AuthToken.getToken(PlayStaticContest.this))   //TODO ADD TOKEN HERE
                .enqueue(new Callback<ContestTotal>() {
                    @Override
                    public void onResponse(Call<ContestTotal> call, Response<ContestTotal> response) {
                        if(response.code()/100 == 2){
                            if(response.body()!=null && response.body().getQuestionList().size()!=0) {
                                    submit_btn.setEnabled(true);
                                    skipbutton.setEnabled(true);
                                    listOfQuestion.addAll(response.body().getQuestionList());
                                    contestDefinition = response.body().getContestDefinition();
                                    stateResponse = response.body().getUserResponse();
                                    Log.d("questions", response.body().getQuestionList().toString()
                                            + response.body().getContestDefinition().toString() +
                                            "userresponse" + response.body().getUserResponse());
                                    if (stateResponse != null) {
                                        Log.d("stateResponse", stateResponse.toString());
                                        for (Map.Entry entry : stateResponse.entrySet()) {
                                            if (entry.getValue().equals("s")) {
                                                skipList.add((Integer) entry.getKey());
                                            } else {
                                                answered.add((Integer) entry.getKey());
                                            }
                                        }
                                    } else {
                                        Log.d("stateResponse", "state null");
                                    }
                                    Log.d("just", skipList.toString() + " answered" + answered);
                                    //fragment
                                    Integer questions = response.body().getContestDefinition().getTotalQuestionsInContest();
                                    Integer skips = response.body().getContestDefinition().getSkipsAllowed();
                                    Integer hard = 0, easy = 0, medium = 0;

                                    Log.e("in response", response.body().getQuestionList().toString());
                                    for (int i = 0; i < listOfQuestion.size(); i++) {
                                        Log.e("in list of questions", listOfQuestion.get(i).getDifficultyLevel());

                                        if (listOfQuestion.get(i).getDifficultyLevel().toLowerCase().equals("hard"))
                                            hard++;
                                        else if (listOfQuestion.get(i).getDifficultyLevel().toLowerCase().equals("easy"))
                                            easy++;
                                        else if (listOfQuestion.get(i).getDifficultyLevel().toLowerCase().equals("medium"))
                                            medium++;

                                    }
                                    Fragment fragment = rules.newInstance(PlayStaticContest.this, questions, skips, hard, medium, easy);
                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.RulesHolder, fragment);

                                    //((rules) fragment).attachThings(questions,skips,hard,medium,easy);
                                    fragmentTransaction.commit();

                                    viewPager = findViewById(R.id.PlayStaticViewPager);
                                    pagerAdapter = new ViewPagerAdapter(PlayStaticContest.this, listOfQuestion);
                                    viewPager.setAdapter(pagerAdapter);
                                    viewPager.setCurrentItem(skipList.size() + answered.size());


                                    nextButton.setEnabled(false);
                                    if (viewPager.getCurrentItem() == 0) {
                                        previousButton.setEnabled(false);
                                    }

                                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                        @Override
                                        public void onPageScrolled(int i, float v, int i1) {

                                        }

                                        @Override
                                        public void onPageSelected(int i) {
                                            VideoView vi = findViewById(R.id.myVideo);
                                            vi.stopPlayback();
                                            if (i == 0) {
                                                previousButton.setEnabled(false);
                                            } else {
                                                previousButton.setEnabled(true);
                                            }

                                            if (skipList.contains(listOfQuestion.get(i).getQuestionId())) {
                                                Log.d("onpageselected", "in skiplist");
                                                nextButton.setEnabled(true);
                                                submit_btn.setEnabled(true);
                                                skipbutton.setEnabled(false);
                                            } else if (answered.contains(listOfQuestion.get(i).getQuestionId())) {
                                                Log.d("onpageselected", "in answered");
                                                skipbutton.setEnabled(false);
                                                nextButton.setEnabled(true);
                                                submit_btn.setEnabled(false);
                                            } else {
                                                Log.d("onpageselected", "in nothing");
                                                nextButton.setEnabled(false);
                                                skipbutton.setEnabled(true);
                                                submit_btn.setEnabled(true);
                                            }

                                            if (i == listOfQuestion.size() - 1) {
                                                nextButton.setEnabled(false);
                                            }

                                        }

                                        @Override
                                        public void onPageScrollStateChanged(int i) {

                                        }
                                    });
                                }
                            else{
                                handlerLayout.setVisibility(View.VISIBLE);
                                TextView textView = handlerLayout.findViewById(R.id.handling_empty_layouts_text);
                                textView.setText("This contest is empty\n\nTry another contest");
                                textView.setVisibility(View.VISIBLE);
                                }
                            handlerLayout.findViewById(R.id.handling_empty_layouts_progress_bar).setVisibility(View.GONE);
                        }
                        else{
                            handlerLayout.setVisibility(View.VISIBLE);
                            TextView textView = handlerLayout.findViewById(R.id.handling_empty_layouts_text);
                            textView.setText("Something went wrong please try again");
                            textView.setVisibility(View.VISIBLE);
                            handlerLayout.findViewById(R.id.handling_empty_layouts_progress_bar).setVisibility(View.GONE);
                            Log.d("contestResponse", response.code()+"");
                        }
                    }
                    @Override
                    public void onFailure(Call<ContestTotal> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Get Total Contest Server Response Failed", Toast.LENGTH_LONG).show();
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
                if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals(VIDEO)){
//                    if(ViewPagerAdapter.videoView.isPlaying()) {
//                        Log.d("vidio", "stopped");
//                        ViewPagerAdapter.videoView.stopPlayback();
//
//                    }
                }
                else if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals(AUDIO)){
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


    // submit button

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals(VIDEO)){
//                    if(ViewPagerAdapter.videoView.isPlaying()) {
//                        Log.d("vidio", "stopped");
//                        VideoView vi= findViewById(R.id.myVideo);
//                        vi.stopPlayback();
//
//                    }
                }
                else if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals(AUDIO)){
                    Log.d("audio", "stopped");
                    if(ViewPagerAdapter.mPlayer!=null && ViewPagerAdapter.mPlayer!=null &&
                            ViewPagerAdapter.mPlayer.isPlaying()){
                        ViewPagerAdapter.mPlayer.stop();
                        ViewPagerAdapter.mPlayer.release();
                        ViewPagerAdapter.mPlayer = null;
                    }
                }
                Log.d("submitbtn",String.valueOf(viewPager.getCurrentItem()));
//                if(skipList.contains(viewPager.getCurrentItem()))
//                {
//                    int indexOfskip = skipList.indexOf(viewPager.getCurrentItem());
//                    try{
//                        skipList.remove(indexOfskip);
//                        Collections.sort(skipList);
//                        Log.d("indexremoved",indexOfskip +"currentpage " + viewPager.getCurrentItem() + skipList.toString());
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
                skipbutton.setEnabled(false);
                submit_btn.setEnabled(false);
                if(viewPager.getCurrentItem() == listOfQuestion.size() - 1){
                    nextButton.setEnabled(false);
                }
                else{
                    nextButton.setEnabled(true);
                }
            //make response api call
                String userResponse = new String();
                View view = viewPager.findViewWithTag("currentView" +
                        listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
                RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
                RadioButton radioButton1 = view.findViewById(R.id.textView4);
                RadioButton radioButton2 = view.findViewById(R.id.textView5);
                RadioButton radioButton3 = view.findViewById(R.id.textView6);
                if (radioGroup!=null) {
                    if (radioButton1.isChecked()) {
                        userResponse = "a";
                    }
                    if (radioButton2.isChecked()) {
                        userResponse = "b";
                    }
                    if (radioButton3.isChecked()) {
                        userResponse = "c";
                    }
                }
                if(listOfQuestion.get(viewPager.getCurrentItem()).getAnswerType().equals("multiple")){
                    CheckBox checkBox1 = view.findViewById(R.id.textView4c);
                    CheckBox checkBox2 = view.findViewById(R.id.textView5c);
                    CheckBox checkBox3 = view.findViewById(R.id.textView6c);
                    if(checkBox1.isChecked())
                        userResponse = userResponse + "a";
                    if(checkBox2.isChecked())
                        userResponse = userResponse + "b";
                    if(checkBox3.isChecked())
                        userResponse = userResponse + "c";
                }


                //Submit response

                Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_AUTH_URL);

                UserResponseService userResponseService = retrofit.create(UserResponseService.class);

                HashMap<String, Object> jsonParams = new HashMap<>();
                jsonParams.put("questionId",listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
                jsonParams.put("contestId", contestId);
                //jsonParams.put("userId", 4);
                jsonParams.put("response", userResponse);
                //jsonParams.put("username","test");

                Log.d("ApiRetrofitClass","Json Value "+jsonParams.toString());

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

                if(skipList.contains(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId()))
                {
                    final int indexOfskip = skipList.indexOf(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
                    try{
                        userResponseService.updateResponseOfSkipped(body, AuthToken.getToken(PlayStaticContest.this))
                                .enqueue(new Callback<SubmittedResponseAck>() {
                                    @Override
                                    public void onResponse(Call<SubmittedResponseAck> call, Response<SubmittedResponseAck> response) {
                                        //TODO what is the response and
                                        if(response.code()/100 == 2){
                                            if(response.body().getResponse()!=null)

                                            {Toast.makeText(getApplicationContext(), "Submitted successfully",
                                                    Toast.LENGTH_SHORT).show();
                                                answered.add(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
                                                Collections.sort(answered);
                                                skipList.remove(indexOfskip);
                                                Collections.sort(skipList);
                                                Log.d("indexremoved",indexOfskip +"currentpage " + viewPager.getCurrentItem() + skipList.toString());
                                                Log.e("sizeofset",answered.size() + skipList.size() + "");
                                                int a = answered.size() + skipList.size();
                                                if(a == listOfQuestion.size()){
                                                    Log.d("lastquestion","all questions answered");
                                                    if(!skipList.isEmpty()){
                                                        Log.d("onsubmit","new page " + answered.size() + skipList.size() + skipList.get(0));
                                                        viewPager.setCurrentItem(skipList.get(0));
                                                    }
                                                    else{
                                                        ScoreCard scoreCard = new ScoreCard();
                                                        Bundle bundle = new Bundle();
                                                        bundle.putInt("contestId", PlayStaticContest.this.contestDefinition.getContestId());
                                                        bundle.putInt("totalQuestions",PlayStaticContest.this.contestDefinition
                                                                .getTotalQuestionsInContest());
                                                        scoreCard.setArguments(bundle);
                                                        scoreCardHolder.setVisibility(View.VISIBLE);
                                                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                                                                .replace(R.id.scoreCardHolder,scoreCard);
                                                        fragmentTransaction.commit();
                                                    }
                                                }
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "response not sumbmitted" +
                                                        "Server issue", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Log.d("responsenotsend",response.code() + "");
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<SubmittedResponseAck> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(),"Server Response Failed - update response of skipped", Toast.LENGTH_LONG).show();
                                        Log.d("responsenotsend",t.getMessage() + "");
                                    }
                                });

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                userResponseService.newResponseToQuestion(body, AuthToken.getToken(PlayStaticContest.this))
                        .enqueue(new Callback<SubmittedResponseAck>() {
                            @Override
                            public void onResponse(Call<SubmittedResponseAck> call, Response<SubmittedResponseAck> response) {
                                //TODO what is the response and
                                if(response.code()/100 == 2){
                                    if(response.body().getResponse()!=null)
                                    {Toast.makeText(getApplicationContext(), "Response Submitted successfully",
                                            Toast.LENGTH_SHORT).show();
                                    answered.add(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
                                    Collections.sort(answered);
                                        Log.e("sizeofset",answered.size() + skipList.size() + "");
                                        int a = answered.size() + skipList.size();
                                        if(a == listOfQuestion.size()){
                                            Log.d("lastquestion","all questions answered");
                                            if(!skipList.isEmpty()){
                                                Log.d("onsubmit","new page " + answered.size() + skipList.size() + skipList.get(0));
                                                viewPager.setCurrentItem(skipList.get(0));
                                            }
                                            else{
                                                ScoreCard scoreCard = new ScoreCard();
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("contestId", PlayStaticContest.this.contestDefinition.getContestId());
                                                bundle.putInt("totalQuestions",PlayStaticContest.this.contestDefinition
                                                        .getTotalQuestionsInContest());
                                                scoreCard.setArguments(bundle);
                                                scoreCardHolder.setVisibility(View.VISIBLE);
                                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                                                        .replace(R.id.scoreCardHolder,scoreCard);
                                                fragmentTransaction.commit();
                                            }
                                        }
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "response not sumbmitted" +
                                                "Server issue", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Log.d("responsenotsend",response.code() + "");
                                }
                            }
                            @Override
                            public void onFailure(Call<SubmittedResponseAck> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Server Response Failed - new response to question", Toast.LENGTH_LONG).show();
                                Log.d("responsenotsend",t.getMessage() + "");
                            }
                        });
                }
            }
        });

        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NO of skipped question instead of 3
                if(skipList.size() < contestDefinition.getSkipsAllowed()){
                    if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals(VIDEO)){
//                        if(ViewPagerAdapter.videoView.isPlaying()) {
//                            Log.d("video", "stopped");
//                            VideoView vi= findViewById(R.id.myVideo);
//                            vi.stopPlayback();
//                            Log.e("ID IS PLAYING: ", vi.isPlaying() + "");
//                            //ViewPagerAdapter.videoView.stopPlayback();
//                            Log.d("video", ViewPagerAdapter.videoView.isPlaying()+"..");
//                        }
                    }
                    else if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals(AUDIO)){
                        Log.d("audio", "stopped");
                        if(ViewPagerAdapter.mPlayer!=null && ViewPagerAdapter.mPlayer.isPlaying()){
                            ViewPagerAdapter.mPlayer.stop();
                            ViewPagerAdapter.mPlayer.release();
                            ViewPagerAdapter.mPlayer = null;
                        }
                    }
                    Collections.sort(skipList);
                    skipbutton.setEnabled(false);
                    Toast toast = Toast.makeText(getApplicationContext(), " added " + skipList.toString(), Toast.LENGTH_SHORT);
                    toast.show();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), " No More Skips Allowed ", Toast.LENGTH_LONG);
                    toast.show();
                }

                //make api call
                Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);

                UserResponseService userResponseService = retrofit.create(UserResponseService.class);

                HashMap<String, Object> jsonParams = new HashMap<>();
                jsonParams.put("questionId",listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
                jsonParams.put("contestId", contestId);
                //jsonParams.put("userId", 4);
                //jsonParams.put("username","test");
                jsonParams.put("response", "s");

                Log.d("ApiRetrofitClass","Json Value "+jsonParams.toString());

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                        (new JSONObject(jsonParams)).toString());

                userResponseService.newResponseToQuestion(body,AuthToken.getToken(PlayStaticContest.this))
                        .enqueue(new Callback<SubmittedResponseAck>() {
                            @Override
                            public void onResponse(Call<SubmittedResponseAck> call, Response<SubmittedResponseAck> response) {
                                //TODO what is the response and
                                if(response.code()/100 == 2){
                                    if(response.body().getResponse()!=null)
                                    {Toast.makeText(getApplicationContext(), "Skipped successfully",
                                            Toast.LENGTH_SHORT).show();
                                    skipList.add(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionId());
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "not submitted",
                                                Toast.LENGTH_SHORT);
                                    }
                                }
                                else {
                                    Log.d("responsenotsend",response.code() + "");
                                }
                            }

                            @Override
                            public void onFailure(Call<SubmittedResponseAck> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Server Response Failed - new response to question",Toast.LENGTH_LONG).show();
                                Log.d("responsenotsend",t.getMessage() + "");
                            }
                        });
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("video", listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType());
                if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals(VIDEO)){

                    VideoView vi= findViewById(R.id.myVideo);
                    vi.stopPlayback();
                    Log.e("ID IS PLAYING: ", vi.isPlaying() + "");


                }
                else if(listOfQuestion.get(viewPager.getCurrentItem()).getQuestionType().equals(AUDIO)){
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
