package com.tharindu.moodlloid.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpManager {

	public static String getData(String uri) {
		
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
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

	private static HttpURLConnection sendRequest(String uri) throws MalformedURLException, IOException {
		URL url = new URL(uri);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		return con;
	}
	
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
