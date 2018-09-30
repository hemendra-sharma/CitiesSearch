package com.hemendra.citiessearch.model.listeners;

import com.hemendra.citiessearch.data.City;

import java.util.ArrayList;

public abstract class PrefixSearchStructure {

    // we are going to hold the reference to the cities in a separate array to reduce
    // space usage and duplicate object creation.
    protected ArrayList<City> cities;

    protected PrefixSearchStructure(ArrayList<City> cities) {
        this.cities = cities;
    }

    /**
     * Insert the city name into the tree.
     *
     * @param city The city to be added to structure
     * @param index index of the actual city object in the "cities" array
     */
    public abstract void insert(City city, int index);

    /**
     * Search and returns a list of matching cities sorted alphabetically.
     *
     * @param key The keyword to search
     * @return If 'key' is empty, then it will return the complete 'cities' array. Else, it
     * will search crawl through the tree to find the best matching node (deepest node)
     * and then it will build the array of cities below that node.
     */
    public abstract ArrayList<City> search(String key);

    public abstract void destroy();
}
