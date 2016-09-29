package sdk.datasources.rx;

import rx.Observable;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.datasources.InspectionSourceBase;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSource extends InspectionSourceBase {

    Observable<DataSet> startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    Observable<DataSet> completeInspection(DataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);
    default Observable<DataSet> updateInspectionItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("updateInspectionItem not supported on this data source");
    }
}