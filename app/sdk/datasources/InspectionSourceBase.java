package sdk.datasources;

import sdk.AppTreeSource;
import sdk.data.ServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.List;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSourceBase extends AppTreeSource {
    List<ServiceConfigurationAttribute> getInspectionItemAttributes(AuthenticationInfo authenticationInfo, Parameters params);
    List<ServiceConfigurationAttribute> getInspectionSearchAttributes(AuthenticationInfo authenticationInfo, Parameters parameters);

    default boolean shouldSendIncrementalUpdates() {
        return false;
    }

    String getServiceName();
}
