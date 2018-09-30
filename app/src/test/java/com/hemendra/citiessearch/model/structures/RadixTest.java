package com.hemendra.citiessearch.model.structures;

import com.hemendra.citiessearch.data.City;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class RadixTest {

    @Test
    public void emptySearchTest() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Denver,", "US", 0, 0));
        cities.add(new City("Sydney,", "Australia", 0, 0));
        Radix radix = Radix.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            radix.insert(cities.get(i), i);

        ArrayList<City> results = radix.search("");

        // should return all the results
        assertEquals(2, results.size());

        radix.destroy();
    }

    @Test
    public void searchTestValidInput_1() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Radix radix = Radix.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            radix.insert(cities.get(i), i);

        // search for "A"
        ArrayList<City> results = radix.search("A");

        // all cities but Sydney should appear
        assertEquals(4, results.size());

        // and it should be sorted correctly
        assertEquals("Alabama, US",     results.get(0).displayName);
        assertEquals("Albuquerque, US", results.get(1).displayName);
        assertEquals("Anaheim, US",     results.get(2).displayName);
        assertEquals("Arizona, US",     results.get(3).displayName);

        radix.destroy();
    }

    @Test
    public void searchTestValidInput_2() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Radix radix = Radix.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            radix.insert(cities.get(i), i);

        // search for "Al"
        ArrayList<City> results = radix.search("Al");

        // all cities which starts with "Al" should appear
        assertEquals(2, results.size());

        // and it should be sorted correctly
        assertEquals("Alabama, US",     results.get(0).displayName);
        assertEquals("Albuquerque, US", results.get(1).displayName);

        radix.destroy();
    }

    @Test
    public void searchTestValidInput_3() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Radix radix = Radix.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            radix.insert(cities.get(i), i);

        // search for "Alb"
        ArrayList<City> results = radix.search("Alb");

        // all cities which starts with "Alb" should appear
        assertEquals(1, results.size());

        // and it should be sorted correctly
        assertEquals(results.get(0).displayName, "Albuquerque, US");

        radix.destroy();
    }

    @Test
    public void searchTestInvalidInput() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Radix radix = Radix.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            radix.insert(cities.get(i), i);

        // search for "New"
        ArrayList<City> results = radix.search("New");

        // no result should come
        assertEquals(0, results.size());

        radix.destroy();
    }

    @Test
    public void searchTestFullMatch() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Radix radix = Radix.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            radix.insert(cities.get(i), i);

        // search for "Alabama, US"
        ArrayList<City> results = radix.search("Alabama, US");

        // no result should come
        assertEquals(1, results.size());

        radix.destroy();
    }

    @Test
    public void caseSensitivityTest() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Albuquerque", "US", 0, 0));
        cities.add(new City("Anaheim", "US", 0, 0));
        cities.add(new City("Arizona", "US", 0, 0));
        cities.add(new City("Sydney", "US", 0, 0));
        Radix radix = Radix.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            radix.insert(cities.get(i), i);

        // search for "Alb"
        ArrayList<City> results = radix.search("Alb");

        // all cities which starts with "Alb" should appear
        assertEquals(1, results.size());

        // and it should be sorted correctly
        assertEquals(results.get(0).displayName, "Albuquerque, US");

        // search for "aLb"
        results = radix.search("aLb");

        // all cities which starts with "aLb" should appear
        assertEquals(1, results.size());

        // and it should be sorted correctly
        assertEquals(results.get(0).displayName, "Albuquerque, US");

        radix.destroy();
    }

    @Test
    public void duplicateCityTest() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Alabama", "US", 0, 0));
        cities.add(new City("Alabama", "US", 0, 0));
        Radix radix = Radix.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            radix.insert(cities.get(i), i);

        // search for "Al"
        ArrayList<City> results = radix.search("Al");

        // Radix should eliminate the duplicates automatically
        // and it should return only 1 result
        assertEquals(1, results.size());

        radix.destroy();
    }

    @Test
    public void hugeDataTest() {
        ArrayList<City> cities = new ArrayList<>();
        System.out.println("Building Data...");
        for(int i=0; i<10000; i++)
            cities.add(new City(randomString(20), randomString(2), 0, 0));

        System.out.println("Setting Up Radix...");
        long start = System.currentTimeMillis();
        Radix radix = Radix.getInstance(cities);
        for(int i=0; i<cities.size(); i++)
            radix.insert(cities.get(i), i);
        long diff = System.currentTimeMillis() - start;
        System.out.println("Time taken to setup radix: "+diff+" ms");

        System.out.println("testing...");
        int N = 1 + new Random().nextInt(10);
        String key = randomString(N);
        System.out.println("Searching for: " + key);

        // search for 'key'
        start = System.currentTimeMillis();
        ArrayList<City> results = radix.search(key);
        System.out.println("Found: " + results.size() + " results");
        diff = System.currentTimeMillis() - start;
        System.out.println("Time taken to perform search: "+diff+" ms");

        radix.destroy();
    }

    private String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<length; i++) {
            sb.append((char) ('a' + new Random().nextInt(26)));
        }
        return sb.toString();
    }

}