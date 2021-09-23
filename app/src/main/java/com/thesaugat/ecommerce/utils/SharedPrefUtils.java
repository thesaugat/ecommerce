package com.thesaugat.ecommerce.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.thesaugat.ecommerce.R;

public class SharedPrefUtils {
    public static boolean getBool(Activity context, String key, boolean defaultV) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getBoolean(key, defaultV);


    }


    public static void setBoolean(Activity activity, String key, boolean val) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }
}
