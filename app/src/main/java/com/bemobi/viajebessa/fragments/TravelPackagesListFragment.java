package com.bemobi.viajebessa.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.bemobi.viajebessa.R;
import com.bemobi.viajebessa.adapters.TravelPackagePerformanceListAdapter;
import com.bemobi.viajebessa.interfaces.ITravelPackagesListView;
import com.bemobi.viajebessa.presenters.TravelPackagesListPresenter;
import com.bemobi.viajebessa.vo.TravelPackage;

public class TravelPackagesListFragment extends SherlockFragment implements
		ITravelPackagesListView<TravelPackage>, OnItemClickListener {

	private final String TAG = getClass().getSimpleName();

	private static final String TRAVEL_LIST_ITEMS = "travel_list_items";
	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private View mParent;
	private TravelPackagesListPresenter mPresenter;
	private TravelPackagePerformanceListAdapter mAdapter;

	private ListView mListView;
	private ProgressBar mProgressBar;

	// TODO Should use Otto/EventBus instead of this
	private Activity mActivity;
	private OnTravelPackageSelected<TravelPackage> mOnTravelPackageSelectedListener;

	private List<TravelPackage> mTravelPackageItems;
	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	public static TravelPackagesListFragment newInstance() {
		TravelPackagesListFragment frag = new TravelPackagesListFragment();
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mPresenter = new TravelPackagesListPresenter(this);

		mParent = inflater.inflate(R.layout.travel_packages_list_fragment,
				container, false);

		loadViews();

		mAdapter = new TravelPackagePerformanceListAdapter();

		if (mTravelPackageItems != null) {
			// mAdapter.removeAll();
			mAdapter.addAll(mTravelPackageItems);

		} else {
			mPresenter.loadTravelPackages(1);

		}

		mListView.setAdapter(mAdapter);

		return mParent;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
		mOnTravelPackageSelectedListener = (OnTravelPackageSelected<TravelPackage>) mActivity;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null
				&& savedInstanceState.containsKey(TRAVEL_LIST_ITEMS)) {

			mTravelPackageItems = savedInstanceState
					.getParcelableArrayList(TRAVEL_LIST_ITEMS);
		}

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
		mTravelPackageItems = mAdapter.getTravelPackages();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (mTravelPackageItems != null && mTravelPackageItems.size() > 0) {

			outState.putParcelableArrayList(TRAVEL_LIST_ITEMS,
					(ArrayList<? extends Parcelable>) mTravelPackageItems);
		}

		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
		super.onSaveInstanceState(outState);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		final TravelPackage item = (TravelPackage) mAdapter.getItem(position);
		if (item != null) {
			// TODO Tell activity to replace fragment with detail fragment with
			// slide in animation
			if (mOnTravelPackageSelectedListener != null) {
				mOnTravelPackageSelectedListener.onTravelPackageSelected(item);
			}
		}

	}

	@Override
	public void showProgressBar() {
		// Could use ViewStub here but has choosen this by simplicity
		mProgressBar.setVisibility(View.VISIBLE);

	}

	@Override
	public void hideProgressBar() {
		// Could use ViewStub here but has choosen this by simplicity
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void makeActionBar() {

	}

	@Override
	public void loadViews() {
		mListView = (ListView) mParent.findViewById(R.id.list);
		mListView.setOnItemClickListener(this);
		mProgressBar = (ProgressBar) mParent.findViewById(R.id.progress_bar);

	}

	@Override
	public void updateList(List<TravelPackage> items) {
		mAdapter.addAll(items);

	}

	@Override
	public void showErrorDialog(String error) {
		// TODO Auto-generated method stub

	}

	public interface OnTravelPackageSelected<T> {
		public void onTravelPackageSelected(T item);
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		mListView
				.setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			mListView.setItemChecked(mActivatedPosition, false);
		} else {
			mListView.setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

}
