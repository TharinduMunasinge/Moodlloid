package com.tharindu.moodlloid;

import com.tharindu.moodlloid.util.UIHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class LoginActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.);
			    
	}
	

	public void authendicate(View view) {
		
		String userName=UIHelper.getText(this,R.id.txtUserName);
		String passWord=UIHelper.getText(this,R.id.txtPassword);
		String url=UIHelper.getText(this,R.id.url);
		
		
		
//		Handler handler = new Handler();
//		handler.postDelayed(new Runnable() {
//	        public void run() {
//	            finish();
//	            startActivity(new Intent(getApplicationContext(), Welcome.class));
//	        }
//	    }, 2000);
		
		
	}
}
