package sdk.datasources.future;

import sdk.list.List;
import sdk.datasources.ListDataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.concurrent.CompletableFuture;

/**
 * Created by alexis on 5/10/16.
 */
public interface CacheableList extends ListDataSource {
    CompletableFuture<List> getList(AuthenticationInfo authenticationInfo, Parameters params);
    boolean isListContentGlobal();
}
