package sdk.datasources.rx;

import rx.Observable;
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

import static sdk.utils.ValidationUtils.safe;

/**
 * Created by Orozco on 8/31/17.
 */
public abstract class TypedListDataSource<T> implements SearchableList, CacheableList {
    private Class<T> dataSourceType;

    @Override
    public Observable<List> getList(AuthenticationInfo authenticationInfo, Parameters params) {
        Observable<Collection<T>> objectList = getAll(authenticationInfo, params);
        return objectList.map(this::getListFromCollection);
    }

    @Override
    public Observable<List> queryList(String queryText, boolean barcodeSearch, Map<String, Object> searchParameters, AuthenticationInfo authenticationInfo, Parameters params) {
        Observable<Collection<T>> objectList = queryAll(queryText, barcodeSearch, searchParameters, authenticationInfo, params);
        return objectList.map(this::getListFromCollection);
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getListServiceAttributes() {
        return ObjectConverter.generateListConfigurationAttributes(getDataSourceType());
    }

    @Override
    public Observable<ListItem> fetchItem(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        Observable<T> item = getItem(id, authenticationInfo, parameters);
        return item.map(listObject -> {
            if ( listObject != null ) {
                ListItem tempItem = new ListItem();
                ObjectConverter.copyToRecord(tempItem, listObject);
                return tempItem;
            }
            throw new PrimaryObjectNotFoundException();
        });
    }

    @Override
    public String getServiceName() {
        return ObjectConverter.inferName(getDataSourceType().getSimpleName());
    }

    public abstract Observable<Collection<T>> getAll(AuthenticationInfo authenticationInfo, Parameters parameters);

    public abstract Observable<Collection<T>> queryAll(String queryText, boolean isBarcodeSearch, Map<String, Object> searchParameters, AuthenticationInfo authenticationInfo, Parameters parameters);

    public abstract Observable<T> getItem(String id, AuthenticationInfo authenticationInfo, Parameters parameters);

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
