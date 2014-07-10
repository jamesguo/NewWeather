package com.NewCleanWeather.util;

import android.util.TypedValue;
import com.NewCleanWeather.WeatherApplication;

/**
 * Created by yrguo on 2014/7/10.
 */
public class SystemUtil {
    public static int getActionBarHeight(){
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (WeatherApplication.getInstance().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,WeatherApplication.getInstance().getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }
}
