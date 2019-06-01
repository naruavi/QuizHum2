package com.example.design1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.design1.activity.DailyLeaderBoard;
import com.example.design1.activity.MonthlyLeaderboard;
import com.example.design1.activity.WeeklyLeaderboard;

public class TabsAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;
    public TabsAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch(i){
            case 0: MonthlyLeaderboard monthlyLeaderboard=new MonthlyLeaderboard();
                    return monthlyLeaderboard;

            case 1: WeeklyLeaderboard weeklyLeaderboard= new WeeklyLeaderboard();
                    return weeklyLeaderboard;

            case 2: DailyLeaderBoard dailyLeaderBoard = new DailyLeaderBoard();
                    return dailyLeaderBoard;

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
