package sdk.datasources.rx;

import rx.Observable;
import sdkmodels.data.DataSet;
import sdkmodels.data.DataSetItem;
import sdkmodels.data.InspectionDataSet;
import sdk.datasources.InspectionSourceBase;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;
import sdkmodels.utils.RecordActionResponse;

import java.util.Map;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSource extends InspectionSourceBase {

    Observable<InspectionDataSet> startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    Observable<DataSet> completeInspection(InspectionDataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);

    default Observable<DataSetItem> searchForInspectionItem(String primaryKey, Map<String, String> inspectionContext, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("searchForInspectionItem not supported on this data source");
    }

    default Observable<RecordActionResponse> updateInspectionItem(DataSetItem dataSetItem,Map<String, String> inspectionContext, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("updateInspectionItem not supported on this data source");
    }
}
