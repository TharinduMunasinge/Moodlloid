package com.tharindu.moodlloid;

import java.util.ArrayList;
import java.util.List;
import com.tharindu.moodlloid.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	

	// Attribute goes here.............
	String[] menutitles; 			//to enumarate through menu items
	TypedArray menuIcons;			//to enumarate through menu items
	private CharSequence mDrawerTitle; 
	private CharSequence mTitle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;	
	private ActionBarDrawerToggle mDrawerToggle;
	private List<RowItem> rowItems;
	private CustomAdapter adapter; 	


	//Getter and Setters for attributes......
	
	public String[] getMenutitles() {
		return menutitles;
	}

	public void setMenutitles(String[] menutitles) {
		this.menutitles = menutitles;
	}

	public TypedArray getMenuIcons() {
		return menuIcons;
	}

	public void setMenuIcons(TypedArray menuIcons) {
		this.menuIcons = menuIcons;
	}

	public CharSequence getmDrawerTitle() {
		return mDrawerTitle;
	}

	public void setmDrawerTitle(CharSequence mDrawerTitle) {
		this.mDrawerTitle = mDrawerTitle;
	}

	public CharSequence getmTitle() {
		return mTitle;
	}

	public void setmTitle(CharSequence mTitle) {
		this.mTitle = mTitle;
	}

	public DrawerLayout getmDrawerLayout() {
		return mDrawerLayout;
	}

	public void setmDrawerLayout(DrawerLayout mDrawerLayout) {
		this.mDrawerLayout = mDrawerLayout;
	}

	public ListView getmDrawerList() {
		return mDrawerList;
	}

	public void setmDrawerList(ListView mDrawerList) {
		this.mDrawerList = mDrawerList;
	}

	public ActionBarDrawerToggle getmDrawerToggle() {
		return mDrawerToggle;
	}

	public void setmDrawerToggle(ActionBarDrawerToggle mDrawerToggle) {
		this.mDrawerToggle = mDrawerToggle;
	}

	public List<RowItem> getRowItems() {
		return rowItems;
	}

	public void setRowItems(List<RowItem> rowItems) {
		this.rowItems = rowItems;
	}

	public CustomAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(CustomAdapter adapter) {
		this.adapter = adapter;
	}

	
	
	// EventHandler goes here.........
	@SuppressLint("NewApi")
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		menutitles = getResources().getStringArray(R.array.titles); //instantiate title list with string values
		menuIcons = getResources().obtainTypedArray(R.array.icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		rowItems = new ArrayList<RowItem>();

		for (int i = 0; i < menutitles.length; i++) {
			RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(i, -1));
			rowItems.add(items);
		}

		menuIcons.recycle();

		adapter = new CustomAdapter(getApplicationContext(), rowItems);

		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new SlideitemListener());

		// enabling action bar app icon and behaving it as toggle button
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, R.string.app_name,R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			updateDisplay(0);
		}

	}

	
	
	class SlideitemListener implements ListView.OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			updateDisplay(position);
		}

	}

	
	//Display the relevent activites based on the user events
	
	private void updateDisplay(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new Profile();
			break;
		case 1:
			fragment = new Courses();
			break;
		case 2:
			fragment = new Marks();
			break;
		case 3:
			fragment = new Explanations();
			break;
		case 4: 
			fragment= new Callendar();
		default:	
			fragment = new Courses();
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			// update selected item and title, then close the drawer
			setTitle(menutitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case  R.id.action_settings :
			return true;
		default :
			return super.onOptionsItemSelected(item);
		}
	}

	
	
	//called when invalidateOptionsMenu() is triggered
	 
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}


//	 When using the ActionBarDrawerToggle, you must call it during onPostCreate() and onConfigurationChanged()...

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

} 
