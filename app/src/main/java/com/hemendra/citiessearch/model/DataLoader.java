package com.hemendra.citiessearch.model;

import android.content.res.AssetManager;

import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.listeners.DataLoaderListener;
import com.hemendra.citiessearch.model.utils.CustomAsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

public class DataLoader extends CustomAsyncTask<AssetManager, String, Trie> {

    private String reason = "Unknown";
    private DataLoaderListener listener;

    public DataLoader(DataLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    protected Trie doInBackground(AssetManager... params) {
        publishProgress("Opening JSON...");
        AssetManager assetManager = params[0];
        try (InputStream stream = assetManager.open("cities")) {

            // decompress gzip file
            GZIPInputStream gzipInputStream = new GZIPInputStream(stream);

            publishProgress("Parsing Cities...");
            ArrayList<City> cities = CitiesJsonParser.parseJson(gzipInputStream);

            publishProgress("Sorting Cities...");
            Collections.sort(cities, (city1, city2) ->
                    city1.displayName.compareTo(city2.displayName));
            publishProgress("Sorting Complete");

            Trie trie = Trie.getInstance(cities);

            long lastProgressPostedAt = 0;
            for(int i=0; i<cities.size() && !isCancelled(); i++) {

                trie.insert(cities.get(i), i);

                long now = System.currentTimeMillis();
                if(now - lastProgressPostedAt > 100) { // don't update too frequently
                    float percent = ((float) i / (float) cities.size()) * 100f;
                    publishProgress(String.format(Locale.getDefault(),
                            "Setting Up Data (%.2f%%)", percent));
                    lastProgressPostedAt = now;
                }
            }

            if(!isCancelled()) {
                return trie;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            reason = "Failed to Read JSON File";
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            reason = "Failed to Parse JSON";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        listener.onProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(Trie trie) {
        if(trie != null) listener.onDataLoaded(trie);
        else listener.onDataLoadingFailed(reason);
    }
}
