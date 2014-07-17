package com.NewCleanWeather.model;

import android.util.Log;
import com.NewCleanWeather.util.UserSettingPerferenceInfo;
import java.lang.System;
/**
 * Created by yrguo on 2014/7/14.
 */
public class WeatherTempModel {
    public float currentTemp_f = 0.0f;
    public float currentTemp_c = 0.0f;
    public float highTemp_f = 0.0f;
    public float highTemp_c = 0.0f;
    public float lowTemp_f = 0.0f;
    public float lowTemp_c = 0.0f;

    public String getTemp_unit() {
        return UserSettingPerferenceInfo.getTempUtil();
    }

    public String getCurrentTemp() {
        if(UserSettingPerferenceInfo.getTempUtil().equals("°„C")){
            return ""+(int)currentTemp_c;
        }else{
            return ""+(int)currentTemp_f;
        }
    }

    public String getHighTemp() {
        if(UserSettingPerferenceInfo.getTempUtil().equals("°„C")){
            return ""+(int)highTemp_c;
        }else{
            return ""+(int)highTemp_f;
        }
    }

    public String getLowTemp() {
        if(UserSettingPerferenceInfo.getTempUtil().equals("°„C")){
            return ""+(int)lowTemp_c;
        }else{
            return ""+(int)lowTemp_f;
        }
    }
}
