package com.tharindu.moodlloid;


//import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.tharindu.moodlloid.model.Site;
import com.tharindu.moodlloid.net.SessionWrapper;
import com.tharindu.moodlloid.rest.MoodleUser;
import com.tharindu.moodlloid.util.UIHelper;

public class TestDataActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.profile_fragment);


		SessionWrapper currentSession=SessionWrapper.getCurrentsession();
		Site site=currentSession.getCurrentLogin();
		MoodleUser user=currentSession.getCurrentUser();

		if(site==null)
			UIHelper.showToast(this,"Site is null");
		if(user==null)
			UIHelper.showToast(this, "user is Null");

		if(user!=null)
		{
			((TextView)findViewById(R.id.lblFirstName)).setText(user.getFirstname());
			((TextView)findViewById(R.id.lblLastName)).setText(user.getLastname());
			((TextView)findViewById(R.id.lblEmail)).setText(user.getEmail());
			((TextView)findViewById(R.id.lblUserName)).setText(site.getUserName());
			((TextView)findViewById(R.id.lblCity)).setText(user.getCity());
			ImageView iv=(ImageView)findViewById(R.id.imageIcon);
			iv.setImageBitmap(currentSession.getUserImage());
		}
		else
		{
			UIHelper.showToast(this,"Eror occured");
			finish();
		}


	}
}	

