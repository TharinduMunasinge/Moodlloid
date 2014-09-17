package com.tharindu.moodlloid.model;

import android.graphics.Bitmap;

import com.tharindu.moodlloid.rest.MoodleCourseContent;
import com.tharindu.moodlloid.rest.MoodleModule;

public class ModuleContent {

		private String Name;
	    private String ImageUrl;
	    private MoodleModule module;
	    
	    private Bitmap image;
	    
	    public void setImage(Bitmap image)
	    {
	    	this.image=image;
	    }
	    
	    public Bitmap getImage()
	    {
	    	return image;
	    }
	   
	    
	    
	    
	    public MoodleModule getModule() {
			return module;
		}

		public void setModule(MoodleModule module) {
			this.module = module;
		}

		public String getName() {
	        return Name;
	    }

	    public void setName(String Name) {
	        this.Name = Name;
	    }

	    public String getImageURl() {
	        return ImageUrl;
	    }

	    public void setImage(String ImageUrl) {
	        this.ImageUrl = ImageUrl;
	    }
}
