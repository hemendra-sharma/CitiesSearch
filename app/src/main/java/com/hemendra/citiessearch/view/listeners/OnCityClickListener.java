package com.hemendra.citiessearch.view.listeners;

import com.hemendra.citiessearch.data.City;

@FunctionalInterface
public interface OnCityClickListener {
    void onCityClicked(City city);
}
