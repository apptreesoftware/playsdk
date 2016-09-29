package sdk.datasources;

import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.Event;
import sdk.data.ServiceConfiguration;
import sdk.datasources.base.DataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;
import sdk.utils.Response;
import sdk.utils.ServiceParameter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/6/16.
 */
public class DataSource_Internal extends BaseSource_Internal {
    DataSource dataSource;
    sdk.datasources.rx.DataSource rxDataSource;
    sdk.datasources.future.DataSource futureDataSource;

    public DataSource_Internal(DataSourceBase dataSourceBase) {
        if ( dataSourceBase instanceof DataSource ) {
            dataSource = (DataSource) dataSourceBase;
        } else if ( dataSourceBase instanceof sdk.datasources.future.DataSource ) {
            futureDataSource = (sdk.datasources.future.DataSource) dataSourceBase;
        } else if ( dataSourceBase instanceof sdk.datasources.rx.DataSource ) {
            rxDataSource = (sdk.datasources.rx.DataSource) dataSourceBase;
        }
    }

    public ServiceConfiguration getConfiguration(AuthenticationInfo authenticationInfo, Parameters params) {
       if ( dataSource != null ) {
           return dataSource.getConfiguration(authenticationInfo, params);
       } else if ( rxDataSource != null ) {
           return rxDataSource.getConfiguration(authenticationInfo, params);
       } else if ( futureDataSource != null ) {
           return futureDataSource.getConfiguration(authenticationInfo, params);
       }
       throw new RuntimeException("No data source available");
    }

    public CompletableFuture<DataSet> getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        if ( dataSource != null ) {
            return CompletableFuture.supplyAsync(() -> dataSource.getDataSet(authenticationInfo, params));
        } else if (futureDataSource != null) {
            return futureDataSource.getDataSet(authenticationInfo, params);
        } else if (rxDataSource != null) {
            return observableToFuture(rxDataSource.getDataSet(authenticationInfo, params));
        }
        throw new RuntimeException("No data source available");
    }

    public CompletableFuture<DataSet> getDataSetItem(AuthenticationInfo authenticationInfo, String id, Parameters params) {
        if ( dataSource != null ) {
            return CompletableFuture.supplyAsync(() -> dataSource.getDataSetItem(authenticationInfo,id, params));
        } else if (futureDataSource != null) {
            return futureDataSource.getDataSetItem(authenticationInfo, id, params);
        } else if (rxDataSource != null) {
            return observableToFuture(rxDataSource.getDataSetItem(authenticationInfo, id, params));
        }
        throw new RuntimeException("No data source available");
    }

    /**
     * @param queryDataItem The data set item containing the values to be searched on
     * @param authenticationInfo      a HashMap of any authentication parameters that came through in the request headers
     * @param params        a HashMap of the URL parameters included in the request
     * @return The data source response that contains the list of data set items which meet the search criteria
     */
    public CompletableFuture<DataSet> queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
        if ( dataSource != null ) {
            return CompletableFuture.supplyAsync(() -> dataSource.queryDataSet(queryDataItem, authenticationInfo, params));
        } else if (futureDataSource != null) {
            return futureDataSource.queryDataSet(queryDataItem, authenticationInfo, params);
        } else if (rxDataSource != null) {
            return observableToFuture(rxDataSource.queryDataSet(queryDataItem, authenticationInfo, params));
        }
        throw new RuntimeException("No data source available");
    }

    /**
     * @param dataSetItem The data set item to be created
     * @param authenticationInfo    a Hashmap of any authentication parameters that came through the request headers
     * @param params      a HashMap of the URL parameters included in the request
     * @return The data source response that contains the newly created data set item
     */
    public CompletableFuture<DataSet> createDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        if ( dataSource != null ) {
            return CompletableFuture.supplyAsync(() -> dataSource.createDataSetItem(dataSetItem, authenticationInfo, params));
        } else if (futureDataSource != null) {
            return futureDataSource.createDataSetItem(dataSetItem, authenticationInfo, params);
        } else if (rxDataSource != null) {
            return observableToFuture(rxDataSource.createDataSetItem(dataSetItem, authenticationInfo, params));
        }
        throw new RuntimeException("No data source available");
    }

    /**
     * @param dataSetItem The data set item to be updated
     * @param authenticationInfo    a HashMap of any authentication parameters that came from the request headers
     * @param params      a Hashmap of the URL parameters included in the request
     * @return The DataSet that contains a single item that represents the updated item.
     */
    public CompletableFuture<DataSet> updateDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        if ( dataSource != null ) {
            return CompletableFuture.supplyAsync(() -> dataSource.updateDataSetItem(dataSetItem, authenticationInfo, params));
        } else if (futureDataSource != null) {
            return futureDataSource.updateDataSetItem(dataSetItem, authenticationInfo, params);
        } else if (rxDataSource != null) {
            return observableToFuture(rxDataSource.updateDataSetItem(dataSetItem, authenticationInfo, params));
        }
        throw new RuntimeException("No data source available");
    }

    /**
     *
     * @param dataSetItemID the data set item ID that the event is related to
     * @param event the ATEvent object
     * @param authenticationInfo a HashMap of any authentication parameters that came from the request
     * @param params a Parameters object of any URL parameters from the request
     */
    public CompletableFuture<Response> updateEventForDataSetItem(String dataSetItemID, Event event, AuthenticationInfo authenticationInfo, Parameters params) {
        if ( dataSource != null ) {
            return CompletableFuture.supplyAsync(() -> dataSource.updateEventForDataSetItem(dataSetItemID, event, authenticationInfo, params));
        } else if (futureDataSource != null) {
            return futureDataSource.updateEventForDataSetItem(dataSetItemID, event, authenticationInfo, params);
        } else if (rxDataSource != null) {
            return observableToFuture(rxDataSource.updateEventForDataSetItem(dataSetItemID, event, authenticationInfo, params));
        }
        throw new RuntimeException("No data source available");
    }

    /**
     * This will update a list of data set items according to the given data set item
     * @param primaryKeys a list of data set item IDs to update
     * @param dataSetItem the data set item values used to update. IMPORTANT: Only the attributes that are getting bulk updated will be included.
     * @param authenticationInfo a HashMap of any authentication parameters sent in the request
     * @param params a Parameters object of any URL parameters from the request
     * @return an DataSourceResponse
     */
    public CompletableFuture<DataSet> bulkUpdateDataSetItems(List<String> primaryKeys, DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        if ( dataSource != null ) {
            return CompletableFuture.supplyAsync(() -> dataSource.bulkUpdateDataSetItems(primaryKeys, dataSetItem, authenticationInfo, params));
        } else if (futureDataSource != null) {
            return futureDataSource.bulkUpdateDataSetItems(primaryKeys, dataSetItem, authenticationInfo, params);
        } else if (rxDataSource != null) {
            return observableToFuture(rxDataSource.bulkUpdateDataSetItems(primaryKeys, dataSetItem, authenticationInfo, params));
        }
        throw new RuntimeException("No data source available");
    }

}
