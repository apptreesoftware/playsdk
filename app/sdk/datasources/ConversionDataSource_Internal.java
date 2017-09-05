package sdk.datasources;

import org.omg.SendingContext.RunTime;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Orozco on 9/5/17.
 */
public class ConversionDataSource_Internal<S, D> extends BaseSource_Internal {
    ConversionDataSource<S, D> conversionDataSource;

    public ConversionDataSource_Internal(ConversionDataSourceBase conversionDataSourceBase) {
        conversionDataSource = (ConversionDataSource<S, D>) conversionDataSourceBase;
    }

    public Class getDestionationType() {
        return getDataTypeType(0);
    }

    public Class getSourceType() {
        return getDataTypeType(0);
    }


    public DataSetItem convert(DataSetItem dataSetItem, S sourceObject) {
        Object object = conversionDataSource.convertRecord(dataSetItem, sourceObject);
        DataSetItem item = new DataSetItem(getConfiguration().attributes);
        ObjectConverter.copyToRecord(item, object);
        return dataSetItem;
    }


    public ServiceConfiguration getConfiguration() {
        if (conversionDataSource != null) {
            return conversionDataSource.getConfiguration();
        }
        throw new RuntimeException("Unable to load configuration");
    }

    public Class getDataTypeType(int index) {
        if (index > 1 || index < 0) throw new RuntimeException("Index must be zero(0) or one(1)");
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) parameterizedType.getActualTypeArguments()[index];
    }
}
