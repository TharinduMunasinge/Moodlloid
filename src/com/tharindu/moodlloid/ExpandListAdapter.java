package com.tharindu.moodlloid;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tharindu.moodlloid.model.CourseContent;
import com.tharindu.moodlloid.model.ModuleContent;
import com.tharindu.moodlloid.net.SessionWrapper;

/****************************************************************
 *  This will show the Expandable List View of the course Content Acitity
 *  
 * @author tharindu
 ****************************************************************/

public class ExpandListAdapter extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<CourseContent> groups;

	private int ParentClickStatus=-1;
	private int ChildClickStatus=-1;
	private  ModuleContent child;

	//Get the number of Group views
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groups.size();
	}


	
	public ExpandListAdapter(Context context, ArrayList<CourseContent> groups) {
		this.context = context;
		this.groups = groups;
	}


	//get the number of childer for given groupView
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		ArrayList<ModuleContent> chList = groups.get(groupPosition).getItems();
		return chList.size();
	}

	//get the group object
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groups.get(groupPosition);
	}


	//Call when child row clicked
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<ModuleContent> chList = groups.get(groupPosition).getItems();
		return chList.get(childPosition);
	}


	//Call when parent row clicked
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		if(groupPosition==2 && ParentClickStatus!=groupPosition){

			//Alert to user
			//UIHelper.showToastSlow(context," "+"Group is"+groupPosition);
		}

		ParentClickStatus=groupPosition;
		if(ParentClickStatus==0)
			ParentClickStatus=-1;


		return groupPosition;
	}


	//caled when Child is clicked
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		if( ChildClickStatus!=childPosition)
		{
			ChildClickStatus = childPosition;
			CourseContent group = (CourseContent) getGroup(groupPosition);

		}  
		return childPosition;
	}

	
	
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}



	// This Function used to inflate parent rows view
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		CourseContent group = (CourseContent) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = inf.inflate(R.layout.coursecontent_item, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.content_name);
		tv.setText(group.getName());
		return convertView;
	}



	// This Function used to inflate child rows view
	//Content should be displayed in child row should be specificed in this function
	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {

		 child = (ModuleContent) getChild(groupPosition, childPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.module_child_item, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.country_name);
		ImageView iv = (ImageView) convertView.findViewById(R.id.flag);

		tv.setText(child.getName().toString());
		//  iv.setImageResource(child.getImage());


		if (child.getImage()!= null) {

			iv.setImageBitmap(child.getImage());
		}
		else {
			if(!child.getImageURl().isEmpty())
			{ModuleImage container = new ModuleImage();
			container.module = child;
			container.view = convertView;

			ImageLoader loader = new ImageLoader();
			loader.execute(container);
			}
		}

		return convertView;
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}





/************** This class is a Model class to keep the references need to execute the IMAGE LOADER asyntask***************/
	
	public class ModuleImage {
		public ModuleContent module;
		public View view;
		public Bitmap bitmap;
	}

/************** This class will be doing the LAZY LOADING of image icons and Willl runs on a separate thread***************/

	private class ImageLoader extends AsyncTask<ModuleImage, Void, ModuleImage> {

		@Override
		protected ModuleImage doInBackground(ModuleImage... params) {

			ModuleImage container = params[0];
			ModuleContent module = container.module;

			try {
				//genearate the URL of the ICON
				String imageUrl = module.getImageURl()+"?token="+URLEncoder.encode(SessionWrapper.getCurrentsession().getCurrentLogin().getToken(),"UTF-8");

				InputStream in = (InputStream) new URL(imageUrl).getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(in);
//				module.setImage(bitmap);
				container.bitmap=bitmap;
				in.close();
				//container.bitmap = bitmap;
				return container;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(ModuleImage result) {
			ImageView image = (ImageView) result.view.findViewById(R.id.flag);
			image.setImageBitmap(result.bitmap);
			//set the images for future usage
			result.module.setImage(result.bitmap);
		
		}

	}










}
