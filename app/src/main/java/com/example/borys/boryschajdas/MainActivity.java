package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    public void logOut(View view){

        if(!sharedPreferences.getBoolean(Config.SHARED_PREFERENCES_FIELD_REMEMBER_USER_DATA, false))
            clearUserSharedPreferences(false);
        else
            clearUserSharedPreferences(true);
        startLoginActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREFERENCES_USER_DATA, MODE_PRIVATE);
    }

    private void clearUserSharedPreferences(boolean rememberData){

        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        if(rememberData)
            preferencesEditor.putBoolean(Config.SHARED_PREFERENCES_FIELD_LOGGEDIN, false);
        else
            preferencesEditor.clear();
        preferencesEditor.apply();
    }

    private void startLoginActivity(){

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
