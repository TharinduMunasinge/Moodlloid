package com.tharindu.moodlloid;

import com.tharindu.moodlloid.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class NotificationsFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
		View rootView = inflater .inflate(R.layout.marks_fragment, container, false);
		
//		WebView webView = (WebView)rootView.findViewById(R.id.webview);
//
//        webView.setInitialScale(0);
//      //  webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setScrollbarFadingEnabled(false);
//
//        webView.loadUrl("file:///android_asset/marks.html");
        
		return rootView;
    }

}
