package com.app.yambautilities.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by HuertaJ on 18.11.2014.
 */
public class PreferencesHelper {

    private static final String LOG_TAG = PreferencesHelper.class.getName();

    private static String PREF_IS_ALREADY_STARTED = "IsAppAlreadyStarted";

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

}
