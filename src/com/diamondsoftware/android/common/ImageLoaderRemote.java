	package com.diamondsoftware.android.common;

	import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
	 

	import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
	
/*
 * This class does the work of fetching a URL image.  Optionally (and usually) it associates it with
 * an ImageView.  Perhaps there is no ImageView to associate to (as is the case when loading Selfie images
 * for the blending of the image with the camera image); but in any case, the fetched bitmap is stored in
 * FileCache for subsequent retrieval without having to do an Internet fetch.
 * 
 * When being constructed, a boolean parameter (scaleImageToScreenWidth) requests that the image is to 
 * be resized to the screen width.  If not (e.g. - all list view icons take on the definite width of
 * the ImageViews width and height), then the bitmap is simply associated with the ImageView; but if so,
 * then the WidthFactor (1 = full width of the display) determines the width, and the bitmap is scaled
 * appropriately.
 */
	public class ImageLoaderRemote extends ImageLoader {
	 
	    FileCache fileCache;
	    // 1=resize to full width of the display; .5=half; and so on
	    float mWidthFactor;
	    Context mContext;
	    ExecutorService executorService;
	 

	    public ImageLoaderRemote( Context context, boolean scaleImageToScreenWidth, float widthFactor){
	    	super(context,scaleImageToScreenWidth);
	    	mWidthFactor=widthFactor;
	        fileCache=new FileCache(context);
	        executorService=Executors.newFixedThreadPool(5);
	        mContext=context;
	    }

	 
	    /* Leaving imageView=null means that we'll load and cache the photo, but not display it anywhere (e.g. - when
	     * Selfie portrait images need to be supplied for the proper blending to the camera bitmap ... this
	     * image isn't displayed in any ImageView.   We just used this class to load and store the image
	     * in the FileCache.)
	    */
	    public void displayImage(String uri,ImageView imageView)
	    {
            queuePhoto(uri, imageView, mContext);
	    }
	 /*
	  * 
	  */
	    private void queuePhoto(String url, ImageView imageView, Context context)
	    {
	        PhotoToLoad p=new PhotoToLoad(url, imageView, context);
	        executorService.submit(new PhotosLoader(p));
	    }
	 
	    private Bitmap getBitmap(String url)
	    {
	        File f=fileCache.getFile(url);
	 
	        //from SD cache
	        Bitmap b = decodeFile(f);
	        if(b!=null)
	            return b;
	 
	        //from web
	        try {
	            Bitmap bitmap=null;
	            String first7=url.substring(0, 7);
	            if(!first7.equalsIgnoreCase("http://")) {
	            	url="http://"+url;
	            }
	            URL imageUrl = new URL(url);
	            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
	            conn.setConnectTimeout(30000);
	            conn.setReadTimeout(30000);
	            conn.setInstanceFollowRedirects(true);
	            InputStream is=conn.getInputStream();
	            OutputStream os = new FileOutputStream(f);
	            Utils.CopyStream(is, os);
	            os.close();
	            bitmap = decodeFile(f);
	            return bitmap;
	        } catch (Exception ex){
	           ex.printStackTrace();
	           return null;
	        }
	    }
	 
	    private Bitmap decodeFile(File f){
	        try {
	        	if(!mScaleImageToScreenWidth) { // just display the bitmap as is
		            return BitmapFactory.decodeStream(new FileInputStream(f));

	        	} else { // scale the width (mWidthFactor = 0 to 1 -- one being full width of display) and the height appropriately
	        		Bitmap bitmap=BitmapFactory.decodeStream(new FileInputStream(f));
	        		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
	        		Display display = wm.getDefaultDisplay();
	        		int width=(int)((float)display.getWidth()*mWidthFactor);
	        		int height=display.getHeight();
	    		// We need to adjust the height if the width of the bitmap is
	    		// smaller than the view width, otherwise the image will be boxed.
	    			final double viewWidthToBitmapWidthRatio = (double)width / (double)bitmap.getWidth();
	    			int newBitmapHeight=(int) (bitmap.getHeight() * viewWidthToBitmapWidthRatio);
	    			int newBitmapWidth=(int)(bitmap.getWidth()*viewWidthToBitmapWidthRatio);
	    			if(newBitmapHeight*2>height) {
	    				return bitmap;
	    			} else {
	    				return getResizedBitmap1(bitmap,newBitmapHeight,newBitmapWidth);
	    			}
	        	}
	        } catch (FileNotFoundException e) {
	        	e=e;
	        	e=null;
	        }
	        return null;
	    }
	 
	    //Task for the queue
	    private class PhotoToLoad
	    {
	        public String url;
	        public ImageView imageView;
	        public Context mContext;
	        public PhotoToLoad(String u, ImageView i, Context context){
	            url=u;
	            imageView=i;
	            mContext=context;
	        }
	    }
	 
	    class PhotosLoader implements Runnable {
	        PhotoToLoad photoToLoad;
	        PhotosLoader(PhotoToLoad photoToLoad){
	            this.photoToLoad=photoToLoad;
	        }
	 
	        @Override
	        public void run() {

	            Bitmap bmp=getBitmap(photoToLoad.url);

	            BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
	            Activity a=(Activity)photoToLoad.mContext;	
	            a.runOnUiThread(bd);
	        }
	    }
	  
	    //Used to display bitmap in the UI thread
	    class BitmapDisplayer implements Runnable
	    {
	        Bitmap bitmap;
	        PhotoToLoad photoToLoad;
	        public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
	        public void run()
	        {
	            if(bitmap!=null && photoToLoad.imageView!=null) { // if either the bitmap load failed, or the imageView=null, then don't set the imageView's bitmap
            			photoToLoad.imageView.setImageBitmap(bitmap);
	            }
	        }
	    }
	 
	    public void clearCache() {
	        fileCache.clear();
	    }
	 
	}

