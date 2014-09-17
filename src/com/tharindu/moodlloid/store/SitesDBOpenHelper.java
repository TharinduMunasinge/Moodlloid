package com.tharindu.moodlloid.store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**************************************************************************
 * This class Will represent the Database object withing the application
 * @author tharindu
 *************************************************************************** */
public class SitesDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "Mooddlloid";
	private static final String DATABASE_NAME = "moodle.db";
	private static final int DATABASE_VERSION = 1;	//version should be update when Schema get updated
	public static final String TABLE_SITE = "sites"; // Name of the table 
	
	//Constants that represent the Column names with Literals
	public static final String COLUMN_ID = "siteId";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_USERNAME = "uname";
	public static final String COLUMN_PASSWORD = "pass";
	public static final String COLUMN_TOKEN = "token";
	
	//Query to create a new table
	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_SITE + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_URL + " TEXT, " +
			COLUMN_USERNAME + " TEXT, " +
			COLUMN_PASSWORD + " TEXT, " +
			COLUMN_TOKEN + " TEXT " +
			")";
			
	
	/**
	 * Constrcutor
	 * @param context
	 */
	public SitesDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		Log.i(LOGTAG, "Table has been created");
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE);
		onCreate(db);
	}

}
