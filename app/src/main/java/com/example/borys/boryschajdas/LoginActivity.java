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

    private EditText emailInput;
    private EditText passwordInput;

    private User user;

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    private void startMainActivity(){

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void logIn(View view){

        Validator fieldValidator = new Validator(emailInput.getText().toString(), passwordInput.getText().toString());

        if(fieldValidator.getEmailValidationState()){
            emailInput.setError(null);
        }
        else{
            emailInput.setError(getString(R.string.bad_email_error));
        }

        if(fieldValidator.getPasswordValidationState()){
            passwordInput.setError(null);
        }
        else{
            passwordInput.setError(getString(R.string.bad_password_error));
        }

        if(fieldValidator.getValidationState()){

            user.logIn(emailInput.getText().toString(), passwordInput.getText().toString());
            user.setRememberUser(((CheckBox)findViewById(R.id.rememberUser_checkbox)).isChecked());
            startMainActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = new User(getSharedPreferences(User.SHARED_PREFERENCES_USER_DATA, MODE_PRIVATE));

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        if(user.getRememberUser()){
            emailInput.setText(user.getEmail());
            passwordInput.setText(user.getPassword());
        }
    }
}
