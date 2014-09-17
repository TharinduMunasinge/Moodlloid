package com.tharindu.moodlloid;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tharindu.moodlloid.model.Site;
import com.tharindu.moodlloid.net.HttpManager;
import com.tharindu.moodlloid.store.SitesDataSource;
import com.tharindu.moodlloid.util.UIHelper;
//import android.R;
enum Errorcode{EMPTY,NOT_A_VALID_URL,CREDENTIAL_WRONG,OK};

/****************************************************************************************
 * This class reprsent the Site Registration Windows appears when user add new site to it
 * @author tharindu
 *****************************************************************************************/

public class SiteRegistrationActivity extends Activity{

	SharedPreferences preferences;	//to store the userpresrfersse
	SharedPreferences.Editor editor; // to send Sqlite3 commands
	SitesDataSource datasource;	 //To Access the database which stores teh credentials
	String userName;
	String password;
	String mURL;
	boolean ok=false;	//flag for error checking
	ProgressBar pb;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_site);

		//Initalize the connection to the database
		datasource = new SitesDataSource(this);
		datasource.open();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		pb=(ProgressBar)findViewById(R.id.progressBar1);
	}

	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==android.R.id.home);
		{
			finish();
		}
		return super.onOptionsItemSelected(item);
	}


	
	/**
	 * This method will try Validate input and Check the inputs...
	 * @param view
	 */
	public void authendicate(View view) {

		//UIHelper.showToast(this, "CLICKED");
		userName=UIHelper.getText(this,R.id.txtUserName);
		password=UIHelper.getText(this,R.id.txtPassword);
		mURL=UIHelper.getText(this,R.id.txtURL);

		if(validateInput(userName, password, mURL)!=Errorcode.OK)
		{
			//UIHelper.showToast(this,"Inputs cannot be left blank");
			return;
		}

		try{
			//Input feidl should not have http:// part , we will do it in back
			
			mURL="http://"+mURL;
			URL uri=new URL(mURL);

			String testURL=mURL+Configuration.restPath;
			MyTask task=new MyTask();
			
			pb.setVisibility(pb.VISIBLE);
			//Send a request to check the availability of web services 
			task.execute(testURL);

			//saveCredentials(userName, passWord, url);
		}catch(MalformedURLException e)
		{
			UIHelper.showToast(this,"Invalid URL");
		}
		catch(Exception e)
		{
			UIHelper.showToast(this,"invalid URL");
			Log.d("siteRegister", e.getMessage());
		}

	}


	/**
	 * Check whether any of the inputs are left blank or not?
	 * @param userName:txt
	 * @param password:txtpassowrd
	 * @param url:txtUrl
	 * @return 
	 */
	public Errorcode validateInput(String userName,String password,String url)
	{
		if(userName.isEmpty() || password.isEmpty() || url.isEmpty())
		{
			UIHelper.showToast(this, "Inputs cannot be left balank");
			return Errorcode.CREDENTIAL_WRONG;
		}
		else
		{
			return Errorcode.OK;
		}
	}

	/**
	 * Save the credentials in LOCAL Storage with secured.
	 * @param userName
	 * @param passWord
	 * @param url
	 * @param token
	 */
	private void saveCredentials(String userName, String passWord, String url,String token) {
		Site site=new Site();
		site.setPassword(passWord);
		site.setSiteURL(url);
		site.setUserName(userName);
		site.setToken(token);
		UIHelper.showToast(this,"Moodle is succefully registered");
		datasource.create(site); // will store on db
		finish();
	}


	
/*************************************BAckground thread to send Web request to the check the authentication**************************************************************/
	private class MyTask extends AsyncTask<String,String ,String> {


		@Override
		protected String doInBackground(String... params) {
			try{
				//send a request to check the availability
				String response= HttpManager.getData(params[0]);

				if(response==null || response.isEmpty()){
					//if the response is 404
					publishProgress("This url is not compatible with this app !");
					return null;					
				}
				else{				    
					//othe wise get the token field
					String tokenURL=mURL+Configuration.loginPath+"?username="+URLEncoder.encode(userName,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8")+"&service="+URLEncoder.encode(Configuration.WsShortName,"UTF-8");
					String tokenResponse= HttpManager.getData(tokenURL);;
					JSONObject data =new  JSONObject(tokenResponse);

					if(data.has("token"))
					{
						String token=data.getString("token");
						return token;
					}
					else{
						publishProgress("UserName or Password is incorect !!!!");
						return null;
					}
				}			

			}catch(JSONException ex)
			{

				publishProgress("UserName or Password is incorect !!!!");
				return null;

			}
			catch(Exception ex)
			{
				publishProgress("UserName or Password is incorect !!!!");
				Log.i("Welcome",ex.getMessage());
			} 	return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			//super.onProgressUpdate(values);
			//	pb.setVisibility(ProgressBar.INVISIBLE);
			UIHelper.showToast(SiteRegistrationActivity.this,values[0]);
			pb.setVisibility(ProgressBar.INVISIBLE);
		}

		@Override
		protected void onPostExecute(String token) {

			if(token!=null)
			{
				//if token recived sussfullly
				
				UIHelper.showToast(SiteRegistrationActivity.this,"You have succesfully registered !!!!!");
				saveCredentials(userName, password, mURL, token);			

			}
			pb.setVisibility(ProgressBar.INVISIBLE);
		}


	}

}
