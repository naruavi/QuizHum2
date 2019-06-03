package com.example.design1;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.design1.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FirebaseMS extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static int notification_id = 0;

    Bundle bundle = new Bundle();


    // Called when message is received.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Date date = new Date(remoteMessage.getSentTime());

        try {
            // fill in data for creating notification
            Log.e(TAG,remoteMessage.getData().toString());
            Map<String, String> data = remoteMessage.getData();
            bundle.putString("type", data.get("type"));
            bundle.putString("title", data.get("title"));
            bundle.putString("body", data.get("body"));
            bundle.putLong("endTime", Long.valueOf(data.get("endTime")));
            bundle.putLong("startTime",Long.valueOf(data.get("startTime")));

            sendNotification(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onNewToken(String token) {
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
        if(endTime !=  null && startTime != null) {

            if(endTime -new Date().getTime() > 0) {

                if(bundle.get("type").equals("contest")){
                    //for instant notification about the contest
                    scheduleNotification(this, Long.valueOf(dataMap.get("startTime")) - new Date().getTime(), 2);

                    //for reminder notification about the contest
                    scheduleNotification(this, Long.valueOf(dataMap.get("startTime")) - 600000, 2);

                }
                else {
                    scheduleNotification(this, Long.valueOf(dataMap.get("startTime")) - new Date().getTime(), 0);
                }
            }

        }
    }

    public void scheduleNotification(Context context, long delay, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification

        if(delay<0)
            delay = 0;


        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtras(bundle);
        PendingIntent notificationPendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_ONE_SHOT);



        //alarm for scheduling the push notifications
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, notificationPendingIntent);

    }


    private void handleNotificationIntent() {
        // TODO duplicate
        Long endTimeInLong = bundle.getLong("endTime");
        Long startTimeInLong = bundle.getLong("startTime");


        Log.e("end, start time in long", endTimeInLong + "  " + startTimeInLong);
        Date presentDate = new Date();

        long diffEndandPresentTime = (endTimeInLong - presentDate.getTime());
        long diffPresentandStartTime = (presentDate.getTime() - startTimeInLong);

        if(bundle.getString("type").equals("contest")) {
            if (diffEndandPresentTime < 0) {
                //expired
                Log.e(TAG,"In if Question slot has expired");
                //showAlert("Contest Expired", "Sorry, Contest Expired. Please try again next time.");
                Toast.makeText(getApplicationContext(), "Contest Expired", Toast.LENGTH_SHORT).show();
            } else if (diffEndandPresentTime > 0 && diffPresentandStartTime > 0) {
                //contest going on
                Log.e(TAG,"In if contest slot has opened");
                showAlert("Contest is going on", "Hurry up to the dynamic contest section to start playing!");
                //Toast.makeText(getApplicationContext(), "Contest going on", Toast.LENGTH_LONG).show();

                //timer(diffEndandPresentTime);

            } else if (diffPresentandStartTime < 0) {
                //contest not yet started
                Log.e(TAG,"In if contest slot has not yet opened");
                showAlert("Contest will start soon", "The contest will begin shortly.");
                //Toast.makeText(getApplicationContext(), "Contest will start", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (diffEndandPresentTime < 0) {
                //expired
                Log.e(TAG,"In else Question slot has expired");
                showAlert("Question Expired", "Sorry, Contest Expired. Please try again next time.");
                //Toast.makeText(getApplicationContext(), "Contest Expired", Toast.LENGTH_SHORT).show();

            } else if (diffEndandPresentTime > 0 && diffPresentandStartTime > 0) {
                //contest going on

                Log.e(TAG,"In else Question slot opened");
                showAlert("Question slot is opened", "Hurry up to the dynamic contest section to start playing!");
                //Toast.makeText(getApplicationContext(), "Contest going on", Toast.LENGTH_LONG).show();

//                timer(diffEndandPresentTime);

            } else if (diffPresentandStartTime < 0) {
                //contest not yet started
                Log.e(TAG,"In else Question slot didn't open");
                showAlert("Question slot didn't open", "The question will be available shortly.");
                //Toast.makeText(getApplicationContext(), "Contest will start", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showAlert(String title, String message) {
        // build notificaiton
        AlertDialog.Builder notificationPopUpBilder = new AlertDialog.Builder(getApplicationContext());

        notificationPopUpBilder
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", null);

        AlertDialog alert = notificationPopUpBilder.create();
        // show pop up alert
        alert.show();

    }


}