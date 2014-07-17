package com.NewCleanWeather.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yrguo on 2014/7/4.
 */
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.NewCleanWeather.R;
import com.alexvasilkov.foldablelayout.sample.items.PaintingsAdapter;
import com.alexvasilkov.foldablelayout.sample.items.WeatherItemListAdapter;

import java.lang.ref.WeakReference;

/**
 * Created by Artem on 06.12.13.
 */
public class FocusCityWeatherListFragment extends HeaderFragment {

    private ListView mListView;
    private String[] mListViewTitles;
    private boolean mLoaded;

    private AsyncLoadSomething mAsyncLoadSomething;
    private FrameLayout mContentOverlay;
    public WeatherItemListAdapter paintingsAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        setHeaderBackgroundScrollMode(HEADER_BACKGROUND_SCROLL_PARALLAX);
        setOnHeaderScrollChangedListener(new OnHeaderScrollChangedListener() {
            @Override
            public void onHeaderScrollChanged(float progress, int height, int scroll) {
                height -= getActivity().getActionBar().getHeight();

                progress = (float) scroll / height;
                if (progress > 1f) progress = 1f;

                progress = (1 - (float) Math.cos(progress * Math.PI)) * 0.5f;
//                ((HomeActivity) getActivity()) .getFadingActionBarHelper().setActionBarAlpha((int) (255 * progress));
            }
        });

        cancelAsyncTask(mAsyncLoadSomething);
        mAsyncLoadSomething = new AsyncLoadSomething(this);
        mAsyncLoadSomething.execute();
    }

    @Override
    public void onDetach() {
        cancelAsyncTask(mAsyncLoadSomething);
        super.onDetach();
    }

    @Override
    public View onCreateHeaderView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_header, container, false);
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        mListView = (ListView) inflater.inflate(R.layout.fragment_listview, container, false);
        if (mLoaded) setListViewTitles(mListViewTitles);
        return mListView;
    }

    @Override
    public View onCreateContentOverlayView(LayoutInflater inflater, ViewGroup container) {
        ProgressBar progressBar = new ProgressBar(getActivity());
        mContentOverlay = new FrameLayout(getActivity());
        mContentOverlay.addView(progressBar, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        if (mLoaded) mContentOverlay.setVisibility(View.GONE);
        return mContentOverlay;
    }

    private void setListViewTitles(String[] titles) {
        mLoaded = true;
        mListViewTitles = titles;
        if (mListView == null) return;

        mListView.setVisibility(View.VISIBLE);
        paintingsAdapter = new WeatherItemListAdapter(getActivity());
//        if(getTargetFragment()!=null&&getTargetFragment() instanceof PaintingsAdapter.UnfoldInterface){
//            paintingsAdapter.unfoldInterface = (PaintingsAdapter.UnfoldInterface)getTargetFragment();
//        }
        if(getTargetFragment()!=null&&getTargetFragment() instanceof WeatherItemListAdapter.WeatherItemUnfoldInterface){
            paintingsAdapter.unfoldInterface = (WeatherItemListAdapter.WeatherItemUnfoldInterface)getTargetFragment();
        }
        setListViewAdapter(mListView, paintingsAdapter);
    }

    private void cancelAsyncTask(AsyncTask task) {
        if (task != null) task.cancel(false);
    }

    // //////////////////////////////////////////
    // ///////////// -- LOADER -- ///////////////
    // //////////////////////////////////////////

    private static class AsyncLoadSomething extends AsyncTask<Void, Void, String[]> {

        private static final String TAG = "AsyncLoadSomething";

        final WeakReference<FocusCityWeatherListFragment> weakFragment;

        public AsyncLoadSomething(FocusCityWeatherListFragment fragment) {
            this.weakFragment = new WeakReference<FocusCityWeatherListFragment>(fragment);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            final FocusCityWeatherListFragment audioFragment = weakFragment.get();
            if (audioFragment.mListView != null) audioFragment.mListView.setVisibility(View.INVISIBLE);
            if (audioFragment.mContentOverlay != null) audioFragment.mContentOverlay.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(Void... voids) {

            try {
                // Emulate long downloading
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return new String[]{"Placeholder", "Placeholder", "Placeholder", "Placeholder",
                    "Placeholder", "Placeholder", "Placeholder", "Placeholder",
                    "Placeholder", "Placeholder", "Placeholder", "Placeholder",
                    "Placeholder", "Placeholder", "Placeholder", "Placeholder",
                    "Placeholder", "Placeholder", "Placeholder", "Placeholder",
                    "Placeholder", "Placeholder", "Placeholder", "Placeholder"};
        }

        @Override
        protected void onPostExecute(String[] titles) {
            super.onPostExecute(titles);
            final FocusCityWeatherListFragment audioFragment = weakFragment.get();
            if (audioFragment == null) {
                Log.d(TAG, "Skipping.., because there is no fragment anymore.");
                return;
            }

            if (audioFragment.mContentOverlay != null) audioFragment.mContentOverlay.setVisibility(View.GONE);
            audioFragment.setListViewTitles(titles);
        }
    }
}