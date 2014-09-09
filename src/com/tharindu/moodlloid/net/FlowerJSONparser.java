package com.tharindu.moodlloid.net;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;


//import com.hanselandpetal.catalog.model.Flower;

public class FlowerJSONparser {
	
//	public static List<Flower> parseFeed(String content) {
//		
//		try {
//			JSONArray array=new JSONArray(content);
//			List<Flower> flowerList=new ArrayList<>();
//			
//			for(int i=0;i<array.length();i++)
//			{
//				JSONObject obj=array.getJSONObject(i);
//				Flower flower=new Flower();
//				flower.setProductId(obj.getInt("productId"));
//				flower.setInstructions(obj.getString("name"));
//
//				flower.setInstructions(obj.getString("instructions"));
//
//				flower.setPhoto(obj.getString("photo"));
//
//				flower.setPrice(obj.getDouble("price"));
//
//				flower.setCategory(obj.getString("category"));
//
//				
//			}
//			Log.d("Flower Json", "done");
//			
//			return flowerList;
//			
//			
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null; 
//		}
//		
//		
//		
//
//	}
}
