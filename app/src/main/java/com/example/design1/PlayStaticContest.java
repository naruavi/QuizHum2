package com.example.design1;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.design1.Pojo.Question;
import com.example.design1.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayStaticContest extends AppCompatActivity {

    private CustomViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    List<Question> liq;
    Button nextButton;
    Button skipbutton;
    Button previousButton;
    Button submit_btn;
    List<Integer> skipList;
    List<Integer> answered;
    int skipCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_static_contest);

        nextButton = findViewById(R.id.next_btn);
        submit_btn = findViewById(R.id.submit_btn);
        skipbutton =findViewById(R.id.skip_btn);
        previousButton = findViewById(R.id.previous_btn);
        skipList = new ArrayList<>();
        answered= new ArrayList<>();

        //TODO getting list of questions
        HashMap<Integer, String> a = new HashMap<>();
        a.put(1, "A");
        a.put(2, "B");
        a.put(3, "AB");
        a.put(4,"S");
        a.put(5,"S");
        a.put(6, "A");
        skipCount = 0;


        for(Map.Entry entry : a.entrySet()){
            if(entry.getValue().equals("S")){
                skipList.add((Integer) entry.getKey() - 1);
            }
            else{
                answered.add((Integer) entry.getKey() - 1);
            }
        }
        Collections.sort(skipList);
        Collections.sort(answered);
        Log.d("answerandskip", skipList.toString() + answered.toString());

        liq = new ArrayList<>();
        Question q1 = new Question(1, 1, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");
        Question q2 = new Question(1, 2, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");
        Question q3 = new Question(1, 3, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");
        Question q4 = new Question(1, 4, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");
        Question q5 = new Question(1, 5, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");
        Question q6 = new Question(1, 6, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");
        Question q7 = new Question(1, 7, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");
        Question q8 = new Question(1, 8, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");
        Question q9 = new Question(1, 9, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");
        Question q10 = new Question(1, 10, "this is the first questions1", "text",
                "","first option","second option","third option"
                ,"sports","hard","single");


        liq.add(q1);
        liq.add(q2);
        liq.add(q3);
        liq.add(q4);
        liq.add(q5);
        liq.add(q6);
        liq.add(q7);
        liq.add(q8);
        liq.add(q9);
        liq.add(q10);

        allOnClickListeners();

    }

    @Override
    protected void onStart() {
        super.onStart();

        viewPager = findViewById(R.id.PlayStaticViewPager);
        pagerAdapter = new ViewPagerAdapter(PlayStaticContest.this, liq);
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

                if(i == liq.size()-1){
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

                //TODO write retrofit call for return of response
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answered.add(viewPager.getCurrentItem());
                Collections.sort(answered);
                Log.d("skiplistbtn",String.valueOf(viewPager.getCurrentItem()));
                if(skipList.contains(viewPager.getCurrentItem()))
                {
                    int indexOfskip = skipList.indexOf(viewPager.getCurrentItem());
                    try{
                        skipList.remove(indexOfskip);
                        Collections.sort(skipList);
                        Log.d("indexremoved",indexOfskip +"currentpage " + viewPager.getCurrentItem() + skipList.toString());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                skipbutton.setEnabled(false);
                submit_btn.setEnabled(false);
                if(viewPager.getCurrentItem() == liq.size() - 1){
                    nextButton.setEnabled(false);
                }
                else{
                    nextButton.setEnabled(true);
                }
                if((answered.size() + skipList.size()) == liq.size()){
                    if(!skipList.isEmpty()){
                        Log.d("onsubmit","new page " + answered.size() + skipList.size() + skipList.get(0));
                        viewPager.setCurrentItem(skipList.get(0));
                    }
                    else{

                    }
                }
            }
        });

        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NO of skipped question instead of 3
                if(skipList.size() < 3 ){
                    skipList.add(viewPager.getCurrentItem());
                    Collections.sort(skipList);
                    skipbutton.setEnabled(false);
                    Toast toast = Toast.makeText(PlayStaticContest.this, " added " + skipList.toString(), Toast.LENGTH_SHORT);
                    toast.show();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }
                else {
                    Toast toast = Toast.makeText(PlayStaticContest.this, " no more skip ", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
    }
}
