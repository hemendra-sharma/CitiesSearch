package com.hemendra.citiessearch.model;

import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.listeners.DataSearcherListener;
import com.hemendra.citiessearch.model.listeners.PrefixSearchStructure;
import com.hemendra.citiessearch.model.utils.CustomAsyncTask;

import java.util.ArrayList;

class DataSearcher extends CustomAsyncTask<String, Void, ArrayList<City>> {

    private PrefixSearchStructure structure;
    private DataSearcherListener listener;

    DataSearcher(PrefixSearchStructure structure, DataSearcherListener listener) {
        this.structure = structure;
        this.listener = listener;
    }

    @Override
    protected ArrayList<City> doInBackground(String... params) {
        return structure.search(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<City> results) {
        listener.onSearchResults(results);
    }
}
