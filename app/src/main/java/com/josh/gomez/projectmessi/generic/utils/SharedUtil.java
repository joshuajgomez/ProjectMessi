package com.josh.gomez.projectmessi.generic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.josh.gomez.projectmessi.generic.repository.Constants;

/**
 * Created by Joshua Gomez on 15/01/2018.
 */

public class SharedUtil {

    private static final String INITIAL_NOTIFY_TIME = "21:00";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static void setSharedPreferences() {
        sharedPreferences = Constants.context.getSharedPreferences(Constants.USERDATA, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static String getSharedString(String key, String defValue) {
        setSharedPreferences();
        return sharedPreferences.getString(key, defValue);
    }

    public static float getSharedFloat(String key, float defValue) {
        setSharedPreferences();
        return sharedPreferences.getFloat(key, defValue);
    }

    public static void putSharedInt(String key, int value) {
        setSharedPreferences();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getSharedInt(String key, int defValue) {
        setSharedPreferences();
        return sharedPreferences.getInt(key, defValue);
    }

    public static void putSharedFloat(String key, float value) {
        setSharedPreferences();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static String getSharedString(String key) {
        setSharedPreferences();
        return sharedPreferences.getString(key, "");
    }

    public static void putSharedString(String key, String value) {
        setSharedPreferences();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean getSharedBoolean(String key) {
        setSharedPreferences();
        return sharedPreferences.getBoolean(key, false);
    }

    public static boolean getSharedBoolean(String key, boolean defValue) {
        setSharedPreferences();
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static boolean getNotifyFlag(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Constants.NOTIFICATION_FLAG, true);
    }

    public static String getNotifyTime() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Constants.context);
        return preferences.getString("time", INITIAL_NOTIFY_TIME);
    }

    public static String getNotifyTime(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("time", INITIAL_NOTIFY_TIME);
    }

    public static String getBreakfastIcon(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("breakfast", "omelet");
    }

    public static void setLunchIcon(String iconName, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lunch", iconName);
        editor.apply();
    }

    public static void setBreakfastIcon(String iconName, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("breakfast", iconName);
        editor.apply();
    }

    public static void setDinnerIcon(String iconName, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dinner", iconName);
        editor.apply();
    }

    public static String getLunchIcon(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("lunch", "lunch");
    }

    public static String getDinnerIcon(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("dinner", "dinner");
    }


    public static void putSharedBoolean(String key, boolean value) {
        setSharedPreferences();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static int getBreakfastCost() {
        return getSharedInt(Constants.BREAKFAST_COST, 30);
    }

    public static void setBreakfastCost(int count) {
        putSharedInt(Constants.BREAKFAST_COST, count);
    }

    public static int getLunchCost() {
        return getSharedInt(Constants.LUNCH_COST, 40);
    }

    public static void setLunchCost(int count) {
        putSharedInt(Constants.LUNCH_COST, count);
    }

    public static int getDinnerCost() {
        return getSharedInt(Constants.DINNER_COST, 30);
    }

    public static void setDinnerCost(int count) {
        putSharedInt(Constants.DINNER_COST, count);
    }

    public static String getUsername() {
        setSharedPreferences();
        return sharedPreferences.getString(Constants.USERNAME, "Mess Eater");
    }

    public static void setUsername(String username) {
        putSharedString(Constants.USERNAME, username);
    }


}
