package com.tharindu.moodlloid.model;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.tharindu.moodlloid.rest.MoodleCourseContent;
import com.tharindu.moodlloid.rest.MoodleModule;
import com.tharindu.moodlloid.rest.MoodleModuleContent;


public class CourseContent {

    private String Name;
    private ArrayList<ModuleContent> Items;
    private MoodleCourseContent content;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<ModuleContent> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ModuleContent> Items) {
        this.Items = Items;
    }

	public MoodleCourseContent getContent() {
		return content;
	}

	public void setContent(MoodleCourseContent content) {
		this.content = content;
	}

}
