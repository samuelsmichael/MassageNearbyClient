package com.diamondsoftware.android.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

public abstract class ImageLoader {
	protected Context mContext;
	protected boolean mScaleImageToScreenWidth=false;

	public ImageLoader(Context context, boolean scaleImageToScreenWidth) {
		mContext=context;
	    mScaleImageToScreenWidth=scaleImageToScreenWidth;
	}
	/*
	 * Do the work of associating the image to the ImageView.
	 * In the case of ImageLoaderLocal, it's a simple matter of 
	 * setting the image resource directly.
	 * With ImageLoaderRemote, things are much more complicated.  First, the FileCache is checked for the same reason.  
	 * If the FileCache doesn't contain the bitmap, then it needs to be fetched (in a background thread), and then resized
	 * so that it expands to the requested size.
	 */
	protected abstract void displayImage(String uri,ImageView imageView);
	protected Bitmap getResizedBitmap1(Bitmap bm, int newHeight, int newWidth)
	{
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // create a matrix for the manipulation
	    Matrix matrix = new Matrix();
	   
	    // resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);
	    // recreate the new Bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	
}
