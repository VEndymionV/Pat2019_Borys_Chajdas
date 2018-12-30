package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

    private final static long SPLASH_SCREEN_DURATION = 5000L; // 5 seconds

    private Handler mainActivityHandler;

    private void finishSplashScreen(){
        // Run MainActivity
        startActivity(new Intent(this, MainActivity.class));
        // Finish this activity
        finish();
    }

    @Override
    public void onBackPressed() {
        // Stop the MainActivity launch
        mainActivityHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Wait x seconds then run finishSplashScreen() method
        mainActivityHandler = new Handler();
        mainActivityHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finishSplashScreen();
            }
        }, SPLASH_SCREEN_DURATION);
    }
}