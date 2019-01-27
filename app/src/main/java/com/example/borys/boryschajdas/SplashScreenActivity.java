package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

    public final static long SPLASH_SCREEN_DURATION = 5000L;

    private Handler loginActivityHandler;

    private void startLoginActivity(){

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void startMainActivity(){

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

        loginActivityHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        User user = new User(getSharedPreferences(User.SHARED_PREFERENCES_USER_DATA, MODE_PRIVATE));

        if(user.isLoggedIn()){
            startMainActivity();
        } else {
            loginActivityHandler = new Handler();
            loginActivityHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLoginActivity();
                }
            }, SPLASH_SCREEN_DURATION);
        }
    }
}