package sdk.datasources;

import sdk.AppTreeSource;
import sdk.data.DataSet;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.utils.AuthenticationInfo;
import sdk.utils.BatchManager;
import sdk.utils.Parameters;
import sdk.utils.ServiceParameter;

import java.util.Collection;
import java.util.List;

/**
 * Created by alexis on 5/3/16.
 */
public interface DataSourceBase extends AppTreeSource {
    /**
     * Return the human readable name you want to provide for this service
     *
     * @return
     */
    public abstract String getServiceDescription();

    /**
     * Return the configuration attributes you want to use for creating a DataSetItem
     *
     * @return a List of service attributes
     */
    Collection<ServiceConfigurationAttribute> getAttributes();

    /**
     * Return any parameters that can be used to filter or modify how the service should behave. The builder read these in and allow the user to modify how the service behaves.
     * An example of this is if you want a single service that will return either all events or events only assigned to a given user.
     * You could specify a filter parameter as myEventsOnly = true/false. By specifying this parameter, the user could modify the true/false value in the builder and you would receive
     * the parameter as part of the urlParams HashMap in the get/create/update calls.
     * @return a list of ATServiceParameters that this web service supports
     */
    default List<ServiceParameter> getServiceFilterParameters() { return null; }
    /**
     *
     * @return The configuration response
     */
    default ServiceConfiguration getConfiguration() {
        try {
            return new ServiceConfiguration.Builder(getServiceDescription())
                    .withAttributes(getAttributes())
                    .withServiceFilterParameters(getServiceFilterParameters())
                    .withDependentListRESTPaths(getDependentLists())
                    .build();

        } catch (Exception e) {
            return (ServiceConfiguration) new ServiceConfiguration("", null, null, null).setFailedWithMessage(e.getMessage());
        }
    }

    /**
     * Returns a list of all the ListServiceConfiguration dataSourceRestPath() endpoints this data source uses. This list is used to
     * auto register the lists when they are used to create features in the builder.
     * @return
     */
    default List<String> getDependentLists() {
        return null;
    }


    default DataSet newEmptyDataSet() {
        Collection<ServiceConfigurationAttribute> attributes = getAttributes();
        return new DataSet(attributes);
    }

    default void getBatchedDataSet(AuthenticationInfo authenticationInfo, Parameters parameters, BatchManager batchManager) {
        throw new UnsupportedOperationException("Paged fetch is not supported in this web service");
    }
}
