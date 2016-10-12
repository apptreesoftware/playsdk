package sdk.datasources;

import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.data.User;

import java.util.Collection;

/**
 * Created by Matthew Smith on 10/12/16.
 * Copyright AppTree Software, Inc.
 */
public interface UserDataSourceBase extends DataSourceBase {
    @Override
    default String getServiceDescription() {
        return "User";
    }
    @Override
    default Collection<ServiceConfigurationAttribute> getDataSetAttributes() {
        return User.getConfigurationAttributesWithCustomAttributes(getCustomUserAttributes());
    }

    Collection<ServiceConfigurationAttribute> getCustomUserAttributes();

    default ServiceConfiguration getConfiguration() {
        try {
            return new ServiceConfiguration.Builder(getServiceDescription())
                    .withAttributes(getDataSetAttributes())
                    .withServiceFilterParameters(getServiceFilterParameters())
                    .withDependentListRESTPaths(getDependentLists())
                    .withContext("userCreateSupported", userCreationSupported())
                    .withContext("userUpdateSupported", userUpdateSupported())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return (ServiceConfiguration) new ServiceConfiguration("", null, null, null).setFailedWithMessage(e.getMessage());
        }
    }


    boolean userCreationSupported();
    boolean userUpdateSupported();
}
