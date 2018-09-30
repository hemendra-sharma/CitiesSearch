package com.hemendra.citiessearch.view.listeners;

import android.content.res.AssetManager;

import com.hemendra.citiessearch.data.City;

import java.util.ArrayList;

public interface ICitiesActivity {

    /**
     * Gets called when the data setup process has started.
     */
    void onSetupStarted();

    /**
     * Gets called to get the data setup process progress update.
     * @param progress The progress message to display.
     */
    void onSetupProgress(String progress);

    /**
     * Gets called when the data setup process is completed.
     */
    void onSetupComplete();

    /**
     * Gets called when the data setup process fails.
     * @param reason The failure reason.
     */
    void onSetupFailed(String reason);

    /**
     * Gets called when it received the search results.
     * @param cities The search results.
     */
    void onSearchResults(ArrayList<City> cities);

    /**
     * Retrieves the {@link AssetManager} from activity.
     * @return The instance of {@link AssetManager}
     */
    AssetManager getAssetManager();
}
