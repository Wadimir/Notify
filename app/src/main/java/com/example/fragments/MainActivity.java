package com.example.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private final int TIMER_OF_PN = 10000;
    private final int TI_VIBRATE = 1000;

    private Timer mTimer = new Timer();
    private Boolean isPause = false;

    Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView bottomnav = findViewById(R.id.navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        else {
            handler.removeCallbacks(runnable);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.action_delightful:
                            selectedFragment = new CameraFragment();
                            break;
                        case R.id.action_share:
                            selectedFragment = new ShareFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
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
    protected void onResume() {
        super.onResume();
        isPause = true;
        handler.removeCallbacks(runnable);
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
