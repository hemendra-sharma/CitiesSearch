package com.hemendra.citiessearch.view;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hemendra.citiessearch.R;
import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.presenter.PresenterFactory;
import com.hemendra.citiessearch.presenter.listeners.IPresenterFactory;
import com.hemendra.citiessearch.presenter.listeners.ISearchPresenter;
import com.hemendra.citiessearch.view.fragments.MapViewFragment;
import com.hemendra.citiessearch.view.fragments.SearchFragment;
import com.hemendra.citiessearch.view.listeners.OnCityClickListener;
import com.hemendra.citiessearch.view.listeners.ICitiesActivity;

import java.util.ArrayList;

public class CitiesActivity extends AppCompatActivity implements ICitiesActivity {

    private static final String SEARCH_FRAGMENT_TAG = "search";
    private SearchFragment searchFragment = null;

    private static final String MAP_FRAGMENT_TAG = "map";
    private MapViewFragment mapViewFragment = null;

    private RelativeLayout rlProgress;
    private TextView tvProgress;

    @VisibleForTesting
    protected IPresenterFactory presenterFactory = new PresenterFactory(this);

    private ISearchPresenter searchPresenter = presenterFactory.getSearchPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        rlProgress = findViewById(R.id.rlProgress);
        tvProgress = findViewById(R.id.tvProgress);

        searchFragment = new SearchFragment();
        mapViewFragment = new MapViewFragment();

        searchFragment.setOnCityClickListener(onCityClickListener);
        searchFragment.setSearchPresenter(searchPresenter);

        searchPresenter.setupData();
    }

    @Override
    public void onSetupStarted() {
        rlProgress.setVisibility(View.VISIBLE);
        tvProgress.setText(R.string.please_wait);
    }

    @Override
    public void onSetupProgress(String progress) {
        tvProgress.post( () -> tvProgress.setText(progress));
    }

    @Override
    public void onSetupComplete() {
        rlProgress.setVisibility(View.GONE);
        showSearchFragment();
    }

    @Override
    public void onSetupFailed(String reason) {
        rlProgress.setVisibility(View.GONE);
        MessageBox.showMessage(this,
                "Failed to setup the data. Reason: " + reason,
                this::finish);
    }

    @Override
    public void onSearchResults(ArrayList<City> results) {
        searchFragment.updateList(results);
    }

    @Override
    public AssetManager getAssetManager() {
        return getAssets();
    }

    private OnCityClickListener onCityClickListener = this::showMapFragment;

    private void showSearchFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.place_holder, searchFragment, SEARCH_FRAGMENT_TAG);
        transaction.addToBackStack(SEARCH_FRAGMENT_TAG);
        transaction.commitAllowingStateLoss();
    }

    /**
     * Shows the map fragment focusing a particular city.
     * @param city The city to focus.
     */
    private void showMapFragment(City city) {
        mapViewFragment.setCityToFocus(city);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.place_holder, mapViewFragment, MAP_FRAGMENT_TAG);
        transaction.addToBackStack(MAP_FRAGMENT_TAG);
        transaction.commitAllowingStateLoss();
    }

    /**
     * Get the tag string of the fragment which is currently showing on top.
     * @return Tag string value.
     */
    private String currentFragmentTag() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) return "";

        FragmentManager.BackStackEntry entry = getSupportFragmentManager()
                .getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
        return entry.getName();
    }

    @Override
    public void onBackPressed() {
        if(currentFragmentTag().equals(MAP_FRAGMENT_TAG)) {
            // it is showing map currently
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        // abort any ongoing process and release memory
        searchPresenter.destroy();
        super.onDestroy();
    }
}
