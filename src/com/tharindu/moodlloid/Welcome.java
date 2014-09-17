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
	private ProgressBar pb;
	private MyTask task;
	private SitesDataSource datasouce; // to store get the loggin information
	public static List<Site> sites;	//list of availble session

	//	Set of Constants used in URL
	private static final String SITE_KEY="url";
	private static final String UNAME_KEY="uname";
	private static final String PASS_KEY="password";
	private static final String TOKE_KEY="toaken";
	ListView list;

	//This member variable will store all the information regarding the Session
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.welcome, menu);
		pb=(ProgressBar)findViewById(R.id.progressBar1);

		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		datasouce.open();//for the persistant DataBase connection
		refreshDisplay();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		datasouce.close(); // for the persistant Database Connection
	}

	/**
	 * Create Sample Loging
	 */
	public void create(){
		Site newsite=new Site();
		newsite.setPassword("text");
		newsite.setSiteURL("abc");
		newsite.setUserName("tharindu");
		newsite.setToken("");
		datasouce.create(newsite);
	}


	public void refreshDisplay()
	{
		sites=datasouce.findAll(); // read the all possible credentials 
		if(sites.size()>0)
		{
			siteListAdapter adapter=new siteListAdapter(this, sites);
			list.setAdapter(adapter);	
			list.setOnItemClickListener(new SiteClickListner());

		}
		else{
			//create();
			UIHelper.showToast(this,"No available Sessions.. Please Register a new MOODLE");
		}

	}

	/**
	 * Start Site Registration Activity
	 * @param v
	 */
	public void addNewSite(View v){

		Intent newSite=new Intent(this,SiteRegistrationActivity.class);
		startActivity(newSite);
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


	/******************************* Inner class for itemClicklister for list item goes here......*************/	

	class SiteClickListner implements ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			loadSite(position);
		}

	}


	/******************************* Inner class for Backgroudn thread that send the HTTP request*************/
	private class MyTask extends AsyncTask<String,String ,MoodleWebService> {

		@Override
		protected void onPreExecute() {
			//UIHelper.showToast(Welcome.this, "On preRequest");
		}

		@Override
		protected MoodleWebService doInBackground(String... params) {

			try{
				//params[0] - Rest url
				//params[1]	- token
				MoodleCallRestWebService.init(params[0],params[1]);

				//get the information about the Moodle
				MoodleWebService siteInfo = MoodleRestWebService.getSiteInfo();

				//update the currentSession information
				currentSessionInfo.setCurrentSiteInfo(siteInfo);

				//get the image of profile
				String imageUrl=siteInfo.getUserPictureURL();
				Log.i("Welcome",imageUrl);
				InputStream in = (InputStream) new URL(imageUrl).getContent();
				Bitmap image = BitmapFactory.decodeStream(in);
				Long[] users=new Long[1];
				users[0]=MoodleRestWebService.getSiteInfo().getUserId();

				
				//get the User Information of the current Session
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
				Intent intent=new Intent(Welcome.this,MainActivity.class);
				startActivity(intent);
			}


		}

	}



}
