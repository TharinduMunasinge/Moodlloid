package com.tharindu.moodlloid.model;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.tharindu.moodlloid.rest.MoodleCourseContent;
import com.tharindu.moodlloid.rest.MoodleModule;
import com.tharindu.moodlloid.rest.MoodleModuleContent;


/****************************************************************************************
 * This class is the Model Class to represent the GroupView of CourseContent Activity
 * 
 * @author tharindu
 *
 *****************************************************************************************/

public class CourseContent {

	//name of the Topic
    private String Name;
    //list of childeren coure Content objects
    private ArrayList<ModuleContent> Items;
    //associate Course content object from the MOOODLE
    private MoodleCourseContent content;

    
    /**
     * return the name of the CurrentTopic
     * @return
     */
    public String getName() {
        return Name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.Name = name;
    }

    /**
     * reurn the list of Child views for this Group view
     * @return
     */
    public ArrayList<ModuleContent> getItems() {
        return Items;
    }

    /**
     * set the list of chidren for this Group view
     * @param Items
     */
    public void setItems(ArrayList<ModuleContent> Items) {
        this.Items = Items;
    }

	/**
	 * set the refernces to Moodle Coruse object associated with this view
	 * @return
	 */
	public MoodleCourseContent getContent() {
		return content;
	}

	/**
	 * get the associated moodle course objects
	 * @param content
	 */
	public void setContent(MoodleCourseContent content) {
		this.content = content;
	}

}
