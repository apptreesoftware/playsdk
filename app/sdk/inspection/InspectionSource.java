package sdk.inspection;

import sdk.AppTreeSource;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.List;

/**
 * Created by matthew on 8/29/16.
 */
public interface InspectionSource extends AppTreeSource {

    List<ServiceConfigurationAttribute> getInspectionItemAttributes(AuthenticationInfo authenticationInfo, Parameters params);
    List<ServiceConfigurationAttribute> getInspectionSearchAttributes(AuthenticationInfo authenticationInfo, Parameters parameters);

    DataSet startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    DataSet completeInspection(DataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);

    default DataSet updateInspectionItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) { return null; }
    default boolean shouldSendIncrementalUpdates() {
        return false;
    }

    String getServiceName();

}
