package com.hemendra.citiessearch.model.listeners;

import com.hemendra.citiessearch.data.City;

import java.util.ArrayList;

public interface DataSearcherListener {
    void onSearchResults(ArrayList<City> results);
}
