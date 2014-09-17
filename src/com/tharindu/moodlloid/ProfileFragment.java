package com.tharindu.moodlloid;

import com.tharindu.moodlloid.R;
import com.tharindu.moodlloid.model.Site;
import com.tharindu.moodlloid.net.SessionWrapper;
import com.tharindu.moodlloid.rest.MoodleUser;
import com.tharindu.moodlloid.rest.MoodleWebService;
import com.tharindu.moodlloid.util.UIHelper;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/***********************************************************
 * This class will shows the Profil of the current user 
 * @author tharindu
 *******************************************************/
public class ProfileFragment extends Fragment {

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
		View rootView = inflater .inflate(R.layout.profile_fragment, container, false);
		
		//get the Current Session Information ....................
		SessionWrapper currentSession=SessionWrapper.getCurrentsession();
		Site site=currentSession.getCurrentLogin();
		MoodleUser user=currentSession.getCurrentUser();
		
		
		//notifiy user if somthing missing?
		if(site==null)
			UIHelper.showToast(rootView.getContext(),"Site is null");
		if(user==null)
			UIHelper.showToast(rootView.getContext(), "user is Null");

		if(user!=null)
		{
			//Set the Profile information
			
			((TextView)rootView.findViewById(R.id.lblFirstName)).setText(user.getFirstname());
			((TextView)rootView.findViewById(R.id.lblLastName)).setText(user.getLastname());
			((TextView)rootView.findViewById(R.id.lblEmail)).setText(user.getEmail());
			((TextView)rootView.findViewById(R.id.lblUserName)).setText(site.getUserName());
			((TextView)rootView.findViewById(R.id.lblCity)).setText(user.getCity());
			ImageView iv=(ImageView)rootView.findViewById(R.id.imageIcon);
			iv.setImageBitmap(currentSession.getUserImage());
		}
		else
		{
			UIHelper.showToast(rootView.getContext(),"Eror occured");
			//finish();
		}
		return rootView;
    }
		
	
}
