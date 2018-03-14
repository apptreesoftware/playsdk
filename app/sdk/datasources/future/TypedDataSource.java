package sdk.datasources.future;

import sdk.converter.ConfigurationManager;
import sdk.converter.ObjectConverter;
import sdk.converter.ParserContext;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.RecordActionResponse;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public abstract class TypedDataSource<T extends Object> implements DataSource {
    private Class<T> dataSourceType;

    @Override
    public CompletableFuture<DataSet> getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        CompletableFuture<Collection<T>> objects = this.getAll(authenticationInfo, params);
        return objects.thenApply(list -> ObjectConverter.getDataSetFromCollection(list, getAttributes()));
    }

    @Override
    public CompletableFuture<DataSetItem> getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        CompletableFuture<T> object = get(id, authenticationInfo, parameters);
        return object.thenApply(item -> {
            DataSetItem dataSetItem = new DataSetItem(getAttributes());
            ObjectConverter.copyToRecord(dataSetItem, item);
            return dataSetItem;
        });
    }


    @Override
    public CompletableFuture<DataSet> queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ObjectConverter.copyFromRecord(queryDataItem, object);
        CompletableFuture<Collection<T>> objects = query(object, authenticationInfo, params);
        return objects.thenApply(list -> ObjectConverter.getDataSetFromCollection(list, getAttributes()));
    }

    @Override
    public CompletableFuture<RecordActionResponse> createRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ObjectConverter.copyFromRecord(dataSetItem, object);
        return create(object, authenticationInfo, params);
    }

    @Override
    public CompletableFuture<RecordActionResponse> updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ParserContext parserContext = ObjectConverter.copyFromRecord(dataSetItem, object);
        return update(object, authenticationInfo, params, parserContext);
    }


    @Override
    public CompletableFuture<RecordActionResponse> validateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ParserContext parserContext = ObjectConverter.copyFromRecord(dataSetItem, object);
        return validate(object, authenticationInfo, params, parserContext);
    }

    abstract public CompletableFuture<Collection<T>> query(T object, AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public CompletableFuture<RecordActionResponse> update(T object, AuthenticationInfo authenticationInfo, Parameters parameters, ParserContext parserContext);

    public CompletableFuture<RecordActionResponse> validate(T object, AuthenticationInfo authenticationInfo, Parameters parameters, ParserContext parserContext){
        throw new RuntimeException("Validate is not supported in this data source");
    }

    abstract public CompletableFuture<Collection<T>> getAll(AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public CompletableFuture<T> get(String id, AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public CompletableFuture<RecordActionResponse> create(T object, AuthenticationInfo authenticationInfo, Parameters parameters);

    @Override
    public String getServiceDescription() {
        return ConfigurationManager.inferName(getDataSourceType().getSimpleName());
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getAttributes() {
        return ObjectConverter.generateConfiguration(getDataSourceType()).getAttributes();
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