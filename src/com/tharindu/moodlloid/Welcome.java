package com.tharindu.moodlloid;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.tharindu.moodlloid.model.Site;
import com.tharindu.moodlloid.net.SessionWrapper;
import com.tharindu.moodlloid.rest.MoodleCallRestWebService;
import com.tharindu.moodlloid.rest.MoodleRestException;
import com.tharindu.moodlloid.rest.MoodleRestUser;
import com.tharindu.moodlloid.rest.MoodleRestWebService;
import com.tharindu.moodlloid.rest.MoodleRestWebServiceException;
import com.tharindu.moodlloid.rest.MoodleUser;
import com.tharindu.moodlloid.rest.MoodleWebService;
import com.tharindu.moodlloid.store.SitesDataSource;
import com.tharindu.moodlloid.util.UIHelper;

public class Welcome extends Activity {

	/*
	 * Main Activity Class which loads when app launcher is clicked!!!!
	 * This activity allow users to register new sites 
	 * loginto one of the to the listed sites.
	 */	
	ProgressBar pb;
	MyTask task;
	SitesDataSource datasouce;
	
	
	public static List<Site> sites;
//	public static Site currentSite;
	private static final String SITE_KEY="url";
	private static final String UNAME_KEY="uname";
	private static final String PASS_KEY="password";
	private static final String TOKE_KEY="toaken";
	ListView list;
	
	
//public static MoodleWebService siteINFO; 

	public static SessionWrapper currentSessionInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitelist_main);  //set the layout 

		list=(ListView)findViewById(R.id.listView1); //get the list view that shows the existing site list
		currentSessionInfo=SessionWrapper.getCurrentsession();
		

		datasouce =new SitesDataSource(this);
		datasouce.open();
		refreshDisplay();



	}

	@Override
	protected void onResume() {
		super.onResume();
		datasouce.open();
		refreshDisplay();
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasouce.close();
	}

	public void create(){
		Site newsite=new Site();
		newsite.setPassword("text");
		newsite.setSiteURL("abc");
		newsite.setUserName("tharindu");
		newsite.setToken("");
		datasouce.create(newsite);
	}

	public void login(View v)
	{
		//UIHelper.showToast(this,"btn clicked"+v.getParent().getParent());



	}
	public void refreshDisplay()
	{

		sites=datasouce.findAll();
		if(sites.size()>0)
		{

			siteListAdapter adapter=new siteListAdapter(this, sites);
			list.setAdapter(adapter);	
		}
		else
			create();
		list.setOnItemClickListener(new SiteClickListner());

	}

	public void addNewSite(View v){
		//		 task=new MyTask();
		//		task.execute("test","test");

		//	UIHelper.showToast(this, "clickde");
		Intent newSite=new Intent(this,SiteRegistrationActivity.class);
		//	newSite.putExtra(, value)

		startActivity(newSite);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.welcome, menu);
		pb=(ProgressBar)findViewById(R.id.progressBar1);
		
		return true;
	}


	void loadSite(int position){
		//loging to one of the site user clicked

		Site s=sites.get(position);
		if(!s.getToken().isEmpty())
		{
			//UIHelper.showToast(this, s.getRestURL()+"\n"+s.getToken());
			
			
			pb.setVisibility(pb.VISIBLE);
			currentSessionInfo.setCurrentLogin(s);
			MyTask task=new MyTask();
			task.execute(s.getRestURL(),s.getToken());
			
		}
		
		else{
			UIHelper.showToast(this,"Token is null");
		}
		//UIHelper.showToast(this,position+" isclicked");

	}


	/* ********************************************************************************************** 
	 * Inner class for itemClicklister for list item goes here......
	 */	

	class SiteClickListner implements ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			loadSite(position);
		}

	}


	private class MyTask extends AsyncTask<String,String ,MoodleWebService> {

		@Override
		protected void onPreExecute() {
			//UIHelper.showToast(Welcome.this, "On preRequest");
		}

		@Override
		protected MoodleWebService doInBackground(String... params) {

			try{

				MoodleCallRestWebService.init(params[0],params[1]);
				MoodleWebService siteInfo = MoodleRestWebService.getSiteInfo();
				currentSessionInfo.setCurrentSiteInfo(siteInfo);
				
				String imageUrl=siteInfo.getUserPictureURL();
			//	publishProgress(imageUrl);
				Log.i("Welcome",imageUrl);
				InputStream in = (InputStream) new URL(imageUrl).getContent();
				Bitmap image = BitmapFactory.decodeStream(in);
				
				
				Long[] users=new Long[1];
			      users[0]=MoodleRestWebService.getSiteInfo().getUserId();
			      
			      MoodleUser[] user=MoodleRestUser.getUsersById(users);
			      currentSessionInfo.setCurrentUser(user[0]);
			      
				if(image!=null)				{
					
					Log.i("Welcome","downloaded");//	publishProgress("Downloaded");
					currentSessionInfo.setUserImage(image);
				}
				else
					Log.i("welcome","not downloaded");
				in.close();
				return siteInfo; 
			}     //printSiteInfo(siteInfo);
			catch (MoodleRestWebServiceException ex) {
				// Logger.getLogger(MoodleGetSiteInfoExample.class.getName()).log(Level.SEVERE, null, ex);
			} catch (MoodleRestException ex) {
				// Logger.getLogger(MoodleGetSiteInfoExample.class.getName()).log(Level.SEVERE, null, ex);
				
			}catch(Exception ex)
			{
				Log.i("Welcome",ex.getMessage());
			} 
			return null;

		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			UIHelper.showToast(Welcome.this,values[0]);
		}
		

		@Override
		protected void onPostExecute(MoodleWebService result) {


			if (result==null) {
				UIHelper.showToast(Welcome.this,"Error Occureed");
			} else {
			
				//siteINFO=result;
				pb.setVisibility(pb.INVISIBLE);
				
				//UIHelper.showToast(Welcome.this,result.getUserName());
				Intent intent=new Intent(Welcome.this,MainActivity.class);
				//intent.putExtra("MoodleWebService",result);
				startActivity(intent);
			}
			

		}

	}



}
