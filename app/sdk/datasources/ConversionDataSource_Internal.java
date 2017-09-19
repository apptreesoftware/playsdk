package sdk.datasources;

import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.base.ConversionDataSource;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * Created by Orozco on 9/5/17.
 */
public class ConversionDataSource_Internal<S, D> extends BaseSource_Internal {
    ConversionDataSource<S, D> conversionDataSource;
    Collection<ServiceConfigurationAttribute> destinationAttributes;
    Collection<ServiceConfigurationAttribute> sourceAttributes;

    public ConversionDataSource_Internal(ConversionDataSourceBase conversionDataSourceBase) {
        conversionDataSource = (ConversionDataSource<S, D>) conversionDataSourceBase;
    }

    public Class getDestionationType() {
        return getDataTypeType(1);
    }

    public Class getSourceType() {
        return getDataTypeType(0);
    }


    public DataSetItem convert(DataSetItem dataSetItem, S sourceObject) {
        Object object = conversionDataSource.convertRecord(dataSetItem, sourceObject);
        DataSetItem item = new DataSetItem(getDestinationAttributes());
        ObjectConverter.copyToRecord(item, object);
        return item;
    }

    public DataSetItem convert(DataSetItem previousDataSetItem, DataSetItem dataSetItem, S previousSourceObject, S sourceObject) {
        Object object = conversionDataSource.convertRecord(previousDataSetItem, dataSetItem, previousSourceObject, sourceObject);
        DataSetItem item = new DataSetItem(getDestinationAttributes());
        ObjectConverter.copyToRecord(item, object);
        return item;
    }

    public Collection<ServiceConfigurationAttribute> getDestinationAttributes() {
        if (destinationAttributes == null) {
            destinationAttributes = ObjectConverter.generateConfigurationAttributes(getDataTypeType(1));
        }
        return destinationAttributes;
    }

    public Collection<ServiceConfigurationAttribute> getSourceDestinationAttributes() {
        if (sourceAttributes == null) {
            sourceAttributes = ObjectConverter.generateConfigurationAttributes(getDataTypeType(0));
        }
        return sourceAttributes;
    }


    public ServiceConfiguration getConfiguration() {
        if (conversionDataSource != null) {
            return conversionDataSource.getConfiguration(conversionDataSource.getSourceType(), conversionDataSource.getDestionationType());
        }
        throw new RuntimeException("Unable to load configuration");
    }

    public Class getDataTypeType(int index) {
        if (index > 1 || index < 0) throw new RuntimeException("Index must be zero(0) or one(1)");
        ParameterizedType parameterizedType = (ParameterizedType) conversionDataSource.getClass().getGenericSuperclass();
        return (Class) parameterizedType.getActualTypeArguments()[index];
    }
}
