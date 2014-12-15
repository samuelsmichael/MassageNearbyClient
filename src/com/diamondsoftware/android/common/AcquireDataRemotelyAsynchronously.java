package com.diamondsoftware.android.common;

import java.util.ArrayList;

import android.os.AsyncTask;
/**
 *  This class provides the means of fetching data in the 
 *  background (something that is required by Android when dealing with a fetch from the Internet).
 *  
 *  All it does is launch an AsyncTask, whose doInBackground method asks the requester (which implements the interface
 *  WaitingForDataAcquiredAsynchronously) to do the work of fetching the data.
 *  
 *  When the data is fetched, the onPostExecute (which executes on the UI thread, and so it is safe
 *  to update UI elements from it), then asks the requester to do what it needs to do with the data
 *  that has been fetched.
 *  
 * @author Diamond
 *
 */
public class AcquireDataRemotelyAsynchronously {
	private WaitingForDataAcquiredAsynchronously mClient;
	private DataGetter mDataGetter;
	private String mName;
	public AcquireDataRemotelyAsynchronously(String name, WaitingForDataAcquiredAsynchronously client, DataGetter dataGetter) {
		mDataGetter=dataGetter;
		mClient=client;
		mName=name;
		new RetrieveDataAsynchronously().execute(mClient);
	}
	public class RetrieveDataAsynchronously extends AsyncTask<WaitingForDataAcquiredAsynchronously, Void, ArrayList<Object>> {
		private WaitingForDataAcquiredAsynchronously mClient;

		protected ArrayList<Object> doInBackground(WaitingForDataAcquiredAsynchronously... clients) {
			mClient=clients[0];
			return mDataGetter.getRemoteData(mName);
		}

		protected void onPostExecute(ArrayList<Object> result) {
			if(mClient!=null) {
				mClient.gotMyData(mName,result);
			}
		}
	}
}
