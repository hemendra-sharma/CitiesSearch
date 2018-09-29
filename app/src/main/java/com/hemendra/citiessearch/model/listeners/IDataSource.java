package com.hemendra.citiessearch.model.listeners;

import android.content.res.AssetManager;

public interface IDataSource {
    void loadData(AssetManager assetManager);
    void performSearch(String key);
    void destroy();
}
