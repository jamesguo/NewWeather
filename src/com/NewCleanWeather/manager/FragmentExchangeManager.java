package com.NewCleanWeather.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.NewCleanWeather.WeatherApplication;

/**
 * Created by yrguo on 2014/7/7.
 */
public class FragmentExchangeManager {
       public static void exchangeFragment(FragmentManager fragmentManager,int layoutId,String fragmentName,Class<?> fragmentClass,Bundle bundle,Fragment targetFragment){
           Fragment fragment = fragmentManager.findFragmentByTag(fragmentName);
           if (fragment==null) {
               fragment = Fragment.instantiate(WeatherApplication.getInstance(), fragmentClass.getName());
           }
           if(targetFragment!=null){
               fragment.setTargetFragment(targetFragment,0);
           }
           fragment.setArguments(bundle);
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(layoutId,fragment,fragmentName);
           fragmentTransaction.commitAllowingStateLoss();
       }
    public static void exchangeFragment(FragmentManager fragmentManager,int layoutId,String fragmentName,Class<?> fragmentClass,Bundle bundle){
        exchangeFragment(fragmentManager,layoutId, fragmentName, fragmentClass, bundle,null);
    }
}
