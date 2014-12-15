package com.diamondsoftware.android.massagenearby.common;

import android.content.ContentResolver;

import com.diamondsoftware.android.massagenearby.model.ItemUser;

public interface ChatPageManager {
	void lostCommunicationsWith(SocketCommunicationsManager itemUser);
	ContentResolver getContentResolver();
	void weveGotANewChat(SocketCommunicationsManager itemUser);
}
