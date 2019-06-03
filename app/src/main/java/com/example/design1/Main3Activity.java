package com.example.design1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = Main3Activity.class.toString();

    AlertDialog.Builder notificationPopUpBilder;
    private long timeLeft = 0;
    private TextView timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);

        notificationPopUpBilder = new AlertDialog.Builder(this);

        //createNotificationChannel();

        if (getIntent().getBundleExtra("Notification") != null) {
            handleNotificationIntent();
        }
    }

    // Creates a notification channel for high priority notifications
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.dynamic_contest_notif_channel_name);
            String description = getString(R.string.dynamic_contest_notif_channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            String channelId = getString(R.string.dynamic_contest_notif_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    // Used to handle click on notification
    private void handleNotificationIntent() {
        // TODO duplicate
        Bundle bundle = getIntent().getBundleExtra("Notification");
        Log.e("bundle", bundle.toString());
        String endTime = bundle.getString("endtime");
        String startTime = bundle.getString("starttime");
        Long endTimeInLong = Long.valueOf(endTime);
        Long startTimeInLong = Long.valueOf(startTime);

        Date endDate = new Date(endTimeInLong);
        Date startDate = new Date(startTimeInLong);

        Log.e("end, start time in date", endDate + "  " + startDate);
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        // TODO server time
        Date presentDate = new Date();

        long diffEndandPresentTime = (endDate.getTime() - presentDate.getTime());
        long diffPresentandStartTime = (presentDate.getTime() - startDate.getTime());

        if (diffEndandPresentTime < 0) {
            //expired
            showAlert("Contest Expired", "Sorry, Contest Expired. Please try again next time.");
            Toast.makeText(getApplicationContext(), "Contest Expired", Toast.LENGTH_SHORT).show();
        } else if (diffEndandPresentTime > 0 && diffPresentandStartTime > 0) {
            //contest going on
            showAlert("Contest is going on", "Hurry up to the dynamic contest section to start playing!");
            Toast.makeText(getApplicationContext(), "Contest going on", Toast.LENGTH_LONG).show();

            timer(diffEndandPresentTime);

        } else if (diffPresentandStartTime < 0) {
            //contest not yet started
            showAlert("Contest will start soon", "The contest will begin shortly.");
            Toast.makeText(getApplicationContext(), "Contest will start", Toast.LENGTH_SHORT).show();
        }
    }

    public void timer(long diffEndandPresentTime){
        timer = findViewById(R.id.timer);
        //timer.setText(diffEndandPresent+"");
        timeLeft = diffEndandPresentTime;
        CountDownTimer downTimer = new CountDownTimer(timeLeft, 1) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void updateTimer(){
        String timeText= String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(timeLeft),
                TimeUnit.MILLISECONDS.toMinutes(timeLeft) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeLeft)),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft)));

        timer.setText(timeText);
    }

    // Shows an informative pop up on the screen
    private void showAlert(String title, String message) {
        // build notificaiton
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
