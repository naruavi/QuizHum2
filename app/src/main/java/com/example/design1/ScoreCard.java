package com.example.design1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.design1.activity.LoginActivity;
import com.example.design1.activity.PlayStaticContest;
import com.example.design1.models.ScoreCardObject;
import com.example.design1.restcalls.UserResponseService;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ScoreCard extends Fragment {

    private TextView CorrectAnswer, WrongAnswer, TotalScore;
    private Button endContest;

    public static ScoreCard newInstance(int contestId, int totalQuestion){
        Bundle bundle = new Bundle();
        bundle.putInt("contestId", contestId);
        bundle.putInt("totalQuestion", totalQuestion);
        ScoreCard scoreCard = new ScoreCard();
        return scoreCard;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score_card, container, false);
        return view;
    }


    //All constants are to be replaced
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        int contestId = savedInstanceState.getInt("contestId");
//        final int totalQuestion = savedInstanceState.getInt("totalQuestion");
        Bundle bundle = getArguments();
        int contestId = bundle.getInt("contestId");
        final int totalQuestion = bundle.getInt("totalQuestions");
        Retrofit retrofit=ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_RESPONSE_URL);
        UserResponseService responseService=retrofit.create(UserResponseService.class);
        Log.d("Final Score",  contestId+" contestId");

        HashMap<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("contestId", contestId);
        //jsonParams.put("userId", 4);
        //jsonParams.put("username","test");

        Log.d("ApiRetrofitClass","Json Value "+jsonParams.toString());


        CorrectAnswer = view.findViewById(R.id.CorrectAnswer);
        WrongAnswer = view.findViewById(R.id.WrongAnswer);
        TotalScore = view.findViewById(R.id.TotalScore);
        endContest = view.findViewById(R.id.endContest);

        endContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PlayStaticContest)v.getContext()).finish();
            }
        });

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        responseService.getContestResult(body, AuthToken.getToken(getContext()))
                .enqueue(new Callback<ScoreCardObject>() {
                    @Override
                    public void onResponse(Call<ScoreCardObject> call, Response<ScoreCardObject> response) {

                        if(response.code()==401){
                            FragmentActivity fragmentActivity = getActivity();
                            if(fragmentActivity!=null) {
                                Intent intent = new Intent(fragmentActivity.getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                        else if(response.code()/100 ==  5){
                            FragmentActivity fragmentActivity = getActivity();
                            if(fragmentActivity!=null){
                            Toast.makeText(fragmentActivity.getApplicationContext(),"Internal Server Error, please come back later.",
                                    Toast.LENGTH_LONG).show();
                            }
                        }


                        else if(response.code()/100 == 2){
                            if (response.body()!=null){
                                Log.e("finalscoresu", response.body().getCorrectAnswers() + "");
                                try{
                                CorrectAnswer.setText(String.valueOf(response.body().getCorrectAnswers()));
                                WrongAnswer.setText(String.valueOf(totalQuestion - response.body().getCorrectAnswers()));
                                TotalScore.setText(String.valueOf(response.body().getTotalScore()));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                            else{
                                Log.e("Final Score",  "nothing in body");
                            }
                        }
                        else{
                            Log.d("Final Score", response.code() + " code");
                        }
                    }
                    @Override
                    public void onFailure(Call<ScoreCardObject> call, Throwable t) {
                        FragmentActivity fragmentActivity = getActivity();
                        if(fragmentActivity != null)  {
                            Toast.makeText(fragmentActivity.getApplicationContext(),"Get contest result - Server Response Failure", Toast.LENGTH_LONG).show();
                            Log.e("Response LeaderBoard","Failure response");
                        }
                    }
                });
    }
}
