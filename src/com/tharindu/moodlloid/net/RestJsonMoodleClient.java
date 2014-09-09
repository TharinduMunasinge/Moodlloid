package com.tharindu.moodlloid.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class RestJsonMoodleClient {

	/**
	 * Do a REST call to Moodle. Result are displayed in the console log.
	 * @param args the command line arguments
	 */

	private String url;
	private String userName;
	private String password;

	public RestJsonMoodleClient(String url, String userName, String password) {
		super();
		this.url = url;
		this.userName = userName;
		this.password = password;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void auth(String url,String userName,String password){

		
	}


	public void SendRequst(String domainName,String token,String functionName) throws ProtocolException, IOException {

		/// NEED TO BE CHANGED
		//  String token = "d956434f31c242a53ee8e120d8b4459f";
		// String domainName = "http://localhost/projects/moodle";

		/// REST RETURNED VALUES FORMAT
	
		String restformat = "xml"; //Also possible in Moodle 2.2 and later: 'json'
		//Setting it to 'json' will fail all calls on earlier Moodle version
		
		if (restformat.equals("json")) {
			restformat = "&moodlewsrestformat=" + restformat;
		} else {
			restformat = "";
		}

		/// PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
		//String functionName = "core_course_get_contents";

		/* String urlParameters =
        "users[0][username]=" + URLEncoder.encode("testusername1", "UTF-8") +
        "&users[0][password]=" + URLEncoder.encode("testpassword1", "UTF-8") +
        "&users[0][firstname]=" + URLEncoder.encode("testfirstname1", "UTF-8") +
        "&users[0][lastname]=" + URLEncoder.encode("testlastname1", "UTF-8") +
        "&users[0][email]=" + URLEncoder.encode("testemail1@moodle.com", "UTF-8") +
        "&users[0][auth]=" + URLEncoder.encode("manual", "UTF-8") +
        "&users[0][idnumber]=" + URLEncoder.encode("testidnumber1", "UTF-8") +
        "&users[0][lang]=" + URLEncoder.encode("en", "UTF-8") +
        "&users[0][theme]=" + URLEncoder.encode("standard", "UTF-8") +
        "&users[0][timezone]=" + URLEncoder.encode("-12.5", "UTF-8") +
        "&users[0][mailformat]=" + URLEncoder.encode("0", "UTF-8") +
        "&users[0][description]=" + URLEncoder.encode("Hello World!", "UTF-8") +
        "&users[0][city]=" + URLEncoder.encode("testcity1", "UTF-8") +
        "&users[0][country]=" + URLEncoder.encode("au", "UTF-8") +
        "&users[0][preferences][0][type]=" + URLEncoder.encode("preference1", "UTF-8") +
        "&users[0][preferences][0][value]=" + URLEncoder.encode("preferencevalue1", "UTF-8") +
        "&users[0][preferences][1][type]=" + URLEncoder.encode("preference2", "UTF-8") +
        "&users[0][preferences][1][value]=" + URLEncoder.encode("preferencevalue2", "UTF-8") +
        "&users[1][username]=" + URLEncoder.encode("testusername2", "UTF-8") +
        "&users[1][password]=" + URLEncoder.encode("testpassword2", "UTF-8") +
        "&users[1][firstname]=" + URLEncoder.encode("testfirstname2", "UTF-8") +
        "&users[1][lastname]=" + URLEncoder.encode("testlastname2", "UTF-8") +
        "&users[1][email]=" + URLEncoder.encode("testemail2@moodle.com", "UTF-8") +
        "&users[1][timezone]=" + URLEncoder.encode("Pacific/Port_Moresby", "UTF-8");
		 */

		String urlParameters = "courseid=" + URLEncoder.encode("2", "UTF-8") ;

		/// REST CALL

		// Send request
		String serverurl = domainName + "/webservice/rest/server.php" + "?wstoken=" + token + "&wsfunction=" + functionName;
		HttpURLConnection con = (HttpURLConnection) new URL(serverurl).openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Language", "en-US");
		con.setDoOutput(true);
		con.setUseCaches (false);
		con.setDoInput(true);
		DataOutputStream wr = new DataOutputStream (
				con.getOutputStream ());
		wr.writeBytes (urlParameters);
		wr.flush ();
		wr.close ();

		//Get Response
		InputStream is =con.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder response = new StringBuilder();
		while((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		System.out.println(response.toString());
	}
}
