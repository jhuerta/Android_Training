package com.app.yamba.app.model;

import android.net.Uri;
import android.provider.BaseColumns;
import com.app.yamba.app.constants.ContentProviders;

public class TweetModel {

	// DB specific constants
	public static final String DB_NAME = "tweets.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "tweets";

	// Provider specific constants
	// content://com.marakana.android.yamba.TweetProvider/tweet
	public static final String AUTHORITY = ContentProviders.TWEET_PROVIDER_AUTHORITY;
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE);
	public static final int TWEET_ITEM = 1;
	public static final int TWEET_DIR = 2;
	public static final String TWEET_TYPE_ITEM = "vnd.android.cursor.item/vnd.com.app.yamba.provider.status";
	public static final String TWEET_TYPE_DIR = "vnd.android.cursor.dir/vnd.com.app.yamba.provider.status";
	public static final String DEFAULT_SORT = Column.CREATED_AT + " DESC";

	public class Column {
		public static final String ID = BaseColumns._ID;
		public static final String USER = "user";
		public static final String MESSAGE = "message";
		public static final String CREATED_AT = "created_at";
	}
}
