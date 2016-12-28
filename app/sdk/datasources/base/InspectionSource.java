package sdk.datasources.base;

import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.InspectionDataSet;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.InspectionSourceBase;
import sdk.datasources.RecordActionResponse;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;

/**
 * Created by matthew on 8/29/16.
 */
public interface InspectionSource extends InspectionSourceBase {

    DataSet startInspection(DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters);
    DataSet completeInspection(InspectionDataSet completedDataSet, AuthenticationInfo authenticationInfo, Parameters parameters);
    default DataSetItem searchForInspectionItem(String primaryKey, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("searchForInspectionItem not supported on this data source");
    }

    default RecordActionResponse updateInspectionItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new UnsupportedOperationException("updateInspectionItem not supported on this data source");
    }

    default DataSource getInspectionDataSource() {
        return new DataSource() {
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
                return updateInspectionItem(dataSetItem,authenticationInfo, params);
            }
        };
    }

    @Override
    default DataSource getInspectionSearchDataSource() {
        return new DataSource() {
            @Override
            public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
                return null;
            }

            @Override
            public DataSet queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
                return startInspection(queryDataItem, authenticationInfo, params);
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
