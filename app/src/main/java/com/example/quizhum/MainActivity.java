package com.example.quizhum;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quizhum.Fragment.TextFragment;
import com.example.quizhum.Pojo.ContestObject;
import com.example.quizhum.Pojo.DetailsOfContest;
import com.example.quizhum.Pojo.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<Integer, String> a = new HashMap<>();
        a.put(1, "lye");
        a.put(2, "lye");
        a.put(3, "lye");


        List<Question> liq = new ArrayList<>();
        Question q1 = new Question(1, 1, "this is the first questions", "text", a);
        Question q2 = new Question(1, 1, "this is the first questions", "text", a);
        Question q3 = new Question(1, 1, "this is the first questions", "text", a);
        Question q4 = new Question(1, 1, "this is the first questions", "text", a);
        Question q5 = new Question(1, 1, "this is the first questions", "text", a);

        liq.add(q1);
        liq.add(q1);
        liq.add(q1);
        liq.add(q1);
        liq.add(q1);


//        DetailsOfContest d  = new DetailsOfContest(1,"fdiem", 2, 5);
//
//        ContestObject contestObject = new ContestObject(a,d,liq);
//
//       // Fragment fragment = new TextFragment(contestObject);
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//       // ft.replace(R.id.back,fragment);
//        ft.commit();
    //}
    }

}
