package com.example.tokyo2020;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleByDayActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default" ;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_by_day);

        Toolbar myChildTb = (Toolbar) findViewById(R.id.toolbarChild);
        setSupportActionBar(myChildTb);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String url = i.getStringExtra("url");
        String isBtnVisible = i.getStringExtra("is_btn_visible");
        WebView byDay = (WebView) findViewById(R.id.webviewSearchByDay);
        WebSettings webSettings = byDay.getSettings();
        webSettings.setJavaScriptEnabled(true);
        byDay.setWebViewClient(new WebViewClient());
        byDay.loadUrl(url);
        Button reminderBtn = (Button) findViewById(R.id.signUpForReminderBtn);
        if(isBtnVisible.contentEquals("false")) {
            reminderBtn.setVisibility(View.GONE);
        }
        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNotification(getNotification()) ;
            }
        });
    }

    private Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle("Olympic Game Reminder");
        builder.setContentText("Your event is happening today");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        return builder.build() ;
    }

    private void scheduleNotification(Notification notification) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher.class ) ;
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent.getBroadcast( this, 0 , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT ) ;

        long futureInMillis = SystemClock.elapsedRealtime() + 5000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }
}
