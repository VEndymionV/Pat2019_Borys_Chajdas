package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private User user;

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    public void logOut(View view){

        user.logOut();
        startLoginActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User(getSharedPreferences(User.SHARED_PREFERENCES_USER_DATA, MODE_PRIVATE));
    }

    private void startLoginActivity(){

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
