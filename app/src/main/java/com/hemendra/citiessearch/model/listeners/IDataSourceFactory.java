package com.hemendra.citiessearch.model.listeners;

/**
 * Provides abstraction for actual data source class.
 */
public interface IDataSourceFactory {

    /**
     * Get the new data source instance.
     * @return A new data source instance.
     */
    IDataSource getDataSource();
}
