package com.tharindu.moodlloid.model;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.tharindu.moodlloid.rest.MoodleCalendar;
import com.tharindu.moodlloid.rest.MoodleRestCalendar;
import com.tharindu.moodlloid.rest.MoodleRestException;

/*****************************************************************
 * Calendar Filter is used in filtering the Event Notification.
 * 
 * @author tharindu
 ******************************************************************/

public class CalendarFilter {

	/**
	 * This method will be retriviing the all the events before current momment
	 * @param calendar : a list of all events
	 * @return MoodleCalendar[] : only the list of event that have been finished..
	 */
	public static  MoodleCalendar[]  getAllEventsUpToNow(MoodleCalendar [] calendar)
	{
		//Create a instance of currnt time
		Date d=new Date();
		
		try {
			
			return MoodleRestCalendar.getCalendarEvents(calendar, 1,1,Long.valueOf(0),Long.valueOf(d.getTime()/1000), 1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MoodleRestException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * This method will filter only the site events that have been completed.
	 * 
	 * @return : List of sites Event from given all list of events
	 */
	public static MoodleCalendar[]  getSiteUptoNow()
	{
		Date d=new Date();
		
		try {
			return MoodleRestCalendar.getCalendarEvents(new MoodleCalendar[1],0,1,Long.valueOf(0),Long.valueOf(d.getTime()/1000), 1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MoodleRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * This method willl return the User Evnt that have taken place by now
	 * @return
	 */
	public static MoodleCalendar[]  getUserEventsUptoNow()
	{
		Date d=new Date();
		
		try {
			return MoodleRestCalendar.getCalendarEvents(new MoodleCalendar[1],1,0,Long.valueOf(0),Long.valueOf(d.getTime()/1000), 1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MoodleRestException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * This method will return the list of course events of particular ID
	 * @param courseId
	 * @return
	 */
	public static MoodleCalendar[]  getCourseEventsUptoNow(int courseId)
	{
		Date d=new Date();
		MoodleCalendar  calendar=new MoodleCalendar();
		calendar.setCourseid(courseId);
		
	
		try {
			return MoodleRestCalendar.getCalendarEvents(calendar,1,0,Long.valueOf(0),Long.valueOf(d.getTime()/1000), 1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MoodleRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

		
	/**
	 * This method will return the List of events of list of courses between given time period
	 * @param calendar : Calendar object should consist of Ids of the courses 
	 * @param startingFrom : timestamp in milisecconds
	 * @param upto : timestam in millisecond
	 * @return
	 */
	public static  MoodleCalendar[]  getAllBetween(MoodleCalendar [] calendar,Date startingFrom,Date upto)
	{
		Date d=new Date();
		
		try {
			return MoodleRestCalendar.getCalendarEvents(calendar, 1,1,Long.valueOf(startingFrom.getTime()/1000),Long.valueOf(upto.getTime()/1000), 1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MoodleRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * This method will return only the site events betwen given time perod
	 * @param startingFrom :timepsatm in miliseconds
	 * @param upto : timestamp in miliseconds
	 * @return
	 */
	public static MoodleCalendar[]  getSiteEventsBetween(Date startingFrom,Date upto)
	{
		Date d=new Date();
		
		try {
			return MoodleRestCalendar.getCalendarEvents(new MoodleCalendar[1],0,1,Long.valueOf(startingFrom.getTime()/1000),Long.valueOf(upto.getTime()/1000), 1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MoodleRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * This method will return the user events betwen time period
	 * @param startingFrom : timestamp in mili
	 * @param upto : timestamp in mili
	 * @return 
	 */
	public static MoodleCalendar[]  getUserEventsBetween(Date startingFrom,Date upto)
	{
		
		try {
			return MoodleRestCalendar.getCalendarEvents(new MoodleCalendar[1],1,0,Long.valueOf(startingFrom.getTime()/1000),Long.valueOf(upto.getTime()/1000), 1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MoodleRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
		
	
	/**
	 * This method will return the user events betwen time period
	 * @param courseId
	 * @param startingFrom : in Miliseconss
	 * @param upto : in milisecons
	 * @return
	 */
	public static MoodleCalendar[]  getCourseEventsBetween(int courseId,Date startingFrom,Date upto)
	{
		
		MoodleCalendar  calendar=new MoodleCalendar();
		calendar.setCourseid(courseId);
		
	
		try {
			return MoodleRestCalendar.getCalendarEvents(calendar,0,0,Long.valueOf(startingFrom.getTime()/1000),Long.valueOf(upto.getTime()/1000), 1);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MoodleRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
