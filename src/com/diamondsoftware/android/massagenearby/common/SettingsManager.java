package com.diamondsoftware.android.massagenearby.common;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingsManager {
	private SharedPreferences mSharedPreferences;
	private Context mContext;
	
	public SettingsManager(Context context) {
		mSharedPreferences=context.getSharedPreferences(context.getPackageName() + "_preferences", Activity.MODE_PRIVATE);
		mContext=context;
	}
	private String getValue(String key, String defValue) {
		return mSharedPreferences.getString(key, defValue);
	}
	private void setValue(String key, String value) {
		Editor editor=mSharedPreferences.edit();
		editor.putString(key,value);
		editor.commit();				
	}
	public boolean getIsEnabledMasseur() {
		String value= getValue(GlobalStaticValuesMassageNearby.KEY_IS_ENABLED_MASSEUR,"true");
		return value.equals("true")?true:false;
	}
	public void setIsEnabledMasseur(boolean value) {
		Editor editor=mSharedPreferences.edit();
		editor.putString(GlobalStaticValuesMassageNearby.KEY_IS_ENABLED_MASSEUR, value?"true":"false");
		editor.commit();
	}
	public String getMasseurName() {
		return getValue(GlobalStaticValuesMassageNearby.KEY_MASSEUR_NAME,null);
	}
	public void setMasseurName(String value) {
		setValue(GlobalStaticValuesMassageNearby.KEY_MASSEUR_NAME,value);
	}
	public int getLoggingLevel() {
		String value=getValue(com.diamondsoftware.android.common.GlobalStaticValues.KEY_LOGGINGLEVEL,String.valueOf(com.diamondsoftware.android.common.GlobalStaticValues.LOG_LEVEL_CRITICAL));
		return Integer.valueOf(value);
	}
	public void setLoggingLevel(int value) {
		setValue(com.diamondsoftware.android.common.GlobalStaticValues.KEY_LOGGINGLEVEL, String.valueOf(value));
	}
	public String getChatId() {
		return getValue("chat_id", "");
	}
	public void setChatId(String chatId) {
		setValue("chat_id",chatId);
	}
    public String getCurrentUserName() {
    	return getValue(GlobalStaticValuesMassageNearby.KEY_CURRENT_USER_NAME, null);
    }
    public void setCurrentUserName(String name) {
    	setValue(GlobalStaticValuesMassageNearby.KEY_CURRENT_USER_NAME, name);
    }
}
