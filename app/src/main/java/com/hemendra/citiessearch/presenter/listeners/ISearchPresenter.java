package com.hemendra.citiessearch.presenter.listeners;

import com.hemendra.citiessearch.data.City;

import java.util.ArrayList;

public interface ISearchPresenter {

    /**
     * Begin setting up the data.
     */
    void setupData();

    /**
     * Aborts everything and destroy all instances to free up the memory.
     */
    void destroy();

    /**
     * Gets called when data setup starts.
     */
    void onLoadingStarted();

    /**
     * Gets called when data loading process publishes a progress message.
     * @param progress The progress message to display.
     */
    void onLoadingProgress(String progress);

    /**
     * Gets called when data setup process finishes.
     */
    void onLoadingComplete();

    /**
     * Gets called when data setup process fails to load data.
     * @param reason The failure reason.
     */
    void onLoadingFailed(String reason);

    /**
     * Start the search process.
     * @param key The search keyword.
     */
    void performSearch(String key);

    /**
     * Gets called when search finishes.
     * @param cities The search results.
     */
    void onSearchResults(ArrayList<City> cities);
}
