package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import edu.fpt.shose_app.R;

public class splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //do something
                startActivity(new Intent(splash_activity.this, Onboard_Activity.class));
            }
        }, 3000 );//time in milisecond
    }
}