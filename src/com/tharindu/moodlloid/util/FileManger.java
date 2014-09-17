package com.tharindu.moodlloid.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class FileManger {

	public static void openFile(Context con,String ExternalStroagepath,String Absolutepath , String fileFormat)
	{
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		File file = new File(ExternalStroagepath,Absolutepath);
		//intent.setDataAndType(Uri.fromFile(file), File.)
		
		
		if(fileFormat.equals("video"))
		{
			intent.setDataAndType(Uri.fromFile(file), "video/*");

		}else if(fileFormat.equals("audio"))
		{
			intent.setDataAndType(Uri.fromFile(file), "audio/*");
			
			
		}
		else if(fileFormat.equals("html"))
		{
			intent.setDataAndType(Uri.fromFile(file), "text/html");
			
				
		}
		else if(fileFormat.equals("text"))
		{
			intent.setDataAndType(Uri.fromFile(file), "text/plain");
			
				
		}
		else if(fileFormat.equals("pdf"))
		{
			intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		}
		else if(fileFormat.equals("image"))
		{
			intent.setDataAndType(Uri.fromFile(file), "image/*");
		}
		else
		{
			//UIHelper.showToast(,sg)
		}
		con.startActivity(intent); 
		
	}
	
	public static String getFileType(String filename)
	{
		String FileTypetoBeOpened="";
		if(filename.endsWith(".txt"))
		{
			FileTypetoBeOpened="text";
				
		}
		else if(filename.endsWith(".pdf"))
		{
			FileTypetoBeOpened="pdf";
		}
		else if(filename.endsWith(".html")|| filename.endsWith(".htm"))
		{
			FileTypetoBeOpened="html";
		}
		else if(filename.endsWith(".jpg") || filename.endsWith(".jpeg") ||  filename.endsWith(".png"))
		{
			FileTypetoBeOpened="image";
		}
		else if (filename.endsWith(".mp3"))
		{
			FileTypetoBeOpened="audio";
		}
		else if (filename.endsWith(".mp4") || filename.endsWith(".flv"))
		{
			FileTypetoBeOpened="vedio";
		}
		else
		{
			FileTypetoBeOpened="unknown";
		}
		
		return FileTypetoBeOpened;
	}
}
