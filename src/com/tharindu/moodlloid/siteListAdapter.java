package com.tharindu.moodlloid;

import java.util.List;

import com.tharindu.moodlloid.model.Site;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/*************************************************************
 * This is the adapter that bind Site List with list view Controller
 * @author tharindu
 ****************************************************************** */
public class siteListAdapter extends ArrayAdapter<Site> {

	Context context;
	List<Site> sites;
	 
	/**
	 * @param context
	 * @param resource
	 */
	public siteListAdapter(Context context, List<Site> resource) {
		super(context, R.layout.sitelist_item,resource);
		this.context=context;
		this.sites=resource;
		
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.sitelist_item, parent, false);
	    }
		TextView view=(TextView)convertView.findViewById(R.id.username);
		TextView tv=(TextView)convertView.findViewById(R.id.url);
		
		view.setText(sites.get(position).getUserName());
		tv.setText(sites.get(position).getSiteURL());
		return convertView;
		
	}
	

	
}
