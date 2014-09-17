package com.tharindu.moodlloid;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.tharindu.moodlloid.net.SessionWrapper;
import com.tharindu.moodlloid.rest.MoodleCalendar;
import com.tharindu.moodlloid.rest.UserEnrolledCourse;
import com.tharindu.moodlloid.util.CalendarManager;
import com.tharindu.moodlloid.util.Timer;
import com.tharindu.moodlloid.util.UIHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract.Events;
import android.service.textservice.SpellCheckerService.Session;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EventsActivity extends Activity{



	private List<UserEnrolledCourse> courses;
	private ListView eventslist;
	private List<MoodleCalendar> events;
	private Spinner eventTypedropDown;
	private ArrayAdapter<CharSequence> TypeAdapter;
	private Spinner eventCourse;
	private ArrayAdapter<CharSequence> courseAdapter;
	private TextView courseType;
	private int selectedTypePosition;
	private int selectedCoursePosition;
	private boolean CourseUnselected=true;
	private EventsAdapter eventAdapter;

	private String LOGTAG="EventsActivity";

	private long upperbound=0;
	private long lowerbound=0;
	private boolean UserEvents=true;
	private boolean SiteEvents=true;
	private long period=3600*24*30;
	private boolean AllCourse=true;	
	private int specificCourse=-1;
	private MoodleCalendar[] dataset;



	boolean usercheck1d;
	boolean usercheck5m;
	boolean usercheck1w;
	boolean coursecheck5m;
	boolean coursecheck1d;
	boolean coursecheck1w;
	boolean sitecheck5m;
	boolean sitecheck1d;
	boolean sitecheck1w;


	void initializeParamters()
	{

		Date d=new Date();

		upperbound=Timer.getEndOftheMonthInMili(d.getTime())/1000;
		lowerbound=Timer.getStartOfTheMonthInMili(d.getTime())/1000;

		UserEvents=true;
		SiteEvents=true;
		period=3600*24*30;
		AllCourse=true;	
		specificCourse=-1;
	}
	public ArrayList<MoodleCalendar> generateList()
	{
		ArrayList<MoodleCalendar> tempList=new ArrayList<MoodleCalendar>();

		for (int i =dataset.length-1; i >= 0; i--) {
			MoodleCalendar currentEvent=dataset[i];

			if(currentEvent.getTimestart().longValue()>=lowerbound && currentEvent.getTimestart().longValue()<=upperbound)
			{		
				if(SiteEvents)
				{
					if(currentEvent.getEventtype().equals("site"))
					{
						tempList.add(currentEvent);
						continue;
					}
				}

				if(UserEvents)
				{
					if(currentEvent.getEventtype().equals("user"))
					{
						tempList.add(currentEvent);
						continue;
					}	
				}

				if(AllCourse)
				{
					if(currentEvent.getEventtype().equals("course"))
					{	tempList.add(currentEvent);
					continue;
					}
				}
				else
				{
					if(currentEvent.getEventtype().equals("course")){


						if(selectedCoursePosition>0 && currentEvent.getCourseId().intValue()==courses.get(selectedCoursePosition-1).getId().longValue())
							tempList.add(currentEvent);
					}
				}
			}

		}
		return tempList;
	}

	private CourseListAdapter courseListAdapter;
	private List<UserEnrolledCourse> courseList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_event_list_main);

		setTitle(R.string.title_activity_event_list);
		eventslist=(ListView)findViewById(R.id.listEvents);
		initializeParamters();
		SetGUI();
		loadData();
		setSharedPreferences();
		updateView();
		eventslist.setOnItemClickListener(new EventClickListner());
	}

	public void SetGUI()
	{
		eventTypedropDown=(Spinner)findViewById(R.id.dropdownType);
		eventCourse=(Spinner)findViewById(R.id.dropdownCourse);
		TypeAdapter = ArrayAdapter.createFromResource(this, R.array.event_types_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		TypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		eventTypedropDown.setAdapter(TypeAdapter);

		courses=SessionWrapper.getCurrentsession().getCurrentUser().getEnrolledCourses();
		ArrayList<String>list = new ArrayList<String>();
		list.add("Any");

		for (UserEnrolledCourse uc : courses) {
			list.add(uc.getShortName());
		}

		getActionBar().setDisplayHomeAsUpEnabled(true);

		ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_item);
		eventCourse.setAdapter(adp);
		eventTypedropDown.setOnItemSelectedListener(new EventTypeItemSelectedListner());
		eventCourse.setOnItemSelectedListener(new CourseTypeItemSelectedListner());

	}

	public MoodleCalendar testData()
	{

		MoodleCalendar calendar=new MoodleCalendar();
		calendar.setCourseid(1);
		calendar.setDescription("Some Descritption");
		calendar.setEventtype("course");
		calendar.setName("Quize 2");
		Date d=new Date();
		calendar.setTimestart(Long.valueOf(d.getTime()/1000));

		return calendar;		

	}


	private void setSharedPreferences() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(EventsActivity.this);
		usercheck1d= prefs.getBoolean("usercheck1d", false);
		usercheck5m = prefs.getBoolean("usercheck5m", false);
		usercheck1w= prefs.getBoolean("usercheck1w", false);

		coursecheck5m= prefs.getBoolean("coursecheck5m", false);

		UIHelper.showToast(this,"coursecheck5m"+coursecheck5m);
		
		coursecheck1d= prefs.getBoolean("coursecheck1d", false);

		UIHelper.showToast(this,"coursecheck5m"+coursecheck5m);
		
		coursecheck1w= prefs.getBoolean("coursecheck1w", false);	  

		UIHelper.showToast(this,"coursecheck1w"+coursecheck1w);
		
		
		
		sitecheck5m= prefs.getBoolean("sitecheck5m", false);

		sitecheck1d= prefs.getBoolean("sitecheck1d", false);

		sitecheck1w= prefs.getBoolean("sitecheck1w", false);
	}
	
	public void loadData()
	{
		dataset=NotificationsFragment.calendarEvents;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Date d=new Date();

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.progress:
			syncCalendar();
			break;
		case R.id.action_all:
			upperbound=Timer.getEndOftheMonthInMili(d.getTime())/1000;
			lowerbound=Timer.getStartOfTheMonthInMili(d.getTime())/1000;
			updateView();
			break;
		case R.id.action_past:
			upperbound=d.getTime()/1000;
			lowerbound=Timer.getStartOfTheMonthInMili(d.getTime())/1000;
			updateView();
			break;
		case R.id.action_today:
			upperbound=Timer.getStartOftheDayInMili(d.getTime())/1000;
			lowerbound=Timer.getEndOftheDayInMili(d.getTime())/1000;
			updateView();
			break;
		case R.id.action_upcomming:
			upperbound=Timer.getEndOftheMonthInMili(d.getTime())/1000;
			lowerbound=d.getTime()/1000;		
			updateView();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}



	public void syncCalendar()
	{

		String AccountName=SessionWrapper.getCurrentsession().getCurrentLogin().getUserName();
		long id=CalendarManager.getCalendarId(this, AccountName);
		int counter=0;
		if(id==-1)
		{
			CalendarManager.createLocalCalendar(this,SessionWrapper.getCurrentsession().getCurrentLogin().getUserName(),"Moodle Calendar",SessionWrapper.getCurrentsession().getCurrentUser().getEmail(), TimeZone.getDefault().getID());
			Log.i(LOGTAG, "Calendar is not Created");
		}
		else
		{


			for(int j=0;j<dataset.length;j++){
				MoodleCalendar calendarEvent=dataset[j];
				String courseName="";

				if(CalendarManager.eventAlreadyExistTest(this,SessionWrapper.getCurrentsession().getCurrentLogin().getUserName(),calendarEvent.getId().intValue())==-1){
					if(calendarEvent.getEventtype().equals("course"))
					{
						ArrayList<UserEnrolledCourse> courses=SessionWrapper.getCurrentsession().getCurrentUser().getEnrolledCourses();

						UserEnrolledCourse currentCourse=null;
						for (int i = 0; i < courses.size(); i++) {
							if(courses.get(i).getId().longValue()==calendarEvent.getCourseid().longValue())
							{
								currentCourse=courses.get(i);
								break;
							}
						}

						courseName=currentCourse.getShortName();


					}
					else{
						courseName=calendarEvent.getEventtype()+" event";

					}
					long endTime=0;

					long eventid=CalendarManager.createEvents(this, AccountName,calendarEvent.getId().intValue(),calendarEvent.getTimestart().longValue()*1000,calendarEvent.getTimestart().longValue()*1000,calendarEvent.getName(),calendarEvent.getDescription(), courseName);
					if(eventid!=-1)
					{

						
						
						counter++;
	
						
						setNotification(eventid,calendarEvent.getEventtype());
						
						
						UIHelper.showToast(this,"Event is Succesfully added");
						Log.i(LOGTAG, "event is created "+eventid);
					}
					else{

						Log.i(LOGTAG, "event is not created "+eventid);
					}
				}
				else
				{
					UIHelper.showToast(this,"This event has already been saved");
				}
			}
			if(counter!=0)
			{
				UIHelper.showToast(this, counter+" new Events are synced to the local calendar");
			}
			else
			{
				UIHelper.showToast(this,"No new events are added.. local calendar is upto dated!");

			}

		}

	}

	public void setNotification(long eventid,String type)
	{
		if(type.equals("course"))
		{
			if(coursecheck1d)CalendarManager.addReminder(this,eventid,24*60);
			if(coursecheck1w)CalendarManager.addReminder(this,eventid,60*24*7);
			if(coursecheck5m)CalendarManager.addReminder(this,eventid,5);
			
		}else if(type.equals("site"))
		{

			if(sitecheck1d)CalendarManager.addReminder(this,eventid,24*60);
			if(sitecheck1w)CalendarManager.addReminder(this,eventid,60*24*7);
			if(sitecheck5m)CalendarManager.addReminder(this,eventid,5);
			
		}else if(type.equals("user"))
		{

			if(usercheck1d)CalendarManager.addReminder(this,eventid,24*60);
			if(usercheck1w)CalendarManager.addReminder(this,eventid,60*24*7);
			if(usercheck5m)CalendarManager.addReminder(this,eventid,5);
		}
		else
		{
			
			
		}
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.events, menu);

		return true;
	}




	public void ToggleCourseVisibility(boolean con)
	{
		if(con){			
			eventCourse.setEnabled(true);
		}
		else
		{
			eventCourse.setEnabled(false);


		}
		selectedCoursePosition=0;
		eventCourse.setSelection(0);
	}

	public void updateList()
	{

		UIHelper.showToast(this,selectedTypePosition+" "+selectedCoursePosition);
		updateView();
	}

	public void updateView()
	{
		events=generateList();

		eventAdapter=new EventsAdapter(this,events);
		eventslist.setAdapter(eventAdapter);

	}

	public void showEventDetailActivity(int position)
	{
		Intent intent=new Intent(this,EventDetailActivity.class);
		intent.putExtra("EventDetailActivity",events.get(position));
		startActivity(intent);
	}


	class EventClickListner implements ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			showEventDetailActivity(position);
		}

	}


	class EventTypeItemSelectedListner implements AdapterView.OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
			// An item was selected. You can retrieve the selected item using
			// parent.getItemAtPosition(pos)
			selectedTypePosition=pos;

			if(pos==0)
			{
				AllCourse=true;
				UserEvents=true;
				SiteEvents=true;
			}
			else if(pos==1)
			{
				UserEvents=false;
				SiteEvents=false;

			} else if(pos==2){
				AllCourse=false;
				SiteEvents=true;
				UserEvents=false;
			}
			else {
				AllCourse=false;
				SiteEvents=false;
				UserEvents=true;
			}


			if(pos==1)
			{
				AllCourse=true;
				CourseUnselected=false;
				ToggleCourseVisibility(true);
				selectedCoursePosition=0;

			}
			else
			{
				CourseUnselected=true;				
				ToggleCourseVisibility(false);

			}

			updateList();		
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}
	}


	class CourseTypeItemSelectedListner implements AdapterView.OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
			// An item was selected. You can retrieve the selected item using
			// parent.getItemAtPosition(pos)


			selectedCoursePosition=pos;
			if(!CourseUnselected){
				updateList();
			}
			else{


			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}
	}









}
