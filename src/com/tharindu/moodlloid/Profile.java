package com.tharindu.moodlloid;

import com.tharindu.moodlloid.R;
import com.tharindu.moodlloid.rest.MoodleWebService;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Profile extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
		View rootView = inflater .inflate(R.layout.profile_fragment, container, false);
	
		//setContentView(R.layout.activity_test_data);
//		MoodleWebService b=Welcome.siteINFO;
//		//MoodleWebService  b=(MoodleWebService)getIntent().getSerializableExtra("MoodleWebService");
//		TextView t=(TextView)rootView.findViewById(R.)
//		t.setText(b.getFirstName());
//		
		
		return rootView;
		
		
    }
	
	
	
}
