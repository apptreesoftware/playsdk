package sdk.datasources.rx;

import sdk.converter.ConfigurationManager;
import sdk.converter.ObjectConverter;
import sdk.converter.ParserContext;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.RecordActionResponse;
import sdk.datasources.rx.DataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import rx.Observable;

/**
 * Created by Orozco on 8/14/17.
 */
public abstract class TypedDataSource<T extends Object> implements DataSource {
    private Class<T> dataSourceType;

    @Override
    public Observable<DataSet> getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        Observable<Collection<T>> objects = this.getAll(authenticationInfo, params);
        return objects.map(list -> ObjectConverter.getDataSetFromCollection(list, getAttributes()));
    }

    @Override
    public Observable<DataSetItem> getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        Observable<T> object = get(id, authenticationInfo, parameters);
        return object.map(item -> {
            DataSetItem dataSetItem = new DataSetItem(getAttributes());
            ObjectConverter.copyToRecord(dataSetItem, item);
            return dataSetItem;
        });
    }


    @Override
    public Observable<DataSet> queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ObjectConverter.copyFromRecord(queryDataItem, object);
        Observable<Collection<T>> objects = query(object, authenticationInfo, params);
        return objects.map(list -> ObjectConverter.getDataSetFromCollection(list, getAttributes()));
    }

    @Override
    public Observable<RecordActionResponse> createRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ObjectConverter.copyFromRecord(dataSetItem, object);
        return create(object, authenticationInfo, params);
    }

    @Override
    public Observable<RecordActionResponse> updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ParserContext parserContext = ObjectConverter.copyFromRecord(dataSetItem, object);
        return update(object, authenticationInfo, params, parserContext);
    }

    abstract public Observable<Collection<T>> query(T object, AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public Observable<RecordActionResponse> update(T object, AuthenticationInfo authenticationInfo, Parameters parameters, ParserContext parserContext);

    abstract public Observable<Collection<T>> getAll(AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public Observable<T> get(String id, AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public Observable<RecordActionResponse> create(T object, AuthenticationInfo authenticationInfo, Parameters parameters);

    @Override
    public String getServiceDescription() {
        return ConfigurationManager.inferName(getDataSourceType().getSimpleName());
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getAttributes() {
        return ObjectConverter.generateConfigurationAttributes(getDataSourceType());
    }

    public Class<T> getDataSourceType() {
        if (dataSourceType != null) {
            return dataSourceType;
        }
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        dataSourceType = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        return dataSourceType;
    }


    public T getNewInstance() {
        try {
            return getDataSourceType().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
