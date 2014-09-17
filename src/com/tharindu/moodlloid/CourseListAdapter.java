package com.tharindu.moodlloid;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tharindu.moodlloid.rest.UserEnrolledCourse;

/****************************************************
 * This Class used to bind the data to CourseList
 * @author tharindu
 *
 *****************************************************/
public class CourseListAdapter extends ArrayAdapter<UserEnrolledCourse> {


	 /**
	 * Context from which this item generated
	 */
	private Context context;
	private List<UserEnrolledCourse> courses;
	
	public CourseListAdapter(Context context, List<UserEnrolledCourse> resource) {
		super(context, R.layout.courselist_item,resource);
		this.context=context;
		this.courses=resource;
		}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.courselist_item, parent, false);
	    }
		TextView view=(TextView)convertView.findViewById(R.id.titleText);
		TextView tv=(TextView)convertView.findViewById(R.id.titleName);
		
		//set the full name and short name of the course
		view.setText(courses.get(position).getFullName());
		tv.setText(courses.get(position).getShortName());
		return convertView;
		
	}

	

}
