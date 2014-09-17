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

public class SiteRegistrationActivity extends Activity{

	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	SitesDataSource datasource;
	String userName;
	String password;
	String mURL;
	boolean ok=false;
	ProgressBar pb;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_site);

		datasource = new SitesDataSource(this);
		datasource.open();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		pb=(ProgressBar)findViewById(R.id.progressBar1);
			
	}

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
				
				mURL="http://"+mURL;
			URL uri=new URL(mURL);
			
			String testURL=mURL+Configuration.restPath;
			//UIHelper.showToast(this, testURL);

			MyTask task=new MyTask();
			pb.setVisibility(pb.VISIBLE);
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

	private void saveCredentials(String userName, String passWord, String url,String token) {
		Site site=new Site();
		site.setPassword(passWord);
		site.setSiteURL(url);
		site.setUserName(userName);
		site.setToken(token);
		UIHelper.showToast(this,"Moodle is succefully registered");
		datasource.create(site);
		finish();
	}


	public void createFile(View v) throws IOException {
		JSONArray site=new JSONArray();
		JSONObject tour=new JSONObject();
		try {
			tour.put("tour","Salton Sea");
			tour.put("price",900);
			site.put(tour);

			tour=new JSONObject();

			tour.put("tour","abc Sea");
			tour.put("price",9);
			site.put(tour);

			tour=new JSONObject();

			tour.put("tour","DEfton Sea");
			tour.put("price",90);
			site.put(tour);

			tour=new JSONObject();
			tour.put("tour","GHIton Sea");
			tour.put("price",9000);
			site.put(tour);



			String text = site.toString();

			FileOutputStream fos = openFileOutput("sites.txt", MODE_PRIVATE);
			fos.write(text.getBytes());
			fos.close();

			//		UIHelper.displayText(this, R.id.textView1, "File written to disk"+data.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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


	private class MyTask extends AsyncTask<String,String ,String> {


		@Override
		protected String doInBackground(String... params) {
			try{
				String response= HttpManager.getData(params[0]);
	
				if(response==null || response.isEmpty()){			
					publishProgress("This url is not compatible with this app !");
					return null;					
				}
				else{				    
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
				
				UIHelper.showToast(SiteRegistrationActivity.this,"You have succesfully registered !!!!!");
				saveCredentials(userName, password, mURL, token);			
				
			}
			pb.setVisibility(ProgressBar.INVISIBLE);
		}
		
		
	}

}
