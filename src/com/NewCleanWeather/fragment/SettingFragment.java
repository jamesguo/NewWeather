package com.NewCleanWeather.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.*;
import android.preference.PreferenceFragment;
import com.NewCleanWeather.R;
import com.NewCleanWeather.WeatherApplication;
import com.NewCleanWeather.util.SystemUtil;

/**
 * Created by yrguo on 2014/7/7.
 */
public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    private static final String KEY_TIME_LIST_PREFERENCE = "time_select";
    private static final String KEY_TEMP_LIST_PREFERENCE = "temp_unit";
    private ListPreference tempListPreference,timeListPreference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.preferences);
        tempListPreference = (ListPreference)findPreference("temp_unit");
        timeListPreference = (ListPreference)findPreference("time_select");
        tempListPreference.setSummary(tempListPreference.getEntry());
        timeListPreference.setSummary(timeListPreference.getEntry());
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        prefs.registerOnSharedPreferenceChangeListener(this);
//        getPreferenceScreen().
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        if(layoutParams!=null){
            layoutParams.setMargins(0, SystemUtil.getActionBarHeight(),0,0);
            view.setLayoutParams(layoutParams);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_TIME_LIST_PREFERENCE)) {
            timeListPreference.setSummary(timeListPreference.getEntry());
        }else if(key.equals(KEY_TEMP_LIST_PREFERENCE)){
            tempListPreference.setSummary(tempListPreference.getEntry());
        }
    }
}

