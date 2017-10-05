package sdk.datasources.base;

import sdk.converter.ConfigurationManager;
import sdk.converter.ObjectConverter;
import sdk.converter.ParserContext;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.RecordActionResponse;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Constants;
import sdk.utils.Parameters;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * Created by Orozco on 8/14/17.
 */
public abstract class TypedDataSource<T extends Object> implements DataSource {
    private Class<T> dataSourceType;

    @Override
    public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        Collection<? extends T> objectList = this.getAll(authenticationInfo, params);
        return ObjectConverter.getDataSetFromCollection(objectList, getAttributes());
    }

    @Override
    public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        T object = get(id, authenticationInfo, parameters);
        DataSetItem dataSetItem = new DataSetItem(getAttributes());
        ObjectConverter.copyToRecord(dataSetItem, object);
        return dataSetItem;
    }


    @Override
    public DataSet queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ObjectConverter.copyFromRecord(queryDataItem, object);
        Collection<? extends Object> objects = query(object, authenticationInfo, params);
        return ObjectConverter.getDataSetFromCollection(objects, getAttributes());
    }

    @Override
    public RecordActionResponse createRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ParserContext parserContext = ObjectConverter.copyFromRecord(dataSetItem, object);
        parserContext.setUserId(authenticationInfo.getUserID());
        parserContext.setAppId(authenticationInfo.getCustomAuthenticationParameter(Constants.APP_ID_HEADER));
        return create(object, authenticationInfo, params, parserContext);
    }

    @Override
    public RecordActionResponse updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        T object = getNewInstance();
        ParserContext parserContext = ObjectConverter.copyFromRecord(dataSetItem, object);
        parserContext.setUserId(authenticationInfo.getUserID());
        parserContext.setAppId(authenticationInfo.getCustomAuthenticationParameter(Constants.APP_ID_HEADER));
        return update(object, authenticationInfo, params, parserContext);
    }

    abstract public Collection<T> query(T object, AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public RecordActionResponse update(T object, AuthenticationInfo authenticationInfo, Parameters parameters, ParserContext parserContext);

    abstract public Collection<T> getAll(AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public T get(String id, AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public RecordActionResponse create(T object, AuthenticationInfo authenticationInfo, Parameters parameters, ParserContext parserContext);

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
