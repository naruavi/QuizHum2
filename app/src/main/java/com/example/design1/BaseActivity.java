package com.example.design1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        ClickHandler.clickDelay(view);
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void logOut(View view) {
        //Log.e("BASE ACTIVITY", "Logout is being called");
        ClickHandler.clickDelay(view);

        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_AUTH_URL);
        ApiLoginSignUp service = retrofit.create(ApiLoginSignUp.class);
        service.userLogout(AuthToken.getToken(BaseActivity.this))
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code() / 100 == 2) {
                            if (response.body() != null) {
                                //Log.e("logout", "logout is success");
                                Toast.makeText(getApplicationContext(), "Logout Successful",
                                        Toast.LENGTH_SHORT).show();
                                deleteSesssionId();
                                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                //Log.d("logout", "response body null");
                            }
                        } else {
                            //Log.d("logout", response.code() + " code");
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Logout has failed", Toast.LENGTH_SHORT).show();
                        //Log.d("logout failed", t.getMessage());
                    }
                });
    }

    public void deleteSesssionId() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.shared_pref_session_id), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("TOKEN");
        editor.apply();
    }

}
