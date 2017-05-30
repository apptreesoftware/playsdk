package sdk.datasources.base;

import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.InspectionDataSet;
import sdk.datasources.InspectionSourceBase;
import sdk.datasources.RecordActionResponse;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Map;

/**
 * Created by matthew on 8/29/16.
 */
public interface InspectionSource extends InspectionSourceBase {

    InspectionDataSet startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    DataSet completeInspection(InspectionDataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);
    default DataSetItem searchForInspectionItem(String primaryKey, Map<String, String> inspectionContext, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("searchForInspectionItem not supported on this data source");
    }

    default RecordActionResponse updateInspectionItem(DataSetItem dataSetItem,Map<String, String> inspectionContext, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("updateInspectionItem not supported on this data source");
    }
}
