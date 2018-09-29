package com.hemendra.citiessearch.data;

import com.google.android.gms.maps.model.LatLng;

public class City {

    public String displayName;
    public double latitude;
    public double longitude;

    public City(String name, String country, double latitude, double longitude) {
        this.displayName = name + ", " + country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

}
