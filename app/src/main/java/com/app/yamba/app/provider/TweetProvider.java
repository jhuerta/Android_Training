package com.app.yamba.app.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.app.yamba.app.model.TweetModel;

public class TweetProvider extends ContentProvider {
    private static final String TAG = TweetProvider.class.getSimpleName();
    private DbHelper dbHelper;

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(TweetModel.AUTHORITY, TweetModel.TABLE,
                TweetModel.TWEET_DIR);
        sURIMatcher.addURI(TweetModel.AUTHORITY, TweetModel.TABLE
                + "/#", TweetModel.TWEET_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        Log.d(TAG, "onCreated");
        return false;
    }

    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case TweetModel.TWEET_DIR:
                Log.d(TAG, "gotType: " + TweetModel.TWEET_TYPE_DIR);
                return TweetModel.TWEET_TYPE_DIR;
            case TweetModel.TWEET_ITEM:
                Log.d(TAG, "gotType: " + TweetModel.TWEET_TYPE_ITEM);
                return TweetModel.TWEET_TYPE_ITEM;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri ret = null;

        // Assert correct uri
        if (sURIMatcher.match(uri) != TweetModel.TWEET_DIR) {
            throw new IllegalArgumentException("Illegal uri: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insertWithOnConflict(TweetModel.TABLE, null,
                values, SQLiteDatabase.CONFLICT_IGNORE);

        // Was insert successful?
        if (rowId != -1) {
            long id = values.getAsLong(TweetModel.Column.ID);
            ret = ContentUris.withAppendedId(uri, id);
            Log.d(TAG, "inserted uri: " + ret);

            // Notify that data for this uri has changed
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return ret;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String where;

        switch (sURIMatcher.match(uri)) {
            case TweetModel.TWEET_DIR:
                // so we count updated rows
                where = selection;
                break;
            case TweetModel.TWEET_ITEM:
                long id = ContentUris.parseId(uri);
                where = TweetModel.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( "
                        + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.update(TweetModel.TABLE, values, where, selectionArgs);

        if(ret>0) {
            // Notify that data for this uri has changed
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "updated records: " + ret);
        return ret;
    }

    // Implement Purge feature
    // Use db.delete()
    // DELETE FROM status WHERE id=? AND user='?'
    // uri: content://com.marakana.android.yamba.TweetModel/status/47
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String where;

        switch (sURIMatcher.match(uri)) {
            case TweetModel.TWEET_DIR:
                // so we count deleted rows
                where = (selection == null) ? "1" : selection;
                break;
            case TweetModel.TWEET_ITEM:
                long id = ContentUris.parseId(uri);
                where = TweetModel.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( "
                        + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.delete(TweetModel.TABLE, where, selectionArgs);

        if(ret>0) {
            // Notify that data for this uri has changed
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "deleted records: " + ret);
        return ret;
    }

    // SELECT username, message, created_at FROM status WHERE user='bob' ORDER
    // BY created_at DESC;
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables( TweetModel.TABLE );

        switch (sURIMatcher.match(uri)) {
            case TweetModel.TWEET_DIR:
                break;
            case TweetModel.TWEET_ITEM:
                qb.appendWhere(TweetModel.Column.ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }

        String orderBy = (TextUtils.isEmpty(sortOrder)) ? TweetModel.DEFAULT_SORT
                : sortOrder;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // register for uri changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        Log.d(TAG, "queried records: "+cursor.getCount());
        return cursor;
    }


}
