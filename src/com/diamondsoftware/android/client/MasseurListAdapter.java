package com.diamondsoftware.android.client;

import java.util.ArrayList;

import com.diamondsoftware.android.common.ImageLoaderRemote;
import com.diamondsoftware.android.massagenearby.model.ItemMasseur;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MasseurListAdapter extends BaseAdapter {
	ArrayList<Object> mMasseurs;
	Context mContext;
	ImageLoaderRemote mImageLoaderRemote;
	
	public MasseurListAdapter(Context context,ArrayList<Object> masseurs) {
		mMasseurs=masseurs;
		mContext=context;
		mImageLoaderRemote=new ImageLoaderRemote(context, false, 0);
	}
	
	@Override
	public int getCount() {
		return mMasseurs==null?0:mMasseurs.size();
	}

	@Override
	public Object getItem(int position) {
		return mMasseurs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return ((ItemMasseur)mMasseurs.get(position)).getmUserId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int width=parent.getWidth();
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams((int)(width/2-5), (int)(width/2-5)));
            //imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }
        if (((ItemMasseur)getItem(position)).getMainPictureURL()!=null) {
			String url="http://"+com.diamondsoftware.android.massagenearby.common.CommonMethods.getBaseURL(mContext)+"/MassageNearby/files/"+((ItemMasseur)getItem(position)).getMainPictureURL();
        	mImageLoaderRemote.displayImage(url, imageView);
        }	
        return imageView;
    }
}
