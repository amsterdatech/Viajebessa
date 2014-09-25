package com.bemobi.viajebessa.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.bemobi.viajebessa.R;
import com.bemobi.viajebessa.infrastructure.Constants;
import com.bemobi.viajebessa.infrastructure.ViajeBessaApp;
import com.bemobi.viajebessa.interfaces.ITravelPackagesDetailView;
import com.bemobi.viajebessa.presenters.TravelPackagesDetailPresenter;
import com.bemobi.viajebessa.vo.TravelPackage;
import com.squareup.picasso.Picasso;

public class TravelPackagesDetailFragment extends SherlockFragment implements
		ITravelPackagesDetailView<TravelPackage> {

	public final String TAG = getClass().getSimpleName();

	private View mParent;
	private TravelPackagesDetailPresenter mPresenter;

	private ProgressBar mProgressBar;
	private LinearLayout mDetailContent;
	private ImageView mPictureView;
	private TextView mHeadlineView;
	private TextView mDescriptionView;
	private TextView mPriceView;
	private Button mBuyButton;

	private ProgressDialog mProgressDialog;

	private TravelPackage mTravelPackageItem;

	public static TravelPackagesDetailFragment newInstance() {
		TravelPackagesDetailFragment frag = new TravelPackagesDetailFragment();
		return frag;
	}

	public static TravelPackagesDetailFragment newInstance(
			final TravelPackage item) {

		TravelPackagesDetailFragment frag = new TravelPackagesDetailFragment();
		Bundle arguments = new Bundle();
		arguments.putParcelable(Constants.TRAVEL_PACKAGE_ITEM, item);

		frag.setArguments(arguments);

		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (savedInstanceState != null
				&& savedInstanceState
						.containsKey(Constants.TRAVEL_PACKAGE_ITEM)) {
			mTravelPackageItem = savedInstanceState
					.getParcelable(Constants.TRAVEL_PACKAGE_ITEM);
		}

		Bundle args = getArguments();
		if (args != null && args.containsKey(Constants.TRAVEL_PACKAGE_ITEM)) {
			mTravelPackageItem = args
					.getParcelable(Constants.TRAVEL_PACKAGE_ITEM);
		}

		mPresenter = new TravelPackagesDetailPresenter(this);

		mParent = inflater.inflate(R.layout.travel_packages_detail_fragment,
				container, false);

		loadViews();

		mPresenter.loadTravelDetails(mTravelPackageItem);

		return mParent;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (mTravelPackageItem != null) {
			outState.putParcelable(Constants.TRAVEL_PACKAGE_ITEM,
					mTravelPackageItem);
		}
		super.onSaveInstanceState(outState);

	}

	@Override
	public void showProgressBar() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgressBar() {
		mProgressBar.setVisibility(View.GONE);

	}

	@Override
	public void makeActionBar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadViews() {
		mProgressBar = (ProgressBar) mParent.findViewById(R.id.progress_bar);
		mDetailContent = (LinearLayout) mParent
				.findViewById(R.id.detail_content);

		mPictureView = (ImageView) mParent.findViewById(R.id.picture);
		mHeadlineView = (TextView) mParent.findViewById(R.id.headline);
		mDescriptionView = (TextView) mParent.findViewById(R.id.description);
		mPriceView = (TextView) mParent.findViewById(R.id.price);
		mBuyButton = (Button) mParent.findViewById(R.id.buy_button);

		mBuyButton.setOnClickListener(new BuyClickListener(mTravelPackageItem));

	}

	@Override
	public void updateDetail(TravelPackage item) {
		renderTravelPackageItem(item);

	}

	private void renderTravelPackageItem(TravelPackage item) {
		if (item != null) {

			mHeadlineView.setText(item.getTitle());
			mDescriptionView.setText(item.getDescription());
			mPriceView
					.setText("R$ " + Integer.toString(item.getCost()) + ",00");

			// Trigger the download of the URL asynchronously into the image
			// view.
			Picasso.with(ViajeBessaApp.getInstance())
					.load(item.getImageUrl())
					.config(Config.ARGB_8888)
					.placeholder(R.color.default_image_background)
					.error(R.color.default_image_background)
					.resizeDimen(R.dimen.detail_item_image_width,
							R.dimen.detail_item_image_height).centerCrop()
					.into(mPictureView);

			mDetailContent.setVisibility(View.VISIBLE);

		}

	}

	@Override
	public void showErrorDialog(String error) {

	}

	private class BuyClickListener implements OnClickListener {
		private TravelPackage mItem;

		public BuyClickListener(TravelPackage item) {
			this.mItem = item;
		}

		@Override
		public void onClick(View v) {
			mPresenter.purchaseTravelPackage(mItem);

		}
	}

	@Override
	public void showProgressDialog(String msg) {
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setMessage(msg);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.show();
	}

	@Override
	public void updateProgressDialog(String msg) {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.setMessage(msg);
		}

	}

	@Override
	public void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	@Override
	public void showPurchaseDialog(String message) {
		// This is absolutely wrong in tmers of context , however due to time
		// issues I'm calling the succcess dialog here
		// Post item data to server, save analytics data, go to shopping
		// cart flow,
		// as such Shipping/Billing Address -> Payment Gateway/Card Info ->
		// Confirmation

		// TODO Show dialog as mock of success

		Log.v(TAG, "SHOW PURCHASE DIALOG");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(R.string.purchase).setMessage(message)
				.setPositiveButton("OK", new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// TODO Back to list with slide out animation
						getActivity().finish();

					}

				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

}
