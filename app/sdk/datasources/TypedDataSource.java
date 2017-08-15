package sdk.datasources;

import sdk.converter.ConfigurationManager;
import sdk.converter.ObjectConverter;
import sdk.converter.ParserContext;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.base.DataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * Created by Orozco on 8/14/17.
 */
public abstract class TypedDataSource<T> implements DataSource {
    private Class<T> dataSourceType;

    @Override
    public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        throw new RuntimeException("Get Data Set is not supported by this data source");
    }

    @Override
    public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {
        throw new RuntimeException("Get Record is not supported by this data source");
    }

    abstract public Collection<? extends Object> queryObjects(T object, AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public RecordActionResponse updateObject(T object, AuthenticationInfo authenticationInfo, Parameters parameters, ParserContext parserContext);

    abstract public Collection<? extends T> getObjects(AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public T getObject(String id, AuthenticationInfo authenticationInfo, Parameters parameters);

    @Override
    public boolean isTypedDataSource() {
        return true;
    }

    @Override
    public String getServiceDescription() {
        return ConfigurationManager.inferName(getDataSourceType().getSimpleName());
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getAttributes() {
        return ObjectConverter.generateConfiguration(getDataSourceType()).getAttributes();
    }

    public Class<?> getDataSourceType() {
        if (dataSourceType != null) {
            return dataSourceType;
        }
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        dataSourceType = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        return dataSourceType;
    }


}
