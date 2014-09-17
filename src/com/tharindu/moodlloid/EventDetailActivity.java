package com.tharindu.moodlloid;

import java.util.ArrayList;
import java.util.Date;

import com.tharindu.moodlloid.net.SessionWrapper;
import com.tharindu.moodlloid.rest.MoodleCalendar;
import com.tharindu.moodlloid.rest.UserEnrolledCourse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detailed);	
		
		//Intent  i=new Int
		//MoodleCalendar cal=new MoodleCalendar();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		MoodleCalendar model = (MoodleCalendar) getIntent().getSerializableExtra("EventDetailActivity");

		TextView eventName=(TextView)findViewById(R.id.lblName);
		TextView eventStartTime=(TextView)findViewById(R.id.lblStartTime);
		TextView eventEndTime=(TextView)findViewById(R.id.lblEndTime);
		TextView eventCoursename=(TextView)findViewById(R.id.lblCourseName);
		TextView eventResource=(TextView)findViewById(R.id.lblDate);
		TextView eventDetails=(TextView)findViewById(R.id.lblDetail);
		TextView eventModifiedTime=(TextView)findViewById(R.id.lblModified);
		ImageView imageView=(ImageView)findViewById(R.id.imageView1);
	
		setTitle(model.getEventtype()+" event");
		eventName.setText(model.getName());
		eventDetails.setText(model.getDescription());

		Date d1=new Date(model.getTimestart()*1000);
		eventStartTime.setText(d1.toLocaleString());

		Date d2=new Date(model.getTimeModified()*1000);
		eventModifiedTime.setText(d2.toLocaleString());
		eventResource.setText(model.getModuleName());

		ArrayList<UserEnrolledCourse> courses=SessionWrapper.getCurrentsession().getCurrentUser().getEnrolledCourses();

		if(model.getEventtype().equals("course")){
			UserEnrolledCourse currentCourse=null;
			for (int i = 0; i < courses.size(); i++) {
				if(courses.get(i).getId().longValue()==model.getCourseid().longValue())
				{
					currentCourse=courses.get(i);
					break;
				}
			}
			imageView.setImageResource(R.drawable.coure_event);
			eventCoursename.setText(currentCourse.getShortName());
		}else if(model.getEventtype().equals("user")){
			imageView.setImageResource(R.drawable.user_event);
		}
		else if(model.getEventtype().equals("site")){
			imageView.setImageResource(R.drawable.site_event);
		}	
		else
		{	
			imageView.setImageResource(R.drawable.default_event);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==android.R.id.home)
			finish();
		return super.onOptionsItemSelected(item);
	}
}
