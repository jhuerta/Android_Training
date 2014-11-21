package com.app.yamba.app.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class SubActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Action bar stuff
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(this, TweetsTimeLineActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
