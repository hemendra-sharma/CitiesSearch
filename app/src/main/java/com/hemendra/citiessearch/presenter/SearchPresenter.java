package com.hemendra.citiessearch.presenter;

import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.DataSource;
import com.hemendra.citiessearch.model.listeners.IDataSource;
import com.hemendra.citiessearch.presenter.listeners.ISearchPresenter;
import com.hemendra.citiessearch.view.listeners.ICitiesActivity;

import java.util.ArrayList;

public class SearchPresenter implements ISearchPresenter {

    private ICitiesActivity ICitiesActivity;
    private IDataSource dataSource = new DataSource(this);

    public SearchPresenter(ICitiesActivity ICitiesActivity) {
        this.ICitiesActivity = ICitiesActivity;
    }

    @Override
    public void setupData() {
        if(dataSource != null) dataSource.loadData(ICitiesActivity.getAssetManager());
    }

    @Override
    public void destroy() {
        if(dataSource != null) dataSource.destroy();
        dataSource = null;
        ICitiesActivity = null;
    }

    @Override
    public void onLoadingStarted() {
        if(ICitiesActivity != null) ICitiesActivity.onSetupStarted();
    }

    @Override
    public void onLoadingProgress(String progress) {
        if(ICitiesActivity != null) ICitiesActivity.onSetupProgress(progress);
    }

    @Override
    public void onLoadingComplete() {
        if(ICitiesActivity != null) ICitiesActivity.onSetupComplete();
    }

    @Override
    public void onLoadingFailed(String reason) {
        if(ICitiesActivity != null) ICitiesActivity.onSetupFailed(reason);
    }

    @Override
    public void performSearch(String key) {
        if(dataSource != null) dataSource.performSearch(key);
    }

    @Override
    public void onSearchResults(ArrayList<City> cities) {
        if(ICitiesActivity != null) ICitiesActivity.onSearchResults(cities);
    }
}
