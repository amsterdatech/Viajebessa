package com.bemobi.viajebessa.infrastructure;

import android.os.Build;

public class DeviceInfo {


	private static DeviceInfo mInstance;

	private DeviceInfo() {

	}

	public static DeviceInfo getInstance() {
		if (mInstance == null) {
			mInstance = new DeviceInfo();
		}
		return mInstance;
	}

	public String getOSVersion() {
		return Build.VERSION.RELEASE;
	}

	public String getDeviceModel() {
		return Build.MODEL;
	}

	public String getDeviceBrand() {
		return Build.BRAND;
	}

	public String getDeviceInfoQueryParameters() {
		StringBuilder builder = new StringBuilder().append(Constants.VERSION)
				.append(getOSVersion()).append(Constants.MODEL).append(getDeviceModel())
				.append(Constants.BRAND).append(getDeviceBrand());

		return builder.toString();
	}
}
