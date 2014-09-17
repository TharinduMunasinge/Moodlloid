package com.tharindu.moodlloid.model;

import android.graphics.Bitmap;

import com.tharindu.moodlloid.rest.MoodleModule;


/*********************************************************************************
 * This Class will represent the Child view for Course Content Expandable view
 * @author tharindu
 **********************************************************************************/

public class ModuleContent {
		
		//name of the current course module ITme
		private String Name;
		//Url to the icon
	    private String ImageUrl;
	    //associated module object
	    private MoodleModule module;
	    //image as an object 
	    private Bitmap image;
	    
	    
	    
	    /**
	     * setter for the Image
	     * @param image
	     */
	    public void setImage(Bitmap image)
	    {
	    	this.image=image;
	    }
	    

	    /**
	     * getter for the image
	     * @return
	     */
	    public Bitmap getImage()
	    {
	    	return image;
	    }
	
	    
	    /**
	     * getter for the MOdule object
	     * @return
	     */
	    public MoodleModule getModule() {
			return module;
		}

	    
		/**
		 * setter for associated moduel object
		 * @param module
		 */
		public void setModule(MoodleModule module) {
			this.module = module;
		}
		

		/**
		 * getter for the Name of the current child View
		 * @return
		 */
		public String getName() {
	        return Name;
	    }

	    /**
	     * Setter for the current Child veiw
	     * @param Name
	     */
	    public void setName(String Name) {
	        this.Name = Name;
	    }

	    
	    
	    /**
	     * getter for the image URL
	     * @return
	     */
	    public String getImageURl() {
	        return ImageUrl;
	    }

	    
	    /**
	     * Setter for the ImageURL
	     * @param ImageUrl
	     */
	    public void setImage(String ImageUrl) {
	        this.ImageUrl = ImageUrl;
	    }
}
