package sdk.datasources;

import sdk.AppTreeSource;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.InspectionDataSet;
import sdk.data.ServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

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
                //return updateInspectionItem(dataSetItem,authenticationInfo, params).toBlocking().first();
                throw new UnsupportedOperationException("updateRecord not supported in InspectionSource");
            }
        };
    }

    default sdk.datasources.base.DataSource getInspectionSearchDataSource() {
        return new sdk.datasources.base.DataSource() {
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
