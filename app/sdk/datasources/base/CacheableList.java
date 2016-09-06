package sdk.datasources.base;

import sdk.list.List;
import sdk.datasources.ListDataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

/**
 * Created by alexis on 5/10/16.
 */
public interface CacheableList extends ListDataSource {
    List getList(AuthenticationInfo authenticationInfo, Parameters params);
    boolean isListContentGlobal();
}
