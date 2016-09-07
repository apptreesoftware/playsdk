package sdk.datasources.future;

import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSource {
    CompletableFuture<DataSet> startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    CompletableFuture<DataSet> completeInspection(DataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);
    default CompletableFuture<DataSet> updateInspectionItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("updateInspectionItem not supported on this data source");
    }
}
