package com.hemendra.citiessearch.model;

import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.listeners.DataSearcherListener;
import com.hemendra.citiessearch.model.utils.CustomAsyncTask;

import java.util.ArrayList;

public class DataSearcher extends CustomAsyncTask<String, Void, ArrayList<City>> {

    private Trie trie;
    private DataSearcherListener listener;

    public DataSearcher(Trie trie, DataSearcherListener listener) {
        this.trie = trie;
        this.listener = listener;
    }

    @Override
    protected ArrayList<City> doInBackground(String... params) {
        return trie.search(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<City> results) {
        listener.onSearchResults(results);
    }
}
