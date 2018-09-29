package com.hemendra.citiessearch.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hemendra.citiessearch.R;
import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.model.utils.CustomAsyncTask;

public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;

    private View savedView = null;
    private TextView tvCityName = null;
    private GoogleMap mMap = null;

    private City cityToFocus = null;
    public void setCityToFocus(City city) {
        this.cityToFocus = city;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedView != null) return savedView;

        return savedView = inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (mapFragment == null) {
            new LoadMapFragment().execute();
        } else {
            setupMapFragment();
        }

        tvCityName = view.findViewById(R.id.tvCityName);
    }

    private class LoadMapFragment extends CustomAsyncTask<Void,Void,SupportMapFragment> {
        @Override
        protected SupportMapFragment doInBackground(Void... voids) {
            return SupportMapFragment.newInstance();
        }

        @Override
        protected void onPostExecute(SupportMapFragment supportMapFragment) {
            mapFragment = supportMapFragment;
            setupMapFragment();
        }
    }

    private void setupMapFragment() {
        mapFragment.getMapAsync(this);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.map_container, mapFragment).commit();
    }

    @Override
    public void onDestroyView() {
        ((ViewGroup) savedView.getParent()).removeAllViews();
        mMap = null;
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        focusCity();
    }

    private void focusCity() {
        // Add a marker in 'latLngToFocus' and move the camera
        if(cityToFocus != null && mMap != null && tvCityName != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .position(cityToFocus.getLatLng())
                    .title(cityToFocus.displayName));

            float _50_percent_zoom = mMap.getMaxZoomLevel() * 0.5f;
            mMap.moveCamera(CameraUpdateFactory.zoomTo(_50_percent_zoom));

            mMap.moveCamera(CameraUpdateFactory
                    .newLatLng(cityToFocus.getLatLng()));

            tvCityName.setText(cityToFocus.displayName);
        }
    }

}
