package com.bemobi.viajebessa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.bemobi.viajebessa.R;
import com.bemobi.viajebessa.fragments.TravelPackagesDetailFragment;
import com.bemobi.viajebessa.fragments.TravelPackagesListFragment;
import com.bemobi.viajebessa.fragments.TravelPackagesListFragment.OnTravelPackageSelected;
import com.bemobi.viajebessa.infrastructure.Constants;
import com.bemobi.viajebessa.vo.TravelPackage;

public class TravelPackagesListActivity extends SherlockFragmentActivity
		implements OnTravelPackageSelected<TravelPackage> {

	private final String TAG = getClass().getSimpleName();

	private final static String CONTENT = "content";

	private Fragment mContent;
	private FragmentManager mFragmentManager;
	private ActionBar mActionBar;

	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFragmentManager = getSupportFragmentManager();
		mActionBar = getSupportActionBar();

		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setIcon(R.drawable.ic_earth16);
		mActionBar.setTitle(R.string.travel_packages);

		setContentView(R.layout.travel_packages_list_activity);

		// if (savedInstanceState != null) {
		// mContent = mFragmentManager
		// .getFragment(savedInstanceState, CONTENT);
		//
		// }
		//
		// if (mContent == null) {
		// mContent = TravelPackagesListFragment.newInstance();
		// }

		// switchContent(mContent);

		if (findViewById(R.id.travel_packages_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((TravelPackagesListFragment) mFragmentManager
					.findFragmentById(R.id.travelpackage_list))
					.setActivateOnItemClick(true);

			// ((TravelPackagesListFragment) mContent)
			// .setActivateOnItemClick(true);
		}

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
	protected void onSaveInstanceState(Bundle outState) {
		// mFragmentManager.putFragment(outState, CONTENT, mContent);
		super.onSaveInstanceState(outState);

	}

	public void switchContent(final Fragment fragment) {
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		// ft.setCustomAnimations(android.R.anim.slide_in_left,
		// android.R.anim.slide_out_right);
		// ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(R.id.content_frame, fragment);
		ft.addToBackStack(null);
		ft.commit();

	}

	@Override
	public void onTravelPackageSelected(TravelPackage item) {

		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.

			Log.d(TAG, "TWO PANE MODE ENABLE");

			TravelPackagesDetailFragment fragment = TravelPackagesDetailFragment
					.newInstance(item);

			mFragmentManager.beginTransaction()
					.replace(R.id.travel_packages_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					TravelPackagesDetailActivity.class);

			detailIntent.putExtra(Constants.TRAVEL_PACKAGE_ITEM, item);
			startActivity(detailIntent);

		}

	}

	@Override
	public void onBackPressed() {
		finish();
	}

}
