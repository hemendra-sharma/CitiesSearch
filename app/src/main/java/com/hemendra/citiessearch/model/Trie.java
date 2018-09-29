package com.hemendra.citiessearch.model;

import com.hemendra.citiessearch.data.City;

import java.util.ArrayList;
import java.util.Collections;

/**
 * We are going to implement the well known algorithm 'Trie' because it is an efficient
 * information retrieval data structure. Using Trie, search complexities can be brought to
 * optimal limit (key length).
 *
 * Using Trie, we can search the key in O(M) time; where M is maximum string length.
 *
 * If any list of words is {their, there, answer, any, bye}
 * then its Trie structure would look like this:
 *
 *                        root
 *                     /    |      \
 *                     t    a       b
 *                     |    |       |
 *                     h    n       y
 *                     |    |  \    |
 *                     e    s   y   e
 *                  /  |    |
 *                  i  r    w
 *                  |  |    |
 *                  r  e    e
 *                          |
 *                          r
 *
 * We are going to use Singleton pattern because we want only 1 instance of this
 * class to be created ever.
 */
public class Trie {

    // a root node which can contain several children and it does not belong to any city
    private TrieNode root = new TrieNode();

    // we are going to hold the reference to the cities in a separate array to reduce
    // space usage and duplicate object creation.
    private ArrayList<City> cities;

    // store the time complexity for last search operation
    private long lastSearchTimeComplexity = 0;

    private Trie(ArrayList<City> cities) {
        this.cities = cities;
    }

    private static Trie instance = null;
    public static Trie getInstance(ArrayList<City> cities) {
        if(instance == null) instance = new Trie(cities);
        return instance;
    }

    /**
     * Insert the city name into the tree.
     *
     * @param city The city to be added to structure
     * @param index index of the actual city object in the "cities" array
     */
    public void insert(City city, int index) {
        String displayName = city.displayName.toLowerCase();
        int length = displayName.length();

        TrieNode node = root;

        for (int level = 0; level < length; level++) {
            int ascii = (int) displayName.charAt(level);
            if (node.children.get(ascii) == null)
                node.children.put(ascii, new TrieNode());

            node = node.children.get(ascii);
        }

        // mark last node as leaf (means it is a complete city name)
        node.cityIndex = index;
    }

    /**
     * Search and returns a list of matching cities sorted alphabetically.
     *
     * @param key The keyword to search
     * @return If 'key' is empty, then it will return the complete 'cities' array. Else, it
     * will search crawl through the tree to find the best matching node (deepest node)
     * and then it will build the array of cities below that node.
     */
    public ArrayList<City> search(String key) {
        lastSearchTimeComplexity = 0;

        int length = key.length();
        if(length == 0)
            return cities; // return all cities if searched with empty string

        key = key.toLowerCase();

        TrieNode node = root;

        int level;

        for(level = 0; level < length; level++) {
            int ascii = (int) key.charAt(level);

            if (node.children.get(ascii) == null)
                return new ArrayList<>(); // tree end reached.

            node = node.children.get(ascii);
        }

        lastSearchTimeComplexity = level;

        // tree end not reached, which means there is some data under this node
        ArrayList<City> filteredCities = getAllCitiesBelowNode(node);
        Collections.sort(filteredCities, (city1, city2) ->
                city1.displayName.compareTo(city2.displayName));
        return filteredCities;
    }

    /**
     * Crawl down the level below given node and return the array of cities.
     * @param node The starting node to start crawling from.
     * @return Array of cities.
     */
    private ArrayList<City> getAllCitiesBelowNode(TrieNode node) {
        ArrayList<City> filteredCities = new ArrayList<>();
        if(node != null) {
            for (TrieNode n : node.children.values()) {
                lastSearchTimeComplexity++;
                if (n.cityIndex >= 0) {
                    filteredCities.add(cities.get(n.cityIndex));
                }
                filteredCities.addAll(getAllCitiesBelowNode(n));
            }
        }
        return filteredCities;
    }

    public long getLastSearchTimeComplexity() {
        return lastSearchTimeComplexity;
    }

    /**
     * Set the instance to null. This should be called when user exits the app.
     */
    public void destroy() {
        instance = null;
    }

}
