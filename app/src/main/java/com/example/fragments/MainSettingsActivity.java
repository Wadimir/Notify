package com.example.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.concurrent.TimeUnit;

public class MainSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);

        Button play = (Button) findViewById(R.id.button);
        NumberPicker np = (NumberPicker) findViewById(R.id.numberPicker);
        np.setMaxValue(10800000);
        np.setMinValue(0);
    }

}
