package com.hemendra.citiessearch.presenter.listeners;

/**
 * Provides abstraction for actual search presenter class.
 */
public interface IPresenterFactory {

    /**
     * Get a new instance of search presenter.
     * @return the new instance of search presenter.
     */
    ISearchPresenter getSearchPresenter();
}
