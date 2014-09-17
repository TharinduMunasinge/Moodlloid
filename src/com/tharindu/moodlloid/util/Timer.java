package com.tharindu.moodlloid.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/********************************************************************************************
 * This Class is an utility Class that generate th UTC timpstap based on complex requirments
 * @author tharindu
 *********************************************************************************************/
public class Timer {

	/**
	 * REturn the timestam of the 0:00:00 time of the first day in the current Mont
	 * @param timepstampInmili
	 * @return
	 */
	public static long getStartOfTheMonthInMili(long timepstampInmili)
	{
		Date DC = new Date(timepstampInmili); //get current Year, Month
		int year = DC.getYear(); //current Year
		int month = DC.getMonth(); //current Month+
		int day = DC.getDate();// current Day

		System.out.println(year+":"+month+":"+day);
		Date dateStart = new Date(); //Today @ 0:00:00 am to unix time stamp 
		dateStart.setYear(year); //set year current Year

		Date endofmonth = new Date(); //Today @ 0:00:00 am to unix time stamp 
		endofmonth.setYear(year); //set year current Year
		endofmonth.setMonth(month); //set month current Month
		endofmonth.setDate(1); //set current day of month
		endofmonth.setMinutes(0); // minute 0
		endofmonth.setHours(0);   // hour 0
		endofmonth.setSeconds(0); // second 0



		return endofmonth.getTime();
	}
	/**
	 * REturn the timpestamp of 0:00:00 time in today 
	 * @param timstampInMili
	 * @return
	 */
	public static long getStartOftheDayInMili(long timstampInMili)
	{
		Date DC = new Date(timstampInMili); //get current Year, Month
		int year = DC.getYear(); //current Year
		int month = DC.getMonth(); //current Month+
		int day = DC.getDate();// current Day


		Date dateStart = new Date(); //Today @ 0:00:00 am to unix time stamp 
		dateStart.setYear(year); //set year current Year
		dateStart.setMonth(month); //set month current Month
		dateStart.setDate(day); //set current day of month
		dateStart.setMinutes(0); // minute 0
		dateStart.setHours(0);   // hour 0
		dateStart.setSeconds(0); // second 0
		//    int iTimeStamp = (int) (dateStart.getTime() * .001);
		//  String sStart_TimeStamp = Integer.toString(iTimeStamp);

		return dateStart.getTime();
	}

	/**
	 * Get the Timstamp of the End of today
	 * @param timstampInMili
	 * @return
	 */
	public static long getEndOftheDayInMili(long timstampInMili)
	{
		Date DC = new Date(timstampInMili); //get current Year, Month
		int year = DC.getYear(); //current Year
		int month = DC.getMonth(); //current Month+
		int day = DC.getDate();// current Day

		Date dateStart = new Date(); //Today @ 0:00:00 am to unix time stamp 
		dateStart.setYear(year); //set year current Year
		dateStart.setMonth(month); //set month current Month
		dateStart.setDate(day); //set current day of month
		dateStart.setMinutes(59); // minute 0
		dateStart.setHours(23);   // hour 0
		dateStart.setSeconds(59); // second 0

		return dateStart.getTime();
	}

	/**
	 * Ged the Timepstamp of the end of the month
	 * @param timstampInMili
	 * @return
	 */
	public static long getEndOftheMonthInMili(long timstampInMili)
	{
		Date DC = new Date(timstampInMili); //get current Year, Month
		int year = DC.getYear(); //current Year
		int month = DC.getMonth(); //current Month+
		int day = DC.getDate();// current Day

		Date dateStart = new Date(); //Today @ 0:00:00 am to unix time stamp 
		dateStart.setYear(year); //set year current Year


		Calendar mycal = new GregorianCalendar();
		mycal.setTimeZone(TimeZone.getDefault());
		mycal.setTimeInMillis(timstampInMili);
		int lastDate = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

		Date endofmonth = new Date(); //Today @ 0:00:00 am to unix time stamp 
		endofmonth.setYear(year); //set year current Year
		endofmonth.setMonth(month); //set month current Month
		endofmonth.setDate(lastDate); //set current day of month
		endofmonth.setMinutes(59); // minute 0
		endofmonth.setHours(23);   // hour 0
		endofmonth.setSeconds(59); // second 0



		return endofmonth.getTime();
	}
}
