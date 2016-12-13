package sdk.datasources.rx;

import rx.Observable;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.InspectionDataSet;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.InspectionSourceBase;
import sdk.datasources.RecordActionResponse;
import sdk.datasources.base.*;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSource extends InspectionSourceBase {

    Observable<DataSet> startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    Observable<DataSet> completeInspection(InspectionDataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);
    default Observable<RecordActionResponse> updateInspectionItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("updateInspectionItem not supported on this data source");
    }

    default sdk.datasources.base.DataSource getInspectionDataSource() {
        return new sdk.datasources.base.DataSource() {
            @Override
            public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
                return null;
            }

            @Override
            public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
                return null;
            }

            @Override
            public String getServiceDescription() {
                return getServiceName() + "- Data";
            }

            @Override
            public Collection<ServiceConfigurationAttribute> getAttributes() {
                return getInspectionItemAttributes();
            }

            @Override
            public RecordActionResponse updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
                //TODO: Change this to Rx
                return updateInspectionItem(dataSetItem,authenticationInfo, params).toBlocking().first();
            }
        };
    }

    @Override
    default sdk.datasources.base.DataSource getInspectionSearchDataSource() {
        return new sdk.datasources.base.DataSource() {
            @Override
            public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
                return null;
            }

            @Override
            public DataSet queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
                //TODO: Change this to Rx
                return startInspection(queryDataItem, authenticationInfo, params).toBlocking().first();
            }

            @Override
            public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
                return null;
            }

            @Override
            public String getServiceDescription() {
                return getServiceName() + "- Search";
            }

            @Override
            public Collection<ServiceConfigurationAttribute> getAttributes() {
                return getInspectionSearchAttributes();
            }
        };
    }
}
