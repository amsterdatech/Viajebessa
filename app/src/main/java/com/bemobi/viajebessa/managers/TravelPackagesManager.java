package com.bemobi.viajebessa.managers;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.bemobi.viajebessa.infrastructure.Constants;
import com.bemobi.viajebessa.infrastructure.DeviceInfo;
import com.bemobi.viajebessa.infrastructure.RequestManager;
import com.bemobi.viajebessa.response.TravelPackagePurchase;
import com.bemobi.viajebessa.response.TravelPackagesData;

public class TravelPackagesManager {
	private final String TAG = getClass().getSimpleName();
	private static TravelPackagesManager mInstance;

	public static final String API_BASE_URL = " http://travelpackages.apiary-mock.com/";
	public static final String TRAVEL_PACKAGES = "travel-packages";
	public static final String PAGE = "page";

	public static TravelPackagesManager getInstance() {
		if (mInstance == null) {
			mInstance = new TravelPackagesManager();
		}

		return mInstance;
	}

	public TravelPackagesManager() {
	}

	public void loadTravelPackages(Listener<TravelPackagesData> success,
			ErrorListener error, int pageNum) {

		Uri.Builder uriBuilder = Uri.parse(API_BASE_URL).buildUpon()
				.appendPath(TRAVEL_PACKAGES)
				.appendQueryParameter(PAGE, Integer.toString(pageNum));

		// TODO Append Tracking Parameters

		uriBuilder.appendQueryParameter(Constants.VERSION, DeviceInfo
				.getInstance().getOSVersion());
		uriBuilder.appendQueryParameter(Constants.MODEL, DeviceInfo
				.getInstance().getDeviceModel());
		uriBuilder.appendQueryParameter(Constants.BRAND, DeviceInfo
				.getInstance().getDeviceBrand());

		String uri = uriBuilder.build().toString();
		Log.i(TAG, "loadTravelPackages: uri = " + uri);

		GsonRequest<TravelPackagesData> request = new GsonRequest<TravelPackagesData>(
				Method.GET, uri, TravelPackagesData.class, success, error);

		Log.v(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);

	}

	public void purchaseTravelPackage(Listener<TravelPackagePurchase> success,
			ErrorListener error) {
		Uri.Builder uriBuilder = Uri.parse(API_BASE_URL).buildUpon()
				.appendPath(TRAVEL_PACKAGES).appendPath("1");

		// TODO Append Tracking Parameters

		uriBuilder.appendQueryParameter(Constants.VERSION, DeviceInfo
				.getInstance().getOSVersion());
		uriBuilder.appendQueryParameter(Constants.MODEL, DeviceInfo
				.getInstance().getDeviceModel());
		uriBuilder.appendQueryParameter(Constants.BRAND, DeviceInfo
				.getInstance().getDeviceBrand());

		String uri = uriBuilder.build().toString();
		Log.i(TAG, "purchaseTravelPackage: uri = " + uri);

		GsonRequest<TravelPackagePurchase> request = new GsonRequest<TravelPackagePurchase>(
				Method.POST, uri, TravelPackagePurchase.class, success, error);

		Log.v(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);

	}
}
