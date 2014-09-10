package com.tharindu.moodlloid;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.tharindu.moodlloid.rest.MoodleCourseContent;
import com.tharindu.moodlloid.rest.MoodleCourse;
import com.tharindu.moodlloid.R;

import com.tharindu.moodlloid.net.SessionWrapper;
import com.tharindu.moodlloid.rest.MoodleCallRestWebService;
import com.tharindu.moodlloid.rest.MoodleRestCourse;
import com.tharindu.moodlloid.rest.MoodleRestException;
import com.tharindu.moodlloid.rest.MoodleRestUser;
import com.tharindu.moodlloid.rest.MoodleRestWebService;
import com.tharindu.moodlloid.rest.MoodleRestWebServiceException;
import com.tharindu.moodlloid.rest.MoodleUser;
import com.tharindu.moodlloid.rest.MoodleWebService;
import com.tharindu.moodlloid.rest.UserEnrolledCourse;
import com.tharindu.moodlloid.util.UIHelper;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class CoursesFragment extends Fragment {

	public static MoodleCourseContent[] currentCourse;
	 ProgressBar pb;
	private List<UserEnrolledCourse> enroledCourseList;
	private ListView courseList;
	private  Activity containerActivity;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
		final View rootView = inflater.inflate(R.layout.courselist_main, container, false);
		containerActivity=(Activity)rootView.getContext();
		enroledCourseList=SessionWrapper.getCurrentsession().getCurrentUser().getEnrolledCourses();
		UIHelper.showToast(rootView.getContext(),enroledCourseList.get(0).getFullName());
//	
		 pb=(ProgressBar)rootView.findViewById(R.id.progressBar1);
	CourseListAdapter adapter= new CourseListAdapter(rootView.getContext(),enroledCourseList);
//		
	
	courseList=(ListView)rootView.findViewById(R.id.list_courses);
	
	if(enroledCourseList==null)
		{
			UIHelper.showToast(rootView.getContext(),"No Enroled courses");
		}
		else
		{
			courseList.setAdapter(adapter);
	
			courseList.setOnItemClickListener(new OnItemClickListener()
			{
			    @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
			    { 
			      UIHelper.showToast(rootView.getContext(), "Stop Clicking me"+position);
			      loadCourse(position);
			    }
			});
		}
//				
//		
//		WebView webView = (WebView)rootView.findViewById(R.id.webview);
//
//        webView.setInitialScale(0);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setScrollbarFadingEnabled(false);
//
//        webView.loadUrl("file:///android_asset/index.html");
        
		return rootView;
    }

	
	
	
	
	
	
	
	void loadCourse(int position){
		//loging to one of the site user clicked

		UserEnrolledCourse course=enroledCourseList.get(position);
		
		
			//UIHelper.showToast(this, s.getRestURL()+"\n"+s.getToken());
			
			
			pb.setVisibility(pb.VISIBLE);
			
			MyTask task=new MyTask();
			task.execute(course.getId());
			
		
		//UIHelper.showToast(this,position+" isclicked");

	}

	
	

	/* ********************************************************************************************** 
	 * Inner class for itemClicklister for list item goes here......
	 */	

	class CourseLoader implements ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			loadCourse(position);
		}

	}


	private class MyTask extends AsyncTask<Long,String ,MoodleCourseContent[]> {

	

		@Override
		protected   MoodleCourseContent[] doInBackground(Long... params) {

			try{

				 	Long courseId=params[0];
		            MoodleCourseContent[] courseContent = MoodleRestCourse.getCourseContent(courseId, null);
		         
//				MoodleWebService siteInfo = MoodleRestWebService.getSiteInfo();
				
//				currentSessionInfo.setCurrentSiteInfo(siteInfo);
//				
//				String imageUrl=siteInfo.getUserPictureURL();
//			//	publishProgress(imageUrl);
//				Log.i("Welcome",imageUrl);
//				InputStream in = (InputStream) new URL(imageUrl).getContent();
//				Bitmap image = BitmapFactory.decodeStream(in);
//				
//				
//				Long[] users=new Long[1];
//			      users[0]=MoodleRestWebService.getSiteInfo().getUserId();
//			      
//			      MoodleUser[] user=MoodleRestUser.getUsersById(users);
//			      currentSessionInfo.setCurrentUser(user[0]);
//			      
	
				return courseContent; 
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
			UIHelper.showToast(containerActivity,values[0]);
		}
		

		@Override
		protected void onPostExecute(MoodleCourseContent [] result) {

				currentCourse=result;

			if (result==null) {
				UIHelper.showToast(containerActivity,"Error Occureed");
			} else {
			
				//siteINFO=result;
				pb.setVisibility(pb.INVISIBLE);
				
				//UIHelper.showToast(Welcome.this,result.getUserName());
				Intent intent=new Intent(containerActivity,CourseDetailActivity.class);
				//intent.putExtra("MoodleWebService",result);
				startActivity(intent);
			}
			

		}

	}

	
}

