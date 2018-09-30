package com.hemendra.citiessearch.presenter;

import com.hemendra.citiessearch.presenter.listeners.IPresenterFactory;
import com.hemendra.citiessearch.presenter.listeners.ISearchPresenter;
import com.hemendra.citiessearch.view.listeners.ICitiesActivity;

public class PresenterFactory implements IPresenterFactory {

    private ICitiesActivity activity;

    public PresenterFactory(ICitiesActivity activity) {
        this.activity = activity;
    }


    @Override
    public ISearchPresenter getSearchPresenter() {
        return new SearchPresenter(activity);
    }
}
