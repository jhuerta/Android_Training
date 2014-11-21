package com.app.yamba.app.gui.fragments;

import android.os.Bundle;
import com.app.yamba.app.gui.activities.SubActivity;

public class DetailsActivity extends SubActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Check if this activity was created before
		if (savedInstanceState == null) {
			// Create a fragment
			DetailsFragment fragment = new DetailsFragment();
			getFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, fragment,
							fragment.getClass().getSimpleName()).commit();
		}
	}
}
