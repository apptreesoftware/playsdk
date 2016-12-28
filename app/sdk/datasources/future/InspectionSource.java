package sdk.datasources.future;

import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.InspectionDataSet;
import sdk.datasources.RecordActionResponse;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSource {
    CompletableFuture<DataSet> startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    CompletableFuture<DataSet> completeInspection(InspectionDataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);

    default CompletableFuture<DataSetItem> searchForInspectionItem(String primaryKey, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("searchForInspectionItem not supported on this data source");
    }

    default CompletableFuture<RecordActionResponse> updateInspectionItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("updateInspectionItem not supported on this data source");
    }
}
