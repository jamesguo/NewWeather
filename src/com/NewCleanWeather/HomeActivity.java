package com.NewCleanWeather;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import com.NewCleanWeather.fragment.AboutFragment;
import com.NewCleanWeather.fragment.SettingFragment;
import com.NewCleanWeather.fragment.WeatherListFragment;
import com.NewCleanWeather.manager.FragmentExchangeManager;
import com.alexvasilkov.foldablelayout.sample.activities.BaseActivity;
import com.alexvasilkov.foldablelayout.sample.items.Painting;
import com.alexvasilkov.foldablelayout.sample.items.Views;

import java.util.ArrayList;

/**
 * Created by yrguo on 2014/7/3.
 */
public class HomeActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "BaseActivity";
    public static final String Weather_TAG = "weather";
    public static final String Setting_TAG = "setting";
    public static final String About_TAG = "about";
    //    private ListView mListView;
    private DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    private ListView drawerList;
    private DrawerListAdapter drawerListAdapter;
    private int currrent = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        mDrawerLayout = Views.find(this, R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer_white, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
//        getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.actionbar_bg));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        drawerList = Views.find(this, R.id.drawer_left_list);
        drawerListAdapter = new DrawerListAdapter(getBaseContext());
        drawerList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        ArrayList<DrawerListAdapter.DreawerItem> dreawerItems = new ArrayList<DrawerListAdapter.DreawerItem>();
        dreawerItems.add(new DrawerListAdapter.DreawerItem(0, R.drawable.ic_drawer_home, getResources().getString(R.string.drawer_home)));
        dreawerItems.add(new DrawerListAdapter.DreawerItem(1, R.drawable.ic_drawer_setting, getResources().getString(R.string.drawer_setting)));
        dreawerItems.add(new DrawerListAdapter.DreawerItem(2, R.drawable.ic_drawer_about, getResources().getString(R.string.drawer_about)));
        dreawerItems.add(new DrawerListAdapter.DreawerItem(3, R.drawable.ic_drawer_feedback, getResources().getString(R.string.drawer_feedback)));
        dreawerItems.add(new DrawerListAdapter.DreawerItem(4, R.drawable.ic_drawer_vote, getResources().getString(R.string.drawer_vote)));
        drawerListAdapter.dreawerItems.clear();
        drawerListAdapter.dreawerItems.addAll(dreawerItems);
        drawerListAdapter.notifyDataSetChanged();
        drawerList.setAdapter(drawerListAdapter);
        drawerList.setOnItemClickListener(this);
        drawerList.setItemChecked(0, true);
        onItemClick(drawerList, null, 0, drawerListAdapter.getItemId(0));
    }

    @Override
    public void onBackPressed() {
        if (mDrawerToggle.isDrawerIndicatorEnabled()) {
            return;
        }
        Fragment fragment = getFragmentManager().findFragmentByTag(Weather_TAG);
        if(fragment!=null&&fragment.isVisible()&&fragment instanceof WeatherListFragment){
            if(((WeatherListFragment) fragment).onBackPressed()){
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Weather_TAG);
//        if(fragment!=null&&fragment.isVisible()&&fragment instanceof WeatherListFragment){
//            fragment.onCreateOptionsMenu(menu,getMenuInflater());
//        }else{
//
//        }
//        return true;
//    }
    private void handleIntent(Intent intent) {
        if (intent == null)
            return;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object localObject = parent.getAdapter().getItem(position);
        if ((localObject instanceof DrawerListAdapter.DreawerItem)) {
            switch ((int) (((DrawerListAdapter.DreawerItem) localObject).index)) {
                case 0:
                    if (currrent != (int) (((DrawerListAdapter.DreawerItem) localObject).index)) {
                        FragmentExchangeManager.exchangeFragment(getFragmentManager(), R.id.content_frame, Weather_TAG, WeatherListFragment.class, null);
                        getActionBar().setTitle(((DrawerListAdapter.DreawerItem) localObject).text);
                    }
                    break;
                case 1:
                    if (currrent != (int) (((DrawerListAdapter.DreawerItem) localObject).index)) {
                        FragmentExchangeManager.exchangeFragment(getFragmentManager(), R.id.content_frame, Setting_TAG, SettingFragment.class, null);
                        getActionBar().setTitle(((DrawerListAdapter.DreawerItem) localObject).text);
                    }
                    break;
                case 2:
                    if (currrent != (int) (((DrawerListAdapter.DreawerItem) localObject).index)) {
                        FragmentExchangeManager.exchangeFragment(getFragmentManager(), R.id.content_frame, About_TAG, AboutFragment.class, null);
                        getActionBar().setTitle(((DrawerListAdapter.DreawerItem) localObject).text);
                    }
                    break;
                case 3:
//                    Intent play = new Intent(Intent.ACTION_MAIN);
//                    play.setClassName("android","com.android.internal.app.PlatLogoActivity");
//                    startActivity(play);
                    Intent data=new Intent(Intent.ACTION_SENDTO);
                    data.setData(Uri.parse("mailto:james.guo89@gmail.com"));
                    data.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.drawer_feedback));
                    data.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(data);
//                    Intent email = new Intent(android.content.Intent.ACTION_SEND);
//                    email.setType("text/plain");
//                    String[] emailReciver = new String[]{"james.guo89@gmail.com"};
//                    String emailSubject = getResources().getString(R.string.drawer_feedback);
////设置邮件默认地址
//                    email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
////设置邮件默认标题
//                    email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
////设置要默认发送的内容
//                    email.putExtra(android.content.Intent.EXTRA_TEXT, "");
////调用系统的邮件系统
//                    startActivity(Intent.createChooser(email, getResources().getString(R.string.send_feedback)));
                    drawerList.setItemChecked(currrent, true);
                    break;
                case 4:
                    //评分
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    drawerList.setItemChecked(currrent, true);
                    break;
                default:
                    break;
            }
            mDrawerLayout.closeDrawers();
            currrent = drawerList.getCheckedItemPosition();
        }
    }
}