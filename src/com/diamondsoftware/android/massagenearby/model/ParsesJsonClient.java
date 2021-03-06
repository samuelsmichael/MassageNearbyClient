package com.diamondsoftware.android.massagenearby.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.diamondsoftware.android.common.ParsesJson;

public class ParsesJsonClient extends ParsesJson {
	String mName;
	public ParsesJsonClient(String name) {
		mName=name;
	}

	@Override
	protected ArrayList<Object> parse(String jsonString) throws Exception {
		ArrayList<Object> items = new ArrayList<Object>();
		JSONArray jsonArray = new JSONArray(jsonString);
		int c=jsonArray.length();
		for(int i=0;i<c;i++) {
			ItemClient item=new ItemClient();
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			item.setmClientId(jsonObject.getInt("ClientId"));
			item.setmName(jsonObject.getString("Name"));
			item.setmURL(jsonObject.getString("URL"));
			item.setmUserId(jsonObject.getInt("UserId"));
			items.add(item);
		}
		return items;
	}

}
