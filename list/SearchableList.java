package sdk.list;

import com.fasterxml.jackson.databind.JsonNode;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

/**
 * Created by alexis on 5/10/16.
 */
public interface SearchableList extends ListDataSource {
    List queryList(String queryText, boolean barcodeSearch, JsonNode context, AuthenticationInfo authenticationInfo, Parameters params);
}
