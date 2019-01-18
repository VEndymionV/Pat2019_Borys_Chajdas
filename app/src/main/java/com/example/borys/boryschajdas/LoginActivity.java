package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private SharedPreferences sharedPreferences;
    private EditText emailInput;
    private EditText passwordInput;

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    private void startMainActivity(){

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void saveLoginDataInSharedPreferences(){

        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putString(Config.SHARED_PREFERENCES_FIELD_EMAIL, emailInput.getText().toString());

        if(((CheckBox)findViewById(R.id.rememberUser_checkbox)).isChecked()){
            preferencesEditor.putString(Config.SHARED_PREFERENCES_FIELD_PASSWORD, passwordInput.getText().toString());
            preferencesEditor.putBoolean(Config.SHARED_PREFERENCES_FIELD_REMEMBER_USER_DATA, true);
        }
        // TODO to nie powinno być konieczne, bo w MainActivity już czyszczę ale bez tego nie działa
        else{
//            preferencesEditor.remove(Config.SHARED_PREFERENCES_FIELD_PASSWORD);
//            preferencesEditor.remove(Config.SHARED_PREFERENCES_FIELD_REMEMBER_USER_DATA);
        }

        preferencesEditor.apply();
    }

    private boolean validateEmail(String email){

        return(email.length() >= 5 && email.matches(Config.REGEX_EMAIL_VALIDATION));
    }

    private boolean validatePassword(String password){

        return(password.length() >= Config.PASSWORD_LENGTH && password.matches(Config.REGEX_PASSWORD_VALIDATION));
    }

    public void logIn(View view){

        boolean emailIsCorrect, passwordIsCorrect;

        if(!validateEmail(emailInput.getText().toString())){
            emailInput.setError(getString(R.string.bad_email_error));
            emailIsCorrect = false;
        }
        else{
            emailInput.setError(null);
            emailIsCorrect = true;
        }

        if(!validatePassword(passwordInput.getText().toString())){
            passwordInput.setError(getString(R.string.bad_password_error));
            passwordIsCorrect = false;
        }
        else{
            passwordInput.setError(null);
            passwordIsCorrect = true;
        }

        if(emailIsCorrect && passwordIsCorrect){

            saveLoginDataInSharedPreferences();
            startMainActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        sharedPreferences = getSharedPreferences(Config.SHARED_PREFERENCES_USER_DATA, MODE_PRIVATE);

        if(sharedPreferences.contains(Config.SHARED_PREFERENCES_FIELD_REMEMBER_USER_DATA)){
            emailInput.setText(sharedPreferences.getString(Config.SHARED_PREFERENCES_FIELD_EMAIL, ""));
            passwordInput.setText(sharedPreferences.getString(Config.SHARED_PREFERENCES_FIELD_PASSWORD, ""));
        }
    }
}
