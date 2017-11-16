package sdk.datasources.rx;

import rx.Observable;
import sdk.list.List;
import sdk.datasources.ListDataSource;
import sdk.list.ListItem;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by alexis on 5/10/16.
 */
public interface SearchableList extends ListDataSource {
    Observable<List> queryList(String queryText, boolean barcodeSearch, Map<String, Object> searchParameters, AuthenticationInfo authenticationInfo, Parameters params);

    default Observable<ListItem> fetchItem(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new RuntimeException("This source does not support fetching a single item");
    }
}
