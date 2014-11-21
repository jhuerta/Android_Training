package com.app.yamba.app.gui.fragments;

import android.app.Fragment;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.app.yamba.app.R;
import com.app.yamba.app.model.TweetModel;

public class DetailsFragment extends Fragment {
	private TextView textUser, textMessage, textCreatedAt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tweet_details_list_item, null, false);

		textUser = (TextView) view.findViewById(R.id.list_item_text_user);
		textMessage = (TextView) view.findViewById(R.id.list_item_text_message);
		textCreatedAt = (TextView) view
				.findViewById(R.id.list_item_text_created_at);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		long id = getActivity().getIntent().getLongExtra(
				TweetModel.Column.ID, -1);

		updateView(id);
	}
	
	public void updateView(long id) {
		if (id == -1) {
			textUser.setText("");
			textMessage.setText("");
			textCreatedAt.setText("");
			return;
		}

		Uri uri = ContentUris.withAppendedId(TweetModel.CONTENT_URI, id);
		
		Cursor cursor = getActivity().getContentResolver().query(uri, null,
				null, null, null);
		if (!cursor.moveToFirst())
			return;
		
		String user = cursor.getString(cursor
				.getColumnIndex(TweetModel.Column.USER));
		String message = cursor.getString(cursor
				.getColumnIndex(TweetModel.Column.MESSAGE));
		long createdAt = cursor.getLong(cursor
				.getColumnIndex(TweetModel.Column.CREATED_AT));
		
		textUser.setText(user);
		textMessage.setText(message);
		textCreatedAt.setText(DateUtils.getRelativeTimeSpanString(createdAt));		
	}
}
