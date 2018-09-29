package com.hemendra.citiessearch.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hemendra.citiessearch.R;
import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.presenter.listeners.ISearchPresenter;
import com.hemendra.citiessearch.view.adapters.SearchResultsAdapter;
import com.hemendra.citiessearch.view.listeners.OnCityClickListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private View savedView = null;
    private OnCityClickListener onCityClickListener = null;
    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }

    private ISearchPresenter searchPresenter = null;
    public void setSearchPresenter(ISearchPresenter presenter) {
        this.searchPresenter = presenter;
    }

    private RecyclerView recycler = null;
    private EditText etSearch = null;
    private SearchResultsAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedView != null) return savedView;

        return savedView = inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(searchPresenter != null) searchPresenter.performSearch("");

        recycler = view.findViewById(R.id.recycler);
        etSearch = view.findViewById(R.id.etSearch);

        etSearch.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            if(searchPresenter != null)
                searchPresenter.performSearch(etSearch.getText().toString().trim());
        }
    };

    public void updateList(ArrayList<City> results) {
        if(adapter == null) {
            adapter = new SearchResultsAdapter(results, onCityClickListener);
            recycler.setAdapter(adapter);
        } else {
            adapter.setData(results);
        }
    }

    @Override
    public void onDestroyView() {
        ((ViewGroup) savedView.getParent()).removeAllViews();
        super.onDestroyView();
    }
}
