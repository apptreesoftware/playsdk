package sdk.datasources.rx;

import rx.Observable;
import sdk.datasources.ListDataSource;
import sdkmodels.list.List;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;

/**
 * Created by alexis on 5/10/16.
 */
public interface CacheableList extends ListDataSource {
    Observable<List> getList(AuthenticationInfo authenticationInfo, Parameters params);
    boolean isListContentGlobal();
}
