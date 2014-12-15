package com.diamondsoftware.android.client;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.diamondsoftware.android.client.R.id;
import com.diamondsoftware.android.client.dummy.DummyContent;
import com.diamondsoftware.android.massagenearby.common.GlobalStaticValuesMassageNearby;
import com.diamondsoftware.android.massagenearby.model.ItemMasseur;

/**
 * A fragment representing a single Masseur detail screen.
 * This fragment is either contained in a {@link MasseurListActivity}
 * in two-pane mode (on tablets) or a {@link MasseurDetailActivity}
 * on handsets.
 */
public class MasseurDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ItemMasseur mItemMasseur;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MasseurDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
        	mItemMasseur=MasseurListActivity.mSingleton.getMasseurWhoseUserIdIs(Integer.valueOf(getArguments().getString(ARG_ITEM_ID)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	if(mItemMasseur!=null) {
	        View rootView = inflater.inflate(R.layout.fragment_masseur_detail, container, false);
	        ((TextView) rootView.findViewById(R.id.masseur_detail)).setText(mItemMasseur.getmName());
	        Button btnChat=(Button)rootView.findViewById(id.bnt_chat);
	        btnChat.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(getActivity(),ActivityChat.class).putExtra(GlobalStaticValuesMassageNearby.KEY_CHAT_ID, String.valueOf(mItemMasseur.getmUserId()));
					startActivity(intent);
				}
			});
	        return rootView;
    	}
    	return null;
    }
}
