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

    private boolean validateEmail(String email){

        return(email.length() > 5 && email.matches(Config.REGEX_EMAIL_VALIDATION));
    }

    private boolean validatePassword(String password){

        return(password.length() >= 8);
    }

    public void logIn(View view){

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

            if(((CheckBox)findViewById(R.id.rememberUser_checkbox)).isChecked()){
                preferencesEditor.putString("password", passwordInput.getText().toString());
                preferencesEditor.putBoolean("remember", true);
                Log.d("UWAAAAAAGA", "TAAAAK");
            }
            // TODO to nie powinno być konieczne, bo w MainActivity już czyszczę ale bez tego nie działa
            else{
                preferencesEditor.remove("password");
                preferencesEditor.remove("remember");
            }

            preferencesEditor.apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);

        if(sharedPreferences.contains("remember")){
            emailInput.setText(sharedPreferences.getString("email", ""));
            passwordInput.setText(sharedPreferences.getString("password", ""));
        }
    }
}
