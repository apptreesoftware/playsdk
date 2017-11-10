package sdk.datasources.base;

import sdk.list.List;
import sdk.datasources.ListDataSource;
import sdk.list.ListItem;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Map;

/**
 * Created by alexis on 5/10/16.
 */
public interface SearchableList extends ListDataSource {
    List queryList(String queryText, boolean barcodeSearch, Map<String, Object> searchParameters, AuthenticationInfo authenticationInfo, Parameters params);

    ListItem fetchItem(String id, AuthenticationInfo authenticationInfo, Parameters parameters);
}
