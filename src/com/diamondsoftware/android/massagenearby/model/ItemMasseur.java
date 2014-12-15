package com.diamondsoftware.android.massagenearby.model;

import java.net.Socket;

public class ItemMasseur extends ItemUser {

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
	
}
