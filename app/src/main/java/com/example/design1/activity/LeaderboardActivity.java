package com.example.design1.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.design1.BaseActivity;
import com.example.design1.R;
import com.example.design1.adapter.TabsAdapter;

public class LeaderboardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected()) buildDialog().show();

        Intent intent = getIntent();
        Integer contestId = intent.getIntExtra("contestId", -1);
        if(contestId != -1){
            //tabLayout.setVisibility(View.GONE);
            setContentView(R.layout.contest_leaderboard_layout);

            createToolbar(R.id.contest_leaderboard_toolbar);

            ContestLeaderboard contestLeaderboard =  new ContestLeaderboard();

            Bundle bundle = new Bundle();

            bundle.putInt("contestId",contestId);

            contestLeaderboard.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().add(R.id.contest_leaderboard_frame_layout,contestLeaderboard).commit();

        }

        else {
        /*Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);*/

            setContentView(R.layout.activity_leaderboard);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

            createToolbar(R.id.leaderboard_toolbar);

            tabLayout.addTab(tabLayout.newTab().setText("Monthly"));
            tabLayout.addTab(tabLayout.newTab().setText("Weekly"));
            tabLayout.addTab(tabLayout.newTab().setText("Daily"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(tabsAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void createToolbar(int resourceId){

        Toolbar toolbar = findViewById(resourceId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        // set white color https://stackoverflow.com/a/14986834
        /*int labelColor = getResources().getColor(R.color.black);
        String сolorString = String.format("%X", labelColor).substring(2); // !!strip alpha value!!
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\">Leaderboard</font>", сolorString)));
*/

    }
}
