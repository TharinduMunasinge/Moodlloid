package com.tharindu.moodlloid.store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SitesDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "EXPLORECA";

	private static final String DATABASE_NAME = "moodle.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_SITE = "sites";
	public static final String COLUMN_ID = "siteId";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_USERNAME = "uname";
	public static final String COLUMN_PASSWORD = "pass";
	public static final String COLUMN_TOKEN = "token";
	
	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_SITE + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_URL + " TEXT, " +
			COLUMN_USERNAME + " TEXT, " +
			COLUMN_PASSWORD + " TEXT, " +
			COLUMN_TOKEN + " TEXT " +
			")";
			
	
	public SitesDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		Log.i(LOGTAG, "Table has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE);
		onCreate(db);
	}

}
