/**
 * 
 */
package com.diamondsoftware.android.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


/**
 * Provides the abstraction for fetching Sunriver data needed by a ListViewAdapter -- whether that
 * data be local on the machine (e.g. LandingPage, EatsAndTreats (whose data has already been acquired 
 * via the fetch of Sunriver locations)), or remote data (e.g. - Activities, Events)
 * 
 * JSon always have an ParsesJson object to which it passes an InputStream (again, acquired
 * either locally or remotely), and ask to parse the data into an ArrayList of SunriverDataItems. 
 *  
 * @author Mike Samuels
 *
 */
public abstract class JsonReader implements FormattedDataReader {
	private ParsesJson mParsesJson;
	/**
	 * 
	 */
	protected JsonReader(ParsesJson parsesJson) {
		mParsesJson=parsesJson;
	}
	public abstract ArrayList<Object> parse() throws Exception; 
	protected ArrayList<Object> parse(String jsonData) throws  Exception {	
        return mParsesJson.parse(jsonData);
	}
}

