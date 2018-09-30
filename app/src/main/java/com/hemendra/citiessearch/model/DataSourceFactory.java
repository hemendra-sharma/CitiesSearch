package com.hemendra.citiessearch.model;

import com.hemendra.citiessearch.model.listeners.IDataSource;
import com.hemendra.citiessearch.model.listeners.IDataSourceFactory;
import com.hemendra.citiessearch.presenter.listeners.ISearchPresenter;

public class DataSourceFactory implements IDataSourceFactory {

    private ISearchPresenter searchPresenter;

    public DataSourceFactory(ISearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

    @Override
    public IDataSource getDataSource() {
        return new DataSource(searchPresenter);
    }
}
