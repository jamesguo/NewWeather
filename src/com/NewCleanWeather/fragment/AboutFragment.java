package com.NewCleanWeather.fragment;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.NewCleanWeather.R;
import com.NewCleanWeather.WeatherApplication;
import com.NewCleanWeather.manager.ChangeLogDialog;
import com.NewCleanWeather.util.DeviceInfoUtil;
import com.NewCleanWeather.util.SystemUtil;

/**
 * Created by yrguo on 2014/7/10.
 */
public class AboutFragment extends PreferenceFragment {
    private static final String KEY_FIRMWARE_VERSION = "version";
    private static final String KEY_CHANGE_LOG = "change_log";
    private long[] mHits = new long[3];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about_preferences);
        String pName = WeatherApplication.getInstance().getPackageName();
        try {
            PackageInfo pinfo = WeatherApplication.getInstance().getPackageManager().getPackageInfo(pName, PackageManager.GET_CONFIGURATIONS);
            String versionName = pinfo.versionName;
            setStringSummary(KEY_FIRMWARE_VERSION, versionName);
        } catch (Exception e) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.setMargins(0, DeviceInfoUtil.getActionBarHeight(), 0, 0);
            view.setLayoutParams(layoutParams);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getKey().equals(KEY_FIRMWARE_VERSION)) {
            System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
            mHits[mHits.length - 1] = SystemClock.uptimeMillis();
            if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
                Toast.makeText(WeatherApplication.getInstance(),getResources().getText(R.string.play_toast),Toast.LENGTH_SHORT).show();
            }
        } else if (preference.getKey().equals(KEY_CHANGE_LOG)) {
            final ChangeLogDialog changeLogDialog = new ChangeLogDialog(getActivity());
            changeLogDialog.setStyle("h1 { margin-left: 10px; font-size: 12pt; color: #006b9a; margin-bottom: 0px;}"
                    + "li { margin-left: 0px; font-size: 12pt; padding-top: 10px; }"
                    + "ul { padding-left: 30px; margin-top: 0px; }"
                    + ".summary { margin-left: 10px; font-size: 10pt; color: #006b9a; margin-top: 5px; display: block; clear: left; }"
                    + ".date { margin-left: 10px; font-size: 10pt; color: #006b9a; margin-top: 5px; display: block; }");
            changeLogDialog.show();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void setStringSummary(String preference, String value) {
        try {
            findPreference(preference).setSummary(value);
        } catch (RuntimeException e) {
            findPreference(preference).setSummary("");
        }
    }

    private void setValueSummary(String preference, String property) {
        try {
            findPreference(preference).setSummary(property);
        } catch (RuntimeException e) {
            // No recovery
        }
    }
}
