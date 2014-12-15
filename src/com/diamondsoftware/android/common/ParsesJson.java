package com.diamondsoftware.android.common;

import java.io.IOException;
import java.util.ArrayList;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
/**
 * All ParsesJson subclasses are passed a Json string.  The
 * subclass parses the json string, creating an ArrayList of objects (generally SunriverDataItems).
 * @author Diamond
 *
 */
public abstract class ParsesJson {
	public ParsesJson() {
	}
	protected abstract ArrayList<Object> parse(String jsonString) throws Exception;
}
