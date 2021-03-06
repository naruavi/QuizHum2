package com.example.design1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.design1.BaseActivity;
import com.example.design1.ClickHandler;
import com.example.design1.Pojo.LoginUserData;
import com.example.design1.R;
import com.example.design1.RetrofitClient;
import com.example.design1.models.HttpResponse;
import com.example.design1.restcalls.ApiLoginSignUp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {

    private Button loginButton;
    private EditText userId;
    private EditText password;
    private LoginUserData loginData;
    private TextView signupButton;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isConnected()) buildDialog().show();
        setContentView(R.layout.activity_login);
        init();
    }


    private void init() {

        userId = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickHandler.clickDelay(v);

                loginData = new LoginUserData();
                loginData.setUserId(userId.getText().toString());
                loginData.setPassword(password.getText().toString());

                if (userId.getText().toString().length() == 0
                        || password.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Can not leave fields Empty", Toast.LENGTH_SHORT).show();

                    //Email login not implemented now
//                    if (userId.getText().toString().trim().matches(emailPattern))
//                    {
//                        //If Email
//                        userVerifyThroughEmail();
//                    }
                } else {
                    userVerify();
                }
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickHandler.clickDelay(v);
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);

            }
        });
    }


    private void userVerifyThroughEmail(String user) {
        userVerify();
    }

    private void userVerifyThroughPass() {
        userVerify();
    }


    private void userVerify() {

        Retrofit retrofit = RetrofitClient.getRetrofitAuthClient();
        ApiLoginSignUp api = retrofit.create(ApiLoginSignUp.class);

        Log.d("LoginLog", "ApiLoginSignUp built, calling");

        api.userLogin(userId.getText().toString(), password.getText().toString())
                .enqueue(new Callback<HttpResponse>() {
                    @Override
                    public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                        Log.e("login: ", response.code()+ " code");
                        if (response.code() == 401) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            //TODO make toast
                            finish();
                        } else if (response.code() / 100 == 5) {
                            Toast.makeText(getApplicationContext(), "Internal Server Error, please come back later.",
                                    Toast.LENGTH_LONG).show();

                        } else if (response.code() / 100 == 2) {
                            if (response.headers().get("Set-Cookie") != null) {
                                storeSesssionId(response.headers().get("Set-Cookie")
                                        .split(";")[0].split("=")[1]);
                                Log.d("sessionid", response.headers().get("Set-Cookie").split(";")[0].split("=")[1]);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            if (response.body() != null) {
                                Log.d("body", response.body().getRole().toString() + response.headers().get("JSESSIONID"));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                            Log.d("responsecode", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<HttpResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connecting to server failed", Toast.LENGTH_SHORT).show();
                        Log.e("loginfailure", t.getMessage());
                    }
                });
    }

    public void storeSesssionId(String sessionId) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.shared_pref_session_id), Context.MODE_PRIVATE);
        String tempString = "SESSION=" + sessionId;
        Log.d("tempString", tempString);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TOKEN", tempString);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
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
}