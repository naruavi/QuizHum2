package com.example.design1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.design1.activity.DynamicContestActivity;
import com.example.design1.activity.MainActivity;

import java.util.Date;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(final Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if(bundle.getString("type").equals("question")) {
            SharedPreferences.Editor sharedPreferencesEditor = context.getSharedPreferences(context.getString(R.string.shared_pref_session_id), Context.MODE_PRIVATE).edit();
            sharedPreferencesEditor.putInt("questionId",bundle.getInt("questionId"));
            sharedPreferencesEditor.putLong("endTime",bundle.getLong("endTime"));
            sharedPreferencesEditor.putLong("startTime",bundle.getLong("startTime"));
            //(context.getSharedPreferences(context.getString(R.string.shared_pref_session_id), Context.MODE_PRIVATE)).edit().putInt("questionId", bundle.getInt("questionId")).apply();
            sharedPreferencesEditor.apply();
        }
        Intent onOpenIntent = new Intent(context.getApplicationContext(), MainActivity.class);
        onOpenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        onOpenIntent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0/* Request code */, onOpenIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        String channelId = context.getResources().getString(R.string.dynamic_contest_notif_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        long longPresentTime = bundle.getLong("endTime") - new Date().getTime();
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.notification_launcher)
                        .setContentTitle(bundle.getString("title"))
                        .setContentText(bundle.getString("body"))
                        .setTimeoutAfter(longPresentTime)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.notification_launcher)).getBitmap());




        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = notificationBuilder.build();
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);

    }
}