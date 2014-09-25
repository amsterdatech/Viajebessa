package com.bemobi.viajebessa.infrastructure;

import android.app.Application;

public class ViajeBessaApp extends Application {
	/**
	 * A singleton instance of the application class for easy access in other
	 * places
	 */
	private static ViajeBessaApp sInstance;

	/**
	 * @return ApplicationController singleton instance
	 */
	public static synchronized ViajeBessaApp getInstance() {
		return sInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		sInstance = this;
		RequestManager.init(this);
		// ImageCacheManager.getInstance().init();

	}

}
