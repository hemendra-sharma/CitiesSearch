package com.hemendra.citiessearch.model.listeners;

public interface DataLoaderListener {
    void onProgress(String progress);
    void onDataLoaded(PrefixSearchStructure structure);
    void onDataLoadingFailed(String reason);
}
