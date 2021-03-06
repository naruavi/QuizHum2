package com.example.design1;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class FirebaseMS extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    //TODO remove unnecessary variables
    //done
    
    //TODO avoid global variable where possible
    //Bundle bundle = new Bundle();


    // Called when message is received.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Date date = new Date(remoteMessage.getSentTime());

        try {

            // fill in data for creating notification
            Log.d(TAG, "remote message data: " + remoteMessage.getData().toString());
            Map<String, String> data = remoteMessage.getData();
            //TODO unnecessary usage of bundle
            //done
            sendNotification(data);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onNewToken(String token) {
        //TODO do not use string resource for non-translable vlues
        getSharedPreferences(getResources().getString(R.string.shared_pref_firebase), MODE_PRIVATE)
                .edit()
                .putString(getString(R.string.firebase_token), token)
                .apply();

        // suscribe the token to topic "All"
        Log.e(TAG, "token: " + token);
        subscribeToAll(token);
    }

    public static String getToken(Context context) {
        return context
                .getSharedPreferences(Resources.getSystem().getString(R.string.shared_pref_firebase), MODE_PRIVATE)
                .getString(Resources.getSystem().getString(R.string.firebase_token), "noFirebaseToken");
    }

    private void subscribeToAll(String token) {
        FirebaseMessaging.getInstance().subscribeToTopic("All")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "subscribe to all";
                        if (!task.isSuccessful()) {
                            msg = "subscription failed";
                        }
                        //TODO log msgs which should not be shown to user
                        Toast.makeText(FirebaseMS.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param //messageBody FCM message body received.
     */
    private void sendNotification(Map<String, String> dataMap) {
        Long endTime = Long.valueOf(dataMap.get("endTime"));
        Long startTime = Long.valueOf(dataMap.get("startTime"));

        Log.e(TAG,dataMap.toString());
        if((endTime !=  null && startTime != null) && (endTime - getCurrentDateTime() > 0)) {

            //TODO make method for more code readibility and scalbility
            //done

            Bundle bundle = new Bundle();

            bundle.putString("type", dataMap.get("type"));
            bundle.putString("title", dataMap.get("title"));
            bundle.putString("body", dataMap.get("body"));
            bundle.putLong("endTime", Long.valueOf(dataMap.get("endTime")));
            bundle.putLong("startTime", Long.valueOf(dataMap.get("startTime")));
            if(!dataMap.get("type").equals("contest"))
                bundle.putInt("cqid", Integer.parseInt(dataMap.get("cqid")));


            if(bundle.get("type").equals("contest")){
                //for instant notification about the contest
                scheduleNotification(this, bundle,Long.valueOf(dataMap.get("startTime")) - getCurrentDateTime() , 0);

                //for reminder notification about the contest
                //scheduleNotification(this, Long.valueOf(dataMap.get("startTime")) - 600000, 2);

            }
            else {
                scheduleNotification(this, bundle,Long.valueOf(dataMap.get("startTime")) - getCurrentDateTime(), 1);
            }

        }
    }

    private Long getCurrentDateTime() {
        return getCurrentDateTime();
    }

    public void scheduleNotification(Context context,Bundle bundle, long delay, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification

        if(delay<0)
            delay = 0;


        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtras(bundle);
        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_ONE_SHOT);



        //alarm for scheduling the push notifications
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, notificationPendingIntent);

    }





}