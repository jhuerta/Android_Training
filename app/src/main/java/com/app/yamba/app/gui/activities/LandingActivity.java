package com.app.yamba.app.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.app.yamba.app.R;
import com.app.yambautilities.preferences.PreferencesHelper;


public class LandingActivity extends Activity {

    private static final String TAG_LOG_LANDING_ACTIVITY=LandingActivity.class.getName();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(PreferencesHelper.getIsApplicationAlreadyRun(this))
        {
            Log.i(TAG_LOG_LANDING_ACTIVITY, "OnCreate Application has run");
            startActivity(new Intent(this, TweetsActivity.class));
        }
        else
        {
            Log.i(TAG_LOG_LANDING_ACTIVITY, "OnCreate Application has not run");
            startActivity(new Intent(this, WelcomeActivity.class));
            //startActivity(new Intent(this, WelcomeActivity.class));
        }

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_settings_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }
}
