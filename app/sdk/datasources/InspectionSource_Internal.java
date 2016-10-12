package sdk.datasources;

import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.base.InspectionSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/6/16.
 */
public class InspectionSource_Internal extends BaseSource_Internal {

    InspectionSourceBase dataSource;

    public InspectionSource_Internal(InspectionSourceBase dataSource) {
        this.dataSource = dataSource;
    }

    public CompletableFuture<DataSet> startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        if ( dataSource instanceof InspectionSource ) {
            return CompletableFuture.supplyAsync(() -> ((InspectionSource)dataSource).startInspection(inspectionSearchDataSetItem, authenticationInfo, parameters));
        } else if ( dataSource instanceof sdk.datasources.future.InspectionSource ) {
            return ((sdk.datasources.future.InspectionSource)dataSource).startInspection(inspectionSearchDataSetItem, authenticationInfo, parameters);
        } else if (dataSource instanceof sdk.datasources.rx.InspectionSource) {
            sdk.datasources.rx.InspectionSource source = (sdk.datasources.rx.InspectionSource) dataSource;
            return observableToFuture(source.startInspection(inspectionSearchDataSetItem, authenticationInfo, parameters));
        }
        throw new RuntimeException("No data source defined");
    }

    public CompletableFuture<DataSet> completeInspection(DataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters) {
        if ( dataSource instanceof InspectionSource ) {
            return CompletableFuture.supplyAsync(() -> ((InspectionSource)dataSource).completeInspection(completedDataSet, authenticationInfo, parameters));
        } else if ( dataSource instanceof sdk.datasources.future.InspectionSource ) {
            return ((sdk.datasources.future.InspectionSource)dataSource).completeInspection(completedDataSet, authenticationInfo, parameters);
        } else if (dataSource instanceof sdk.datasources.rx.InspectionSource) {
            sdk.datasources.rx.InspectionSource source = (sdk.datasources.rx.InspectionSource) dataSource;
            return observableToFuture(source.completeInspection(completedDataSet, authenticationInfo, parameters));
        }
        throw new RuntimeException("No data source defined");
    }

    public CompletableFuture<DataSet> updateInspectionItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        if ( dataSource instanceof InspectionSource ) {
            return CompletableFuture.supplyAsync(() -> ((InspectionSource)dataSource).updateInspectionItem(dataSetItem, authenticationInfo, parameters));
        } else if ( dataSource instanceof sdk.datasources.future.InspectionSource ) {
            return ((sdk.datasources.future.InspectionSource)dataSource).updateInspectionItem(dataSetItem, authenticationInfo, parameters);
        } else if (dataSource instanceof sdk.datasources.rx.InspectionSource) {
            sdk.datasources.rx.InspectionSource source = (sdk.datasources.rx.InspectionSource) dataSource;
            return observableToFuture(source.updateInspectionItem(dataSetItem, authenticationInfo, parameters));
        }
        throw new RuntimeException("No data source defined");
    }

    public Collection<ServiceConfigurationAttribute> getInspectionItemAttributes(AuthenticationInfo authenticationInfo, Parameters params) {
        return dataSource.getInspectionItemAttributes();
    }
    public Collection<ServiceConfigurationAttribute> getInspectionSearchAttributes(AuthenticationInfo authenticationInfo, Parameters parameters) {
        return dataSource.getInspectionSearchAttributes();
    }

    public boolean shouldSendIncrementalUpdates() {
        return dataSource.shouldSendIncrementalUpdates();
    }

    String getServiceName() {
        return dataSource.getServiceName();
    }
}
