package com.example.borys.boryschajdas;

class Config {

    public final static long SPLASH_SCREEN_DURATION = 5000L; // 5 seconds

    public final static String REGEX_EMAIL_VALIDATION = "^(.)+@(.)+\\.(\\w)+$";
    public final static String REGEX_PASSWORD_VALIDATION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(.)*$";
    public final static int PASSWORD_LENGTH = 8;

    public final static  String SHARED_PREFERENCES_USER_DATA = "User";
    public final static String SHARED_PREFERENCES_FIELD_EMAIL = "email";
    public final static String SHARED_PREFERENCES_FIELD_PASSWORD = "password";
    public final static String SHARED_PREFERENCES_FIELD_REMEMBER_USER_DATA = "remember";
    public final static String SHARED_PREFERENCES_FIELD_LOGGEDIN = "loggedIn";
}
