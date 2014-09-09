package com.tharindu.moodlloid.store;

import java.util.ArrayList;
import java.util.List;

import com.tharindu.moodlloid.model.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SitesDataSource {

	public static final String LOGTAG="SITE_DATA_SOURCE";
	
	public static SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String[] allColumns = {
		SitesDBOpenHelper.COLUMN_ID,
		SitesDBOpenHelper.COLUMN_URL,
		SitesDBOpenHelper.COLUMN_USERNAME,
		SitesDBOpenHelper.COLUMN_PASSWORD,
		SitesDBOpenHelper.COLUMN_TOKEN};
	
	public SitesDataSource(Context context) {
		dbhelper = new SitesDBOpenHelper(context);
	}
	
	public void open() {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		Log.i(LOGTAG, "Database closed");		
		dbhelper.close();
	}
	
	public Site create(Site tour) {
		ContentValues values = new ContentValues();
		values.put(SitesDBOpenHelper.COLUMN_URL, tour.getSiteURL());
		values.put(SitesDBOpenHelper.COLUMN_USERNAME, tour.getUserName());
		values.put(SitesDBOpenHelper.COLUMN_PASSWORD, tour.getPassword());
		values.put(SitesDBOpenHelper.COLUMN_TOKEN, tour.getToken());
		long insertid = database.insert(SitesDBOpenHelper.TABLE_SITE, null, values);
		tour.setId(insertid);
		return tour;
	}
	
	public List<Site> findAll() {
		
		Cursor cursor = database.query(SitesDBOpenHelper.TABLE_SITE, allColumns, 
				null, null, null, null, null);
				
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		List<Site> tours = cursorToList(cursor);
		return tours;
	}

	public List<Site> findFiltered(String selection, String orderBy) {
		
		Cursor cursor = database.query(SitesDBOpenHelper.TABLE_SITE, allColumns, 
				selection, null, null, null, orderBy);
		
		Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
	
		
		List<Site> tours = cursorToList(cursor);
		Log.i(LOGTAG, "succefully readed");
		return tours;
	}
	
	private List<Site> cursorToList(Cursor cursor) {
		List<Site> tours = new ArrayList<Site>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Site tour = new Site();
				tour.setId(cursor.getLong(cursor.getColumnIndex(SitesDBOpenHelper.COLUMN_ID)));
				tour.setSiteURL(cursor.getString(cursor.getColumnIndex(SitesDBOpenHelper.COLUMN_URL)));
				tour.setUserName(cursor.getString(cursor.getColumnIndex(SitesDBOpenHelper.COLUMN_USERNAME)));
				tour.setPassword(cursor.getString(cursor.getColumnIndex(SitesDBOpenHelper.COLUMN_PASSWORD)));
				tour.setToken(cursor.getString(cursor.getColumnIndex(SitesDBOpenHelper.COLUMN_TOKEN)));
				tours.add(tour);
			}
		}
		return tours;
	}
	
}
