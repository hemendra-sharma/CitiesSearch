package com.hemendra.citiessearch.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hemendra.citiessearch.R;
import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.view.listeners.OnCityClickListener;

import java.util.ArrayList;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private ArrayList<City> cities;
    private OnCityClickListener listener;

    public SearchResultsAdapter(ArrayList<City> cities, OnCityClickListener listener) {
        this.cities = cities;
        this.listener = listener;
    }

    public void setData(ArrayList<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.city_list_item, viewGroup, false);
        return new SearchResultViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder searchResultViewHolder, int i) {
        searchResultViewHolder.setCity(cities.get(i));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
