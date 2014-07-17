package com.NewCleanWeather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import com.NewCleanWeather.R;
import com.NewCleanWeather.model.WeatherForcastModel;
import com.NewCleanWeather.model.WeatherListItemModel;
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

            WeatherDetailViewHelper.otherInfoTextSize = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.15f);
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
            WeatherDetailViewHelper.currentTempOffset_Y = (int) (0.35f * WeatherDetailViewHelper.usefaulItemHeight);

            WeatherDetailViewHelper.currentTempTextSize_long = (int) (WeatherDetailViewHelper.itemWith * 0.28f);
            WeatherDetailViewHelper.currentTempPaint_long = new Paint();
            WeatherDetailViewHelper.currentTempPaint_long.setAntiAlias(true);
            WeatherDetailViewHelper.currentTempPaint_long.setTextAlign(Paint.Align.LEFT);
            WeatherDetailViewHelper.currentTempPaint_long.setTypeface(WeatherDetailViewHelper.Roboto_Thin);
            WeatherDetailViewHelper.currentTempPaint_long.setColor(ColorUtil.getColor(R.color.text_color_solid_orange));
            WeatherDetailViewHelper.currentTempPaint_long.setTextSize(WeatherDetailViewHelper.currentTempTextSize_long);
            WeatherDetailViewHelper.currentTempOffset_X_long = WeatherDetailViewHelper.defaultMargin * 2;
            WeatherDetailViewHelper.currentTempOffset_Y_long = (int) (0.38f * WeatherDetailViewHelper.usefaulItemHeight);

            WeatherDetailViewHelper.otherTempTextSize = (int) (WeatherDetailViewHelper.itemWith * 0.06f);
            WeatherDetailViewHelper.otherInfoTextSize = (int) (WeatherDetailViewHelper.itemWith * 0.08f);
            WeatherDetailViewHelper.otherTempOffset_Y = (int) (0.35f * WeatherDetailViewHelper.usefaulItemHeight);
            WeatherDetailViewHelper.otherTextPaint = new Paint();
            WeatherDetailViewHelper.otherTextPaint.setAntiAlias(true);
            WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.CENTER);

            WeatherDetailViewHelper.otherPaint = new Paint();
            WeatherDetailViewHelper.otherPaint.setAntiAlias(true);
            WeatherDetailViewHelper.otherPaint.setColor(ColorUtil.getColor(R.color.vpi__background_holo_dark));

            WeatherDetailViewHelper.forcastItemWidth = (int) (WeatherDetailViewHelper.itemWith * 0.293f);
            WeatherDetailViewHelper.forcastItemDividor = (int) (WeatherDetailViewHelper.itemWith * 0.03f);
            WeatherDetailViewHelper.forcastItemHeight = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.15f);
            WeatherDetailViewHelper.forcastItemOffset_X = (int) (WeatherDetailViewHelper.itemWith * 0.06f);
            WeatherDetailViewHelper.forcastItemOffset_Y = (int) (WeatherDetailViewHelper.usefaulItemHeight * 0.6f);
            ;
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
        canvas.drawLine(WeatherDetailViewHelper.defaultMargin, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.itemWith - WeatherDetailViewHelper.defaultMargin, 0.38f * WeatherDetailViewHelper.usefaulItemHeight, WeatherDetailViewHelper.otherPaint);
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

            }
            if (mWeatherListItemModel.mWeatherInfoModel != null) {
                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.RIGHT);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                canvas.drawText(mWeatherListItemModel.mWeatherInfoModel.weatherText, WeatherDetailViewHelper.itemWith - WeatherDetailViewHelper.defaultMargin, WeatherDetailViewHelper.defaultMargin + WeatherDetailViewHelper.otherInfoTextSize, WeatherDetailViewHelper.otherTextPaint);
            }
            if (mWeatherListItemModel.mWeatherLocalModel != null) {
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.LEFT);
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                canvas.drawText(mWeatherListItemModel.mWeatherLocalModel.localName, WeatherDetailViewHelper.defaultMargin, WeatherDetailViewHelper.defaultMargin + WeatherDetailViewHelper.otherInfoTextSize, WeatherDetailViewHelper.otherTextPaint);
            }
            int count = mWeatherListItemModel.weatherForcastModels.size();
            if (count > 0) {
                WeatherDetailViewHelper.otherTextPaint.setTextAlign(Paint.Align.CENTER);
                WeatherDetailViewHelper.otherTextPaint.setTypeface(WeatherDetailViewHelper.Roboto_Condensed);
                WeatherDetailViewHelper.otherTextPaint.setTextSize(WeatherDetailViewHelper.otherInfoTextSize);
                WeatherDetailViewHelper.otherTextPaint.setColor(ColorUtil.getColor(R.color.text_color_white));
                for (int index = 0; index < count; index++) {
                    WeatherForcastModel mWeatherForcastModel = mWeatherListItemModel.weatherForcastModels.get(index);
                    float startX = (index % 3 + 1) * (WeatherDetailViewHelper.forcastItemWidth + WeatherDetailViewHelper.forcastItemDividor) - WeatherDetailViewHelper.forcastItemWidth * 0.5f;
                    float startY = ((int) (index / 3) + 1) * (WeatherDetailViewHelper.forcastItemHeight + WeatherDetailViewHelper.forcastItemOffset_Y + WeatherDetailViewHelper.forcastItemDividor) - WeatherDetailViewHelper.forcastItemHeight * 0.5f;
                    Rect bound = new Rect();
                    int rectX = (index % 3) * (WeatherDetailViewHelper.forcastItemWidth + WeatherDetailViewHelper.forcastItemDividor) + WeatherDetailViewHelper.forcastItemDividor;
                    int rectY = (int) (index / 3) * (WeatherDetailViewHelper.forcastItemHeight + WeatherDetailViewHelper.forcastItemDividor) + WeatherDetailViewHelper.forcastItemDividor + WeatherDetailViewHelper.forcastItemOffset_Y;
                    bound.set(rectX, rectY, rectX + WeatherDetailViewHelper.forcastItemWidth, rectY + WeatherDetailViewHelper.forcastItemHeight);
                    canvas.drawRect(bound, WeatherDetailViewHelper.otherPaint);
                    canvas.drawText(mWeatherForcastModel.mWeatherInfoModel.weatherText, startX, startY, WeatherDetailViewHelper.otherTextPaint);
                }
            }
            canvas.restore();
        }
    }
}
