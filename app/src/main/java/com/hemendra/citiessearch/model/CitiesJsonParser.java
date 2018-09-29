package com.hemendra.citiessearch.model;

import android.util.JsonReader;
import android.util.JsonToken;

import com.hemendra.citiessearch.data.City;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CitiesJsonParser {

    /**
     * We don't want to load the huge string in memory, so we are going to read directly
     * from the asset file stream using JsonReader.
     */
    public static ArrayList<City> parseJson(InputStream stream)
            throws IOException, IllegalStateException {
        ArrayList<City> cities = new ArrayList<>();
        InputStreamReader inReader = new InputStreamReader(stream, "UTF-8");
        try (JsonReader reader = new JsonReader(inReader)) {
            reader.beginArray();
            while (reader.hasNext() && !Thread.interrupted()) {
                cities.add(readCity(reader));
            }
            reader.endArray();
        } finally {
            stream.close();
        }
        return cities;
    }

    private static City readCity(JsonReader reader) throws IOException {
        reader.beginObject();
        String country = "", name = "";
        long _id = 0;
        double[] latLon = new double[2];
        while(reader.hasNext() && !Thread.interrupted()) {
            if(reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }
            String next = reader.nextName();
            if(reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }
            switch (next) {
                case "country": {
                    country = reader.nextString();
                    break;
                }
                case "name": {
                    name = reader.nextString();
                    break;
                }
                case "_id": {
                    _id = reader.nextLong();
                    break;
                }
                case "coord": {
                    latLon = getCoords(reader);
                    break;
                }
            }
        }
        reader.endObject();
        return new City(name, country, latLon[0], latLon[1]);
    }

    private static double[] getCoords(JsonReader reader) throws IOException{
        reader.beginObject();
        double[] latLon = new double[2];
        while(reader.hasNext() && !Thread.interrupted()) {
            if (reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }
            String next = reader.nextName();
            if(reader.peek() == JsonToken.NULL) {
                reader.skipValue();
                continue;
            }
            switch (next) {
                case "lat": {
                    latLon[0] = reader.nextDouble();
                    break;
                }
                case "lon": {
                    latLon[1] = reader.nextDouble();
                    break;
                }
            }
        }
        reader.endObject();
        return latLon;
    }

}
