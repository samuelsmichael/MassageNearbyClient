package com.diamondsoftware.android.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class CommonMethods {

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                    	String hostAddress=inetAddress.getHostAddress().toString();
                    	return hostAddress; 
                    	}
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
    }
    public static String getCurrentSsid(Context context) {

  	  String ssid = null;
  	  ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
  	  NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
  	  if (networkInfo.isConnected()) {
  	    final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
  	    final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
  	    if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {
  	        //if (connectionInfo != null && !StringUtil.isBlank(connectionInfo.getSSID())) {
  	      ssid = connectionInfo.getSSID();
  	    }
  	  }
  	  return ssid;
  	}

}
