package com.apptree.models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by matthew on 11/5/14.
 */
public abstract class ATDataSource {

    /***
     * Returns the REST path endpoint you want to use for this service. Any path you specify will result in a URL of http://{server-url}/apptree/{path}
     *
     * @return A string that you want to use for the endpoint
     */
    public abstract String dataSourceRESTPath();

    /***
     * @param authenticationInfo A HashMap of any authentication information that came through in the request headers from the mobile client
     * @param offset   The startOffset of the data the client is requesting. Will be 0 if paging is not supported.
     * @param pageSize The number or records the client is requesting
     * @param params   a HashMap of the URL parameters included in the request.
     * @return The data source response that contains the list of data set items you want to return
     */
    public abstract ATDataSourceResponse getDataSet(AuthenticationInfo authenticationInfo, int offset, int pageSize, Parameters params) throws InvalidAttributeValueException;

    /***
     * @param authenticationInfo a HashMap of any authentication information that came through in the request headers from the mobile client
     * @param id       The ID of the item to fetch
     * @param params   a HashMap of the URL parameters included in the request
     * @return The data source response that contains the data set item with the requested ID
     */
    public abstract ATDataSourceResponse getDataSetItem(AuthenticationInfo authenticationInfo, String id, Parameters params) throws InvalidAttributeValueException;

    /**
     * @param queryDataItem The data set item containing the values to be searched on
     * @param authenticationInfo      a HashMap of any authentication parameters that came through in the request headers
     * @param offset        The start offset of the data the client is requesting. Will be 0 if paging is not supported
     * @param pageSize      The number of records the client is requesting
     * @param params        a HashMap of the URL parameters included in the request
     * @return The data source response that contains the list of data set items which meet the search criteria
     */
    public abstract ATDataSourceResponse queryDataSet(ATDataSetItem queryDataItem, AuthenticationInfo authenticationInfo, int offset, int pageSize, Parameters params) throws InvalidAttributeValueException;

    /**
     * @param dataSetItem The data set item to be created
     * @param authenticationInfo    a Hashmap of any authentication parameters that came through the request headers
     * @param params      a HashMap of the URL parameters included in the request
     * @return The data source response that contains the newly created data set item
     */
    public abstract ATDataSourceResponse createDataSetItem(ATDataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;

    /**
     * @param dataSetItem The data set item to be updated
     * @param authenticationInfo    a HashMap of any authentication parameters that came from the request headers
     * @param params      a Hashmap of the URL parameters included in the request
     * @return The data source response that contains the updated data set item
     */
    public abstract ATDataSourceResponse updateDataSetItem(ATDataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;

    /**
     * @param dataSetItem The data set item to be deleted
     * @param authenticationInfo    a HashMap of any authentication parameters that came from the request headers
     * @param params      a HashMap of the URL parameters included in the request
     * @return The data source response
     */
    public abstract ATDataSourceResponse deleteDataSetItem(ATDataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;

    /**
     * Return the human readable name you want to provide for this service
     *
     * @return
     */
    public abstract String getServiceName();

    /**
     * Return the configuration attributes you want to use for creating a DataSetItem
     *
     * @param authenticationInfo
     * @param params
     * @return a List of service attributes
     */
    public abstract List<ATServiceConfigurationAttribute> getCreateConfigurationAttributes(AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Return the configuration attributes you want to use for viewing and updating a DataSetItem
     *
     * @param authenticationInfo
     * @param params
     * @return a List of service attributes
     */
    public abstract List<ATServiceConfigurationAttribute> getViewAndUpdateConfigurationAttributes(AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Return the configuration attributes you want to use for searching a DataSetItem
     *
     * @param authenticationInfo
     * @param params
     * @return a List of service attributes
     */
    public abstract List<ATServiceConfigurationAttribute> getSearchConfigurationAttributes(AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Return any parameters that can be used to filter or modify how the service should behave. The builder read these in and allow the user to modify how the service behaves.
     * An example of this is if you want a single service that will return either all events or events only assigned to a given user.
     * You could specify a filter parameter as myEventsOnly = true/false. By specifying this parameter, the user could modify the true/false value in the builder and you would receive
     * the parameter as part of the urlParams HashMap in the get/create/update calls.
     * @return a list of ATServiceParameters that this web service supports
     */
    public abstract List<ATServiceParameter> getServiceFilterParameters();

    /**
     * @param authenticationInfo a HashMap of any authentication parameters that came from the request headers
     * @param params   a HashMap of the URL parameters included in the request
     * @return The default data source response which includes any default values for data set items
     */
    public abstract ATDefaultDataSourceResponse getDefaults(AuthenticationInfo authenticationInfo, Parameters params);

    /**
     *
     * @param authenticationInfo a HashMap of any authentication parameters that came from the request headers
     * @param params a HashMap of the URL parameters included in the request
     * @return The configuration response
     */
    public ATConfigurationResponse getConfiguration(AuthenticationInfo authenticationInfo, Parameters params) {
        try {
            ATServiceConfiguration configuration = new ATServiceConfiguration.Builder(getServiceName()).
                    withUpdateAttributes(getViewAndUpdateConfigurationAttributes(authenticationInfo, params)).
                    withCreateAttributes(getCreateConfigurationAttributes(authenticationInfo, params)).
                    withSearchAttributes(getSearchConfigurationAttributes(authenticationInfo, params)).
                    withServiceFilterParameters(getServiceFilterParameters()).
                    withDependentListRESTPaths(getDependentLists()).build();
            return new ATConfigurationResponse(true, "", configuration);
        } catch (Exception e) {
            return new ATConfigurationResponse(false, e.getMessage(), null);
        }
    }

    /**
     * Creates a data source response with a data set using the view and update configuration attributes
     * @param authenticationInfo a HashMap of any authentication parameters that came from the request
     * @param params a Parameters object of any URL parameters from the request
     * @return an ATDataSourceResponse
     */
    public ATDataSourceResponse newDataSourceResponse(AuthenticationInfo authenticationInfo, Parameters params) {
        return new ATDataSourceResponse(true,null,new ATDataSet(getViewAndUpdateConfigurationAttributes(authenticationInfo, params)));
    }

    /**
     *
     * @param dataSetItemID the data set item ID that the event is related to
     * @param event the ATEvent object
     * @param authenticationInfo a HashMap of any authentication parameters that came from the request
     * @param params a Parameters object of any URL parameters from the request
     */
    public abstract ATResponse updateEventForDataSetItem(String dataSetItemID, ATEvent event, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Returns a list of all the ATListServiceConfiguration dataSourceRestPath() endpoints this data source uses. This list is used to
     * auto register the lists when they are used to create features in the builder.
     * @return
     */
    public abstract List<String> getDependentLists();

    /**
     * This will update a list of data set items according to the given data set item
     * @param primaryKeys a list of data set item IDs to update
     * @param dataSetItem the data set item values used to update
     * @param authenticationInfo a HashMap of any authentication parameters sent in the request
     * @param params a Parameters object of any URL parameters from the request
     * @return an ATDataSourceResponse
     */
    public abstract ATDataSourceResponse bulkUpdateDataSetItems(List<String> primaryKeys, ATDataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;
}
