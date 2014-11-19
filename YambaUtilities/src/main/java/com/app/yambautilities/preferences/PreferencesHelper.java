package com.app.yambautilities.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by HuertaJ on 18.11.2014.
 */
public class PreferencesHelper {

    private static final String LOG_TAG = PreferencesHelper.class.getName();
    private static final String default_username = "default_username";
    private static final String default_password = "thepassword";

    private static String PREF_IS_ALREADY_STARTED = "IsAppAlreadyStarted";
    private static String USERNAME = "IsAppAlreadyStarted";
    private static String PASSWORD = "IsAppAlreadyStarted";

    public static boolean getIsApplicationAlreadyRun(final Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean flag = sharedPreferences.getBoolean(PREF_IS_ALREADY_STARTED, false);

        if(flag == false)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean( PREF_IS_ALREADY_STARTED, true);
            editor.commit();
        }

        return flag;
    }

    public static void setUserName(final Context context, String username) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public static void setPassword(final Context context, String password) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public static String getUserName(final Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(USERNAME, default_username);
    }

    public static String getPassword(final Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(PASSWORD, default_password);
    }
}
