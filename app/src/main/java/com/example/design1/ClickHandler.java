package com.example.design1;

import android.view.View;

public class ClickHandler {

    public static void clickDelay(final View v) {
        v.setClickable(false);
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setClickable(true);
            }
        }, 500);
    }
}
