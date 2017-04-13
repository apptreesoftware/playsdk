package sdk.datacollection;

import sdk.AppTreeSource;
import sdkmodels.data.*;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;
import sdkmodels.utils.Response;

import java.util.List;

/**
 * Created by Matthew on 5/24/2016.
 */
public interface DataCollectionSource extends AppTreeSource {
    String getServiceName();
    List<ServiceConfigurationAttribute> getDataSetAttributes(AuthenticationInfo authenticationInfo, Parameters params);
    DataSet getDataSet(String sessionID, AuthenticationInfo authenticationInfo, Parameters parameters);
    DataSet updateDataSetItem(String sessionID, DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    String startDataCollectionSession(String inspectionItemID, AuthenticationInfo authenticationInfo, Parameters parameters);
    Response saveDataCollection(String sessionID, AuthenticationInfo authenticationInfo, Parameters parameters);
    Response endDataCollection(String sessionID, AuthenticationInfo authenticationInfo, Parameters parameters);
    Response cancelDataCollection(String sessionID, AuthenticationInfo authenticationInfo, Parameters parameters);

    default DataSet searchExternalItem(String sessionID, String dataSetItemID, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("Searching external items is not supported for this service");
    }

    default Response updateEventForDataSetItem(String dataSetItem, Event event, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException(getServiceName() + " does not support events");
    }

    default ServiceConfiguration getConfiguration(AuthenticationInfo authenticationInfo, Parameters params) {
        try {
            return new ServiceConfiguration.Builder(getServiceName()).
                    withAttributes(getDataSetAttributes(authenticationInfo, params))
                    .build();
        } catch (Exception e) {
            return (ServiceConfiguration) new ServiceConfiguration("", null, null, null).setFailedWithMessage(e.getMessage());
        }
    }

    default DataSet newEmptyDataSet(AuthenticationInfo authenticationInfo, Parameters parameters) {
        List<ServiceConfigurationAttribute> attributes = getDataSetAttributes(authenticationInfo, parameters);
        return new DataSet(attributes);
    }
}
