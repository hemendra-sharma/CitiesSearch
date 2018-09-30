package com.hemendra.citiessearch.model;

import android.content.res.AssetManager;

import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.listeners.DataLoaderListener;
import com.hemendra.citiessearch.model.listeners.PrefixSearchStructure;
import com.hemendra.citiessearch.model.structures.PrefixSearchStructureFactory;
import com.hemendra.citiessearch.model.structures.StructureType;
import com.hemendra.citiessearch.model.utils.CustomAsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

class DataLoader extends CustomAsyncTask<AssetManager, String, PrefixSearchStructure> {

    private String reason = "Unknown";
    private DataLoaderListener listener;

    DataLoader(DataLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    protected PrefixSearchStructure doInBackground(AssetManager... params) {
        publishProgress("Opening JSON...");
        reason = "Failed to open the JSON file.";
        AssetManager assetManager = params[0];
        try (InputStream stream = assetManager.open("cities")) {

            // decompress gzip file
            GZIPInputStream gzipInputStream = new GZIPInputStream(stream);

            reason = "Failed to parse cities.";
            publishProgress("Parsing Cities...");
            ArrayList<City> cities = CitiesJsonParser.parseJson(gzipInputStream);

            gzipInputStream.close();

            reason = "Failed to sort cities.";
            publishProgress("Sorting Cities...");
            Collections.sort(cities, (city1, city2) ->
                    city1.displayName.compareTo(city2.displayName));

            reason = "Failed to build data structure.";
            PrefixSearchStructure trie = PrefixSearchStructureFactory
                    .getStructure(StructureType.RADIX, cities);

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
            reason = "Aborted";
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
    protected void onPostExecute(PrefixSearchStructure structure) {
        if(structure != null) listener.onDataLoaded(structure);
        else listener.onDataLoadingFailed(reason);
    }
}
