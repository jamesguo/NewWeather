package com.NewCleanWeather.util;

import com.NewCleanWeather.WeatherApplication;

import java.util.HashMap;

/**
 * Created by yrguo on 2014/7/16.
 */
public class ColorUtil {
    public static HashMap<String, Integer> colorMap = new HashMap<String, Integer>();

    public static Integer getColor(int resID) {
        if (colorMap.get("" + resID) != null) {
            return colorMap.get("" + resID);
        } else {
            int color = WeatherApplication.getInstance().getResources().getColor(resID);
            colorMap.put("" + resID, color);
            return color;
        }
    }
}
