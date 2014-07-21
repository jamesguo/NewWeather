package com.NewCleanWeather.util;

import android.graphics.Typeface;
import com.NewCleanWeather.WeatherApplication;

/**
 * Created by yrguo on 2014/7/18.
 */
public class ResourceStringUtil {
    public static String getString(int resID) {
        return WeatherApplication.getInstance().getResources().getString(resID);
    }
}
