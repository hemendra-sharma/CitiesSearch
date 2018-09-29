package com.hemendra.citiessearch.view.listeners;

import com.hemendra.citiessearch.presenter.listeners.ISearchPresenter;
import com.hemendra.citiessearch.view.CitiesActivity;

public interface IPresenterFactory {
    ISearchPresenter getPresenter(CitiesActivity activity);
}
