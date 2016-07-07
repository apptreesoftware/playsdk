package sdk.data;

import sdk.AppTreeSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;
import sdk.utils.Response;
import sdk.utils.ServiceParameter;

import java.util.List;

/**
 * Created by alexis on 5/3/16.
 */
public interface DataSource extends AppTreeSource {
    /***
     * @param authenticationInfo A HashMap of any authentication information that came through in the request headers from the mobile client
     * @param params   a HashMap of the URL parameters included in the request.
     * @return The data source response that contains the list of data set items you want to return
     */
    DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params);

    /***
     * @param authenticationInfo a HashMap of any authentication information that came through in the request headers from the mobile client
     * @param id       The ID of the item to fetch
     * @param params   a HashMap of the URL parameters included in the request
     * @return The data source response that contains the data set item with the requested ID
     */
    DataSet getDataSetItem(AuthenticationInfo authenticationInfo, String id, Parameters params);

    /**
     * @param queryDataItem The data set item containing the values to be searched on
     * @param authenticationInfo      a HashMap of any authentication parameters that came through in the request headers
     * @param params        a HashMap of the URL parameters included in the request
     * @return The data source response that contains the list of data set items which meet the search criteria
     */
    default DataSet queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
        throw new UnsupportedOperationException("Search is not supported on this web service");
    }

    /**
     * @param dataSetItem The data set item to be created
     * @param authenticationInfo    a Hashmap of any authentication parameters that came through the request headers
     * @param params      a HashMap of the URL parameters included in the request
     * @return The data source response that contains the newly created data set item
     */
    default DataSet createDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        throw new UnsupportedOperationException("Create is not supported on this web service");
    }

    /**
     * @param dataSetItem The data set item to be updated
     * @param authenticationInfo    a HashMap of any authentication parameters that came from the request headers
     * @param params      a Hashmap of the URL parameters included in the request
     * @return The DataSet that contains a single item that represents the updated item.
     */
    default DataSet updateDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        throw new UnsupportedOperationException("Update is not supported on this web service");
    }

    /**
     * @param dataSetItem The data set item to be deleted
     * @param authenticationInfo    a HashMap of any authentication parameters that came from the request headers
     * @param params      a HashMap of the URL parameters included in the request
     * @return The data source response
     */
    default Response deleteDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        throw new UnsupportedOperationException("Delete is not supported");
    }

    /**
     * Return the human readable name you want to provide for this service
     *
     * @return
     */
    public abstract String getServiceDescription();

    /**
     * Return the configuration attributes you want to use for creating a DataSetItem
     *
     * @param authenticationInfo
     * @param params
     * @return a List of service attributes
     */
    List<ServiceConfigurationAttribute> getDataSetAttributes(AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Return any parameters that can be used to filter or modify how the service should behave. The builder read these in and allow the user to modify how the service behaves.
     * An example of this is if you want a single service that will return either all events or events only assigned to a given user.
     * You could specify a filter parameter as myEventsOnly = true/false. By specifying this parameter, the user could modify the true/false value in the builder and you would receive
     * the parameter as part of the urlParams HashMap in the get/create/update calls.
     * @return a list of ATServiceParameters that this web service supports
     */
    default List<ServiceParameter> getServiceFilterParameters() { return null; }
    /**
     *
     * @param authenticationInfo a HashMap of any authentication parameters that came from the request headers
     * @param params a HashMap of the URL parameters included in the request
     * @return The configuration response
     */
    default ServiceConfiguration getConfiguration(AuthenticationInfo authenticationInfo, Parameters params) {
        try {
            return new ServiceConfiguration.Builder(getServiceDescription()).
                    withAttributes(getDataSetAttributes(authenticationInfo, params)).
                    withServiceFilterParameters(getServiceFilterParameters()).
                    withDependentListRESTPaths(getDependentLists()).build();
        } catch (Exception e) {
            return (ServiceConfiguration) new ServiceConfiguration("", null, null, null).setFailedWithMessage(e.getMessage());
        }
    }

    /**
     *
     * @param dataSetItemID the data set item ID that the event is related to
     * @param event the ATEvent object
     * @param authenticationInfo a HashMap of any authentication parameters that came from the request
     * @param params a Parameters object of any URL parameters from the request
     */
    default Response updateEventForDataSetItem(String dataSetItemID, Event event, AuthenticationInfo authenticationInfo, Parameters params) {
        throw new UnsupportedOperationException("Event update is not supported by this web service");
    }

    /**
     * Returns a list of all the ListServiceConfiguration dataSourceRestPath() endpoints this data source uses. This list is used to
     * auto register the lists when they are used to create features in the builder.
     * @return
     */
    default List<String> getDependentLists() {
        return null;
    }

    /**
     * This will update a list of data set items according to the given data set item
     * @param primaryKeys a list of data set item IDs to update
     * @param dataSetItem the data set item values used to update. IMPORTANT: Only the attributes that are getting bulk updated will be included.
     * @param authenticationInfo a HashMap of any authentication parameters sent in the request
     * @param params a Parameters object of any URL parameters from the request
     * @return an DataSourceResponse
     */
    default DataSet bulkUpdateDataSetItems(List<String> primaryKeys, DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        throw new UnsupportedOperationException("Bulk update is not supported by this web service");
    }

    default DataSet newEmptyDataSet(AuthenticationInfo authenticationInfo, Parameters parameters) {
        List<ServiceConfigurationAttribute> attributes = getDataSetAttributes(authenticationInfo, parameters);
        return new DataSet(attributes);
    }
}
