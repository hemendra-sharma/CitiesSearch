package com.hemendra.citiessearch.presenter.listeners;

import com.hemendra.citiessearch.data.City;

import java.util.ArrayList;

public interface ISearchPresenter {
    void setupData();
    void destroy();

    void onLoadingStarted();
    void onLoadingProgress(String progress);
    void onLoadingComplete();
    void onLoadingFailed(String reason);

    void performSearch(String key);
    void onSearchResults(ArrayList<City> cities);
}
