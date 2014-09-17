package com.tharindu.moodlloid;


import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tharindu.moodlloid.net.SessionWrapper;
import com.tharindu.moodlloid.rest.MoodleCalendar;
import com.tharindu.moodlloid.rest.MoodleRestCalendar;
import com.tharindu.moodlloid.rest.MoodleRestException;
import com.tharindu.moodlloid.rest.MoodleRestWebServiceException;
import com.tharindu.moodlloid.rest.UserEnrolledCourse;
import com.tharindu.moodlloid.util.Timer;
import com.tharindu.moodlloid.util.UIHelper;

/*******************************************************************
 * This class will Get the Notification Informations and
 * store them in dataSe for the further usage from other activities
 * @author tharindu
 ******************************************************************* */
public class NotificationsFragment extends Fragment {

	public static MoodleCalendar[] calendarEvents;
	public Activity containerActivity;
	public TextView tv;
	public String LOGTAG="NOTIFICATION";
	public ProgressBar pb;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater .inflate(R.layout.marks_fragment, container, false);
		//		tv=(TextView)rootView.findViewById(R.id.textView1);
		//Button settings=(Button)rootView.findViewById(R.id.bt)

		Button btnSettings=(Button)rootView.findViewById(R.id.btnSettings);
		btnSettings.setOnClickListener(new SettingsListner());

		Button calendarBtn=(Button)rootView.findViewById(R.id.btnCalendar);
		pb=(ProgressBar)rootView.findViewById(R.id.progressBar1);
		Button calendarListBtn=(Button)rootView.findViewById(R.id.btnEventList);

		containerActivity=(Activity)rootView.getContext();

		calendarBtn.setOnClickListener(new CalendarClickListner());
		calendarListBtn.setOnClickListener(new CalendarListListner() );

		return rootView;
	}

	/**
	 * Sync Calendar option
	 */
	public void LoadCalendar(){
		UIHelper.showToast(containerActivity,"Calendar is Syncd");
	}

	/**
	 * Load the event list using Asyntask
	 */
	public void loadEventList()	{		
		MyTask task=new MyTask();
		task.execute("");
	}


	/***************************Inner class that handle the Settings click*************** ***********/

	class SettingsListner implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			Intent i= new Intent(containerActivity,EventSettingsPreference.class);
			startActivity(i);
		}

	}

	/***************************Inner class that Sync the calendar to local calendar *****************/

	class  CalendarClickListner implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			pb.setVisibility(ProgressBar.VISIBLE);
			MyTask task=new MyTask();
			task.execute("");
		}

	}

	/***************************Inner class that  Load the Event List ********************************/

	class CalendarListListner implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			pb.setVisibility(ProgressBar.VISIBLE);
			MyTask task=new MyTask();
			task.execute("");
		}

	}

	/***************************Inner class that Send the request to the web servies and get the notification messages ***********/

	private class MyTask extends AsyncTask<String,String ,MoodleCalendar[]> {
		@Override
		protected   MoodleCalendar[] doInBackground(String... params) {

			try{
				ArrayList<UserEnrolledCourse> currentCourses= SessionWrapper.getCurrentsession().getCurrentUser().getEnrolledCourses();
				//get the current Courses
				MoodleCalendar [] calenderDetails=new MoodleCalendar[currentCourses.size()];
				//Create the moodleCallendar object array with course ID to pass to the CalendarREstClient
				for (int i = 0; i < calenderDetails.length; i++) {

					MoodleCalendar cal= new MoodleCalendar();
					cal.setCourseid((int)currentCourses.get(i).getId().longValue());
					//				Log.i(LOGTAG,"i "+(int)currentCourses.get(i).getId().longValue());

					calenderDetails[i]=cal;
				}

				Date d=new Date();// current Time instance
				long timstamps=d.getTime();			

				MoodleCalendar [] responseEvents=MoodleRestCalendar.getCalendarEvents(calenderDetails, 1,1,Timer.getStartOfTheMonthInMili(timstamps)/1000,Timer.getEndOftheMonthInMili(timstamps)/1000,1);
				//should send request to server and get Calendar responsess and return to onPostExecute 
				return responseEvents; 
			}     //printSiteInfo(siteInfo);
			catch (MoodleRestWebServiceException ex) {
				// Logger.getLogger(MoodleGetSiteInfoExample.class.getName()).log(Level.SEVERE, null, ex);
			} catch (MoodleRestException ex) {
				// Logger.getLogger(MoodleGetSiteInfoExample.class.getName()).log(Level.SEVERE, null, ex);

			}catch(Exception ex)
			{
				Log.i("Welcome",ex.getMessage());
			} 
			return null;

		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			//	UIHelper.showToast(eve,values[0]);
			//retur

		}


		@Override
		protected void onPostExecute(MoodleCalendar [] result) {


			pb.setVisibility(ProgressBar.INVISIBLE);


			if (result==null) {
				UIHelper.showToast(containerActivity,"Error Occureed");
			} else {


				if(result[0]==null)
				{
					//tv.setText("You don't have any Events");
				}
				else
				{
					//Start The calendar Event List
					calendarEvents=result;
					Intent i=new Intent(containerActivity,EventsActivity.class);
					startActivity(i);	
				}		

			}


		}

	}


}
