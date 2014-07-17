package com.NewCleanWeather.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.NewCleanWeather.WeatherApplication;

import java.util.HashMap;

/**
 * Created by yrguo on 2014/7/15.
 */
public class BitmapUtil {
    public static HashMap<String,Bitmap> bitmapHashMap = new HashMap<String, Bitmap>();
    public static Bitmap getBitmapFromResources(int resID){
           if(bitmapHashMap.get("" + resID)!=null&&!bitmapHashMap.get("" + resID).isRecycled()){
               return bitmapHashMap.get(""+resID);
           }else{
               Bitmap bitmap = BitmapFactory.decodeResource(WeatherApplication.getInstance().getResources(),resID);
               bitmapHashMap.put("" + resID,bitmap);
               return bitmap;
           }
    }
}
