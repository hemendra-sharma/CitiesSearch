package com.hemendra.citiessearch.model.listeners;

import com.hemendra.citiessearch.model.Trie;

public interface DataLoaderListener {
    void onProgress(String progress);
    void onDataLoaded(Trie trie);
    void onDataLoadingFailed(String reason);
}
