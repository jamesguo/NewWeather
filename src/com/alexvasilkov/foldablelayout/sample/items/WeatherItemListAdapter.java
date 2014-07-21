package com.alexvasilkov.foldablelayout.sample.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.NewCleanWeather.R;
import com.NewCleanWeather.model.*;
import com.NewCleanWeather.widget.WeatherListItemView;
import com.stylingandroid.blurring.BlurredTextView;

import java.util.ArrayList;
import java.util.Arrays;

public class WeatherItemListAdapter extends ItemsAdapter<WeatherListItemModel> implements View.OnClickListener {
    public WeatherItemUnfoldInterface unfoldInterface;

    public WeatherItemListAdapter(Context context) {
        super(context);
        ArrayList<WeatherListItemModel> arrayList = new ArrayList<WeatherListItemModel>();
        for (int index = 0; index < 19; index++) {
            WeatherListItemModel itemModel = new WeatherListItemModel();
            itemModel.mWeatherLocalModel = new WeatherLocalModel();
            itemModel.mWeatherLocalModel.localName = "place:" + index;

            itemModel.mWeatherWindModel = new WeatherWindModel();
            itemModel.mWeatherWindModel.wind_unit = "KM/H";
            itemModel.mWeatherWindModel.wind_speed = "30";
            itemModel.mWeatherWindModel.wind_direction = 270;

            itemModel.mWeatherInfoModel = new WeatherInfoModel();
            itemModel.mWeatherInfoModel.weather_uv_index = 2;
            itemModel.mWeatherInfoModel.weatherCode = 30;
            itemModel.mWeatherInfoModel.weatherText = "Rain";


            itemModel.mWeatherRainModel = new WeatherRainModel();
            itemModel.mWeatherRainModel.rain_rate = "90%";
            itemModel.mWeatherRainModel.humidity = "90%";

            itemModel.mWeatherPressureModel = new WeatherPressureModel();
            itemModel.mWeatherPressureModel.pressure = "60.8";
            itemModel.mWeatherPressureModel.pressure_unit = "in";
            itemModel.mWeatherTempModel = new WeatherTempModel();
            itemModel.mWeatherTempModel.currentTemp_c = 21 * (index + 1);
            itemModel.mWeatherTempModel.lowTemp_c = 16;
            itemModel.mWeatherTempModel.highTemp_c = 35;
            itemModel.mWeatherTempModel.feellike_c = 21 * (index + 1);
            itemModel.mWeatherTempModel.feellike_f = 35;
            itemModel.mWeatherTempModel.dew_point_c = 21 * (index + 1);
            itemModel.mWeatherTempModel.dew_point_f = 35;

            WeatherForcastModel weatherForcastModel = new WeatherForcastModel();
            weatherForcastModel.mWeatherInfoModel = itemModel.mWeatherInfoModel;
            weatherForcastModel.mWeatherTempModel = itemModel.mWeatherTempModel;
            weatherForcastModel.mWeatherRainModel = itemModel.mWeatherRainModel;
            itemModel.weatherForcastModels.add(weatherForcastModel);
            weatherForcastModel = new WeatherForcastModel();
            weatherForcastModel.mWeatherInfoModel = itemModel.mWeatherInfoModel;
            weatherForcastModel.mWeatherTempModel = itemModel.mWeatherTempModel;
            weatherForcastModel.mWeatherRainModel = itemModel.mWeatherRainModel;
            itemModel.weatherForcastModels.add(weatherForcastModel);
            weatherForcastModel = new WeatherForcastModel();
            weatherForcastModel.mWeatherInfoModel = itemModel.mWeatherInfoModel;
            weatherForcastModel.mWeatherTempModel = itemModel.mWeatherTempModel;
            weatherForcastModel.mWeatherRainModel = itemModel.mWeatherRainModel;
            itemModel.weatherForcastModels.add(weatherForcastModel);


            arrayList.add(itemModel);
        }
        setItemsList(arrayList);
    }

    @Override
    protected View createView(WeatherListItemModel item, int pos, ViewGroup parent, LayoutInflater inflater) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_list_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.itemView = Views.find(view, R.id.subInfo);
        vh.image = Views.find(view, R.id.update);
        vh.itemView.setOnClickListener(this);
        vh.blurredTextView = Views.find(view, R.id.text);
        view.setTag(vh);

        return view;
    }

    @Override
    protected void bindView(WeatherListItemModel item, int pos, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.itemView.setTag(item);
        vh.itemView.setmWeatherListItemModel(item);
//        vh.image.setBackground(convertView.getContext().getResources().getDrawable(item.getImageId()));
//        vh.title.setText(item.getTitle());
    }


    @Override
    public void onClick(View view) {
        if (unfoldInterface != null) {
            unfoldInterface.openDetails(view, (WeatherListItemModel) view.getTag());
        } else {
            if (view.getContext() instanceof WeatherItemUnfoldInterface) {
                WeatherItemUnfoldInterface activity = (WeatherItemUnfoldInterface) view.getContext();
                activity.openDetails(view, (WeatherListItemModel) view.getTag());
            }
        }
    }

    private static class ViewHolder {
        ImageView image;
        WeatherListItemView itemView;
        BlurredTextView blurredTextView;
    }

    public interface WeatherItemUnfoldInterface {
        public void openDetails(View view, WeatherListItemModel painting);
    }
}
