package com.tharindu.moodlloid;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class EventSettingsPreference extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_calendar_settings);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId()==android.R.id.home)
			finish();
		return super.onOptionsItemSelected(item);
	}
}
