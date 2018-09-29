package com.hemendra.citiessearch.view;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hemendra.citiessearch.R;
import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.presenter.listeners.ISearchPresenter;
import com.hemendra.citiessearch.presenter.SearchPresenter;
import com.hemendra.citiessearch.view.fragments.MapViewFragment;
import com.hemendra.citiessearch.view.fragments.SearchFragment;
import com.hemendra.citiessearch.view.listeners.OnCityClickListener;
import com.hemendra.citiessearch.view.listeners.ICitiesActivity;

import java.util.ArrayList;

public class CitiesActivity extends AppCompatActivity implements ICitiesActivity {

    private final String SEARCH_FRAGMENT_TAG = "search";
    private SearchFragment searchFragment = null;

    private final String MAP_FRAGMENT_TAG = "map";
    private MapViewFragment mapViewFragment = null;

    private RelativeLayout rlProgress;
    private TextView tvProgress;

    private ISearchPresenter setupPresenter = new SearchPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        rlProgress = findViewById(R.id.rlProgress);
        tvProgress = findViewById(R.id.tvProgress);

        searchFragment = new SearchFragment();
        mapViewFragment = new MapViewFragment();

        searchFragment.setOnCityClickListener(onCityClickListener);
        searchFragment.setSearchPresenter(setupPresenter);

        setupPresenter.setupData();
    }

    @Override
    public void onSetupStarted() {
        rlProgress.setVisibility(View.VISIBLE);
        tvProgress.setText("Please Wait");
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

    private void showMapFragment(City city) {
        mapViewFragment.setCityToFocus(city);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.place_holder, mapViewFragment, MAP_FRAGMENT_TAG);
        transaction.addToBackStack(MAP_FRAGMENT_TAG);
        transaction.commitAllowingStateLoss();
    }

    private String currentFragmentTag() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) return "";

        FragmentManager.BackStackEntry entry = getSupportFragmentManager()
                .getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
        return entry.getName();
    }

    @Override
    public void onBackPressed() {
        if(currentFragmentTag().equals(MAP_FRAGMENT_TAG)) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        setupPresenter.destroy();
        super.onDestroy();
    }
}
