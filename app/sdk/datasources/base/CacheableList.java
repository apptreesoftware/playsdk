package sdk.datasources.base;

import sdk.list.List;
import sdk.datasources.ListDataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Map;

/**
 * Created by alexis on 5/10/16.
 */
public interface CacheableList extends ListDataSource, SearchableList {
    List getList(AuthenticationInfo authenticationInfo, Parameters params);
    boolean isListContentGlobal();

    @Override
    default List queryList(String queryText, boolean barcodeSearch, Map<String, Object> searchParameters, AuthenticationInfo authenticationInfo, Parameters params) {
        return getList(authenticationInfo, params);
    }
}
