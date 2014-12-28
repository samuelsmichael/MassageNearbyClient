package com.diamondsoftware.android.massagenearby.model;

import java.net.Socket;

public class ItemMasseur extends ItemUser {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(mIsOnline) {
			return mName + " (online)";
		} else {
			return mName;
		}
	}
	public int getmMasserId() {
		return mMasserId;
	}
	public void setmMasserId(int mMasserId) {
		this.mMasserId = mMasserId;
	}
	public boolean ismIsOnline() {
		return mIsOnline;
	}
	public void setmIsOnline(boolean mIsOnline) {
		this.mIsOnline = mIsOnline;
	}
	public Socket getmSocket() {
		return mSocket;
	}
	public void setmSocket(Socket mSocket) {
		this.mSocket = mSocket;
	}
	int mMasserId;
	boolean mIsOnline;
	Socket mSocket;
	boolean mConnected=false;
	String MainPictureURL;
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return Longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return Latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	double Longitude;
	double Latitude;
	/**
	 * @return the mainPictureURL
	 */
	public String getMainPictureURL() {
		return MainPictureURL;
	}
	/**
	 * @param mainPictureURL the mainPictureURL to set
	 */
	public void setMainPictureURL(String mainPictureURL) {
		MainPictureURL = mainPictureURL;
	}
	/**
	 * @return the certifiedPictureURL
	 */
	public String getCertifiedPictureURL() {
		return CertifiedPictureURL;
	}
	/**
	 * @param certifiedPictureURL the certifiedPictureURL to set
	 */
	public void setCertifiedPictureURL(String certifiedPictureURL) {
		CertifiedPictureURL = certifiedPictureURL;
	}
	String CertifiedPictureURL;
	/**
	 * @return the mConnected
	 */
	public boolean ismConnected() {
		return mConnected;
	}
	/**
	 * @param mConnected the mConnected to set
	 */
	public void setmConnected(boolean mConnected) {
		this.mConnected = mConnected;
	}
}
