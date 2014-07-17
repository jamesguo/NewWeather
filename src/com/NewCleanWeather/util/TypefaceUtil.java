package com.NewCleanWeather.util;

import android.graphics.Typeface;
import com.NewCleanWeather.WeatherApplication;

import java.util.HashMap;

/**
 * Created by yrguo on 2014/7/16.
 */
public class TypefaceUtil {
    public static HashMap<String, Typeface> colorMap = new HashMap<String, Typeface>();

    public static Typeface createFromAsset(String name) {
        if (colorMap.get(name) != null) {
            return colorMap.get(name);
        } else {
            Typeface typeface = Typeface.createFromAsset(WeatherApplication.getInstance().getAssets(), name);
            colorMap.put(name, typeface);
            return typeface;
        }
    }
}
