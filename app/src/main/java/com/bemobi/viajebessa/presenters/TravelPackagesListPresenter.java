package com.bemobi.viajebessa.presenters;

import android.os.Handler;
import android.os.Looper;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.bemobi.viajebessa.interfaces.ITravelPackagesListPresenter;
import com.bemobi.viajebessa.interfaces.ITravelPackagesListView;
import com.bemobi.viajebessa.managers.TravelPackagesManager;
import com.bemobi.viajebessa.response.TravelPackagesData;
import com.bemobi.viajebessa.vo.TravelPackage;

public class TravelPackagesListPresenter implements
		ITravelPackagesListPresenter {

	private ITravelPackagesListView<TravelPackage> mView;
	private TravelPackagesManager mTravelPackagesManager;
	private Handler mHandler;

	public TravelPackagesListPresenter(
			ITravelPackagesListView<TravelPackage> view) {

		this.mView = view;
		this.mTravelPackagesManager = TravelPackagesManager.getInstance();
		this.mHandler = new Handler(Looper.getMainLooper());
	}

	private Listener<TravelPackagesData> mSuccessListener = new Listener<TravelPackagesData>() {

		@Override
		public void onResponse(TravelPackagesData response) {
			mHandler.postDelayed(new SuccessAction(response), 1000);

		}
	};

	private ErrorListener mErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			mHandler.post(new ErrorAction(error));

		}
	};

	@Override
	public void loadTravelPackages(int pageNum) {
		mView.showProgressBar();
		mTravelPackagesManager.loadTravelPackages(mSuccessListener,
				mErrorListener, 1);
	}

	private class SuccessAction implements Runnable {
		private final TravelPackagesData mResponseData;

		public SuccessAction(final TravelPackagesData response) {
			this.mResponseData = response;
		}

		@Override
		public void run() {
			mView.updateList(mResponseData.getResults());
			mView.hideProgressBar();

		}
	}

	private class ErrorAction implements Runnable {
		private final VolleyError mError;

		public ErrorAction(VolleyError error) {
			this.mError = error;
		}

		@Override
		public void run() {
			mView.hideProgressBar();
			mView.showErrorDialog(mError != null ? mError.getMessage() : "");
		}

	}

}
