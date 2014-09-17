package com.tharindu.moodlloid.net;

import java.io.Serializable;

import android.graphics.Bitmap;

import com.tharindu.moodlloid.model.Site;
import com.tharindu.moodlloid.rest.MoodleUser;
import com.tharindu.moodlloid.rest.MoodleWebService;

/*******************************************************************
 * 
 * This class represent the sesssion information of the current Session
 * This class Uses SINGLTON pattern to assure that only one Sesssion coudl be made in a momment
 * 
 * @author tharindu
 *
 ******************************************************************/
public class SessionWrapper implements Serializable{

	/**
	 * Moodle user  Information regarding the Current User    
	 */
	private  MoodleUser currentUser;
		
	/**
	 * Information regarding the  current Loging Creditials
	 */
	private  Site  currentLogin;
	
	/**
	 *User profile picture 
	 */
	private  Bitmap userImage;
	
	/**
	 *Information of the Moodle server and allowed function 
	 */
	private  MoodleWebService currentSiteInfo;
		
	/**
	 *To implement Singlton Pattern Static refernce to the only object is used
	 */
	private  static SessionWrapper currentSession;
	
	
	
	
	
	/**
	 *By making the constuctor : essentially block creating new instance from this class 
	 */
	private SessionWrapper()
	{
		
	}
	
	
	
	/********************** Methods of the class goes here............***************/
	
	
	/*** 
	 * This method generate the Single Session object always
	 * @return 
	 */
	public static SessionWrapper getCurrentsession()
	{
		if(currentSession==null)
			return currentSession=new SessionWrapper();
		else
			return currentSession;
	}
		
	/**
	 * getter for the MOODLE site infrmation
	 * @return
	 */
	public  MoodleWebService getCurrentSiteInfo() {
		return currentSiteInfo;
	}
	
	/**
	 * setter for the MOODLE site information
	 * @param currentSiteInfo
	 */
	public  void setCurrentSiteInfo(MoodleWebService currentSiteInfo) {
		this.currentSiteInfo = currentSiteInfo;
	}
	
	/**
	 * Getter for the Current USER information
	 * @return
	 */
	public  MoodleUser getCurrentUser() {
		return currentUser;
	}
		
	/**
	 * Setter for the Current USER information
	 * @param currentUser
	 */
	public  void setCurrentUser(MoodleUser currentUser) {
		this.currentUser = currentUser;
	}
		
	/**
	 * Getters for the Credentials
	 * @return
	 */
	public Site getCurrentLogin() {
		return currentLogin;
	}
		
	/**
	 * setter for the Current Sesssion credetentials
	 * @param currentLogin
	 */
	public  void setCurrentLogin(Site currentLogin) {
		this.currentLogin = currentLogin;
	}

	/**
	 * getter of the ProfilePicture
	 * @return
	 */
	public Bitmap getUserImage() {
		return userImage;
	}

	/**
	 * Setter of the profile Picture
	 * @param userImage
	 */
	public void setUserImage(Bitmap userImage) {
		this.userImage = userImage;
	}
	
	
}
