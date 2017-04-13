package sdk.datasources.base;

import sdk.datasources.InspectionSourceBase;
import sdkmodels.data.DataSet;
import sdkmodels.data.DataSetItem;
import sdkmodels.data.InspectionDataSet;
import sdkmodels.utils.RecordActionResponse;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;

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
