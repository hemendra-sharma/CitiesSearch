package com.hemendra.citiessearch.model.structures;

import android.util.Log;

import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.listeners.PrefixSearchStructure;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Radix data structure is quite similar to Trie, with just one modification. We eliminate the
 * useless nodes. These are such nodes who have only 1 parent and 1 child.
 *
 * For example: in the word "Albuquerque", we will find there are several other cities
 * starting with "Albu...". But when user try to search for "Albuq..." there will be only 1
 * match. But in the Trie structure, the remaining nodes ("uerque") will still occupy there space,
 * which is useless because we already know how this is going to end.
 *
 * So, using Radix we can reduce those useless tails of words.
 *
 * And, yes this helped to get app RAM usage down significantly.
 */
public class Radix extends PrefixSearchStructure {

    // a root node which can contain several children and it does not belong to any city
    private RadixNode root = new RadixNode();

    private Radix(ArrayList<City> cities) {
        super(cities);
    }

    private static Radix instance = null;
    public static Radix getInstance(ArrayList<City> cities) {
        if(instance == null) instance = new Radix(cities);
        return instance;
    }

    @Override
    public void insert(City city, int cityIndex) {
        String word = city.displayName.toLowerCase();

        RadixNode node = root;
        int i = 0;

        while (i < word.length() && node.labels.get((int) word.charAt(i)) != null) {
            int ascii = (int) word.charAt(i), j = 0;
            StringBuilder label = node.labels.get(ascii);

            while (j < label.length() && i < word.length() && label.charAt(j) == word.charAt(i)) {
                i++;
                j++;
            }

            if (j == label.length()) {
                node = node.children.get(ascii);
            } else {
                if (i == word.length()) {
                    // inserting a prefix of existing word
                    RadixNode existingChild = node.children.get(ascii);

                    RadixNode newChild = new RadixNode();
                    newChild.cityIndex = cityIndex; // is complete word

                    // making "Albuquerque" as "Al"
                    label.setLength(j);


                    StringBuilder remainingLabel = strCopy(label, j);

                    // new node for "Al"
                    node.children.put(ascii, newChild);

                    newChild.children.put((int) remainingLabel.charAt(0), existingChild);
                    newChild.labels.put((int) remainingLabel.charAt(0), remainingLabel);
                } else {
                    // inserting word which has a partial match with existing word

                    //e.g. exiting => Albuquerque
                    // and new => Alabama

                    // making 'Albuquerque' as 'buquerque'
                    StringBuilder remainingLabel = strCopy(label, j);

                    // making 'Alabama' as 'abama'
                    StringBuilder remainingWord = strCopy(word, i);

                    // exiting node for 'buquerque'
                    RadixNode temp = node.children.get(ascii);

                    // new node for "Al"
                    RadixNode newChild = new RadixNode();
                    node.children.put(ascii, newChild);

                    // making 'Albuquerque' as 'Al'
                    label.setLength(j);

                    newChild.children.put((int) remainingLabel.charAt(0), temp);
                    newChild.labels.put((int) remainingLabel.charAt(0), remainingLabel);

                    // new node for 'abama'
                    RadixNode n = new RadixNode();
                    n.cityIndex = cityIndex;
                    newChild.children.put((int) remainingWord.charAt(0), n);
                    newChild.labels.put((int) remainingWord.charAt(0), remainingWord);
                }

                return;
            }
        }

        if (i < word.length()) {
            // inserting new node for new word
            node.labels.put((int) word.charAt(i), strCopy(word, i));
            RadixNode n = new RadixNode();
            n.cityIndex = cityIndex;
            node.children.put((int) word.charAt(i), n);
        } else {
            // inserting "Alb" when "Alabama" and "Albuquerque" are existing
            node.cityIndex = cityIndex;
        }
    }

    // Creates a new String from an existing
    // string starting from the given index
    private StringBuilder strCopy(CharSequence str, int index) {
        StringBuilder result = new StringBuilder();
        while (index < str.length()) {
            result.append(str.charAt(index));
            index++;
        }
        return result;
    }

    @Override
    public ArrayList<City> search(String word) {
        int length = word.length();
        if(length == 0)
            return cities; // return all cities if searched with empty string

        word = word.toLowerCase();

        int i = 0;

        RadixNode node = root;
        City extraCity = null;

        if(node.labels.get((int) word.charAt(i)) == null)
            return new ArrayList<>(); // no match from the very beginning

        while (i < length && node.labels.get((int) word.charAt(i)) != null) {
            int ascii = (int) word.charAt(i);

            if (node.children.get(ascii) == null)
                return new ArrayList<>(); // tree end reached.

            StringBuilder label = node.labels.get(ascii);
            Log.d("label", "=> "+label.toString()+" ("+label.length()+")");

            int j = 0;
            while (i < length && j < label.length()) {
                if (word.charAt(i) != label.charAt(j)) {
                    return new ArrayList<>(); // character mismatch
                }

                i++;
                j++;
            }

            if (i <= length) {
                node = node.children.get(ascii); // traverse further
            } else if(node.cityIndex >= 0) {
                extraCity = cities.get(node.cityIndex);
            }

            if(i < length && node.labels.get((int) word.charAt(i)) == null)
                return new ArrayList<>(); // no match for the next character
        }

        ArrayList<City> filteredCities = getAllCitiesBelowNode(node);
        if(node.cityIndex >= 0) filteredCities.add(cities.get(node.cityIndex));
        if(extraCity != null) filteredCities.add(extraCity);

        Collections.sort(filteredCities, (city1, city2) ->
                city1.displayName.compareTo(city2.displayName));

        return filteredCities;
    }

    private ArrayList<City> getAllCitiesBelowNode(RadixNode node) {
        ArrayList<City> filteredCities = new ArrayList<>();
        if(node != null) {
            for (RadixNode n : node.children.values()) {
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
        instance = null;
    }

}
