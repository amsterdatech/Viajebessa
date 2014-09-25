package com.bemobi.viajebessa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.bemobi.viajebessa.R;
import com.bemobi.viajebessa.fragments.TravelPackagesDetailFragment;
import com.bemobi.viajebessa.infrastructure.Constants;
import com.bemobi.viajebessa.vo.TravelPackage;

public class TravelPackagesDetailActivity extends SherlockFragmentActivity {
	private final String TAG = getClass().getSimpleName();

	private final static String CONTENT = "content";

	private Fragment mContent;
	private FragmentManager mFragmentManager;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// if (getResources().getConfiguration().orientation ==
		// Configuration.ORIENTATION_LANDSCAPE) {
		// If the screen is now in landscape mode, we can show the
		// dialog in-line with the list so we don't need this activity.
		// finish();
		// return;
		// }

		mFragmentManager = getSupportFragmentManager();
		mActionBar = getSupportActionBar();

		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setIcon(R.drawable.ic_earth16);
		mActionBar.setTitle(R.string.travel_package_detail);

		setContentView(R.layout.travel_packages_detail_activity);

		if (savedInstanceState != null) {
			mContent = mFragmentManager
					.getFragment(savedInstanceState, CONTENT);

		}

		Intent intent = getIntent();
		Bundle args = null;

		if (intent != null) {
			args = intent.getExtras();
			if (args != null && args.containsKey(Constants.TRAVEL_PACKAGE_ITEM)) {
				mContent = TravelPackagesDetailFragment
						.newInstance((TravelPackage) args
								.getParcelable(Constants.TRAVEL_PACKAGE_ITEM));
			}
		}

		switchContent(mContent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mFragmentManager.putFragment(outState, CONTENT, mContent);
		super.onSaveInstanceState(outState);

	}

	public void switchContent(final Fragment fragment) {
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		// ft.setCustomAnimations(android.R.anim.slide_in_left,
		// android.R.anim.slide_out_right);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(R.id.content_frame, fragment);
		ft.addToBackStack(null);
		ft.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		finish();

	}

}
