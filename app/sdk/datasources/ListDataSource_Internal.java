package sdk.datasources;

import rx.Observable;
import sdk.datasources.base.CacheableList;
import sdk.list.List;
import sdk.list.ListServiceConfiguration;
import sdk.datasources.base.SearchableList;
import sdk.list.UserList;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/5/16.
 */
public class ListDataSource_Internal extends BaseSource_Internal {
    ListDataSource dataSource;

    public ListDataSource_Internal(ListDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ListServiceConfiguration getListServiceConfiguration() {
        ListServiceConfiguration configuration = new ListServiceConfiguration(dataSource.getServiceName());
        configuration.setCanCache(this instanceof CacheableList);
        if ( isCacheSupported() ) {
            configuration.canCache = true;
            configuration.authenticationRequired = !isListContentGlobal();
        }
        if ( isSearchSupported() ) {
            configuration.canSearch = true;
        }
        if ( this instanceof UserList) {
            configuration.setUserIDIndex(((UserList) this).userIDIndex());
        }
        configuration.getAttributes().addAll(dataSource.getListServiceAttributes());
        configuration.getServiceFilterParameters().addAll(dataSource.getListFilterParameters());
        return configuration;
    }

    public CompletableFuture<List> getList(AuthenticationInfo authenticationInfo, Parameters parameters) {
        if ( dataSource instanceof CacheableList ) {
            return CompletableFuture.supplyAsync(() -> ((CacheableList) dataSource).getList(authenticationInfo, parameters));
        } else if ( dataSource instanceof sdk.datasources.future.CacheableList ) {
            return ((sdk.datasources.future.CacheableList) dataSource).getList(authenticationInfo, parameters);
        } else if ( dataSource instanceof sdk.datasources.rx.CacheableList ) {
            return observableToFuture(((sdk.datasources.rx.CacheableList) dataSource).getList(authenticationInfo, parameters));
        }
        throw new RuntimeException("Data source does not support cached lists");
    }

    public CompletableFuture<List> queryList(String queryText, boolean barcodeSearch, Map<String, Object> searchParameters, AuthenticationInfo authenticationInfo, Parameters params) {
        if ( dataSource instanceof SearchableList ) {
            return CompletableFuture.supplyAsync(() -> ((SearchableList) dataSource).queryList(queryText, barcodeSearch, searchParameters, authenticationInfo, params));
        } else if ( dataSource instanceof sdk.datasources.future.SearchableList ) {
            return ((sdk.datasources.future.SearchableList)dataSource).queryList(queryText, barcodeSearch, searchParameters, authenticationInfo, params);
        }
        else if ( dataSource instanceof sdk.datasources.rx.SearchableList ) {
            try {
                Observable<List> observable = ((sdk.datasources.rx.SearchableList)dataSource).queryList(queryText, barcodeSearch, searchParameters, authenticationInfo, params);
                return observableToFuture(observable);
            } catch (Exception e) {
                CompletableFuture<List> future = new CompletableFuture<>();
                future.completeExceptionally(e);
                return future;
            }
        }
        throw new RuntimeException("Data source does not support searching lists");
    }

    private boolean isSearchSupported() {
        return dataSource instanceof sdk.datasources.rx.SearchableList || dataSource instanceof sdk.datasources.base.SearchableList || dataSource instanceof sdk.datasources.future.SearchableList;
    }

    private boolean isCacheSupported() {
        return dataSource instanceof sdk.datasources.rx.CacheableList || dataSource instanceof sdk.datasources.base.CacheableList || dataSource instanceof sdk.datasources.future.CacheableList;
    }

    private boolean isListContentGlobal() {
        if (dataSource instanceof CacheableList ) {
            return ((CacheableList) dataSource).isListContentGlobal();
        } else if ( dataSource  instanceof sdk.datasources.future.CacheableList ) {
            return ((sdk.datasources.future.CacheableList) dataSource).isListContentGlobal();
        } else if ( dataSource instanceof sdk.datasources.rx.CacheableList ) {
            return ((sdk.datasources.rx.CacheableList) dataSource).isListContentGlobal();
        }
        return false;
    }

}
