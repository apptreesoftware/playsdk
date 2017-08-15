package sdk.datasources;

import sdk.AppTreeSource;
import sdk.data.ServiceConfigurationAttribute;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.utils.ServiceParameter;

import java.util.Collection;
import java.util.Set;

/**
 * Created by alexis on 5/4/16.
 */
public interface ListDataSource extends AppTreeSource {
    /**
     *
     * @return A list service configuration containing the attributes of a list
     */
    <T extends ServiceConfigurationAttribute> Collection<T> getListServiceAttributes();

    /**
     *
     * @return the name of the list service
     */
    String getServiceName();

    /**
     *
     * @return A list service configuration containing the possible server filter parameters of a list
     */
    default Set<ServiceParameter> getListFilterParameters() { return null; }
}
