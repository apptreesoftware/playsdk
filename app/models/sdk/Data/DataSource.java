package models.sdk.Data;

import models.sdk.Utils.AuthenticationInfo;
import models.sdk.Utils.Parameters;
import models.sdk.Utils.Response;
import models.sdk.Utils.ServiceParameter;

import java.util.List;

/**
 * Created by alexis on 5/3/16.
 */
public abstract class DataSource {
    /***
     * @param authenticationInfo A HashMap of any authentication information that came through in the request headers from the mobile client
     * @param params   a HashMap of the URL parameters included in the request.
     * @return The data source response that contains the list of data set items you want to return
     */
    public abstract DataSourceResponse getDataSet(AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;

    /***
     * @param authenticationInfo a HashMap of any authentication information that came through in the request headers from the mobile client
     * @param id       The ID of the item to fetch
     * @param params   a HashMap of the URL parameters included in the request
     * @return The data source response that contains the data set item with the requested ID
     */
    public abstract DataSourceResponse getDataSetItem(AuthenticationInfo authenticationInfo, String id, Parameters params) throws InvalidAttributeValueException;

    /**
     * @param queryDataItem The data set item containing the values to be searched on
     * @param authenticationInfo      a HashMap of any authentication parameters that came through in the request headers
     * @param params        a HashMap of the URL parameters included in the request
     * @return The data source response that contains the list of data set items which meet the search criteria
     */
    public abstract DataSourceResponse queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;

    /**
     * @param dataSetItem The data set item to be created
     * @param authenticationInfo    a Hashmap of any authentication parameters that came through the request headers
     * @param params      a HashMap of the URL parameters included in the request
     * @return The data source response that contains the newly created data set item
     */
    public abstract DataSourceResponse createDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;

    /**
     * @param dataSetItem The data set item to be updated
     * @param authenticationInfo    a HashMap of any authentication parameters that came from the request headers
     * @param params      a Hashmap of the URL parameters included in the request
     * @return The data source response that contains the updated data set item
     */
    public abstract DataSourceResponse updateDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;

    /**
     * @param dataSetItem The data set item to be deleted
     * @param authenticationInfo    a HashMap of any authentication parameters that came from the request headers
     * @param params      a HashMap of the URL parameters included in the request
     * @return The data source response
     */
    public abstract DataSourceResponse deleteDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;

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
    public abstract List<ServiceConfigurationAttribute> getDataSetAttributes(AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Return any parameters that can be used to filter or modify how the service should behave. The builder read these in and allow the user to modify how the service behaves.
     * An example of this is if you want a single service that will return either all events or events only assigned to a given user.
     * You could specify a filter parameter as myEventsOnly = true/false. By specifying this parameter, the user could modify the true/false value in the builder and you would receive
     * the parameter as part of the urlParams HashMap in the get/create/update calls.
     * @return a list of ATServiceParameters that this web service supports
     */
    public abstract List<ServiceParameter> getServiceFilterParameters();
    /**
     *
     * @param authenticationInfo a HashMap of any authentication parameters that came from the request headers
     * @param params a HashMap of the URL parameters included in the request
     * @return The configuration response
     */
    public ConfigurationResponse getConfiguration(AuthenticationInfo authenticationInfo, Parameters params) {
        try {
            ServiceConfiguration configuration = new ServiceConfiguration.Builder(getServiceName()).
                    withAttributes(getDataSetAttributes(authenticationInfo, params)).
                    withServiceFilterParameters(getServiceFilterParameters()).
                    withDependentListRESTPaths(getDependentLists()).build();
            return new ConfigurationResponse.Builder().setSuccess(true).setMessage("").setConfiguration(configuration).createConfigurationResponse();
        } catch (Exception e) {
            return new ConfigurationResponse.Builder().setSuccess(false).setMessage(e.getMessage()).setConfiguration(null).createConfigurationResponse();
        }
    }

    /**
     *
     * @param dataSetItemID the data set item ID that the event is related to
     * @param event the ATEvent object
     * @param authenticationInfo a HashMap of any authentication parameters that came from the request
     * @param params a Parameters object of any URL parameters from the request
     */
    public abstract Response updateEventForDataSetItem(String dataSetItemID, Event event, AuthenticationInfo authenticationInfo, Parameters params);

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
     * @return an DataSourceResponse
     */
    public abstract DataSourceResponse bulkUpdateDataSetItems(List<String> primaryKeys, DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException;
}
