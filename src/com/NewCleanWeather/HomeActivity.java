package com.NewCleanWeather;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.*;
import android.widget.SearchView;
import com.NewCleanWeather.fragment.FadingActionBarHelper;
import com.NewCleanWeather.fragment.FocusCityWeatherListFragment;
import com.NewCleanWeather.widget.SlidingUpPanelLayout;
import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.sample.activities.BaseActivity;
import com.alexvasilkov.foldablelayout.sample.items.Painting;
import com.alexvasilkov.foldablelayout.sample.items.Views;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;

/**
 * Created by yrguo on 2014/7/3.
 */
public class HomeActivity extends BaseActivity {
    private static final String TAG = "BaseActivity";
    //    private ListView mListView;
    private View mListTouchInterceptor;
    private View mDetailsLayout;
    private UnfoldableView mUnfoldableView;
    private SlidingUpPanelLayout mLayout;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FadingActionBarHelper mFadingActionBarHelper;
    private int currentAlpha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        mFadingActionBarHelper = new FadingActionBarHelper(getActionBar(), getResources().getDrawable(R.drawable.actionbar_bg));

//        mListView = Views.find(this, R.id.list_view);
//        mListView.setAdapter(new PaintingsAdapter(this));
        mListTouchInterceptor = Views.find(this, R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);
        mDetailsLayout = Views.find(this, R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);
        mUnfoldableView = Views.find(this, R.id.unfoldable_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

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
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        Bitmap glance = ((BitmapDrawable) getResources().getDrawable(R.drawable.unfold_glance)).getBitmap();
        mUnfoldableView.setFoldShading(new GlanceFoldShading(this, glance));

        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                mListTouchInterceptor.setClickable(false);
                mDrawerToggle.setDrawerIndicatorEnabled(false);
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
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
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
            getFragmentManager().beginTransaction().add(R.id.list_container, new FocusCityWeatherListFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerToggle.isDrawerIndicatorEnabled()) {
            return;
        }
        if (mLayout != null && (mLayout.isPanelExpanded() || mLayout.isPanelDragging())) {
            mLayout.setAnchorPoint(1.0f);
            mLayout.collapsePanel();
        } else {
            if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
                mUnfoldableView.foldBack();
            } else {
                super.onBackPressed();
            }
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        if (mUnfoldableView.isUnfolded()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.detail_bar, menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_bar, menu);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setIconifiedByDefault(true);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return true;
    }

    public void openDetails(View coverView, Painting painting) {
//        ImageView image = Views.find(mDetailsLayout, R.id.details_image);
//        TextView title = Views.find(mDetailsLayout, R.id.details_title);
//        TextView description = Views.find(mDetailsLayout, R.id.details_text);
//
//        image.setBackground(getResources().getDrawable(painting.getImageId()));
//        title.setText(painting.getTitle());
//
//        description.setText(painting.getTitle());
        currentAlpha = getFadingActionBarHelper().getActionBarAlpha();
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        mUnfoldableView.unfold(coverView, mDetailsLayout);
    }

    private void handleIntent(Intent intent) {
        if (intent == null)
            return;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    public FadingActionBarHelper getFadingActionBarHelper() {
        return mFadingActionBarHelper;
    }

}