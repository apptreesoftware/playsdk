package sdk.datasources;

import sdk.datasources.base.DataSource;
import sdk.AppTreeSource;
import sdkmodels.data.DataSet;
import sdkmodels.data.DataSetItem;
import sdkmodels.data.InspectionDataSet;
import sdkmodels.data.ServiceConfigurationAttribute;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;
import sdkmodels.utils.RecordActionResponse;

import java.util.Collection;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSourceBase extends AppTreeSource {
    Collection<ServiceConfigurationAttribute> getInspectionItemAttributes();
    Collection<ServiceConfigurationAttribute> getInspectionSearchAttributes();

    default boolean shouldSendIncrementalUpdates() {
        return false;
    }

    String getServiceName();

    default InspectionDataSet newEmptyInspectionDataSet() {
        Collection<ServiceConfigurationAttribute> attributes = getInspectionItemAttributes();
        return new InspectionDataSet(attributes);
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
                //TODO: Change this to Rx
                //return updateInspectionItem(dataSetItem,authenticationInfo, params).toBlocking().first();
                throw new UnsupportedOperationException("updateRecord not supported in InspectionSource");
            }
        };
    }

    default DataSource getInspectionSearchDataSource() {
        return new DataSource() {
            @Override
            public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
                return null;
            }

            @Override
            public DataSet queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
                //TODO: Change this to Rx
                return null;
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
