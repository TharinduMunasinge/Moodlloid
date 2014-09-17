package com.tharindu.moodlloid.model;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.tharindu.moodlloid.rest.MoodleCalendar;
import com.tharindu.moodlloid.rest.MoodleRestCalendar;
import com.tharindu.moodlloid.rest.MoodleRestException;

public class CalendarFilter {

	/**
	 * @param calendar
	 * @return
	 */
	public static  MoodleCalendar[]  getAllEventsUpToNow(MoodleCalendar [] calendar)
	{
		Date d=new Date();
		
		try {
			return MoodleRestCalendar.getCalendarEvents(calendar, 1,1,Long.valueOf(0),Long.valueOf(d.getTime()/1000), 1);
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
	 * @return
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
	 * @return
	 */
	public static MoodleCalendar[]  getUserEventsUptoNow()
	{
		Date d=new Date();
		
		try {
			return MoodleRestCalendar.getCalendarEvents(new MoodleCalendar[1],1,0,Long.valueOf(0),Long.valueOf(d.getTime()/1000), 1);
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
	 * @param calendar
	 * @param startingFrom
	 * @param upto
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
	 * @param startingFrom
	 * @param upto
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
	 * @param startingFrom
	 * @param upto
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
	 * @param courseId
	 * @param startingFrom
	 * @param upto
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
