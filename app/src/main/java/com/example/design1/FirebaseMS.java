package com.example.design1;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
            Map<String, String> data = remoteMessage.getData();
            bundle.putString("type", data.get("type"));
            bundle.putString("title", data.get("title"));
            bundle.putString("body", data.get("body"));
            bundle.putString("endtime", data.get("endTime"));
            bundle.putString("starttime", data.get("startTime"));

            sendNotification(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onNewToken(String token) {
        getSharedPreferences(getString(R.string.shared_pref_firebase), MODE_PRIVATE)
                .edit()
                .putString(getString(R.string.firebase_token), token)
                .apply();

        // suscribe the token to topic "All"
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
        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Notification", bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        String channelId = getString(R.string.dynamic_contest_notif_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.download111)
                        .setContentTitle(dataMap.get("title"))
                        .setContentText(dataMap.get("body"))
                        .setTimeoutAfter(15000)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // TODO user random id
        notificationManager.notify(notification_id++, notificationBuilder.build());
    }
}