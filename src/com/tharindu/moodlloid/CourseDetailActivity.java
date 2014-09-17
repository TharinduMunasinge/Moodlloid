package com.tharindu.moodlloid;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.acl.Group;
import java.util.ArrayList;


import com.tharindu.moodlloid.TestDataActivity.DownloadFileFromURL;
import com.tharindu.moodlloid.model.CourseContent;
import com.tharindu.moodlloid.model.ModuleContent;
import com.tharindu.moodlloid.net.SessionWrapper;
import com.tharindu.moodlloid.rest.MoodleCourseContent;
import com.tharindu.moodlloid.rest.MoodleModule;
import com.tharindu.moodlloid.rest.MoodleModuleContent;
import com.tharindu.moodlloid.rest.MoodleCourse;
import com.tharindu.moodlloid.util.FileManger;
import com.tharindu.moodlloid.util.UIHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class CourseDetailActivity extends Activity {

	
	private ExpandListAdapter ExpAdapter;
	private String FilepathToBeOpened="";
	private String FileTypetoBeOpened="";
	private ArrayList<CourseContent> ExpListItems;
	private ExpandableListView ExpandList;
	private ProgressDialog pDialog;
	private String LOGTAG="CourseDetailActivity";
	private MoodleModule ClickedResource;
	//	private final String LOGTAG="CourseDetailActivity";
	public static final int progress_bar_type = 0; 

	private boolean clickedAction;
	private CourseContent ClickedTopic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.course_content_list_main);
		MoodleCourseContent[] listOfContent=CoursesFragment.currentCourse;
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ExpandList = (ExpandableListView) findViewById(R.id.exp_list);
		ExpListItems = SetStandardGroups(listOfContent);
		ExpAdapter = new ExpandListAdapter(CourseDetailActivity.this, ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
		
		setTitle(CoursesFragment.currentCourseDetails.getFullName());
		ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				clickModule(groupPosition, childPosition);
				return false;
			}
		});

	}

	public void clickModule(int groupId,int childID)
	{
		ClickedResource =ExpListItems.get(groupId).getItems().get(childID).getModule();
		ClickedTopic=ExpListItems.get(groupId);



		if(ClickedResource.getModName().equals("resource") ||  ClickedResource.getModName().equals("page"))
		{
			String array []=generateFileDiscriptor();

			if(array!=null)
			{
				String fileurl=array[0];
				String path=array[1];
				String filename=array[2];

				if(alreadyDownloaded(path, filename))
				{
					FilepathToBeOpened=path+"/"+filename;
					FileTypetoBeOpened=FileManger.getFileType(filename);		
					String message="File is already downloaded! Do you want to open it ?";
					DialogFragment newFragment = new openFileDialog(message);
					newFragment.show(getFragmentManager(), "missiles");

				}
				else
				{
					String message="Do you want to download \""+ ClickedResource.getName()+"\" resource ?";
					DialogFragment newFragment = new FileDownladerDialog(message);
					newFragment.show(getFragmentManager(), "missiles");
				}
			}
		}
		else if(ClickedResource.getModName().equals("page"))
		{
			
			String message="Do you want to browse \""+ClickedResource.getName() +"\" web link ?";
			DialogFragment newFragment = new webBrowserDiagllog(message);
			newFragment.show(getFragmentManager(), "missiles");
		}
		else if(ClickedResource.getModName().equals("forum"))
		{
			String message="Do you want to browse forum ?";
			DialogFragment newFragment = new webBrowserDiagllog(message);
			newFragment.show(getFragmentManager(), "missiles");
		}
		else
		{

			UIHelper.showToast(this,"is a not a page");
		}
	}


	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		if(item.getItemId()==android.R.id.home)
		{
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type: // we set this to 0
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Downloading file. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(true);
			pDialog.show();
			return pDialog;
		default:
			return null;
		}
	}

	public ArrayList<CourseContent> SetStandardGroups(MoodleCourseContent [] content) {


		ArrayList<CourseContent> listOfGroups = new ArrayList<CourseContent>();

		ArrayList<ModuleContent> childListOfITHGroup;


		for (int i=0; i<content.length; i++)
		{

			CourseContent iTHcourseContentGroup=new CourseContent(); //Create a new Group view to store childViews
			MoodleCourseContent iTHmoodleCourseContent=content[i]; //Get the Ith (CourseContent) from WEBSERVER
			iTHcourseContentGroup.setName(iTHmoodleCourseContent.getName()); //setGui GROUPName
			iTHcourseContentGroup.setContent(iTHmoodleCourseContent); //setThe associated COURSECONTENT OBJECT
			Log.i(LOGTAG,iTHmoodleCourseContent.getName()+" topic is processing");

			childListOfITHGroup=new ArrayList<ModuleContent>();  //Create CHILD GUI list




			if (iTHmoodleCourseContent.getMoodleModules()!=null){ //if that topic has internal modlues				


				for (int j=0; j<iTHmoodleCourseContent.getMoodleModules().length; j++)
				{
					MoodleModule moodleModule=iTHmoodleCourseContent.getMoodleModules()[j]; //Get the jth MODULE from web service

					if(moodleModule!=null){ //fore each module from webserver

						ModuleContent jTHChildOfIthGroup=new ModuleContent();	//create ChilGUIITEM

						//setproperties of GUI Module

						jTHChildOfIthGroup.setName(moodleModule.getName());
						jTHChildOfIthGroup.setImage(moodleModule.getModIcon());
						jTHChildOfIthGroup.setModule(moodleModule);


						//add jth Child GUI to List of ith GROU GUI
						childListOfITHGroup.add(jTHChildOfIthGroup);
					}
					else
						Log.i(LOGTAG,iTHmoodleCourseContent.getName()+"'s MOdule list is nulll");
				}


			}
			else
				Log.i(LOGTAG,iTHmoodleCourseContent.getName()+"'s MOdule list is nulll");



			iTHcourseContentGroup.setItems(childListOfITHGroup);
			listOfGroups.add(iTHcourseContentGroup);	




		}

		return listOfGroups;
	}



	public String[] generateFileDiscriptor()
	{
		MoodleModuleContent content=ClickedResource.getContent()[0];
		String hostName="";
		String fileurl="";
		String path="";
		String filename="";
		String fileType="";
		try{
			URL url=new URL(SessionWrapper.getCurrentsession().getCurrentSiteInfo().getSiteURL());
			hostName=url.getHost();


			fileurl=content.getFileURL();
			if(fileurl.contains("?"))
			{
				fileurl=fileurl+"&";
			}
			else
			{
				fileurl=fileurl+"?";
			}

			fileurl+="token="+URLEncoder.encode(SessionWrapper.getCurrentsession().getCurrentLogin().getToken(),"UTF-8");
			String username=SessionWrapper.getCurrentsession().getCurrentLogin().getUserName();
			username=username.replace(':','_').replace('/','_').replace('\\','_');

			String courseName=CoursesFragment.currentCourseDetails.getFullName();
			courseName=courseName.replace(':','_').replace('/','_').replace('\\','_');

			String topicName=ClickedTopic.getName();
			topicName=topicName.replace(':','_').replace('/','_').replace('\\','_');

			path="Mooddlloid/"+hostName+"/"+username+"/courses/"+courseName+"/"+topicName+"/";

			filename=content.getFilename();
		
			fileType=content.getType();
//			content.get
			String []array={fileurl,path,filename,fileType};
			return array;
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public boolean alreadyDownloaded(String path,String filename)
	{
		File rootPath = new File(Environment.getExternalStorageDirectory(),path+"/"+filename);
		if(rootPath.exists()) {

			UIHelper.showToast(this,rootPath.getAbsolutePath()+" is downloaded");
			//create the directory structure if it doesn't exist
			return true;
			//					UIHelper.showToast(this,"folder exist :"+rootPath);
		}
		else{
			UIHelper.showToast(this,rootPath.getAbsolutePath());
			return false;
		}
	}

	class FileDownladerDialog extends DialogFragment {

		String messageString="";

		public FileDownladerDialog(String message) {
			// TODO Auto-generated constructor stub
			messageString=message;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the Builder class for convenient dialog construction
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setMessage(messageString)
			.setPositiveButton("Yes", 
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					String array []=generateFileDiscriptor();

					if(array!=null)
					{
						String fileurl=array[0];
						String path=array[1];
						String filename=array[2];
						if(isOnline()){
							if(checkStorage())
								new DownloadFileFromURL().execute(fileurl,path,filename);
							else
								UIHelper.showToast(CourseDetailActivity.this,"SD card is unavailble");
						}
						else
						{
							UIHelper.showToast(CourseDetailActivity.this,"Sorry Network Connection is unavailable");

						}
						//UIHelper.showToast(CourseDetailActivity.this,fileurl);
					}
				}
			})

			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// User cancelled the dialog

					clickedAction=false;
				}
			});
			// Create the AlertDialog object and return it
			return builder.create();
		}
	}


	class openFileDialog extends DialogFragment {

		String messageString="";

		public openFileDialog(String message) {
			// TODO Auto-generated constructor stub
			messageString=message;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the Builder class for convenient dialog construction
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setMessage(messageString)
			.setPositiveButton("Yes", 
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					
					
					FileManger.openFile(CourseDetailActivity.this,Environment.getExternalStorageDirectory().getAbsolutePath(),FilepathToBeOpened, FileTypetoBeOpened);
										
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// User cancelled the dialog

					clickedAction=false;
					
					
				}
			});
			// Create the AlertDialog object and return it
			return builder.create();
		}
	}

	class webBrowserDiagllog extends DialogFragment {

		String messageString="";

		public webBrowserDiagllog(String message) {
			// TODO Auto-generated constructor stub
			messageString=message;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the Builder class for convenient dialog construction
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setMessage(messageString)
			.setPositiveButton("Yes", 
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

									
					try {
						String normalUrl=ClickedResource.getContent()[0].getFileURL();
						String fileurl="";
						if(normalUrl.contains("?"))
						{
							fileurl=normalUrl+"&token="+SessionWrapper.getCurrentsession().getCurrentLogin().getToken();
						}
						else
							fileurl=normalUrl+"?token="+SessionWrapper.getCurrentsession().getCurrentLogin().getToken();
						
						Log.i(LOGTAG,fileurl);
					    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileurl));
					    startActivity(myIntent);
					} catch (ActivityNotFoundException e) {
					    UIHelper.showToast(CourseDetailActivity.this, "No application can handle this request." + " Please install a webbrowser");
					    e.printStackTrace();
					}
									
					
					
					
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// User cancelled the dialog

					clickedAction=false;
				}
			});
			// Create the AlertDialog object and return it
			return builder.create();
		}
	}



	public  class ClickListner implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}


	/**
	 *check tne network connection
	 */
	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 *check the availabilty of the storagelocation
	 **/ 
	protected boolean checkStorage()
	{
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			UIHelper.showToast(this,"Media is unmounted");
			//finish();
			return false;
		}
		else return true;
	}



	/**
	 * Background Async Task to download file
	 * */
	class DownloadFileFromURL extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread
		 * Show Progress Bar Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(progress_bar_type);
		}



		/**
		 * Downloading file in background thread
		 * */
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				//f_url[0] is url of the file location
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();

				// getting file length
				int lenghtOfFile = conection.getContentLength();
				// input stream to read file - with 8k buffer
				InputStream input = new BufferedInputStream(url.openStream(), lenghtOfFile);

				//f_url[1] is the path 
				//f_url[2] is the fileName
				//Create the fileObjectand return the refernece to it
				File file= createDriectories(f_url[1],f_url[2]);

				// Output stream to write file
				OutputStream output = new FileOutputStream(file);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					// publishing the progress....
					// After this onProgressUpdate will be called
					publishProgress(""+(int)((total*100)/lenghtOfFile));

					// writing data to file
					output.write(data, 0, count);
				}

				// flushing output
				output.flush();

				// closing streams
				output.close();
				input.close();

			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}

			return null;
		}



		/**
		 *To Create necessaryFolder Structure;
		 **/
		protected File createDriectories(String path,String filename)
		{
			if(checkStorage()){
				//if the storage is available

				//directory structure to maintain the course content
				String rootDirectory=path;
				//name of the file
				String testFile=filename;

				//create the directory hierachy if it doesn't exist
				File rootPath = new File(Environment.getExternalStorageDirectory(),rootDirectory);
				if(!rootPath.exists()) {
					//create the directory structure if it doesn't exist
					rootPath.mkdirs();
					//					UIHelper.showToast(this,"folder exist :"+rootPath);
				}


				//initialize a file to write the content;
				File dataFile = new File(rootPath,testFile);

				return dataFile;
			}
			return null;
		}


		/**
		 * Updating progress bar
		 * */
		protected void onProgressUpdate(String... progress) {
			// setting progress percentage
			pDialog.setProgress(Integer.parseInt(progress[0]));
		}


		/**
		 * After completing background task
		 * Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after the file was downloaded
			dismissDialog(progress_bar_type);
			UIHelper.showToast(CourseDetailActivity.this,"File is succesfully downloaded !");
		}

	}

}
