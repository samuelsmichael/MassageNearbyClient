package com.diamondsoftware.android.massagenearby.model;

public abstract class ItemUser {
	protected int mUserId;
	protected String mName;
	protected String mURL;
	/**
	 * @return the mName
	 */
	public String getmName() {
		return mName;
	}
	/**
	 * @param mName the mName to set
	 */
	public void setmName(String mName) {
		this.mName = mName;
	}
	/**
	 * @return the mURL
	 */
	public String getmURL() {
		return mURL;
	}
	/**
	 * @param mURL the mURL to set
	 */
	public void setmURL(String mURL) {
		this.mURL = mURL;
	}
	/**
	 * @return the mUserId
	 */
	public int getmUserId() {
		return mUserId;
	}
	/**
	 * @param mUserId the mUserId to set
	 */
	public void setmUserId(int mUserId) {
		this.mUserId = mUserId;
	}
}
