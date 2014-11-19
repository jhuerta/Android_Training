package com.app.yamba.app.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.app.yamba.app.R;
import com.app.yamba.app.constants.IntentsActions;
import com.app.yamba.app.services.TweetsRefreshService;

public class TweetsTimeLineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets_time_line);
    }

    // Called to lazily initialize the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweets_time_line_activity, menu);
        return true;
    }

    // Called every time user clicks on an action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, WelcomeActivity.class));
                return true;
            case R.id.action_tweet:
                startActivity(new Intent(IntentsActions.INTENT_ACTION_TWEET_EDITOR_ACTIVITY));
                return true;
            case R.id.action_refresh:
                startService(new Intent(this, TweetsRefreshService.class));
                return true;
            case R.id.action_purge:
                /* TODO : TO IMPLEMENT - uncomment and implement tweets comment*/
                //int rows = getContentResolver().delete(TweetsContents.CONTENT_URI, null, null);
                //Toast.makeText(this, "Deleted "+rows+" rows", Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }
    }
}
