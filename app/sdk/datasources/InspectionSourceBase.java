package sdk.datasources;

import sdk.AppTreeSource;
import sdk.data.DataSet;
import sdk.data.ServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSourceBase extends AppTreeSource {
    Collection<ServiceConfigurationAttribute> getInspectionItemAttributes(AuthenticationInfo authenticationInfo, Parameters params);
    Collection<ServiceConfigurationAttribute> getInspectionSearchAttributes(AuthenticationInfo authenticationInfo, Parameters parameters);

    default boolean shouldSendIncrementalUpdates() {
        return false;
    }

    String getServiceName();

    default DataSet newEmptyInspectionDataSet(AuthenticationInfo authenticationInfo, Parameters parameters) {
        Collection<ServiceConfigurationAttribute> attributes = getInspectionItemAttributes(authenticationInfo, parameters);
        return new DataSet(attributes);
    }
}
