package com.hemendra.citiessearch.view.listeners;

import android.content.res.AssetManager;

import com.hemendra.citiessearch.data.City;

import java.util.ArrayList;

public interface ICitiesActivity {
    void onSetupStarted();
    void onSetupProgress(String progress);
    void onSetupComplete();
    void onSetupFailed(String reason);

    void onSearchResults(ArrayList<City> cities);

    AssetManager getAssetManager();
}
