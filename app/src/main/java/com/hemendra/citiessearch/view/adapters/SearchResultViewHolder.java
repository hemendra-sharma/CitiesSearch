package com.hemendra.citiessearch.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hemendra.citiessearch.R;
import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.view.listeners.OnCityClickListener;

import java.util.Locale;

public class SearchResultViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCityName, tvLatLong;
    private City city = null;

    public SearchResultViewHolder(@NonNull View itemView,
                                  @NonNull OnCityClickListener listener) {
        super(itemView);
        tvCityName = itemView.findViewById(R.id.tvCityName);
        tvLatLong = itemView.findViewById(R.id.tvLatLong);

        itemView.setOnClickListener( v -> {
            if(city != null) listener.onCityClicked(city);
        });
    }

    public void setCity(City city) {
        this.city = city;
        tvCityName.setText(city.displayName);
        tvLatLong.setText(String.format(Locale.getDefault(),
                "%.4f, %.4f", city.latitude, city.longitude));
    }

}
