package com.tharindu.moodlloid.net;

import java.io.Serializable;

import android.graphics.Bitmap;

import com.tharindu.moodlloid.model.Site;
import com.tharindu.moodlloid.rest.MoodleUser;
import com.tharindu.moodlloid.rest.MoodleWebService;

public class SessionWrapper implements Serializable{

	private  MoodleUser currentUser;
	private  Site  currentLogin;
	private  Bitmap userImage;
	private  MoodleWebService currentSiteInfo;
	private  static SessionWrapper currentSession;
	
	private SessionWrapper()
	{
		
	}
	
	public static SessionWrapper getCurrentsession()
	{
		if(currentSession==null)
			return currentSession=new SessionWrapper();
		else
			return currentSession;
	}
	
	public  MoodleWebService getCurrentSiteInfo() {
		return currentSiteInfo;
	}
	
	public  void setCurrentSiteInfo(MoodleWebService currentSiteInfo) {
		this.currentSiteInfo = currentSiteInfo;
	}
	public  MoodleUser getCurrentUser() {
		return currentUser;
	}
	public  void setCurrentUser(MoodleUser currentUser) {
		this.currentUser = currentUser;
	}
	public Site getCurrentLogin() {
		return currentLogin;
	}
	public  void setCurrentLogin(Site currentLogin) {
		this.currentLogin = currentLogin;
	}

	public Bitmap getUserImage() {
		return userImage;
	}

	public void setUserImage(Bitmap userImage) {
		this.userImage = userImage;
	}
	
	
}
