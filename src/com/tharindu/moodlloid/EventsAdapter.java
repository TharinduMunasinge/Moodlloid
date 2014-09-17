package com.tharindu.moodlloid;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tharindu.moodlloid.rest.MoodleCalendar;

/***************************************************
 * This class bind the event data to EventList Item
 * @author tharindu
 ****************************************************** */
public class EventsAdapter extends ArrayAdapter<MoodleCalendar> {


	Context context;
	List<MoodleCalendar> events;

	/**
	 * @param context
	 * @param resource
	 */
	public EventsAdapter(Context context, List<MoodleCalendar> resource) {
		super(context,R.layout.eventlist_item,resource);
		this.context=context;
		this.events=resource;

		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) { // TODO Auto-generated method stub



		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.eventlist_item, parent, false);
		}
		
		TextView nameText=(TextView)convertView.findViewById(R.id.eventName);
		TextView timeText=(TextView)convertView.findViewById(R.id.eventTime);
		TextView coursText=(TextView)convertView.findViewById(R.id.eventCourse);
		ImageView imageView=(ImageView)convertView.findViewById(R.id.eventImage);

		
		
		long timeStart=events.get(position).getTimestart();
		Date d=new Date(timeStart*1000);
		
		
		nameText.setText(events.get(position).getName());
		timeText.setText(d.toGMTString());
		coursText.setText(events.get(position).getCourseid().toString());
		
		
		if(events.get(position).getEventtype().equals("course"))
		{
			imageView.setImageResource(R.drawable.coure_event);
		}
		else if(events.get(position).getEventtype().equals("user"))
		{
			imageView.setImageResource(R.drawable.user_event);
		}
		else if(events.get(position).getEventtype().equals("site"))
		{
			imageView.setImageResource(R.drawable.site_event);

		}
		else
		{
			imageView.setImageResource(R.drawable.default_event);
		}

		return convertView;

	}

}
