package com.NewCleanWeather.util;

import android.util.TypedValue;
import com.NewCleanWeather.WeatherApplication;

/**
 * Created by yrguo on 2014/7/14.
 */
public class DeviceInfoUtil {
    public static int windowWidth = 0;
    public static int windowHeight = 0;
    public static int actionBarHeight = 0;

    public static int getActionBarHeight() {
        if (actionBarHeight <= 0) {
            TypedValue tv = new TypedValue();
            if (WeatherApplication.getInstance().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, WeatherApplication.getInstance().getResources().getDisplayMetrics());
            }
        }
        return actionBarHeight;
    }

    public static int getWindowWidth() {
        if (windowWidth <= 0) {
            windowWidth = WeatherApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
        }
        return windowWidth;
    }

    public static int getWindowHeight() {
        if (windowHeight <= 0) {
            windowHeight = WeatherApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
        }
        return windowHeight;
    }

    public static int dip2px(float dipValue) {
        final float scale = WeatherApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = WeatherApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
