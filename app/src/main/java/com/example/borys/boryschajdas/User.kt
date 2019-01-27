package com.example.borys.boryschajdas

import android.content.SharedPreferences

class User(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val SHARED_PREFERENCES_USER_DATA = "User"
        private const val SHARED_PREFERENCES_FIELD_EMAIL = "email"
        private const val SHARED_PREFERENCES_FIELD_PASSWORD = "password"
        private const val SHARED_PREFERENCES_FIELD_REMEMBER_USER_DATA = "remember"
        private const val SHARED_PREFERENCES_FIELD_LOGGED_IN = "loggedIn"
    }

    fun getEmail(): String {

        return sharedPreferences.getString(SHARED_PREFERENCES_FIELD_EMAIL, "")
    }

    fun setEmail(email: String){

        sharedPreferences.edit().putString(SHARED_PREFERENCES_FIELD_EMAIL, email).apply()
    }

    fun getPassword(): String{

        return sharedPreferences.getString(SHARED_PREFERENCES_FIELD_PASSWORD, "")
    }

    fun setPassword(password: String){

        sharedPreferences.edit().putString(SHARED_PREFERENCES_FIELD_PASSWORD, password).apply()
    }

    fun getRememberUser(): Boolean{
        return sharedPreferences.getBoolean(SHARED_PREFERENCES_FIELD_REMEMBER_USER_DATA, false)
    }

    fun setRememberUser(yes: Boolean){

        val preferencesEditor = sharedPreferences.edit()
        preferencesEditor.putBoolean(SHARED_PREFERENCES_FIELD_REMEMBER_USER_DATA, yes)
        preferencesEditor.apply()
    }

    fun logIn(email: String, password: String){

        val preferencesEditor = sharedPreferences.edit()
        preferencesEditor.putBoolean(SHARED_PREFERENCES_FIELD_LOGGED_IN, true)
        preferencesEditor.putString(SHARED_PREFERENCES_FIELD_EMAIL, email)
        preferencesEditor.putString(SHARED_PREFERENCES_FIELD_PASSWORD, password)
        preferencesEditor.apply()
    }

    fun logOut(){

        if(getRememberUser()){
            sharedPreferences.edit().putBoolean(SHARED_PREFERENCES_FIELD_LOGGED_IN, false).apply()
        }
        else{
            sharedPreferences.edit().clear().apply()
        }
    }

    fun isLoggedIn(): Boolean {

        return sharedPreferences.getBoolean(SHARED_PREFERENCES_FIELD_LOGGED_IN, false)
    }
}