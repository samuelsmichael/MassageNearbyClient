package com.diamondsoftware.android.client;

import java.net.URLEncoder;
import java.util.ArrayList;



import com.diamondsoftware.android.common.AcquireDataRemotelyAsynchronously;
import com.diamondsoftware.android.common.DataGetter;
import com.diamondsoftware.android.common.JsonReaderFromRemotelyAcquiredJson;
import com.diamondsoftware.android.common.WaitingForDataAcquiredAsynchronously;
import com.diamondsoftware.android.massagenearby.common.SettingsManager;
import com.diamondsoftware.android.massagenearby.common.SocketCommunicationsManager;
import com.diamondsoftware.android.massagenearby.common.TellMeWhenYouveGotNewMesseurs;
import com.diamondsoftware.android.massagenearby.model.ItemClient;
import com.diamondsoftware.android.massagenearby.model.ItemMasseur;
import com.diamondsoftware.android.massagenearby.model.ParsesJsonClient;
import com.diamondsoftware.android.massagenearby.model.ParsesJsonMasseur;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;




/**
 * An activity representing a list of Masseur. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MasseurDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MasseurListFragment} and the item details
 * (if present) is a {@link MasseurDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link MasseurListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class MasseurListActivity extends Activity
        implements MasseurListFragment.Callbacks,
        WaitingForDataAcquiredAsynchronously,DataGetter {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static MasseurListActivity mSingleton;
	private SettingsManager mSettingsManager;
	ProgressDialog mProgressDialog;
	ArrayList<Object> mAllMasseurs=new ArrayList<Object>();
	Handler mHandler;
	ItemClient mItemClientMe;
	private TellMeWhenYouveGotNewMesseurs mTellNewMasseurs;

	public void updateMasseursList(TellMeWhenYouveGotNewMesseurs needsToKnowAboutNewMasseurs) {
		mTellNewMasseurs=needsToKnowAboutNewMasseurs;
		new AcquireDataRemotelyAsynchronously("all~", this, this);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masseur_list);
        mSettingsManager=new SettingsManager(this);
        mHandler=new Handler();
        mSingleton=this;
  	   new AcquireDataRemotelyAsynchronously("all~", this, this);

        if (findViewById(R.id.masseur_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MasseurListFragment) getFragmentManager()
                    .findFragmentById(R.id.masseur_list))
                    .setActivateOnItemClick(true);
        }


	    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
	    android.app.Fragment prev = getFragmentManager().findFragmentByTag("login");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    Login selectLogin=new Login();
		selectLogin.show(ft,"login");
    }

    /**
     * Callback method from {@link MasseurListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(MasseurDetailFragment.ARG_ITEM_ID, id);
            MasseurDetailFragment fragment = new MasseurDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.masseur_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, MasseurDetailActivity.class);
            detailIntent.putExtra(MasseurDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
    public class Login extends DialogFragment {
    	View mView;
    	EditText mEditText;
    	

		@Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
		        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		        // Get the layout inflater
		        LayoutInflater inflater = getActivity().getLayoutInflater();
		        mView = inflater.inflate(R.layout.login, null);
		        mEditText=(EditText)mView.findViewById(R.id.edittextloginid);
		        mEditText.setText(mSettingsManager.getCurrentUserName());
		        mEditText.selectAll();
		        builder.setView(mView);
	
		        // Inflate and set the layout for the dialog
		        // Pass null as the parent view because its going in the dialog layout
//		        builder.setView(inflater.inflate(R.layout.find_home, null));
		        builder.setTitle("Login")
		        	   .setMessage("Key in your name")
		               .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   final EditText mEditText=(EditText)Login.this.getDialog().findViewById(R.id.edittextloginid);
		                	   MasseurListActivity mainActivity=(MasseurListActivity)getActivity();
		                	   new AcquireDataRemotelyAsynchronously("moi~"+ mEditText.getText().toString(), mainActivity, mainActivity);
		           			   mainActivity.mProgressDialog = ProgressDialog.show(getActivity(),"Working ...","Logging in "+mEditText.getText().toString().trim(),true,false,null);
		           			
		                  }
		               })
		               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   finish();
		                   }
		               });
		        // Create the AlertDialog object and return it
		        return builder.create();
        }		
    }
	ItemMasseur getMasseurInPosition(int position) {
		return (ItemMasseur)mAllMasseurs.get(position);
	}

	@Override
	public ArrayList<Object> getRemoteData(String keyname) {
		try {
			ArrayList<Object> data=null;
			String[] array = keyname.split("\\~", -1);
			String key=array[0];
			String name=array[1];
			// 10.0.0.253 when wifi on my computer
			String ipAddress=com.diamondsoftware.android.common.CommonMethods.getLocalIpAddress();
			String url=null;
			if(key.equals("moi")) {
				url="http://"+com.diamondsoftware.android.massagenearby.common.CommonMethods.getBaseURL(this)+"/MassageNearby/Client.aspx"+"?Name="+URLEncoder.encode(name)+"&URL="+URLEncoder.encode(ipAddress);
				data = new JsonReaderFromRemotelyAcquiredJson(
						new ParsesJsonClient(name), 
						url
						).parse();
			} else {
				if(key.equals("all")) {
					url="http://"+com.diamondsoftware.android.massagenearby.common.CommonMethods.getBaseURL(this)+"/MassageNearby/Masseur.aspx";
					data = new JsonReaderFromRemotelyAcquiredJson(
							new ParsesJsonMasseur(name), 
							url
							).parse();
				}
			}

			return data;
		} catch (Exception e) {
			int bkhere1=3;
			int bkhere2=bkhere1;
		} finally {
		}			
	return null;
	}

	@Override
	public void gotMyData(String keyname, ArrayList<Object> data) {
		String[] array = keyname.split("\\~", -1);
		String key=array[0];
		String name=array[1];
		if(key.equals("moi")) {
			this.mProgressDialog.dismiss();
		}
		if(data!=null && data.size()>0) {
			if(key.equals("moi")) {
				mItemClientMe=(ItemClient)data.get(0);
	        	mSettingsManager.setChatId(String.valueOf(mItemClientMe.getmUserId()));
				mSettingsManager.setCurrentUserName(mItemClientMe.getmName());
			} else {
				if(key.equals("all")) {
					mAllMasseurs=data;
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							ListFragment fm=(ListFragment)getFragmentManager().findFragmentById(R.id.masseur_list);
					        fm.setListAdapter(new ArrayAdapter<Object>(
					                MasseurListActivity.this,
					                android.R.layout.simple_list_item_activated_1,
					                android.R.id.text1,
					                mAllMasseurs));
						//	ArrayAdapter<Object> aa=(ArrayAdapter<Object>)fm.getListAdapter(); // this doesn't work
						//	aa.notifyDataSetChanged();
						}
						
					});
					if(mTellNewMasseurs!=null) {
						try {
							mTellNewMasseurs.IveGotNewMesseurs();
						} catch (Exception e) {}
						mTellNewMasseurs=null;
					}
				}
			}
		}
	}

	public ItemMasseur getMasseurWhoseUserIdIs(int userId) {
		ItemMasseur retValue=null;
		for(Object masseur: mAllMasseurs) {
			if(((ItemMasseur)masseur).getmUserId()==userId) {
				retValue=(ItemMasseur)masseur;
				break;
			}
		}
		return retValue;
	}

}
