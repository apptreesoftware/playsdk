package sdk.list;

import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Map;

/**
 * Created by alexis on 5/10/16.
 */
public interface SearchableList extends ListDataSource {
    List queryList(String queryText, boolean barcodeSearch, Map<String, Object> searchParameters, AuthenticationInfo authenticationInfo, Parameters params);
}
