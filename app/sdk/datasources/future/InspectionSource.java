package sdk.datasources.future;

import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.InspectionDataSet;
import sdk.datasources.InspectionSourceBase;
import sdk.datasources.RecordActionResponse;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSource extends InspectionSourceBase {
    CompletableFuture<InspectionDataSet> startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    CompletableFuture<DataSet> completeInspection(InspectionDataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);

    default CompletableFuture<DataSetItem> searchForInspectionItem(String primaryKey,Map<String, String> inspectionContext, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("searchForInspectionItem not supported on this data source");
    }

    default CompletableFuture<RecordActionResponse> updateInspectionItem(DataSetItem dataSetItem,Map<String, String> inspectionContext, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("updateInspectionItem not supported on this data source");
    }
}
