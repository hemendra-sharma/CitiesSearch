package com.hemendra.citiessearch.model.structures;

import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.listeners.PrefixSearchStructure;

import java.util.ArrayList;
import java.util.Collections;

/**
 * We are going to implement the well known algorithm 'Trie' because it is an efficient
 * information retrieval data structure. Using Trie, search complexities can be brought to
 * optimal limit (key length).
 *
 * Using Trie, we can search the key in O(M) time; where M is maximum string length.
 *
 * We are going to use Singleton pattern because we want only 1 instance of this
 * class to be created ever.
 *
 * LATE NOTE:
 *
 * I found that, although Trie is really fast for searching, it uses a lot of space.
 * The used RAM gets bigger where there is too much variation in data and the data-set is huge.
 *
 * So, I decided to use Radix instead of Trie. I know that Radix is (a bit) slower than Trie,
 * but this slowness cannot be noticed visually.
 *
 */
public class Trie extends PrefixSearchStructure {

    // a root node which can contain several children and it does not belong to any city
    private TrieNode root = new TrieNode();

    private Trie(ArrayList<City> cities) {
        super(cities);
    }

    private static Trie instance = null;
    public static Trie getInstance(ArrayList<City> cities) {
        if(instance == null) instance = new Trie(cities);
        return instance;
    }

    @Override
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

    @Override
    public ArrayList<City> search(String key) {
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

        // tree end not reached, which means there is some data under this node
        ArrayList<City> filteredCities = getAllCitiesBelowNode(node);
        if(node.cityIndex >= 0) filteredCities.add(cities.get(node.cityIndex));

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
                if (n.cityIndex >= 0) {
                    filteredCities.add(cities.get(n.cityIndex));
                }
                filteredCities.addAll(getAllCitiesBelowNode(n));
            }
        }
        return filteredCities;
    }

    /**
     * Set the instance to null. This should be called when user exits the app.
     */
    @Override
    public void destroy() {
        instance.cities.clear();
        instance.root = null;
        instance = null;
    }

}
