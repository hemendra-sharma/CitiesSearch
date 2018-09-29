package com.hemendra.citiessearch.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hemendra.citiessearch.R;
import com.hemendra.citiessearch.data.City;
import com.hemendra.citiessearch.presenter.listeners.ISearchPresenter;
import com.hemendra.citiessearch.view.adapters.SearchResultsAdapter;
import com.hemendra.citiessearch.view.listeners.OnCityClickListener;

import java.util.ArrayList;
import java.util.Locale;

@SuppressLint("ClickableViewAccessibility")
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
    private TextView tvInfo = null;
    private SearchResultsAdapter adapter = null;

    private long searchStartedAt = 0;

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
        tvInfo = view.findViewById(R.id.tvInfo);
        TextView tvCross = view.findViewById(R.id.tvCross);

        etSearch.addTextChangedListener(textWatcher);
        tvCross.setOnTouchListener(doFocus);
        tvCross.setOnClickListener(onCrossClicked);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            searchStartedAt = System.currentTimeMillis();
            if(searchPresenter != null)
                searchPresenter.performSearch(etSearch.getText().toString().trim());
        }
    };

    private View.OnClickListener onCrossClicked = v -> {
        etSearch.setText("");
        etSearch.requestFocus();
        showKeyboard();
    };

    private View.OnTouchListener doFocus = (v, event) -> {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            v.requestFocus();
        return false;
    };

    private OnCityClickListener internalOnCityClickListener = city -> {
        hideKeyboard();
        if(onCityClickListener != null) onCityClickListener.onCityClicked(city);
    };

    private void hideKeyboard() {
        Context ctx = getContext();
        if(ctx != null) {
            InputMethodManager mgr =
                    (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(mgr != null) mgr.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        }
    }

    private void showKeyboard() {
        Context ctx = getContext();
        if(ctx != null) {
            InputMethodManager mgr =
                    (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(mgr != null) mgr.showSoftInput(etSearch, 0);
        }
    }

    public void updateList(ArrayList<City> results) {
        if(adapter == null) {
            adapter = new SearchResultsAdapter(results, internalOnCityClickListener);
            recycler.setAdapter(adapter);
        } else {
            adapter.setData(results);
        }
        setInfo();
    }

    private void setInfo() {
        if(etSearch.getText().toString().trim().length() > 0) {
            long milliseconds = System.currentTimeMillis() - searchStartedAt;
            double seconds = (double) milliseconds / (double) 1000;
            String info = String.format(Locale.getDefault(),
                    "%d results filtered in %f seconds", adapter.getItemCount(), seconds);
            tvInfo.setText(info);
            tvInfo.setVisibility(View.VISIBLE);
        } else {
            tvInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        ((ViewGroup) savedView.getParent()).removeAllViews();
        super.onDestroyView();
    }
}
