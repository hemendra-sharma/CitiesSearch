package com.hemendra.citiessearch.model.listeners;

import com.hemendra.citiessearch.data.City;

import java.util.ArrayList;

/**
 * The listener for searching city names.
 */
public interface DataSearcherListener {

    /**
     * Gets called when search has finished.
     * @param results The search results.
     */
    void onSearchResults(ArrayList<City> results);
}
