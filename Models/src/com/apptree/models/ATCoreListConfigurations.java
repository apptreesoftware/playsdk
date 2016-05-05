package com.apptree.models;

/**
 * Created by alexis on 12/18/15.
 */
public class ATCoreListConfigurations {
    public static ATListServiceConfiguration getMobileUserListConfiguration() {
        ATListServiceConfiguration serviceConfiguration;

        serviceConfiguration = new ATListServiceConfiguration("Core Mobile User List");
        serviceConfiguration.setCanCache(true);
        serviceConfiguration.setAttribute1(new ATListServiceConfigurationAttribute.Builder("Name").build());
        serviceConfiguration.setAttribute2(new ATListServiceConfigurationAttribute.Builder("Username").build());
        serviceConfiguration.setAttribute3(new ATListServiceConfigurationAttribute.Builder("Roles").build());

        return serviceConfiguration;
    }
}
