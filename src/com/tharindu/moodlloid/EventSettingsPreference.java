package com.tharindu.moodlloid;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

/**********************************************************************************
 * This is a shared Prefernce acitivty to handle the settings of Event Notification
 * @author tharindu
 ***********************************************************************************/
public class EventSettingsPreference extends PreferenceActivity {

	/* (non-Javadoc)
	 * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_calendar_settings);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId()==android.R.id.home)
			finish();
		return super.onOptionsItemSelected(item);
	}
}
