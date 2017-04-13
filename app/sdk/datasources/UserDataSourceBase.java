package sdk.datasources;

import sdkmodels.data.ServiceConfiguration;
import sdkmodels.data.ServiceConfigurationAttribute;
import sdkmodels.data.User;

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
    default Collection<ServiceConfigurationAttribute> getAttributes() {
        return User.getConfigurationAttributesWithCustomAttributes(getCustomUserAttributes());
    }

    Collection<ServiceConfigurationAttribute> getCustomUserAttributes();

    default ServiceConfiguration getConfiguration() {
        try {
            return new ServiceConfiguration.Builder(getServiceDescription())
                    .withAttributes(getAttributes())
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
