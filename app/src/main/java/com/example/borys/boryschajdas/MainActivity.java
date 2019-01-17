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
        // Return to home menu
        moveTaskToBack(true);
    }

    // logout button onClick()
    public void logOut(View view){
        if(!sharedPreferences.contains("remember"))
            clearUserSharedPreferences();
        startLoginActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
    }

    private void clearUserSharedPreferences(){
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

    private void startLoginActivity(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}