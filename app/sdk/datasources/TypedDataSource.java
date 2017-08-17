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

    }

    @Override
    public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters parameters) {

    }

    abstract public Collection<? extends Object> query(T object, AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public RecordActionResponse update(T object, AuthenticationInfo authenticationInfo, Parameters parameters, ParserContext parserContext);

    abstract public Collection<? extends T> getAll(AuthenticationInfo authenticationInfo, Parameters parameters);

    abstract public T get(String id, AuthenticationInfo authenticationInfo, Parameters parameters);

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
