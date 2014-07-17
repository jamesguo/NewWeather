package com.NewCleanWeather.model;

import java.util.ArrayList;

/**
 * Created by yrguo on 2014/7/14.
 */
public class WeatherListItemModel {
    public WeatherLocalModel mWeatherLocalModel;
    public WeatherInfoModel mWeatherInfoModel;
    public WeatherTempModel mWeatherTempModel;
    public WeatherWindModel mWeatherWindModel;
    public WeatherPressureModel mWeatherPressureModel;
    public WeatherRainModel mWeatherRainModel;
    public ArrayList<WeatherForcastModel> weatherForcastModels = new ArrayList<WeatherForcastModel>();
}
