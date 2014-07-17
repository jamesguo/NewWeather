package com.NewCleanWeather.util;

import com.NewCleanWeather.WeatherApplication;

import java.util.HashMap;

/**
 * Created by yrguo on 2014/7/16.
 */
public class DimenUtil {
    public static HashMap<String, Integer> dimenMap = new HashMap<String, Integer>();

    public static Integer getDimensionPixelSize(int resID) {
        if (dimenMap.get("" + resID) != null) {
            return dimenMap.get("" + resID);
        } else {
            int px = WeatherApplication.getInstance().getResources().getDimensionPixelSize(resID);
            dimenMap.put("" + resID, px);
            return px;
        }
    }
}
