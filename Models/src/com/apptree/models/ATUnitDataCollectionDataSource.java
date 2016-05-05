package com.apptree.models;

import java.util.HashMap;

/**
 * Created by alexis on 12/8/15.
 */
public abstract class ATUnitDataCollectionDataSource extends ATDataCollectionDataSource {
    /**
     * Starts a data collection session
     * @param collectionUnitItem The collection unit to use for the data collection session
     * @param authInfo a HashMap of any authentication headers passed through from the request
     * @param params a Parameters object of any URL parameters passed through from the request
     * @return
     */
    public abstract ATDataCollectionStartResponse beginDataCollection(ATCollectionUnitItem collectionUnitItem, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Gets the collection unit for a data set
     * @param authInfo a HashMap of any authentication headers passed through from the request
     * @param params a Parameters object of any URL parameters passed through from the request
     * @return
     */
    public abstract ATCollectionUnit getCollectionUnitForDataCollection(AuthenticationInfo authenticationInfo, Parameters params);
}
