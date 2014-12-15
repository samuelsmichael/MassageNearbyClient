package com.diamondsoftware.android.client;

import java.net.Socket;

import com.diamondsoftware.android.client.R.id;
import com.diamondsoftware.android.massagenearby.common.ChatPageManager;
import com.diamondsoftware.android.massagenearby.common.GlobalStaticValuesMassageNearby;
import com.diamondsoftware.android.massagenearby.common.MessagesFragment;
import com.diamondsoftware.android.massagenearby.common.SocketCommunicationsManager;
import com.diamondsoftware.android.massagenearby.common.TellMeWhenYouveGotNewMesseurs;
import com.diamondsoftware.android.massagenearby.model.ItemUser;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class ActivityChat extends FragmentActivity implements
		MessagesFragment.OnFragmentInteractionListener,
		ChatPageManager, TellMeWhenYouveGotNewMesseurs {
	String mProfileChatId;
	SocketCommunicationsManager mSCM;
	Button mBtnSend;
	EditText mEditText;

	@Override
	public String getProfileChatId() {

		return mProfileChatId;
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_chat);
		mEditText=(EditText)findViewById(id.msg_edit_client);
		mBtnSend=(Button)findViewById(id.send_btn_client);
		mBtnSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String msg=mEditText.getText().toString().trim();
				if(!TextUtils.isEmpty(msg)) {
					mBtnSend.setEnabled(false);
				}
				try {
					mSCM.doSend(com.diamondsoftware.android.common.GlobalStaticValues.COMMAND_HERES_MY_CHAT_MSG, msg);
				} catch (Exception e) {
    				mBtnSend.setEnabled(true);
       				return;
				}
				
				ContentValues values=new ContentValues(2);
				values.put(DataProvider.COL_MSG, msg);
				values.put(DataProvider.COL_TO, String.valueOf(mSCM.getmItemUserClient().getmUserId()));
				getContentResolver().insert(com.diamondsoftware.android.client.DataProvider.CONTENT_URI_MESSAGES, values);
				
				mEditText.setText(null);
				mBtnSend.setEnabled(true);
			}
		});
		mProfileChatId=this.getIntent().getExtras().getString(GlobalStaticValuesMassageNearby.KEY_CHAT_ID);
		mSCM=new SocketCommunicationsManager(
				null, 
				this, 
				MasseurListActivity.mSingleton.getMasseurWhoseUserIdIs(Integer.valueOf(mProfileChatId)), 
				MasseurListActivity.mSingleton.mItemClientMe);
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.add(R.id.container, MessagesFragment.newInstance(mSCM)).commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void lostCommunicationsWith(SocketCommunicationsManager itemUser) {
		// 1. get new list of mMasseurs
		MasseurListActivity.mSingleton.updateMasseursList(this);
	}

	@Override
	public void weveGotANewChat(SocketCommunicationsManager itemUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void IveGotNewMesseurs() {
		// 2. create mSCM, and update my messagesfragment with mSCM
		mSCM=new SocketCommunicationsManager(
				null, 
				this, 
				MasseurListActivity.mSingleton.getMasseurWhoseUserIdIs(Integer.valueOf(mProfileChatId)), 
				MasseurListActivity.mSingleton.mItemClientMe);		
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		MessagesFragment theOldOne = (MessagesFragment)fragmentManager.findFragmentById(id.container);
		fragmentManager.beginTransaction()
				.remove(theOldOne)
				.add(R.id.container, MessagesFragment.newInstance(mSCM)).commit();
	}
}
