package com.hemendra.citiessearch.model;

import android.content.res.AssetManager;

import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.listeners.DataLoaderListener;
import com.hemendra.citiessearch.model.listeners.DataSearcherListener;
import com.hemendra.citiessearch.model.listeners.IDataSource;
import com.hemendra.citiessearch.presenter.listeners.ISearchPresenter;

import java.util.ArrayList;

public class DataSource implements IDataSource, DataLoaderListener, DataSearcherListener {

    private ISearchPresenter searchPresenter;
    private DataLoader loader = null;
    private DataSearcher searcher = null;
    private Trie trie = null;

    public DataSource(ISearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

    @Override
    public void loadData(AssetManager assetManager) {
        if(searchPresenter == null) return;

        if(loader != null && loader.isExecuting()) loader.cancel(true);

        loader = new DataLoader(this);
        loader.execute(assetManager);

        searchPresenter.onLoadingStarted();
    }

    @Override
    public void performSearch(String key) {
        if(searchPresenter == null) return;

        if(searcher != null && searcher.isExecuting()) searcher.cancel(true);

        searcher = new DataSearcher(trie, this);
        searcher.execute(key);
    }

    @Override
    public void onProgress(String progress) {
        if(searchPresenter == null) return;

        searchPresenter.onLoadingProgress(progress);
    }

    @Override
    public void onSearchResults(ArrayList<City> results) {
        if(searchPresenter == null) return;

        searchPresenter.onSearchResults(results);
    }

    @Override
    public void onDataLoaded(Trie trie) {
        if(searchPresenter == null) return;

        this.trie = trie;
        searchPresenter.onLoadingComplete();
    }

    @Override
    public void onDataLoadingFailed(String reason) {
        if(searchPresenter == null) return;

        searchPresenter.onLoadingFailed(reason);
    }

    @Override
    public void destroy() {
        searchPresenter = null;

        if(trie != null) trie.destroy();
        trie = null;

        if(loader != null) loader.cancel(true);
        loader = null;
    }
}
