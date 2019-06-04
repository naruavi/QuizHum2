package com.example.design1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) BaseActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return (activeNetwork!=null && activeNetwork.isConnectedOrConnecting());
        } else {
            return false;
        }
    }

    public AlertDialog.Builder buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }

    public void enableBackToolbar(int resourceId) {
        Toolbar toolbar = (Toolbar) findViewById(resourceId);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
