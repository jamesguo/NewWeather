package com.NewCleanWeather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.NewCleanWeather.R;
import com.NewCleanWeather.model.*;
import com.NewCleanWeather.util.*;

/**
 * Created by yrguo on 2014/7/16.
 */
public class WeatherDetailView extends View {
    public WeatherListItemModel mWeatherListItemModel;

    public WeatherDetailView(Context context) {
        super(context);
    }

    public WeatherDetailView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        initPositionsAndSizes();
    }

    private void initPositionsAndSizes() {
        if (WeatherDetailViewHelper.currentTempPaint == null) {
            int windowWidth = DeviceInfoUtil.getWindowWidth();
            int windowHeight = DeviceInfoUtil.getWindowHeight();
            WeatherDetailViewHelper.defaultMargin = DimenUtil.getDimensionPixelSize(R.dimen.margin_medium);
            WeatherDetailViewHelper.defaultMarginTiny = DimenUtil.getDimensionPixelSize(R.dimen.margin_tiny);
            WeatherDetailViewHelper.itemWith = windowWidth;
            WeatherDetailViewHelper.itemHeight = windowHeight - DimenUtil.getDimensionPixelSize(R.dimen.default_itemHeight);
            WeatherDetailViewHelper.usefaulItemHeight = WeatherDetailViewHelper.itemHeight - DeviceInfoUtil.getActionBarHeight();

            WeatherDetailViewHelper.otherInfoTextSize = (int) (WeatherDetailViewHelper.itemWith * 0.15f);
            WeatherDetailViewHelper.currentTempPaint = new Paint();

            WeatherDetailViewHelper.Roboto_Condensed = TypefaceUtil.createFromAsset("Roboto-Condensed.ttf");
            WeatherDetailViewHelper.Roboto_Regular = TypefaceUtil.createFromAsset("Roboto-Regular.ttf");
            WeatherDetailViewHelper.Roboto_Thin = TypefaceUtil.createFromAsset("Roboto-Thin.ttf");
            WeatherDetailViewHelper.Roboto_Light = TypefaceUtil.createFromAsset("Roboto-Light.ttf");
            WeatherDetailViewHelper.Roboto_Medium = TypefaceUtil.createFromAsset("Roboto-Medium.ttf");


            WeatherDetailViewHelper.currentTempTextSize = (int) (WeatherDetailViewHelper.itemWith * 0.38f);
            WeatherDetailViewHelper.currentTempPaint = new Paint();
            WeatherDetailViewHelper.currentTempPaint.setAntiAlias(true);
            WeatherDetailViewHelper.currentTempPaint.setTextAlign(Paint.Align.LEFT);
            WeatherDetailViewHelper.currentTempPaint.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
            WeatherDetailViewHelper.currentTempPaint.setColor(ColorUtil.getColor(R.color.text_color_solid_orange));
            WeatherDetailViewHelper.currentTempPaint.setTextSize(WeatherDetailViewHelper.currentTempTextSize);
            WeatherDetailViewHelper.currentTempOffset_X = WeatherDetailViewHelper.defaultMargin * 2;
            WeatherDetailViewHelper.currentTempOffset_Y = (int) (0.32f * WeatherDetailViewHelper.usefaulItemHeight);

            WeatherDetailViewHelper.currentTempTextSize_long = (int) (WeatherDetailViewHelper.itemWith * 0.28f);
            WeatherDetailViewHelper.currentTempPaint_long = new Paint();
            WeatherDetailViewHelper.currentTempPaint_long.setAntiAlias(true);
            WeatherDetailViewHelper.currentTempPaint_long.setTextAlign(Paint.Align.LEFT);
            WeatherDetailViewHelper.currentTempPaint_long.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
            WeatherDetailViewHelper.currentTempPaint_long.setColor(ColorUtil.getColor(R.color.text_color_solid_orange));
            WeatherDetailViewHelper.currentTempPaint_long.setTextSize(WeatherDetailViewHelper.currentTempTextSize_long);
            WeatherDetailViewHelper.currentTempOffset_X_long = WeatherDetailViewHelper.defaultMargin * 2;
            WeatherDetailViewHelper.currentTempOffset_Y_long = (int) (0.32f * WeatherDetailViewHelper.usefaulItemHeight);

            WeatherDetailViewHelper.otherTempTextSize = (int) (WeatherDetailViewHelper.itemWith * 0.06f);
            WeatherDetailViewHelper.otherInfoTextSize = (int) (WeatherDetailViewHelper.itemWith * 0.08f);
            WeatherDetailViewHelper.otherTempOffset_Y = (int) (0.32f * WeatherDetailViewHelper.usefaulItemHeight);
            WeatherDetailViewHelper.otherTextPaint = new Paint();
            WeatherDetailViewHelper.otherTextPaint.setAntiAlias(true);
            WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.CENTER);

            WeatherDetailViewHelper.otherPaint = new Paint();
            WeatherDetailViewHelper.otherPaint.setAntiAlias(true);
            WeatherDetailViewHelper.otherPaint.setColor(ColorUtil.getColor(R.color.vpi__background_holo_dark));

            WeatherDetailViewHelper.forcastItemWidth = (int) (WeatherDetailViewHelper.itemWith * 0.293f);
            WeatherDetailViewHelper.forcastItemDividor = (int) (WeatherDetailViewHelper.itemWith * 0.03f);
            WeatherDetailViewHelper.forcastItemHeight = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.33f);
            WeatherDetailViewHelper.forcastItemOffset_X = (int) (WeatherDetailViewHelper.itemWith * 0.06f);
            WeatherDetailViewHelper.forcastItemOffset_Y = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.6f);

            WeatherDetailViewHelper.currentItemOffset_Y = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.45f);
            WeatherDetailViewHelper.currentItemOffset_Y_2 = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.58f);
            WeatherDetailViewHelper.currentItemOffset_X = (int) (WeatherDetailViewHelper.itemWith * 0.36f);
            WeatherDetailViewHelper.currentItemOffset_X_2 = (int) (WeatherDetailViewHelper.itemWith * 0.54f);
            WeatherDetailViewHelper.currentItemOffset_X_3 = (int) (WeatherDetailViewHelper.itemWith * 0.78f);
            WeatherDetailViewHelper.currentItemWidth = (int) (WeatherDetailViewHelper.itemWith * 0.293f);
            WeatherDetailViewHelper.currentItemHeight = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.01f);
            WeatherDetailViewHelper.currentItemDividor = (int) (WeatherDetailViewHelper.itemWith * 0.03f);

            WeatherDetailViewHelper.feelLikeItemOffset_Y = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.5f);
            WeatherDetailViewHelper.feelLikeTextItemOffset_Y = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.58f);
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
        setMeasuredDimension(WeatherDetailViewHelper.itemWith, WeatherDetailViewHelper.itemHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();
        canvas.translate(0, DeviceInfoUtil.getActionBarHeight());
        WeatherDetailViewHelper.otherPaint.setColor(ColorUtil.getColor(R.color.text_color_black));
        WeatherDetailViewHelper.otherPaint.setStrokeWidth(DimenUtil.getDimensionPixelSize(R.dimen.avatar_corner_radius_xlarge));
//        canvas.drawLine(0, 0.1f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
//        canvas.drawLine(0, 0.2f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
        canvas.drawLine(WeatherDetailViewHelper.defaultMargin, 0.34f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith - WeatherDetailViewHelper.defaultMargin, 0.34f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
//        canvas.drawLine(0, 0.4f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
//        canvas.drawLine(0, 0.5f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
//        canvas.drawLine(0, 0.6f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
//        canvas.drawLine(0, 0.7f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
//        canvas.drawLine(0, 0.8f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
//        canvas.drawLine(0, 0.9f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
//        canvas.drawCircle(0.5f * WeatherDetailViewHelper.itemWith, 0.45f * WeatherDetailViewHelper.usefaulItemHeight - WeatherDetailViewHelper.itemWith * 0.09f, 0.35f * WeatherDetailViewHelper.itemWith, WeatherDetailViewHelper.otherPaint);
        canvas.restore();
        if (mWeatherListItemModel != null) {
            canvas.save();
            canvas.translate(0, DeviceInfoUtil.getActionBarHeight());
            if (mWeatherListItemModel.mWeatherTempModel != null) {
                String temp = mWeatherListItemModel.mWeatherTempModel.getCurrentTemp();
                if (temp.length() > 2) {
                    canvas.drawText(temp + "бу", WeatherDetailViewHelper.currentTempOffset_X_long, WeatherDetailViewHelper.currentTempOffset_Y_long, WeatherDetailViewHelper.currentTempPaint_long);
                } else {
                    canvas.drawText(temp + "бу", WeatherDetailViewHelper.currentTempOffset_X, WeatherDetailViewHelper.currentTempOffset_Y, WeatherDetailViewHelper.currentTempPaint);
                }
                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherTempTextSize);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.RIGHT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Regular);
                canvas.drawText("/" + mWeatherListItemModel.mWeatherTempModel.getLowTemp() + "бу", WeatherDetailViewHelper.itemWith - WeatherDetailViewHelper.defaultMargin, WeatherDetailViewHelper.otherTempOffset_Y, WeatherDetailViewHelper.otherTextPaint);
                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                canvas.drawText(mWeatherListItemModel.mWeatherTempModel.getHighTemp() + "бу", WeatherDetailViewHelper.itemWith - WeatherDetailViewHelper.defaultMargin - (mWeatherListItemModel.mWeatherTempModel.getHighTemp().length() + 1) * WeatherDetailViewHelper.otherInfoTextSize * 0.5f, WeatherDetailViewHelper.otherTempOffset_Y, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 2);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Regular);
                canvas.drawText(mWeatherListItemModel.mWeatherTempModel.getFeelLikeTemp() + "бу", WeatherDetailViewHelper.currentItemDividor, WeatherDetailViewHelper.feelLikeItemOffset_Y, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherTempTextSize);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Regular);
                String indexStr = getResources().getText(R.string.feels_like).toString();
                canvas.drawText(indexStr, WeatherDetailViewHelper.currentItemDividor, WeatherDetailViewHelper.feelLikeTextItemOffset_Y, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 0.5f);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                String dewPoint = getResources().getText(R.string.dew_point).toString();
                canvas.drawText(dewPoint, WeatherDetailViewHelper.currentItemOffset_X_2, WeatherDetailViewHelper.currentItemOffset_Y, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
                canvas.drawText(mWeatherListItemModel.mWeatherTempModel.getDewPointTemp() + "бу", WeatherDetailViewHelper.currentItemOffset_X_2, WeatherDetailViewHelper.currentItemOffset_Y - WeatherDetailViewHelper.otherInfoTextSize * 0.7f, WeatherDetailViewHelper.otherTextPaint);


            }
            if (mWeatherListItemModel.mWeatherWindModel != null) {

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 0.5f);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                String indexStr = getResources().getText(R.string.wind).toString();
                canvas.drawText(indexStr, WeatherDetailViewHelper.currentItemOffset_X_3, WeatherDetailViewHelper.currentItemOffset_Y_2, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 0.55f);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
                canvas.drawText("" + mWeatherListItemModel.mWeatherWindModel.wind_direction, WeatherDetailViewHelper.currentItemOffset_X_3, WeatherDetailViewHelper.currentItemOffset_Y_2 - WeatherDetailViewHelper.otherInfoTextSize * 0.6f, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 0.55f);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
                canvas.drawText("" + mWeatherListItemModel.mWeatherWindModel.wind_speed, WeatherDetailViewHelper.currentItemOffset_X_3, WeatherDetailViewHelper.currentItemOffset_Y_2 - WeatherDetailViewHelper.otherInfoTextSize * 1.2f, WeatherDetailViewHelper.otherTextPaint);

            }
            if (mWeatherListItemModel.mWeatherRainModel != null) {

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 0.5f);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                String indexStr = getResources().getText(R.string.humidity).toString();
                canvas.drawText(indexStr, WeatherDetailViewHelper.currentItemOffset_X, WeatherDetailViewHelper.currentItemOffset_Y, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
                canvas.drawText("" + mWeatherListItemModel.mWeatherRainModel.humidity, WeatherDetailViewHelper.currentItemOffset_X, WeatherDetailViewHelper.currentItemOffset_Y - WeatherDetailViewHelper.otherInfoTextSize * 0.7f, WeatherDetailViewHelper.otherTextPaint);

            }
            if (mWeatherListItemModel.mWeatherPressureModel != null) {

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 0.5f);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                String indexStr = getResources().getText(R.string.pressure).toString();
                canvas.drawText(indexStr, WeatherDetailViewHelper.currentItemOffset_X, WeatherDetailViewHelper.currentItemOffset_Y_2, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
                canvas.drawText("" + mWeatherListItemModel.mWeatherPressureModel.pressure, WeatherDetailViewHelper.currentItemOffset_X, WeatherDetailViewHelper.currentItemOffset_Y_2 - WeatherDetailViewHelper.otherInfoTextSize * 0.7f, WeatherDetailViewHelper.otherTextPaint);
            }
            if (mWeatherListItemModel.mWeatherInfoModel != null) {
                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.RIGHT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                canvas.drawText(mWeatherListItemModel.mWeatherInfoModel.weatherText, WeatherDetailViewHelper.itemWith - WeatherDetailViewHelper.defaultMargin, WeatherDetailViewHelper.defaultMargin + WeatherDetailViewHelper.otherInfoTextSize, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 0.5f);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                String uvString = getResources().getText(R.string.uv_index).toString();
                canvas.drawText(uvString, WeatherDetailViewHelper.currentItemOffset_X_2, WeatherDetailViewHelper.currentItemOffset_Y_2, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
                canvas.drawText("" + mWeatherListItemModel.mWeatherInfoModel.weather_uv_index, WeatherDetailViewHelper.currentItemOffset_X_2, WeatherDetailViewHelper.currentItemOffset_Y_2 - WeatherDetailViewHelper.otherInfoTextSize * 0.7f, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 0.5f);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                String viewAbleString = getResources().getText(R.string.visibility).toString();
                canvas.drawText(viewAbleString, WeatherDetailViewHelper.currentItemOffset_X_3, WeatherDetailViewHelper.currentItemOffset_Y, WeatherDetailViewHelper.otherTextPaint);

                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
                canvas.drawText("" + mWeatherListItemModel.mWeatherInfoModel.weather_avaliable, WeatherDetailViewHelper.currentItemOffset_X_3, WeatherDetailViewHelper.currentItemOffset_Y - WeatherDetailViewHelper.otherInfoTextSize * 0.7f, WeatherDetailViewHelper.otherTextPaint);


            }
//            if (mWeatherListItemModel.mWeatherLocalModel != null) {
//                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
//                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
//                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
//                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
//                canvas.drawText(mWeatherListItemModel.mWeatherLocalModel.localName, WeatherDetailViewHelper.defaultMargin, WeatherDetailViewHelper.defaultMargin + WeatherDetailViewHelper.otherInfoTextSize, WeatherDetailViewHelper.otherTextPaint);
//            }
            int count = mWeatherListItemModel.weatherForcastModels.size();
            if (count > 0) {
                for (int index = 0; index < count; index++) {
                    WeatherForcastModel mWeatherForcastModel = mWeatherListItemModel.weatherForcastModels.get(index);
                    Rect bound = new Rect();
                    int rectX = (index % 3) * (WeatherDetailViewHelper.forcastItemWidth + WeatherDetailViewHelper.forcastItemDividor) + WeatherDetailViewHelper.forcastItemDividor;
                    int rectY = WeatherDetailViewHelper.forcastItemOffset_Y;
                    bound.set(rectX, rectY, rectX + WeatherDetailViewHelper.forcastItemWidth, rectY + WeatherDetailViewHelper.forcastItemHeight);
                    canvas.drawRect(bound, WeatherDetailViewHelper.otherPaint);

//                    float startX = (index % 3 + 1) * (WeatherDetailViewHelper.forcastItemWidth + WeatherDetailViewHelper.forcastItemDividor) - WeatherDetailViewHelper.forcastItemWidth * 0.75f;
//                    float startY = WeatherDetailViewHelper.forcastItemOffset_Y - WeatherDetailViewHelper.forcastItemHeight * 0.6f;
//                    WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.CENTER);
//                    WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
//                    WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize * 0.5f);
//                    WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
//                    canvas.drawText(mWeatherForcastModel.mWeatherInfoModel.weatherText, startX, startY, WeatherDetailViewHelper.otherTextPaint);


                    float dateX = (index % 3 + 1) * (WeatherDetailViewHelper.forcastItemWidth + WeatherDetailViewHelper.forcastItemDividor) - WeatherDetailViewHelper.forcastItemWidth * 0.5f;
                    float dateY = WeatherDetailViewHelper.forcastItemOffset_Y + WeatherDetailViewHelper.forcastItemHeight * 0.22f;
                    WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.CENTER);
                    WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Light);
                    WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.forcastItemHeight * 0.18f);
                    WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_orange));
                    canvas.drawText("Thu", dateX, dateY, WeatherDetailViewHelper.otherTextPaint);

                    float tempX = (index % 3 + 1) * (WeatherDetailViewHelper.forcastItemWidth + WeatherDetailViewHelper.forcastItemDividor) - WeatherDetailViewHelper.forcastItemWidth * 0.5f;
                    float tempY = WeatherDetailViewHelper.forcastItemOffset_Y + WeatherDetailViewHelper.forcastItemHeight * 0.5f;

                    WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.RIGHT);
                    WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                    WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.forcastItemHeight * 0.15f);
                    WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                    canvas.drawText(mWeatherForcastModel.mWeatherTempModel.getHighTemp() + "бу", tempX, tempY + WeatherDetailViewHelper.forcastItemHeight * 0.15f, WeatherDetailViewHelper.otherTextPaint);

                    WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                    WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Light);
                    WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.forcastItemHeight * 0.12f);
                    WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                    canvas.drawText("/" + mWeatherForcastModel.mWeatherTempModel.getLowTemp() + "бу", tempX, tempY + WeatherDetailViewHelper.forcastItemHeight * 0.15f, WeatherDetailViewHelper.otherTextPaint);

                    float rainRateX = (index % 3 + 1) * (WeatherDetailViewHelper.forcastItemWidth + WeatherDetailViewHelper.forcastItemDividor) - WeatherDetailViewHelper.forcastItemWidth * 0.5f;
                    float rainRateY = WeatherDetailViewHelper.forcastItemOffset_Y + WeatherDetailViewHelper.forcastItemHeight * 0.82f;
                    WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                    WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Light);
                    WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.forcastItemHeight * 0.08f);
                    WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                    canvas.drawText(mWeatherForcastModel.mWeatherRainModel.rain_rate, rainRateX + WeatherDetailViewHelper.forcastItemHeight * 0.01f, rainRateY, WeatherDetailViewHelper.otherTextPaint);

                    WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.RIGHT);
                    WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Light);
                    WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.forcastItemHeight * 0.08f);
                    WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                    canvas.drawText(ResourceStringUtil.getString(R.string.rain_rate), rainRateX - WeatherDetailViewHelper.forcastItemHeight * 0.01f, rainRateY, WeatherDetailViewHelper.otherTextPaint);

                    float uvX = (index % 3 + 1) * (WeatherDetailViewHelper.forcastItemWidth + WeatherDetailViewHelper.forcastItemDividor) - WeatherDetailViewHelper.forcastItemWidth * 0.5f;
                    float uvY = WeatherDetailViewHelper.forcastItemOffset_Y + WeatherDetailViewHelper.forcastItemHeight * 0.96f;
                    WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                    WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Light);
                    WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.forcastItemHeight * 0.08f);
                    WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                    canvas.drawText(mWeatherForcastModel.mWeatherRainModel.rain_rate, uvX + WeatherDetailViewHelper.forcastItemHeight * 0.01f, uvY, WeatherDetailViewHelper.otherTextPaint);

                    WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.RIGHT);
                    WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Light);
                    WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.forcastItemHeight * 0.08f);
                    WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                    canvas.drawText("UV", uvX - WeatherDetailViewHelper.forcastItemHeight * 0.01f, uvY, WeatherDetailViewHelper.otherTextPaint);

                }
            }
            canvas.restore();
        }
    }
}
