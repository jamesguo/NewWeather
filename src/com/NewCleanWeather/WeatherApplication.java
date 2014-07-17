package com.NewCleanWeather;

import android.app.Application;
import android.preference.PreferenceManager;
import com.NewCleanWeather.util.UserSettingPerferenceInfo;

/**
 * Created by yrguo on 2014/7/7.
 */
public class WeatherApplication extends Application {
    private static WeatherApplication mWeatherApplication;

    public static WeatherApplication getInstance() {
        return mWeatherApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWeatherApplication = this;
        UserSettingPerferenceInfo.setting = PreferenceManager.getDefaultSharedPreferences(WeatherApplication.getInstance());
    }
}
