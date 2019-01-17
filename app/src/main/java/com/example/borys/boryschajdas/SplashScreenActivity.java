package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

    private Handler loginActivityHandler;

    private void startLoginActivity(){
        // Run LoginActivity
        startActivity(new Intent(this, LoginActivity.class));
        // Finish this activity
        finish();
    }

    private void startMainActivity(){
        // Run LoginActivity
        startActivity(new Intent(this, MainActivity.class));
        // Finish this activity
        finish();
    }

    @Override
    public void onBackPressed() {
        // Stop the MainActivity launch
        loginActivityHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);

        if (sharedPreferences.contains("email")) {
            startMainActivity();
        } else {
            // Wait x seconds then run startLoginActivity() method
            loginActivityHandler = new Handler();
            loginActivityHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLoginActivity();
                }
            }, Config.SPLASH_SCREEN_DURATION);
        }
    }
}