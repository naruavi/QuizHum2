package com.example.design1.activity;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.design1.ApiRetrofitClass;
import com.example.design1.BaseActivity;
import com.example.design1.CONSTANTS;
import com.example.design1.ClickHandler;
import com.example.design1.Pojo.SignupUserData;
import com.example.design1.R;
import com.example.design1.RetrofitClient;
import com.example.design1.restcalls.ApiLoginSignUp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends BaseActivity {


    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private EditText emailId, name, password, confPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected()) buildDialog().show();
        setContentView(R.layout.activity_sign_up);

        Button signUpButton = findViewById(R.id.signup_button);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickHandler.clickDelay(v);

                getUserData();

                if (emailId.getText().toString().length() == 0
                        || name.getText().toString().length() == 0
                        || password.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Can not leave fields Empty", Toast.LENGTH_SHORT).show();
                }
                else if (emailId.getText().toString().trim().matches(emailPattern)) {
                    if (password.getText().toString().equals(confPass.getText().toString())) {
                        init();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void getUserData() {
        emailId = findViewById(R.id.signup_userEmail);
        name = findViewById(R.id.signup_userName);
        password = findViewById(R.id.signup_passWord);
        confPass = findViewById(R.id.signup_confirmPass);
    }

    private void init() {
        Retrofit retrofit = ApiRetrofitClass.getNewRetrofit(CONSTANTS.USER_AUTH_URL);

        ApiLoginSignUp api = retrofit.create(ApiLoginSignUp.class);
        SignupUserData user = new SignupUserData();

        user.setEmail(emailId.getText().toString());
        user.setUsername(name.getText().toString());
        user.setPassword(password.getText().toString());
        user.setName(name.getText().toString());
        user.setRole("PLAYER");

        Log.d("SignUp", user.toString());

        api.userSignUp(user).enqueue(new Callback<Object>() {
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
                else if (response.body()!=null){
                    Log.d("signup", "response not null");
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else if(response.code() == 200){
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    Toast.makeText(getApplicationContext(),"Signed up Succesfully - Sign In to continue", Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                    //Will be here if response.code() is 200
                    //TODO
                    //Go to landing page
                }
                else{
                    Toast.makeText(getApplicationContext(),"Already User Exist", Toast.LENGTH_SHORT).show();
                    Log.d("responsenot200", "signup Failed");
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("SignUpfailure", t.getMessage());
                Toast.makeText(getApplicationContext(),"SignUp failure - Response Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
