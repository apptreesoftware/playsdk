package sdk.datasources.rx;

import rx.Observable;
import sdk.list.List;
import sdk.datasources.ListDataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

/**
 * Created by alexis on 5/10/16.
 */
public interface CacheableList extends ListDataSource {
    Observable<List> getList(AuthenticationInfo authenticationInfo, Parameters params);
    boolean isListContentGlobal();
}
