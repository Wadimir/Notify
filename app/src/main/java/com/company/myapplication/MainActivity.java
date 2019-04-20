package com.company.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private final int TIMER_OF_PN = 10000;
    private final int TI_VIBRATE = 1000;

    private Timer mTimer = new Timer();
    private Boolean isPause = false;

    Handler handler = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        else {
            handler.removeCallbacks(runnable);
        }

    }

    private void addNotification() {

        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/sounds");

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.title_program))
                        .setContentText(getString(R.string.notification_text))
                        .setLargeIcon(bitmap)
                        .setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_SOUND |
                                Notification.DEFAULT_VIBRATE)
                        .setSound(sound)
                        .setVibrate(new long[]{TI_VIBRATE, TI_VIBRATE, TI_VIBRATE, TI_VIBRATE, TI_VIBRATE})
                        .setLights(Color.RED, 0, 1)
                        .setContentIntent(resultPendingIntent);


        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;

        handler.postDelayed(runnable, TIMER_OF_PN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isPause = false;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isPause) {
                addNotification();
            }
        }
    };
}

