package com.example.design1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.design1.BaseActivity;
import com.example.design1.R;

public class QuestionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected()) buildDialog().show();
        setContentView(R.layout.activity_question);
    }
}
