package com.bemobi.viajebessa.presenters;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bemobi.viajebessa.R;
import com.bemobi.viajebessa.infrastructure.ViajeBessaApp;
import com.bemobi.viajebessa.interfaces.ITravelPackagesDetailView;
import com.bemobi.viajebessa.managers.TravelPackagesManager;
import com.bemobi.viajebessa.response.TravelPackagePurchase;
import com.bemobi.viajebessa.vo.TravelPackage;

public class TravelPackagesDetailPresenter {
	public final String TAG = getClass().getSimpleName();

	private TravelPackagesManager mTravelPackagesManager;
	private ITravelPackagesDetailView<TravelPackage> mView;
	private Handler mHandler;

	public TravelPackagesDetailPresenter(
			ITravelPackagesDetailView<TravelPackage> view) {
		mView = view;
		mTravelPackagesManager = TravelPackagesManager.getInstance();
		mHandler = new Handler(Looper.getMainLooper());
	}

	public void loadTravelDetails(final TravelPackage item) {
		mView.showProgressBar();
		// This delay is to simulate a network call to get fresh data about
		// detail item
		mHandler.postDelayed(new TravelPackageDetailSuccessAction(item), 1000);
	}

	public void purchaseTravelPackage(final TravelPackage item) {
		mView.showProgressDialog(ViajeBessaApp.getInstance().getString(
				R.string.sending));

		mTravelPackagesManager.purchaseTravelPackage(mSuccessPurcharseListener,
				mErrorPurchaseListener);

	}

	private class TravelPackageDetailSuccessAction implements Runnable {
		private final TravelPackage mData;

		public TravelPackageDetailSuccessAction(TravelPackage data) {
			this.mData = data;
		}

		@Override
		public void run() {
			mView.updateDetail(mData);
			mView.hideProgressBar();

		}
	}

	/*
	 * Volley Callbacks
	 */

	private Listener<TravelPackagePurchase> mSuccessPurcharseListener = new Listener<TravelPackagePurchase>() {

		@Override
		public void onResponse(TravelPackagePurchase response) {
			mHandler.post(new SuccessPurchase(response.getStatus()));

		}
	};

	private ErrorListener mErrorPurchaseListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			if (error != null)
				mHandler.post(new ErrorPurchase(error.getMessage()));

		}
	};

	/*
	 * Runnables to update UI Thread
	 */

	private class SuccessPurchase implements Runnable {
		private String mMessage;

		public SuccessPurchase(String msg) {
			this.mMessage = msg;
		}

		@Override
		public void run() {
			mView.hideProgressDialog();
			mView.showPurchaseDialog(mMessage);
		}

	}

	private class ErrorPurchase implements Runnable {
		private final String mError;

		public ErrorPurchase(String error) {
			this.mError = error;
		}

		@Override
		public void run() {
			mView.hideProgressDialog();
			mView.showErrorDialog(mError);

		}

	}
}
