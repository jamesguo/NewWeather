package com.NewCleanWeather.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.NewCleanWeather.WeatherApplication;

/**
 * Created by yrguo on 2014/7/14.
 */
public class UserSettingPerferenceInfo {
    public static SharedPreferences setting;
    public static String getTempUtil(){
        if(setting==null){
            setting = PreferenceManager.getDefaultSharedPreferences(WeatherApplication.getInstance());
        }
        String unit = setting.getString("temp_unit","0");
        if(unit.equals("0")){
            return "°„C";
        }else{
            return "°„F";
        }
    }
}
