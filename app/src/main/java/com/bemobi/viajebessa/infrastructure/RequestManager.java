package com.bemobi.viajebessa.infrastructure;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RequestManager {
	/**
	 * the queue
	 */
	private static RequestQueue mRequestQueue;

	/**
	 * converter/serializer
	 */
	private static Gson mGsonConverter;

	/**
	 * Nothing to see here.
	 */
	private RequestManager() {
		// no instances
	}

	/**
	 * @param context
	 *            application context
	 */
	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		mGsonConverter = new GsonBuilder().create();
	}

	/**
	 * @return instance of the queue
	 * @throws IllegalStatException
	 *             if init has not yet been called
	 */
	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("Not initialized");
		}
	}

	public static Gson getGsonConverter() {
		if (mGsonConverter != null) {
			return mGsonConverter;
		} else {
			throw new IllegalStateException("Not initialized");
		}
	}
}
