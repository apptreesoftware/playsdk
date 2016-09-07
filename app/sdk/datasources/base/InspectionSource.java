package sdk.datasources.base;

import sdk.AppTreeSource;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.InspectionSourceBase;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.List;

/**
 * Created by matthew on 8/29/16.
 */
public interface InspectionSource extends InspectionSourceBase {

    DataSet startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    DataSet completeInspection(DataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);

    default DataSet updateInspectionItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("updateInspectionItem not supported on this data source");
    }

}
