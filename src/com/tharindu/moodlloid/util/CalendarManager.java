package com.tharindu.moodlloid.util;

import java.security.acl.Owner;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;

/**********************************************************************************************************
 * This class responsible for
	 * Creating Calendar
	 * Crating events
	 * Createing reiminders
	 * and REtrive the event details
 * 
 * This class directyl deal with CalendarContact.Provider and store information in local calendar database
 * @author tharindu 
 ***********************************************************************************************************/
public class CalendarManager {


	public static String LOGTAG="CalendarManager";

	/**
	 * This method will create the callendar in local storge
	 * @param context : Activity name
	 * @param AccountName : name of the account shoud be used 
	 * @param CalendarName: Name of the callendar
	 * @param AccountOwnerEmail 
	 * @param TimeZone : Times zone should be sent from UTC/GMT +XX:XX format
	 * 
	 * Remarks : Given calendar is unique to the ACCOUNTNAME + CALLENDARTYPE(Local or synced)
	 */
	public static void createLocalCalendar(Context context,String AccountName,String CalendarName,String AccountOwnerEmail,String TimeZone){
		//    	Toast.makeText(this,"CLICKED", Toast.LENGTH_LONG).show();
		ContentValues values = new ContentValues();
		values.put(
				Calendars.ACCOUNT_NAME, 
				AccountName);
		values.put(
				Calendars.ACCOUNT_TYPE, 
				CalendarContract.ACCOUNT_TYPE_LOCAL);
		values.put(
				Calendars.NAME, 
				CalendarName);
		values.put(
				Calendars.CALENDAR_DISPLAY_NAME, 
				CalendarName);
		values.put(
				Calendars.CALENDAR_COLOR, 
				0xffff0000);
		values.put(
				Calendars.CALENDAR_ACCESS_LEVEL, 
				Calendars.CAL_ACCESS_OWNER);
		values.put(
				Calendars.OWNER_ACCOUNT, 
				AccountOwnerEmail);
		values.put(
				Calendars.CALENDAR_TIME_ZONE, 
				TimeZone);
		values.put(
				Calendars.SYNC_EVENTS, 
				1);
		Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon(); 


		builder.appendQueryParameter(Calendars.ACCOUNT_NAME,AccountName);
		builder.appendQueryParameter(
				Calendars.ACCOUNT_TYPE, 
				CalendarContract.ACCOUNT_TYPE_LOCAL);
		builder.appendQueryParameter(
				CalendarContract.CALLER_IS_SYNCADAPTER, 
				"true");

		//url to the newly creaetd calednar content
		Uri uri =  context.getContentResolver().insert(builder.build(), values);

	}

	/**
	 * will return the ID of given AccountNAME 
	 * @param context :Activity
	 * @param AccountName: 
	 * @return
	 */
	public static long getCalendarId(Context context,String AccountName) { 
		String[] projection = new String[]{Calendars._ID}; 
		String selection = 
				Calendars.ACCOUNT_NAME + 
				" = ? AND " + 
				Calendars.ACCOUNT_TYPE + 
				" = ? "; 
		// use the same values as above:
		String[] selArgs =  new String[]{
				AccountName, 
				CalendarContract.ACCOUNT_TYPE_LOCAL}; 

		Cursor cursor = 
				context.getContentResolver().
				query(
						Calendars.CONTENT_URI, 
						projection, 
						selection, 
						selArgs, 
						null); 
		if (cursor.moveToFirst()) { 
			return cursor.getLong(0); 
		} 
		return -1; 
	} 


	/**
	 * CREATE an EVEnt for given information 
	 
	 * @param context :Activity
	 * @param AccountName  : Name of the ACcount
	 * @param eventID : Event ID from the MOODLE
	 * @param startTimeInMili : Starting time
	 * @param endTimeInMili : End time in miliseconds
	 * @param eventTitile : title of the Events
	 * @param Description : Descriptions of the Evnt
	 * @param courseShortName : name of the Course if its a Course event
	 * @return
	 */
	public static long createEvents(Context context,String AccountName,int eventID,long startTimeInMili,long endTimeInMili,String eventTitile,String Description,String courseShortName){
		long calId = getCalendarId(context,AccountName);
		if (calId == -1) {
			return -1;
		}
		try{
			ContentValues values = new ContentValues();

			values.put(Events.DTSTART, startTimeInMili);

			values.put(Events.DTEND, startTimeInMili);
			//				values.put(Events.RRULE, 
			//						"FREQ=DAILY;COUNT=20;BYDAY=MO,TU,WE,TH,FR;WKST=MO");

			values.put(Events.TITLE, eventTitile+" ["+courseShortName+"]");

			values.put(Events.EVENT_LOCATION,eventID);

			values.put(Events.CALENDAR_ID, calId);

			values.put(Events.EVENT_TIMEZONE,"UTC/GMT +5:30");
			values.put(Events.DESCRIPTION, Description);
			// reasonable defaults exist:
			values.put(Events.ACCESS_LEVEL, Events.ACCESS_PUBLIC);
			values.put(Events.SELF_ATTENDEE_STATUS,
					Events.STATUS_CONFIRMED);
			values.put(Events.ALL_DAY, 0);
			//				values.put(Events.ORGANIZER, "some.mail@some.address.com");
			//				values.put(Events.GUESTS_CAN_INVITE_OTHERS, 1);
			//				values.put(Events.GUESTS_CAN_MODIFY, 1);
			//				values.put(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);

			Uri uri = context.getContentResolver().insert(Events.CONTENT_URI, values);

			long eventId = new Long(uri.getLastPathSegment());

			return eventId;
		}catch(Exception e)
		{
			Log.i(LOGTAG,e.getMessage());
			return -1;
		}
	}


	/**
	 * Check whether A given Event is already contin in the Calendar 
	 * 
	 * @param context :ActivityD
	 * @param accountName : name of the account
	 * @param calendarEventIdFromMoodle : MOODLE's  Calendar eVent id
	 * @return
	 */
	public static long eventAlreadyExistTest(Context context,String accountName,int calendarEventIdFromMoodle)
	{
		long calId=getCalendarId(context, accountName);
		if(calId==-1)
			return -1;


		String[] projection = new String[]{Event._ID}; 
		String selection = 
				Events.EVENT_LOCATION + 
				" = ? AND " + 
				Events.CALENDAR_ID+ 
				" = ? "; 
		// use the same values as above:
		String[] selArgs =  new String[]{
				""+calendarEventIdFromMoodle, 
				""+calId}; 

		Cursor cursor = 
				context.getContentResolver().query(Events.CONTENT_URI, 
						projection, 
						selection, 
						selArgs, 
						null); 
		if (cursor.moveToFirst()) { 
			return cursor.getLong(0); 
		} 
		return -1; 




	}


	/**
	 * Set reminders for given Event
	 * ONly Notification method is :Alert
	 * @param context
	 * @param eventID
	 * @param NumberofMinutesBefore
	 */
	public static void addReminder(Context context,long eventID,int NumberofMinutesBefore){

		ContentResolver cr = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Reminders.MINUTES, NumberofMinutesBefore);
		values.put(Reminders.EVENT_ID, eventID);
		values.put(Reminders.METHOD, Reminders.METHOD_ALERT);
		Uri uri = cr.insert(Reminders.CONTENT_URI, values);		

	}
}
