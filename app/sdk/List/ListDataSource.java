package sdk.list;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.cache.Cache;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

/**
 * Created by alexis on 5/4/16.
 */
public interface ListDataSource {
    /**
     *
     * @return A list service configuration containing the attributes of a list
     */
    java.util.List<ListServiceConfigurationAttribute> getListServiceAttributes();
    /**
     *
     * @return the name of the list service
     */
    String getServiceName();


    default ListServiceConfiguration getListServiceConfiguration() {
        ListServiceConfiguration configuration = new ListServiceConfiguration(getServiceName());
        configuration.setCanCache(this instanceof CacheableList);
        if ( this instanceof CacheableList ) {
            configuration.canCache = true;
            configuration.authenticationRequired = !((CacheableList) this).isListContentGlobal();
        }
        if ( this instanceof SearchableList ) {
            configuration.canSearch = true;
        }
        if ( this instanceof UserList ) {
            configuration.setUserIDIndex(((UserList) this).userIDIndex());
        }
        configuration.attributes.addAll(getListServiceAttributes());
        return configuration;
    }
}
