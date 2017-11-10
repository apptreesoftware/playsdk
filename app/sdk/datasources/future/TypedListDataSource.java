package sdk.datasources.future;

import sdk.converter.ObjectConverter;
import sdk.data.ServiceConfigurationAttribute;
import sdk.exceptions.PrimaryObjectNotFoundException;
import sdk.list.List;
import sdk.list.ListItem;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static sdk.utils.ValidationUtils.safe;

/**
 * Created by Orozco on 8/31/17.
 */
public abstract class TypedListDataSource<T> implements SearchableList, CacheableList {
    private Class<T> dataSourceType;

    @Override
    public CompletableFuture<List> getList(AuthenticationInfo authenticationInfo, Parameters params) {
        CompletableFuture<Collection<T>> completableList = getAll(authenticationInfo, params);
        return completableList.thenApply(this::getListFromCollection);
    }

    @Override
    public CompletableFuture<List> queryList(String queryText, boolean barcodeSearch, Map<String, Object> searchParameters, AuthenticationInfo authenticationInfo, Parameters params) {
        CompletableFuture<Collection<T>> completableList = queryAll(queryText, barcodeSearch, searchParameters, authenticationInfo, params);
        return completableList.thenApply(this::getListFromCollection);
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getListServiceAttributes() {
        return ObjectConverter.generateListConfigurationAttributes(getDataSourceType());
    }

    @Override
    public String getServiceName() {
        return ObjectConverter.inferName(getDataSourceType().getSimpleName());
    }

    @Override
    public CompletableFuture<ListItem> fetchItem(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        CompletableFuture<T> item = getItem(id, authenticationInfo, parameters);
        return item.thenApply(listObject -> {
            if ( listObject != null ) {
                ListItem tempItem = new ListItem();
                ObjectConverter.copyToRecord(tempItem, listObject);
                return tempItem;
            }
            throw new PrimaryObjectNotFoundException();
        });
    }

    public abstract CompletableFuture<Collection<T>> getAll(AuthenticationInfo authenticationInfo, Parameters parameters);

    public abstract CompletableFuture<Collection<T>> queryAll(String queryText, boolean isBarcodeSearch, Map<String, Object> searchParameters, AuthenticationInfo authenticationInfo, Parameters parameters);

    public abstract CompletableFuture<T> getItem(String id, AuthenticationInfo authenticationInfo, Parameters parameters);


    public Class<T> getDataSourceType() {
        if (dataSourceType != null) {
            return dataSourceType;
        }
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        dataSourceType = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        return dataSourceType;
    }


    private List getListFromCollection(Collection<T> listOfValues) {
        java.util.List<ListItem> items = new ArrayList<>();
        for (T object : safe(listOfValues)) {
            ListItem tempItem = new ListItem();
            ObjectConverter.copyToRecord(tempItem, object);
            items.add(tempItem);
        }
        return new List(items);
    }


}
