package com.hemendra.citiessearch.model.listeners;

import android.content.res.AssetManager;

public interface IDataSource {

    /**
     * Starts loading the data.
     * @param assetManager the {@link AssetManager} fetched from activity class.
     */
    void loadData(AssetManager assetManager);

    /**
     * Starts searching for given key.
     * @param key The search keyword.
     */
    void performSearch(String key);

    /**
     * Stops all ongoing processes and destroy the data source instance.
     */
    void destroy();
}
