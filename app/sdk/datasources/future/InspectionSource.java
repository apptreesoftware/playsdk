package sdk.datasources.future;

import sdk.datasources.InspectionSourceBase;
import sdkmodels.data.DataSet;
import sdkmodels.data.DataSetItem;
import sdkmodels.data.InspectionDataSet;
import sdkmodels.utils.RecordActionResponse;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;

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
