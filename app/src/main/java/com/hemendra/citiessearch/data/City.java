package com.hemendra.citiessearch.data;

import com.google.android.gms.maps.model.LatLng;

public class City {

    /**
     * The text which will be displayed to user in the list.
     */
    public String displayName;

    /**
     * GPS Latitude
     */
    public double latitude;

    /**
     * GPS Longitude
     */
    public double longitude;

    public City(String name, String country, double latitude, double longitude) {
        this.displayName = name + ", " + country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Converts the double latitude, longitude values to LatLng instance.
     * @return The instance of LatLng which can be used by Google Map.
     */
    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

}
