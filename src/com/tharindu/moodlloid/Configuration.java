package com.tharindu.moodlloid;

/**
 * This class Contains the configuration of the path information of moodle server wher
 * where we have to send the requests 
 * @author tharindu
 * 
 */
public class Configuration {
	
	/**
	 *restPath: This is the normal path to which we should send http requests 
	 */
	public static String restPath="/webservice/rest/server.php";

	
	/**
	 * loginPath: Initial request to take a token , this path shoud be used 
	 */
	public static String loginPath="/login/token.php";
	
	
	/**
	 *wsShortName: Static string representing the webservice we are using
	 *
	 * For normal operation -> Default moodle_mobile web service would do
	 * If we need to access custom function->need to create driver for moodle to make them avaiable
	 */
	public static String WsShortName="moodle_mobile_app";
}
