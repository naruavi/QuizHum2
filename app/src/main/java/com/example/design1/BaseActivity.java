package com.example.design1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.design1.activity.LeaderboardActivity;
import com.example.design1.activity.LoginActivity;
import com.example.design1.restcalls.ApiLoginSignUp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BaseActivity extends AppCompatActivity {

    public void goToLeaderBoard(final View view) {
        view.setClickable(false);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setClickable(true);
            }
        }, 500);
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void logOut(View view){
        view.setClickable(false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_AUTH_URL);
                ApiLoginSignUp service = retrofit.create(ApiLoginSignUp.class);
                service.userLogout(AuthToken.getToken(BaseActivity.this))
                        .enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if(response.code()/100 == 2){
                                    if(response.body()!=null){
                                        Toast.makeText(BaseActivity.this, "Logout Successful",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Log.d("logout", "response body null");
                                    }
                                }
                                else {
                                    Log.d("logout", response.code() + " code");
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.d("logout", t.getMessage());
                            }
                        });

            }
        });
    }

}
