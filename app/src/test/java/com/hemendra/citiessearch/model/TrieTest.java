package com.hemendra.citiessearch.model;

import com.hemendra.citiessearch.data.City;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class TrieTest {

    @Test
    public void emptySearchTest() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Denver,", "US", 0, 0));
        cities.add(new City("Sydney,", "Australia", 0, 0));
        Trie trie = Trie.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            trie.insert(cities.get(i), i);

        ArrayList<City> results = trie.search("");

        // should return all the results
        assertEquals(2, results.size());

        trie.destroy();
    }

    @Test
    public void searchTest1() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Trie trie = Trie.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            trie.insert(cities.get(i), i);

        // search for "A"
        ArrayList<City> results = trie.search("A");

        // all cities but Sydney should appear
        assertEquals(4, results.size());

        // and it should be sorted correctly
        assertEquals("Alabama, US",     results.get(0).displayName);
        assertEquals("Albuquerque, US", results.get(1).displayName);
        assertEquals("Anaheim, US",     results.get(2).displayName);
        assertEquals("Arizona, US",     results.get(3).displayName);

        trie.destroy();
    }

    @Test
    public void searchTest2() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Trie trie = Trie.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            trie.insert(cities.get(i), i);

        // search for "Al"
        ArrayList<City> results = trie.search("Al");

        // all cities which starts with "Al" should appear
        assertEquals(2, results.size());

        // and it should be sorted correctly
        assertEquals("Alabama, US",     results.get(0).displayName);
        assertEquals("Albuquerque, US", results.get(1).displayName);

        trie.destroy();
    }

    @Test
    public void searchTest3() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Trie trie = Trie.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            trie.insert(cities.get(i), i);

        // search for "Alb"
        ArrayList<City> results = trie.search("Alb");

        // all cities which starts with "Alb" should appear
        assertEquals(1, results.size());

        // and it should be sorted correctly
        assertEquals(results.get(0).displayName, "Albuquerque, US");

        trie.destroy();
    }

    @Test
    public void searchTest4() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Trie trie = Trie.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            trie.insert(cities.get(i), i);

        // search for "New"
        ArrayList<City> results = trie.search("New");

        // no result should come
        assertEquals(0, results.size());

        trie.destroy();
    }

    @Test
    public void duplicateCityTest() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Alabama", "US", 0, 0));
        Trie trie = Trie.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            trie.insert(cities.get(i), i);

        // search for "Al"
        ArrayList<City> results = trie.search("Al");

        // Trie should eliminate the duplicates automatically
        // and it should return only 1 result
        assertEquals(1, results.size());

        trie.destroy();
    }

    @Test
    public void hugeDataTest() {
        ArrayList<City> cities = new ArrayList<>();
        System.out.println("Building Data...");
        for(int i=0; i<10000; i++)
            cities.add(new City(randomString(20), randomString(2), 0, 0));

        System.out.println("Setting Up Trie...");
        long start = System.currentTimeMillis();
        Trie trie = Trie.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            trie.insert(cities.get(i), i);
        long diff = System.currentTimeMillis() - start;
        System.out.println("Time taken to setup trie: "+diff+" ms");

        System.out.println("testing...");
        int N = 1 + new Random().nextInt(10);
        String key = randomString(N);
        System.out.println("Searching for: " + key);

        // search for 'key'
        start = System.currentTimeMillis();
        ArrayList<City> results = trie.search(key);
        System.out.println("Found: " + results.size() + " results");
        diff = System.currentTimeMillis() - start;
        System.out.println("Time taken to perform search: "+diff+" ms");

        System.out.println("Time Complexity: "+trie.getLastSearchTimeComplexity());

        trie.destroy();
    }

    private String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<length; i++) {
            sb.append((char) ('a' + new Random().nextInt(26)));
        }
        return sb.toString();
    }

    @Test
    public void timeComplexityTest() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Trie trie = Trie.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            trie.insert(cities.get(i), i);

        String searchKeyword = "Albuquerque";

        // search for 'Albuquerque'
        ArrayList<City> results = trie.search(searchKeyword);
        System.out.println("Found: " + results.size() + " results");

        System.out.println("Time Complexity: "+trie.getLastSearchTimeComplexity());

        long N = searchKeyword.length();

        // time complexity should be smaller than N * log(N)
        assertTrue(trie.getLastSearchTimeComplexity() <= N * Math.log(N));

        trie.destroy();
    }
}