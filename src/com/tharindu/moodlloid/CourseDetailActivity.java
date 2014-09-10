package com.tharindu.moodlloid;

import java.util.ArrayList;


import com.tharindu.moodlloid.rest.MoodleCourseContent;
import com.tharindu.moodlloid.rest.MoodleModule;
import com.tharindu.moodlloid.rest.MoodleModuleContent;
import com.tharindu.moodlloid.rest.MoodleCourse;
import android.app.Activity;
import android.os.Bundle;

public class CourseDetailActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		MoodleCourseContent[] listOfContent=CoursesFragment.currentCourse;
		showResult(listOfContent);
	}
	
	public void showResult(MoodleCourseContent[] content)
	{
		  for (int i=0; i<content.length; i++, System.out.println("*****************************"))
              printCourseContent(content[i]);
	}
	
	private static void printCourseContent(MoodleCourseContent moodleCourseContent) {
        System.out.println("id      ="+moodleCourseContent.getId());
        System.out.println("name    ="+moodleCourseContent.getName());
        System.out.println("visible ="+moodleCourseContent.getVisible());
        System.out.println("summary ="+moodleCourseContent.getSummary());
        if (moodleCourseContent.getMoodleModules()!=null)
            for (int i=0; i<moodleCourseContent.getMoodleModules().length; i++)
                printCourseModules(moodleCourseContent.getMoodleModules()[i]);
    }

    private static void printCourseModules(MoodleModule moodleModule) {
        System.out.println("Module: id             ="+moodleModule.getId());
        System.out.println("Module: url            ="+moodleModule.getUrl());
        System.out.println("Module: name           ="+moodleModule.getName());
        System.out.println("Module: description    ="+moodleModule.getDescription());
        System.out.println("Module: visible        ="+moodleModule.getVisible());
        System.out.println("Module: modicon        ="+moodleModule.getModIcon());
        System.out.println("Module: modname        ="+moodleModule.getModName());
        System.out.println("Module: modplural      ="+moodleModule.getModPlural());
        System.out.println("Module: availablefrom  ="+moodleModule.getAvailableFrom());
        System.out.println("Module: availableuntil ="+moodleModule.getAvailableUntil());
        System.out.println("Module: indent         ="+moodleModule.getIndent());
        MoodleModuleContent[] content = moodleModule.getContent();
        if (content!=null)
            for (int i=0; i<content.length; i++)
                printModuleContent(content[i]);
    }

    private static void printModuleContent(MoodleModuleContent moodleModuleContent) {
        System.out.println("Content: author       ="+moodleModuleContent.getAuthor());
        System.out.println("Content: content      ="+moodleModuleContent.getContent());
        System.out.println("Content: filepath     ="+moodleModuleContent.getFilePath());
        System.out.println("Content: fileurl      ="+moodleModuleContent.getFileURL());
        System.out.println("Content: filename     ="+moodleModuleContent.getFilename());
        System.out.println("Content: license      ="+moodleModuleContent.getLicense());
        System.out.println("Content: type         ="+moodleModuleContent.getType());
        System.out.println("Content: filesize     ="+moodleModuleContent.getFileSize());
        System.out.println("Content: sortorder    ="+moodleModuleContent.getSortOrder());
        System.out.println("Content: timecreated  ="+moodleModuleContent.getTimeCreated());
        System.out.println("Content: timemodified ="+moodleModuleContent.getTimeModified());
        System.out.println("Content: userid       ="+moodleModuleContent.getUserId());
    }


}
