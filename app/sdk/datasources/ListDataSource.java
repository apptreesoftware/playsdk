package sdk.datasources;

import sdk.AppTreeSource;
import sdk.datasources.base.CacheableList;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.utils.ServiceParameter;

import java.util.List;
import java.util.Set;

/**
 * Created by alexis on 5/4/16.
 */
public interface ListDataSource extends AppTreeSource {
    /**
     *
     * @return A list service configuration containing the attributes of a list
     */
    Set<ListServiceConfigurationAttribute> getListServiceAttributes();
    /**
     *
     * @return the name of the list service
     */
    String getServiceName();

    /**
     *
     * @return A list service configuration containing the possible server filter parameters of a list
     */
    default Set<ServiceParameter> getServiceFilterParameters() { return null; }
}
