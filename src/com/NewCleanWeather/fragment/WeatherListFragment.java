package com.NewCleanWeather.fragment;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.SearchView;
import com.NewCleanWeather.HomeActivity;
import com.NewCleanWeather.R;
import com.NewCleanWeather.manager.FragmentExchangeManager;
import com.NewCleanWeather.model.WeatherListItemModel;
import com.NewCleanWeather.widget.SlidingUpPanelLayout;
import com.NewCleanWeather.widget.WeatherDetailView;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.sample.items.Painting;
import com.alexvasilkov.foldablelayout.sample.items.PaintingsAdapter;
import com.alexvasilkov.foldablelayout.sample.items.Views;
import com.alexvasilkov.foldablelayout.sample.items.WeatherItemListAdapter;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;

import java.lang.reflect.Field;


/**
 * Created by yrguo on 2014/7/7.
 */
public class WeatherListFragment extends Fragment implements PaintingsAdapter.UnfoldInterface, WeatherItemListAdapter.WeatherItemUnfoldInterface {
    private View mListTouchInterceptor;
    private View mDetailsLayout;
    private UnfoldableView mUnfoldableView;
    private SlidingUpPanelLayout mLayout;
    private static final String TAG = "WeatherListFragment";
    public static final String List_TAG = "WeatherList";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather_list_layout, container, false);

//        mListView = Views.find(this, R.id.list_view);
//        mListView.setAdapter(new PaintingsAdapter(this));
        mListTouchInterceptor = Views.find(rootView, R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);
        mDetailsLayout = Views.find(rootView, R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);
        mUnfoldableView = Views.find(rootView, R.id.unfoldable_view);
        mLayout = (SlidingUpPanelLayout) Views.find(rootView, R.id.sliding_layout);
        Bitmap glance = ((BitmapDrawable) getResources().getDrawable(R.drawable.unfold_glance)).getBitmap();
        mUnfoldableView.setFoldShading(new GlanceFoldShading(inflater.getContext(), glance));

        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                getActivity().getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                mListTouchInterceptor.setClickable(false);
                ((HomeActivity) getActivity()).mDrawerToggle.setDrawerIndicatorEnabled(false);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onFoldProgress(UnfoldableView unfoldableView, float progress) {
//                if(currentAlpha!=255){
//                    getFadingActionBarHelper().setActionBarAlpha((int)((255-currentAlpha)*progress));
//                }
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
                mDetailsLayout.setVisibility(View.INVISIBLE);
                getActivity().getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                ((HomeActivity) getActivity()).mDrawerToggle.setDrawerIndicatorEnabled(true);
                getActivity().invalidateOptionsMenu();
            }
        });


        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
//                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
//                setActionBarTranslation(mLayout.getCurrentParalaxOffset());
            }

            @Override
            public void onPanelExpanded(View panel) {
//                Log.i(TAG, "onPanelExpanded");

            }

            @Override
            public void onPanelCollapsed(View panel) {
//                Log.i(TAG, "onPanelCollapsed");

            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "onPanelHidden");
            }
        });

        if (savedInstanceState == null) {
            FragmentExchangeManager.exchangeFragment(getChildFragmentManager(), R.id.list_container, List_TAG, FocusCityWeatherListFragment.class, null, WeatherListFragment.this);
//            int commit = getChildFragmentManager().beginTransaction().(R.id.list_container, new FocusCityWeatherListFragment()).commit();
        }
        return rootView;
    }

    @Override
    public void openDetails(View coverView, Painting painting) {
        if (getActivity().getActionBar() != null) getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        mUnfoldableView.unfold(coverView, mDetailsLayout);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mUnfoldableView.isUnfolded()) {
            inflater.inflate(R.menu.detail_bar, menu);
        } else {
            inflater.inflate(R.menu.main_bar, menu);
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setIconifiedByDefault(true);
            int id = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
            ((ImageView) Views.find(searchView, id)).setImageResource(R.drawable.ab_search_clear);
            int searchId = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
//            SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete)ll3.getChildAt(0);
//            Drawable drawable= getResources().getDrawable(R.drawable.ic_action_search);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((ImageView) Views.find(searchView, searchId)).setImageResource(R.drawable.ic_action_search);
            int searchId2 = searchView.getContext().getResources().getIdentifier("android:id/search_button", null, null);
            ((ImageView) Views.find(searchView, searchId2)).setBackgroundResource(R.drawable.ic_action_search);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            try {
                Field searchField = SearchView.class.getDeclaredField("mSearchButton");
                searchField.setAccessible(true);
                ImageView searchBtn = (ImageView) searchField.get(searchView);
                searchBtn.setImageResource(R.drawable.ic_action_search);
                searchField = SearchView.class.getDeclaredField("mSearchPlate");
                searchField.setAccessible(true);
//                LinearLayout searchPlate = (LinearLayout)searchField.get(searchView);
//                Drawable drawable= getResources().getDrawable(R.drawable.ic_action_search);
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                ((TextView)searchPlate.getChildAt(0)).setCompoundDrawables(drawable,null,null,null);
//                searchPlate.setBackgroundResource(R.drawable.edit_text_bkg);
            } catch (NoSuchFieldException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_to)));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean onBackPressed() {
        if (mLayout != null && (mLayout.isPanelExpanded() || mLayout.isPanelDragging())) {
            mLayout.setAnchorPoint(1.0f);
            mLayout.collapsePanel();
            return true;
        } else {
            if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
                mUnfoldableView.foldBack();
                return true;
            }
        }
        return false;
    }

    @Override
    public void openDetails(View view, WeatherListItemModel weatherListItemModel) {
        if (getActivity().getActionBar() != null) getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        WeatherDetailView weatherDetailView = Views.find(mDetailsLayout, R.id.detailView);
        weatherDetailView.setmWeatherListItemModel(weatherListItemModel);
        mUnfoldableView.unfold(view, mDetailsLayout);
    }
}