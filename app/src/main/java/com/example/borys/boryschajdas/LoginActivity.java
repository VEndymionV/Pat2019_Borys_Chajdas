package com.example.borys.boryschajdas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private final static String SIMPLE_REGEX_EMAIL_VALIDATION = "^(.)+@(.)+\\.(\\w)+$";
//    private final static String SIMPLE_REGEX_PASSWORD_VALIDATION = "";

//    private  final static String REGEX_EMAIL_VALIDATION = ""

    private SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private boolean validateEmail(String email){

        return(email.length() > 5 && email.matches(SIMPLE_REGEX_EMAIL_VALIDATION));
    }

    private boolean validatePassword(String password){

        return(password.length() >= 8);
    }

    public void startMainActivity(View view){

        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);

        boolean emailIsCorrect, passwordIsCorrect;

        // Walidacja adresu e-mail, jeżeli jest niepoprawny zostaje wyświetlona informacja
        if(!validateEmail(emailInput.getText().toString())){
            emailInput.setError("Niepoprawny adres email");
            emailIsCorrect = false;
        }
        else{
            emailInput.setError(null);
            emailIsCorrect = true;
        }

        // Walidacja hasła, jeżeli jest niepoprawne zostaje wyświetlona informacja
        if(!validatePassword(passwordInput.getText().toString())){
            passwordInput.setError("Niepoprawne hasło");
            passwordIsCorrect = false;
        }
        else{
            passwordInput.setError(null);
            passwordIsCorrect = true;
        }

        if(emailIsCorrect && passwordIsCorrect){

            SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
            preferencesEditor.putString("email", emailInput.getText().toString());
            preferencesEditor.apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
    }
}
