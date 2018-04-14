package com.insurance.easycover.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

/**
 * Created by NaveedAli on 2/23/2017.
 */

public class AppSharedPreferences {

    private static final String PREF_NAME = "easycover_preferences";

    private static final String CURR_LANG = "current_language";
    private static final String DEVICE_TOKEN = "device_token";
    private static final String IS_SYNCED_DEVICE_TOKEN = "is_token_synced";
    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String REMEMBER_ME = "is_remembered";
    private static final String USER_DATA = "user_data";
    private static final String USER_TOKEN = "user_token";
    private static final String USER_SURNAME = "user_surname";
    private static final String USER_LONGITUDE = "longitude";
    private static final String USER_LATITUDE = "latitude";

    private static AppSharedPreferences uniqInstance;
    private static Context mContext;
    private static SharedPreferences sSharedPreferences;
    private static int PRIVATE_MODE = 0;
    private static SharedPreferences.Editor editor;


    public static synchronized AppSharedPreferences getInstance(Context context) {
        if (uniqInstance == null) {
            mContext = context;
            sSharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = sSharedPreferences.edit();
            uniqInstance = new AppSharedPreferences();
        }
        return uniqInstance;
    }

    public void clearData() {
        editor.clear();
        editor.commit();
    }

    public void saveCurrentLanguage(String langCode) {
        editor.putString(CURR_LANG, langCode);
        editor.commit();
    }

    public void saveUserData(String userData) {
        editor.putString(USER_DATA, userData);
        editor.commit();
    }

    public Long getLongitude() {
        return sSharedPreferences.getLong(USER_LONGITUDE, 0);
    }

    public Long getLatitude() {
        return sSharedPreferences.getLong(USER_LATITUDE, 0);
    }

    public void saveLatitude(long latitude) {
        editor.putLong(USER_LATITUDE, latitude);
        editor.commit();
    }

    public void saveLongitude(long longitude) {
        editor.putLong(USER_LONGITUDE, longitude);
        editor.commit();
    }

    public void saveToken(String token) {
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }

    public static String getUserSurname() {
        return sSharedPreferences.getString(USER_SURNAME, null);
    }

    public static void saveUserSurname(String surName) {
        editor.putString(USER_SURNAME, surName);
        editor.commit();
    }

    public String getUserData() {
        return sSharedPreferences.getString(USER_DATA, null);
    }

    public String getToken() { return sSharedPreferences.getString(USER_TOKEN,null); }

    public String getCurrentLanguage() {
        return sSharedPreferences.getString(CURR_LANG, "English");
    }


    public boolean isSyncedDeviceToken(String deviceToken) {

        String devToken = sSharedPreferences.getString(DEVICE_TOKEN, "");
        boolean isSynced = sSharedPreferences.getBoolean(IS_SYNCED_DEVICE_TOKEN, false);

        if (!deviceToken.equals(deviceToken) || !isSynced) {
            editor.putString(DEVICE_TOKEN, deviceToken);
            editor.putBoolean(IS_SYNCED_DEVICE_TOKEN, false);
            return false;
        } else {
            return true;
        }

    }

    public void setDeviceToken(String deviceToken) {
        editor.putString(DEVICE_TOKEN, deviceToken);
        editor.commit();

    }

    public void setIsSyncedToken(boolean isSynced) {
        editor.putBoolean(IS_SYNCED_DEVICE_TOKEN, isSynced);
        editor.commit();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean getIsLoggedIn() {
        return sSharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void setRememberMe(boolean isLoggedIn) {
        editor.putBoolean(REMEMBER_ME, isLoggedIn);
        editor.commit();

    }

    public boolean getRememberMe() {
        return sSharedPreferences.getBoolean(REMEMBER_ME, false);
    }
}
