package com.tharindu.moodlloid;


//import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tharindu.moodlloid.model.CourseContent;
import com.tharindu.moodlloid.model.ModuleContent;
import com.tharindu.moodlloid.model.Site;
import com.tharindu.moodlloid.net.SessionWrapper;
import com.tharindu.moodlloid.rest.MoodleUser;
import com.tharindu.moodlloid.util.UIHelper;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class TestDataActivity extends Activity {


	private ProgressDialog pDialog;
	public static final int progress_bar_type = 0; 
	private String LOGTAG="TestDataActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_data);
		Button btn=(Button)findViewById(R.id.button1);
		btn.setOnClickListener(new ClickListner());


	}

	public  class ClickListner implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String fileurl="http://192.168.1.100/projects/moodle/webservice/pluginfile.php/1220/mod_resource/content/0/resource1.txt?forcedownload=1&token=18021c8d742daad226625f63b8acf652";
			String filename="resource1.txt";
			String path="Moodlloid/host/userName/Course/Topic/";
			if(isOnline()){
				if(checkStorage())
					new DownloadFileFromURL().execute(fileurl,path,filename);
				else
					UIHelper.showToast(TestDataActivity.this,"SD card is unavailble");
			}
			else
			{
				UIHelper.showToast(TestDataActivity.this,"Sorry Network Connection is unavailable");
				
			}
		}

	}

	
	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

	



	protected boolean checkStorage()
	{
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			UIHelper.showToast(this,"Media is unmounted");
			//finish();
			return false;
		}
		else return false;
	}
	
	
	
	public void writeFile(File f)
	{
		
	}
	


	/**
	 *  root is created
	 **/	
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
	
	
	
	
	
	public ArrayList<CourseContent> SetStandardGroups() {

		String group_names[] = { "GROUP A", "GROUP B", "GROUP C", "GROUP D",
				"GROUP E", "GROUP F", "GROUP G", "GROUP H" };

		String country_names[] = { "Brazil", "Mexico", "Croatia", "Cameroon",
				"Netherlands", "chile", "Spain", "Australia", "Colombia",
				"Greece", "Ivory Coast", "Japan", "Costa Rica", "Uruguay",
				"Italy", "England", "France", "Switzerland", "Ecuador",
				"Honduras", "Agrentina", "Nigeria", "Bosnia and Herzegovina",
				"Iran", "Germany", "United States", "Portugal", "Ghana",
				"Belgium", "Algeria", "Russia", "Korea Republic" };


		ArrayList<CourseContent> list = new ArrayList<CourseContent>();

		ArrayList<ModuleContent> ch_list;

		int size = 4;
		int j = 0;

		for (String group_name : group_names) {
			CourseContent gru = new CourseContent();
			gru.setName(group_name);

			ch_list = new ArrayList<ModuleContent>();
			for (; j < size; j++) {
				ModuleContent ch = new ModuleContent();
				ch.setName(country_names[j]);
				// ch.setImage(Images[j]);
				ch_list.add(ch);
			}
			gru.setItems(ch_list);
			list.add(gru);

			size = size + 4;
		}

		return list;
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
			UIHelper.showToast(TestDataActivity.this,"File is succesfully downloaded !");
		}

	}
}


