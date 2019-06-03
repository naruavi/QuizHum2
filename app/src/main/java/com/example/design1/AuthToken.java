package com.example.design1;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthToken {

    public static String getToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.shared_pref_session_id), Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("TOKEN",null);
        return s;
    }

}
