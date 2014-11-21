package com.app.yamba.app.services;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.app.yamba.app.constants.IntentParams;
import com.app.yamba.app.constants.IntentsActions;
import com.app.yamba.app.model.TweetModel;
import com.app.yamba.client.YambaClient;
import com.app.yamba.client.YambaClientException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TweetsRefreshService extends IntentService {
    private static final String TAG = TweetsRefreshService.class.getSimpleName();

    public TweetsRefreshService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreated");
    }

    // Executes on a worker thread
    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        final String username = prefs.getString("username", "");
        final String password = prefs.getString("password", "");

        // Check that username and password are not empty
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please update your username and password",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(TAG, "onStarted");

        ContentValues values = new ContentValues();

        YambaClient cloud = new YambaClient(username, password);
        try {
            int count = 0;
            List<YambaClient.Status> timeline = cloud.getTimeline(20);
            for (YambaClient.Status status : timeline) {
                values.clear();

                values.put(TweetModel.Column.ID, status.getId());
                values.put(TweetModel.Column.USER, status.getUser());
                values.put(TweetModel.Column.MESSAGE, status.getMessage());
                values.put(TweetModel.Column.CREATED_AT, status.getCreatedAt().getTime());
                Uri uri = getContentResolver().insert(TweetModel.CONTENT_URI, values);

                if (uri != null) {
                    count++;
                   Log.d(TAG,
                           String.format("%s: %s", status.getUser(),
                                   status.getMessage()));
                }
            }

            if (count > 0) {
                sendBroadcast(new Intent(
                        IntentsActions.ACTION_NEW_TWEETS_AVAILABLE).putExtra(
                        IntentParams.PARAM_TWEETS_REFRESH_SERVICE_COUNT, count));
            }

        } catch (YambaClientException e) {
            Log.e(TAG, "Failed to fetch the timeline", e);
            e.printStackTrace();
        }

        return;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroyed");
    }


}
