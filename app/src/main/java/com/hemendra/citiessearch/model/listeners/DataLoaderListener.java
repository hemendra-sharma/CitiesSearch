package com.hemendra.citiessearch.model.listeners;

/**
 * The listener for initial data setup process.
 */
public interface DataLoaderListener {
    /**
     * Gets called when the data setup process publishes progress.
     * @param progress The progress message to display.
     */
    void onProgress(String progress);

    /**
     * Gets called when setup process completes.
     * @param structure The data structure used to load data.
     */
    void onDataLoaded(PrefixSearchStructure structure);

    /**
     * Gets called when data loading fails for some reason.
     * @param reason Failure reason to display/
     */
    void onDataLoadingFailed(String reason);
}
