package com.diamondsoftware.android.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

 
public class Utils {
	/*
	 * Makes the first letter of each word captialized, and all other letters lower-case
	 */
	public static String scrubResortName(String resortName) {
		return resortName.replace("ln", "lane").replace("Ln", "Lane").replace("LN", "LANE");
		
	}
	public static double round(double unrounded, int precision, int roundingMode)
	{
	    BigDecimal bd = new BigDecimal(unrounded);
	    BigDecimal rounded = bd.setScale(precision, roundingMode);
	    return rounded.doubleValue();
	}
	
	public static boolean canHandleIntent(Context context, Intent intent){
	    PackageManager packageManager = context.getPackageManager();
	    List activities = packageManager.queryIntentActivities(
	        intent, 
	        PackageManager.MATCH_DEFAULT_ONLY);
	    return activities.size() > 0;
	}	
	
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
    
	public static GregorianCalendar toGregorianCalendarFromYYYYdashMMdashDDTHHcolonMMcolonSS(String s) {
		return new GregorianCalendar(
			  Integer.valueOf(s.substring(0, 3)), 
			  Integer.valueOf(s.substring(5, 6)),
			  Integer.valueOf(s.substring(8,2)),
			  Integer.valueOf(s.substring(11, 2)),
			  Integer.valueOf(s.substring(14, 2)),
			  Integer.valueOf(s.substring(17, 2)));
	}
	public static GregorianCalendar toDateFromYYYYdashMMdashDDTHHcolonMMcolonSS(String s) {
		if (s==null || s.equalsIgnoreCase("null")) {
			s="0001-01-01T00:00:00"; // the system was originally written to return null's like this
		}
		int yyyy=Integer.valueOf(s.substring(0, 4));
		int mm=Integer.valueOf(s.substring(5, 7));
		int dd=Integer.valueOf(s.substring(8,10));
		int hh=Integer.valueOf(s.substring(11,13));
		int min=Integer.valueOf(s.substring(14,16));
		int sec=Integer.valueOf(s.substring(17));
		return new GregorianCalendar(
			  yyyy, 
			  mm-1,
			  dd,hh,min,sec);
	}
	public static java.util.Calendar toDateFromMMdashDDdashYYYY(String s) throws ParseException {
		DateFormat df1 = new SimpleDateFormat("MM-dd-yyyy");
		DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat df3 = new SimpleDateFormat("MM.dd.yyyy");
		Date parsed=null;
		try {
			parsed = df1.parse(s);
		} catch (Exception e) {
			try {
				parsed=df2.parse(s);
			} catch (Exception e2) {
				parsed=df3.parse(s);
			}
		}
		java.util.Calendar newCalendar = GregorianCalendar.getInstance();
		newCalendar.setTime(parsed);
		return newCalendar;
	}
	
}