package com.diamondsoftware.android.massagenearby.model;

import java.net.Socket;

public class ItemClient extends ItemUser {
	private int mClientId=-1;
	private String mPendingMessageTillIGetIDAndName;
	/**
	 * @return the mPendingMessageTillIGetIDAndName
	 */
	public String getmPendingMessageTillIGetIDAndName() {
		return mPendingMessageTillIGetIDAndName;
	}

	/**
	 * @param mPendingMessageTillIGetIDAndName the mPendingMessageTillIGetIDAndName to set
	 */
	public void setmPendingMessageTillIGetIDAndName(
			String mPendingMessageTillIGetIDAndName) {
		this.mPendingMessageTillIGetIDAndName = mPendingMessageTillIGetIDAndName;
	}

	/**
	 * @return the mClientId
	 */
	public int getmClientId() {
		return mClientId;
	}

	/**
	 * @param mClientId the mClientId to set
	 */
	public void setmClientId(int mClientId) {
		this.mClientId = mClientId;
	}

}
