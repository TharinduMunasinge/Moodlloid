package com.tharindu.moodlloid.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/***************************************************************
 * This is the CLASS which HANDLE ALL THE HTTP REQUST HANDLING
 * @author tharindu
 * 
 ****************************************************************/
public class HttpManager {

	/**
	 * @param uri : Url to which http request should be send
	 * @return : The response body
	 */
	public static String getData(String uri) {
		
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		//to store the response for the request
		try {
			
			//send request using HTTP URL CONNECTION JAVA LIBRARy
			HttpURLConnection con = sendRequest(uri);
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		
	}

	/**
	 * This is the method which set the TCP connection the requst and return the Connection object
	 * @param uri : URL
	 * @return : Connnection object 
	 * @throws MalformedURLException : if the format of the url is invalid 
	 * @throws IOException
	 */
	private static HttpURLConnection sendRequest(String uri) throws MalformedURLException, IOException {
		URL url = new URL(uri);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		return con;
	}
	
	/**
	 * This method will return the RESPONSE STATUS CODE 
	 * 
	 * @param url
	 * @return Status code of the resoponse
	 */
	public static int getStatus(String url)
	{
		try {
			HttpURLConnection connection=sendRequest(url);
			return connection.getResponseCode();
			
		} catch (Exception e) {
			return -1;
		}
	}
	
}
