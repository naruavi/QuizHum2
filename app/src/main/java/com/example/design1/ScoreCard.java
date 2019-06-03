package com.example.design1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    private Button getTotal;

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
        CorrectAnswer = view.findViewById(R.id.CorrectAnswer);
        WrongAnswer = view.findViewById(R.id.WrongAnswer);
        TotalScore = view.findViewById(R.id.TotalScore);
        getTotal = view.findViewById(R.id.endContest);
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

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        responseService.getContestResult(body, AuthToken.getToken(getContext()))
                .enqueue(new Callback<ScoreCardObject>() {
                    @Override
                    public void onResponse(Call<ScoreCardObject> call, Response<ScoreCardObject> response) {
                        if(response.code()/100 == 2){
                            if (response.body()!=null){
                                CorrectAnswer.setText(response.body().getCorrectAnswers());
                                WrongAnswer.setText(totalQuestion - response.body().getCorrectAnswers());
                                TotalScore.setText(response.body().getTotalScore());
                            }
                            else{
                                Log.d("Final Score",  "nothing in body");
                            }
                        }
                        else{
                            Log.d("Final Score", response.code() + " code");
                        }
                    }
                    @Override
                    public void onFailure(Call<ScoreCardObject> call, Throwable t) {
                        Log.d("Final Score", t.getMessage());
                    }
                });
    }
}
