package com.tharindu.moodlloid;



/********************************************
 * Row item of Drawer UI component
 * @author tharindu
 ********************************************/
public class RowItem {
		 //To reprsent an Item in list
	     private String title;
	     private int icon;

	     /**
	     * @param title
	     * @param icon
	     */
	    public RowItem(String title, int icon) {
	             this.title = title;
	            this.icon = icon;

	        }

	     
	     /**
	      * getter for title
	      * @return
	     */
	    public String getTitle() {
	           return title;
	       }

	     /**
	      * setter for title
	     * @param title
	     */
	    public void setTitle(String title) {
	          this.title = title;
	     }

	     /**
	      * getter for Icon
	     * @return
	     */
	    public int getIcon() {
	         return icon;
	   }

	     /**
	      * setter for the icon
	     * @param icon
	     */
	    public void setIcon(int icon) {
	      this.icon = icon;
	    }

}
