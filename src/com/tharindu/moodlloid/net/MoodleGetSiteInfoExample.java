package com.tharindu.moodlloid.net;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tharindu.moodlloid.rest.*;

/**
 *
 * @author bill
 */
public class MoodleGetSiteInfoExample {

  /**
   * @param args the command line arguments
   */
  public MoodleWebService getSiteInfo() {
    try {
      MoodleCallRestWebService.init("http://192.168.1.100/projects/moodle/webservice/rest/server.php", "0007280157587e196bf672c1ca3358e3");
      MoodleWebService siteInfo = MoodleRestWebService.getSiteInfo();
 //     MoodleCourse courses=new MoodleCourse();
     return siteInfo;
      //printSiteInfo(siteInfo);
    } catch (MoodleRestWebServiceException ex) {
     // Logger.getLogger(MoodleGetSiteInfoExample.class.getName()).log(Level.SEVERE, null, ex);
    } catch (MoodleRestException ex) {
     // Logger.getLogger(MoodleGetSiteInfoExample.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  private static void printSiteInfo(MoodleWebService siteInfo) {
    System.out.println("sitename      ="+siteInfo.getSiteName());
    System.out.println("username      ="+siteInfo.getUserName());
    System.out.println("firstname     ="+siteInfo.getFirstName());
    System.out.println("lastname      ="+siteInfo.getLastName());
    System.out.println("userid        ="+siteInfo.getUserId());
    System.out.println("siteurl       ="+siteInfo.getSiteURL());
    System.out.println("userpictureurl="+siteInfo.getUserPictureURL());
    System.out.println("downloadfiles ="+siteInfo.canDownloadFiles());
    ArrayList<Function> functions = siteInfo.getFunctions();
    for (int i=0; i<functions.size(); i++)
      System.out.println("Functions: "+functions.get(i).getName()+" Version: "+functions.get(i).getVersion());
    
  }
}
