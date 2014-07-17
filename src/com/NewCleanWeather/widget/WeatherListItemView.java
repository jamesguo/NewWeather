package com.NewCleanWeather.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.*;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import com.NewCleanWeather.R;
import com.NewCleanWeather.WeatherApplication;
import com.NewCleanWeather.model.WeatherListItemModel;
import com.NewCleanWeather.util.*;

/**
 * Created by yrguo on 2014/7/14.
 */
public class WeatherListItemView extends View {
    public WeatherListItemModel mWeatherListItemModel;

    public WeatherListItemView(Context context) {
        super(context);
    }

    public WeatherListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        initPositionsAndSizes();
    }


    private void initPositionsAndSizes() {
        if (WeatherListItemViewHelper.Roboto_Condensed == null) {
            int windowWidth = DeviceInfoUtil.getWindowWidth();
            WeatherListItemViewHelper.Roboto_Condensed = TypefaceUtil.createFromAsset("Roboto-Condensed.ttf");
            WeatherListItemViewHelper.Roboto_Regular = TypefaceUtil.createFromAsset("Roboto-Regular.ttf");
            WeatherListItemViewHelper.Roboto_Thin = TypefaceUtil.createFromAsset("Roboto-Thin.ttf");
            ColorUtil.getColor(R.color.vpi__background_holo_dark);
            WeatherListItemViewHelper.defaultMargin = DimenUtil.getDimensionPixelSize(R.dimen.margin_medium);
            WeatherListItemViewHelper.defaultMarginTiny = DimenUtil.getDimensionPixelSize(R.dimen.margin_tiny);
            WeatherListItemViewHelper.itemWith = windowWidth - 2 * DimenUtil.getDimensionPixelSize(R.dimen.horizontal_padding_large);
            WeatherListItemViewHelper.itemHeight = (int) (0.4f * WeatherListItemViewHelper.itemWith);

            WeatherListItemViewHelper.otherInfoTextSize = (int) (WeatherListItemViewHelper.itemHeight * 0.15f);

            WeatherListItemViewHelper.otherTextPaint = new Paint();
            WeatherListItemViewHelper.otherTextPaint.setAntiAlias(true);
            WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.CENTER);
            WeatherListItemViewHelper.otherTextPaint.setTypeface(Typeface.DEFAULT);

            WeatherListItemViewHelper.otherPaint = new Paint();
            WeatherListItemViewHelper.otherPaint.setAntiAlias(true);

            Bitmap downBitmap = BitmapUtil.getBitmapFromResources(R.drawable.temp_low);
            Bitmap upBitmap = BitmapUtil.getBitmapFromResources(R.drawable.temp_high);

            WeatherListItemViewHelper.lowTempOffset_Y = (int) (WeatherListItemViewHelper.itemWith * 0.05f + downBitmap.getWidth() * 2);
            WeatherListItemViewHelper.highTempOffset_Y = (int) (WeatherListItemViewHelper.itemWith * 0.25f + downBitmap.getWidth() * 2);
            WeatherListItemViewHelper.otherTempTextSize = downBitmap.getHeight() * getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_HIGH;
            WeatherListItemViewHelper.bitmapSrcRect = new Rect();
            WeatherListItemViewHelper.bitmapSrcRect.set(0, 0, downBitmap.getWidth(), downBitmap.getHeight());
            if (WeatherListItemViewHelper.lowTempRect == null) {
                WeatherListItemViewHelper.lowTempRect = new Rect();
                WeatherListItemViewHelper.lowTempRect.set((int) (WeatherListItemViewHelper.itemWith * 0.05f), WeatherListItemViewHelper.itemHeight - WeatherListItemViewHelper.defaultMarginTiny - downBitmap.getHeight(), (int) (WeatherListItemViewHelper.itemWith * 0.05f + downBitmap.getWidth()), WeatherListItemViewHelper.itemHeight - WeatherListItemViewHelper.defaultMarginTiny);
            }
            if (WeatherListItemViewHelper.highTempRect == null) {
                WeatherListItemViewHelper.highTempRect = new Rect();
                WeatherListItemViewHelper.highTempRect.set((int) (WeatherListItemViewHelper.itemWith * 0.25f), WeatherListItemViewHelper.itemHeight - WeatherListItemViewHelper.defaultMarginTiny - downBitmap.getHeight(), (int) (WeatherListItemViewHelper.itemWith * 0.25f + downBitmap.getWidth()), WeatherListItemViewHelper.itemHeight - WeatherListItemViewHelper.defaultMarginTiny);
            }

            WeatherListItemViewHelper.currentTempTextSize = (int) (WeatherListItemViewHelper.itemHeight * 0.7f);
            WeatherListItemViewHelper.currentTempPaint = new Paint();
            WeatherListItemViewHelper.currentTempPaint.setAntiAlias(true);
            WeatherListItemViewHelper.currentTempPaint.setTextAlign(Paint.Align.RIGHT);
            WeatherListItemViewHelper.currentTempPaint.setTypeface(WeatherListItemViewHelper.Roboto_Thin);
            WeatherListItemViewHelper.currentTempPaint.setColor(ColorUtil.getColor(R.color.text_color_solid_orange));
            WeatherListItemViewHelper.currentTempPaint.setTextSize(WeatherListItemViewHelper.currentTempTextSize);
            WeatherListItemViewHelper.currentTempOffset_X = (int) (1.1f * WeatherListItemViewHelper.itemHeight);
            WeatherListItemViewHelper.currentTempOffset_Y = (int) (0.7f * WeatherListItemViewHelper.itemHeight);

            WeatherListItemViewHelper.currentTempTextSize_long = (int) (WeatherListItemViewHelper.itemHeight * 0.5f);
            WeatherListItemViewHelper.currentTempPaint_long = new Paint();
            WeatherListItemViewHelper.currentTempPaint_long.setAntiAlias(true);
            WeatherListItemViewHelper.currentTempPaint_long.setTextAlign(Paint.Align.RIGHT);
            WeatherListItemViewHelper.currentTempPaint_long.setTypeface(WeatherListItemViewHelper.Roboto_Thin);
            WeatherListItemViewHelper.currentTempPaint_long.setColor(ColorUtil.getColor(R.color.text_color_solid_orange));
            WeatherListItemViewHelper.currentTempPaint_long.setTextSize(WeatherListItemViewHelper.currentTempTextSize_long);
            WeatherListItemViewHelper.currentTempOffset_X_long = (int) (1.1f * WeatherListItemViewHelper.itemHeight);
            WeatherListItemViewHelper.currentTempOffset_Y_long = (int) (0.7f * WeatherListItemViewHelper.itemHeight);

//        WeatherListItemViewHelper.lowTempRect = new Rect();
//        WeatherListItemViewHelper.lowTempRect.set((int) (WeatherListItemViewHelper.itemWith * 0.1f), (int) (WeatherListItemViewHelper.itemHeight * 0.8f), (int) (WeatherListItemViewHelper.itemWith * 0.1f), (int) (WeatherListItemViewHelper.itemHeight * 0.85f));
//
//        WeatherListItemViewHelper.highTempRect = new Rect();
//        WeatherListItemViewHelper.highTempRect.set((int) (WeatherListItemViewHelper.itemWith * 0.15f), (int) (WeatherListItemViewHelper.itemHeight * 0.8f), (int) (WeatherListItemViewHelper.itemWith * 0.28f + WeatherListItemViewHelper.itemHeight * 0.02f), (int) (WeatherListItemViewHelper.itemHeight * 0.85f));

        }
    }

    public WeatherListItemModel getmWeatherListItemModel() {
        return mWeatherListItemModel;
    }

    public void setmWeatherListItemModel(WeatherListItemModel mWeatherListItemModel) {
        this.mWeatherListItemModel = mWeatherListItemModel;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(WeatherListItemViewHelper.itemWith, WeatherListItemViewHelper.itemHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        WeatherListItemViewHelper.otherPaint.setColor(ColorUtil.getColor(R.color.text_color_black));
        WeatherListItemViewHelper.otherPaint.setStrokeWidth(DimenUtil.getDimensionPixelSize(R.dimen.avatar_corner_radius_large));
        canvas.drawLine(0.45f * WeatherListItemViewHelper.itemWith, 0.2f * WeatherListItemViewHelper.itemHeight, 0.45f * WeatherListItemViewHelper.itemWith, 0.8f * WeatherListItemViewHelper.itemHeight, WeatherListItemViewHelper.otherPaint);
        if (mWeatherListItemModel != null) {
            canvas.save();
            if (mWeatherListItemModel.mWeatherTempModel != null) {
                String temp = mWeatherListItemModel.mWeatherTempModel.getCurrentTemp() + "бу";
                if (temp.length() > 3) {
                    canvas.drawText(temp, WeatherListItemViewHelper.currentTempOffset_X_long, WeatherListItemViewHelper.currentTempOffset_Y_long, WeatherListItemViewHelper.currentTempPaint_long);
                } else {
                    canvas.drawText(temp, WeatherListItemViewHelper.currentTempOffset_X, WeatherListItemViewHelper.currentTempOffset_Y, WeatherListItemViewHelper.currentTempPaint);
                }
                Bitmap downBitmap = BitmapUtil.getBitmapFromResources(R.drawable.temp_low);
                Bitmap upBitmap = BitmapUtil.getBitmapFromResources(R.drawable.temp_high);
                canvas.drawBitmap(downBitmap, WeatherListItemViewHelper.bitmapSrcRect, WeatherListItemViewHelper.lowTempRect, WeatherListItemViewHelper.otherPaint);
                canvas.drawBitmap(upBitmap, WeatherListItemViewHelper.bitmapSrcRect, WeatherListItemViewHelper.highTempRect, WeatherListItemViewHelper.otherPaint);
                WeatherListItemViewHelper.otherTextPaint.setTextSize(WeatherListItemViewHelper.otherTempTextSize);
                WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherListItemViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.vpi__background_holo_dark));
                WeatherListItemViewHelper.otherTextPaint.setTypeface(WeatherListItemViewHelper.Roboto_Condensed);
                canvas.drawText(mWeatherListItemModel.mWeatherTempModel.getLowTemp(),WeatherListItemViewHelper.lowTempOffset_Y , WeatherListItemViewHelper.itemHeight - WeatherListItemViewHelper.defaultMarginTiny, WeatherListItemViewHelper.otherTextPaint);
                canvas.drawText(mWeatherListItemModel.mWeatherTempModel.getHighTemp(), WeatherListItemViewHelper.highTempOffset_Y, WeatherListItemViewHelper.itemHeight - WeatherListItemViewHelper.defaultMarginTiny, WeatherListItemViewHelper.otherTextPaint);
            }

            if (mWeatherListItemModel.mWeatherWindModel != null) {
                WeatherListItemViewHelper.otherTextPaint.setTextSize(WeatherListItemViewHelper.otherInfoTextSize * 0.8f);
                WeatherListItemViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherListItemViewHelper.otherTextPaint.setTypeface(WeatherListItemViewHelper.Roboto_Condensed);
                WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                String indexStr = getResources().getText(R.string.wind).toString();
                canvas.drawText(indexStr, (int) (WeatherListItemViewHelper.itemWith * 0.47f), 0.35f * WeatherListItemViewHelper.itemHeight, WeatherListItemViewHelper.otherTextPaint);

                WeatherListItemViewHelper.otherTextPaint.setTextSize(WeatherListItemViewHelper.otherInfoTextSize * 0.8f);
                WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherListItemViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_blue_light));
                WeatherListItemViewHelper.otherTextPaint.setTypeface(WeatherListItemViewHelper.Roboto_Regular);
                String text = mWeatherListItemModel.mWeatherWindModel.wind_speed + mWeatherListItemModel.mWeatherWindModel.wind_unit + " " + mWeatherListItemModel.mWeatherWindModel.wind_direction;
                canvas.drawText(text, (int) (WeatherListItemViewHelper.itemWith * 0.72f), 0.35f * WeatherListItemViewHelper.itemHeight, WeatherListItemViewHelper.otherTextPaint);
            }

            if (mWeatherListItemModel.mWeatherRainModel != null) {
                WeatherListItemViewHelper.otherTextPaint.setTextSize(WeatherListItemViewHelper.otherInfoTextSize * 0.8f);
                WeatherListItemViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherListItemViewHelper.otherTextPaint.setTypeface(WeatherListItemViewHelper.Roboto_Condensed);
                WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                String indexStr = getResources().getText(R.string.humidity).toString();
                canvas.drawText(indexStr, (int) (WeatherListItemViewHelper.itemWith * 0.47f), 0.55f * WeatherListItemViewHelper.itemHeight, WeatherListItemViewHelper.otherTextPaint);

                WeatherListItemViewHelper.otherTextPaint.setTextSize(WeatherListItemViewHelper.otherInfoTextSize * 0.8f);
                WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherListItemViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_blue_light));
                WeatherListItemViewHelper.otherTextPaint.setTypeface(WeatherListItemViewHelper.Roboto_Regular);
                canvas.drawText(mWeatherListItemModel.mWeatherRainModel.rain_rate, (int) (WeatherListItemViewHelper.itemWith * 0.72f), 0.55f * WeatherListItemViewHelper.itemHeight, WeatherListItemViewHelper.otherTextPaint);
            }
            if (mWeatherListItemModel.mWeatherInfoModel != null) {
                WeatherListItemViewHelper.otherTextPaint.setTextSize(WeatherListItemViewHelper.otherInfoTextSize * 0.8f);
                WeatherListItemViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherListItemViewHelper.otherTextPaint.setTypeface(WeatherListItemViewHelper.Roboto_Condensed);
                WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                String indexStr = getResources().getText(R.string.uv_index).toString();
                canvas.drawText(indexStr, (int) (WeatherListItemViewHelper.itemWith * 0.47f), 0.75f * WeatherListItemViewHelper.itemHeight, WeatherListItemViewHelper.otherTextPaint);

                WeatherListItemViewHelper.otherTextPaint.setTextSize(WeatherListItemViewHelper.otherInfoTextSize * 0.8f);
                WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherListItemViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_blue_light));
                WeatherListItemViewHelper.otherTextPaint.setTypeface(WeatherListItemViewHelper.Roboto_Regular);
                canvas.drawText("" + mWeatherListItemModel.mWeatherInfoModel.weather_uv_index, (int) (WeatherListItemViewHelper.itemWith * 0.72f), 0.75f * WeatherListItemViewHelper.itemHeight, WeatherListItemViewHelper.otherTextPaint);

                WeatherListItemViewHelper.otherTextPaint.setTextSize(WeatherListItemViewHelper.otherInfoTextSize);
                WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherListItemViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_black));
                WeatherListItemViewHelper.otherTextPaint.setTypeface(WeatherListItemViewHelper.Roboto_Condensed);
                canvas.drawText(mWeatherListItemModel.mWeatherInfoModel.weatherText, WeatherListItemViewHelper.defaultMargin, 0.15f * WeatherListItemViewHelper.itemHeight, WeatherListItemViewHelper.otherTextPaint);
            }
            if (mWeatherListItemModel.mWeatherLocalModel != null) {
                WeatherListItemViewHelper.otherTextPaint.setTextAlign(Paint.Align.RIGHT);
                WeatherListItemViewHelper.otherTextPaint.setTypeface(WeatherListItemViewHelper.Roboto_Condensed);
                WeatherListItemViewHelper.otherTextPaint.setTextSize(WeatherListItemViewHelper.otherInfoTextSize);
                WeatherListItemViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_black));
                canvas.drawText(mWeatherListItemModel.mWeatherLocalModel.localName, WeatherListItemViewHelper.itemWith - WeatherListItemViewHelper.defaultMargin, WeatherListItemViewHelper.otherInfoTextSize, WeatherListItemViewHelper.otherTextPaint);
            }
            canvas.restore();
        }

    }

}
