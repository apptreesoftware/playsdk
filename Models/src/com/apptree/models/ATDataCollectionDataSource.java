package com.apptree.models;

/**
 * Created by alexis on 12/8/15.
 */
public abstract class ATDataCollectionDataSource {

    /**
     * Returns the RESTful path to this web service.
     * @return
     */
    public abstract String dataSourceRESTPath();

    /**
     * Returns the name of this service
     * @return
     */
    public abstract String getServiceName();

    /**
     * Returns the configuration of this service
     * @return
     */
    public abstract ATDataCollectionConfigurationResponse getConfiguration() throws ATServiceConfiguration.InvalidServiceAttributeException;

    /**
     * Gets the data set for a given data collection
     * @param dataCollectionSessionID the ID of the data collection
     * @param authInfo HashMap of the authentication headers that may have been sent in the request
     * @param params Any URL params that were sent in the request
     * @return
     */
    public abstract ATDataSourceResponse getDataSet(String dataCollectionSessionID, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Saves the current state of a data collection. This is used when the user wants to save the the data collection and complete another time
     * @param dataCollectionSessionID the ID of the data collection
     * @param authInfo HashMap of the authentication headers sent in the request
     * @param params A parameters object of the URL parameters sent in the request
     * @return
     */
    public abstract ATResponse saveDataCollection(String dataCollectionSessionID, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Ends the data collection with the given ID
     * @param dataCollectionSessionID the ID of the data collection
     * @param authInfo  HashMap of the authentication headers sent in the request
     * @param params A parameters object of the URL parameters sent in the request
     * @return
     */
    public abstract ATResponse endDataCollection(String dataCollectionSessionID, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Cancels a data collection
     * @param dataCollectionSessionID The ID of the data collection session to cancel
     * @param authInfo HashMap of the authentication parameters sent in the request
     * @param params Parameters object of the URL parameters sent in the request
     * @return
     */
    public abstract ATResponse cancelDataCollection(String dataCollectionSessionID, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Updates the given data set item in the data collection session
     * @param dataCollectionSessionID The ID of the data collection session
     * @param item The dataSetItem to be updated
     * @param authInfo HashMap of the authentication parameters sent in the request
     * @param params Parameters object of the URL parameters sent in the request
     * @return
     */
    public abstract ATDataSourceResponse updateDataSetItem(String dataCollectionSessionID, ATDataSetItem item, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Searches for the given dataSetItem outside of the specified dat collection session location/collection unit
     * @param dataCollectionSessionID The ID of the data collection session
     * @param dataSetItemID The ID of the data collection session
     * @param authInfo HashMap of the authentication parameters sent in the request
     * @param params Parameters object of the URL parameters sent in the request
     * @return
     */
    public abstract ATDataSourceResponse searchForExternalDataSetItem(String dataCollectionSessionID, String dataSetItemID, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     *
     * @return The list handler for the possible exception types
     */
    public abstract ATListDataSource getExceptionListHandler();

    /**
     * Sends an event
     * @param dataSetItemID the primary key of the data set item related to the event
     * @param event the ATEvent
     * @param authInfo a HashMap of any authentication parameters sent in the request
     * @param params a Parameters object of any URL parameters sent in the request
     */
    public abstract void updateEventForDataSetItem(String dataSetItemID, ATEvent event, AuthenticationInfo authenticationInfo, Parameters params);
}
