package com.diamondsoftware.android.massagenearby.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.diamondsoftware.android.client.ApplicationClient;
import com.diamondsoftware.android.client.DataProvider;
import com.diamondsoftware.android.client.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p /> diamondsoftware
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class MessagesFragment extends android.support.v4.app.ListFragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat[] df = new DateFormat[] {
		DateFormat.getDateInstance(), DateFormat.getTimeInstance()};

	private OnFragmentInteractionListener mListener;
	private SimpleCursorAdapter adapter;
	private Date now;
	private SettingsManager mSettingsManager;
	private SocketCommunicationsManager mSSC;
		
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
		}
	}	

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		now = new Date();
		mSettingsManager=new SettingsManager(getActivity());
		adapter = new SimpleCursorAdapter(getActivity(), 
				
				R.layout.chat_list_item, 
				null, 
				new String[]{DataProvider.COL_MSG, DataProvider.COL_AT}, 
				new int[]{R.id.text1, R.id.text2},
				0);
		
		adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				String from = cursor.getString(cursor.getColumnIndex(DataProvider.COL_FROM));
				String to = cursor.getString(cursor.getColumnIndex(DataProvider.COL_TO));
				
				switch(view.getId()) {
				case R.id.text1:
					LinearLayout parent = (LinearLayout) view.getParent();
					LinearLayout root = (LinearLayout) parent.getParent();
					if (from == null) {//myself
						root.setGravity(Gravity.RIGHT);
						root.setPadding(50, 10, 10, 10);
						parent.setBackgroundColor(getResources().getColor(android.R.color.white));
					} else {
						root.setGravity(Gravity.LEFT);
						root.setPadding(10, 10, 50, 10);
						parent.setBackgroundColor(getResources().getColor(android.R.color.background_light));
					}
					break;
					
				case R.id.text2:
					TextView timeText = (TextView) view;
					timeText.setText(getDisplayTime(cursor.getString(columnIndex)));
					return true;
					
							
				}
				return false;
			}
		});		
		
		setListAdapter(adapter);
	}	
	
    private static final String ARG_SECTION_NUMBER = "section_number";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MessagesFragment newInstance(SocketCommunicationsManager scm) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, scm.getmItemUserClient().getmUserId());
        fragment.setArguments(args);
        return fragment;
    }
	
    
    
    public MessagesFragment() {}
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setDivider(null);
		
		Bundle args = new Bundle();
		args.putString(DataProvider.COL_CHATID, mListener.getProfileChatId());
		getLoaderManager().initLoader(0, args, this);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnFragmentInteractionListener {
		public String getProfileChatId();
	}
	
	private String getDisplayTime(String datetime) {
		try {
			Date dt = sdf.parse(datetime);
			if (now.getYear()==dt.getYear() && now.getMonth()==dt.getMonth() && now.getDate()==dt.getDate()) {
				return df[1].format(dt);
			}
			return df[0].format(dt);
		} catch (ParseException e) {
			return datetime;
		}
	}

	
	//----------------------------------------------------------------------------

	@Override
	public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String profileChatId = args.getString(DataProvider.COL_CHATID);
		android.support.v4.content.CursorLoader loader = new android.support.v4.content.CursorLoader(getActivity(), 
				DataProvider.CONTENT_URI_MESSAGES, 
				null, 
				DataProvider.COL_TO + " = ? or (" + DataProvider.COL_FROM + " = ? and " + DataProvider.COL_TO + " = ?)",
				new String[]{profileChatId, profileChatId, mSettingsManager.getChatId()}, 
				DataProvider.COL_AT + " DESC"); 
		return loader;
	}

	@Override
	public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}


}
