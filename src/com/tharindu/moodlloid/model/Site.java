package com.tharindu.moodlloid.model;

import java.io.Serializable;

import com.tharindu.moodlloid.Configuration;

/**************************************************************
 * 
 *This class is the Model class for the Differnt User Sessions 
 *There is a database table maintain the 
 *(ID,USERNAME,PASSWORD,TOKEN) 
 * @author tharindu
 * 
 **************************************************************/
public class Site{

	/**
	 * Url of the VALID Moodle instance
	 */
	private String siteURL;


	/**
	 *password of the current User 
	 */
	private String password;

	/**
	 * userName of the Currnt users
	 */
	private String userName;

	/**
	 * This is the Tokenn used to authendicate once , the moodle instance is succefully registeredd with this app
	 * No login is needed with username, password prompt
	 */
	private String token;

	/**
	 * Primary key of the site Table
	 */
	private long id;

	/**
	 * This is the Rest path need to access Web services ...
	 * with requried parameters...
	 */
	private String restURL;

	
	
/*************************** Method goes here... *******************************************/

	/**
	 * gettter for Primary key
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for the primary key
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * getter for the URL
	 * @return
	 */
	public String getSiteURL() {
		return siteURL;
	}


	/**
	 * setter for the URL
	 * @param siteURL
	 */
	public void setSiteURL(String siteURL) {
		this.siteURL = siteURL;
		setRestURL(siteURL+Configuration.restPath);
	}


	/**
	 * getter for the Password
	 * @return
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * Setter for the password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

	/**
	 * getter fot the UserName
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Setter for the username
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	/**
	 * Getter the token value recived from the web service login
	 * @return
	 */
	public String getToken() {
		return token;
	}
	

	/**
	 * setter for the tokenn
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	

	/**
	 * @return
	 */
	public String getRestURL() {
		return restURL;
	}
	

	/**
	 * @param restURL
	 */
	public void setRestURL(String restURL) {
		this.restURL = restURL;
	}



}
