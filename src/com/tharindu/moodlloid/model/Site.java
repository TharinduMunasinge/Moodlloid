package com.tharindu.moodlloid.model;

import java.io.Serializable;

import com.tharindu.moodlloid.Configuration;

public class Site{

	private String siteURL;
	private String password;
	private String userName;
	private String token;
	private long id;
	private String restURL;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSiteURL() {
		return siteURL;
	}
	public void setSiteURL(String siteURL) {
		this.siteURL = siteURL;
		setRestURL(siteURL+Configuration.restPath);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRestURL() {
		return restURL;
	}
	public void setRestURL(String restURL) {
		this.restURL = restURL;
	}
	
	
	
}
