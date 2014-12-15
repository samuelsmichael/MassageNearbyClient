package com.diamondsoftware.android.massagenearby.common;

import android.content.Context;

public class CommonMethods {
	public static String getBaseURL(Context context) {
		 String ssid=com.diamondsoftware.android.common.CommonMethods.getCurrentSsid(context);
		if(ssid!=null && ssid.indexOf("HOME-B608")!=-1) {
			return "10.0.0.253";
		} else {
			return "listplus.no-ip.org";
		}
	}
}
