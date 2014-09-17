package com.tharindu.moodlloid.util;

import android.app.Activity;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**********************************************************************
 * This is an Utitilty class that will simplify the GUI handling codes
 * @author tharindu
 ************************************************************************/

public class UIHelper {

	/**
	 * This method will Display a text in Given Text View of given Activity
	 * @param activity
	 * @param id :Resouces id
	 * @param text :String to be display
	 */
	public static void displayText(Activity activity, int id, String text) {
		TextView tv = (TextView) activity.findViewById(id);
		tv.setText(text);
	}
	
	/**
	 * This method will return the text inside editable textVeiw
	 * @param activity
	 * @param id
	 * @return
	 */
	public static String getText(Activity activity, int id) {
		EditText et = (EditText) activity.findViewById(id);
		return et.getText().toString();
	}
	
	/**
	 * GEt the CheckBoxk State of given checkbox
	 * @param activity
	 * @param id
	 * @return
	 */
	public static boolean getCBChecked(Activity activity, int id) {
		CheckBox cb = (CheckBox) activity.findViewById(id);
		return cb.isChecked();
	}

	/**
	 * Check or uncheckd the Given check box
	 * @param activity
	 * @param id
	 * @param value
	 */
	public static void setCBChecked(Activity activity, int id, boolean value) {
		CheckBox cb = (CheckBox) activity.findViewById(id);
		cb.setChecked(value);
	}
	
	/**
	 * Show toast message inside a given acivity with given message
	 * @param activity
	 * @param msg
	 */
	public static void showToast(Context activity,String msg){
		Toast.makeText(activity, msg,Toast.LENGTH_LONG).show();
	}

	/**
	 * Showo quick Toast
	 * @param activity
	 * @param msg
	 */
	public static void showToastSlow(Context activity,String msg){
		Toast.makeText(activity, msg,Toast.LENGTH_SHORT).show();
	}

}
