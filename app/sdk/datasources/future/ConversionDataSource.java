package sdk.datasources.future;

import io.netty.util.concurrent.CompleteFuture;
import sdk.converter.ObjectConverter;
import sdk.data.ConversionServiceConfiguration;
import sdk.data.DataSetItem;
import sdk.datasources.ConversionDataSourceBase;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Orozco on 9/5/17.
 */
public abstract class ConversionDataSource<S, D> implements ConversionDataSourceBase {

    public CompletableFuture<D> convertRecord(DataSetItem dataSetItem, S object) {
        ObjectConverter.copyFromRecord(dataSetItem, object);
        return convert(object);
    }

    abstract CompletableFuture<D> convert(S sourceRecord);

    @Override
    public String getServiceDescription() {
        return getDataTypeType(0).getSimpleName() + "->" + getDataTypeType(1).getSimpleName();
    }

    @Override
    public ConversionServiceConfiguration getConfiguration() {
        ConversionServiceConfiguration conversionServiceConfiguration = new ConversionServiceConfiguration();
        conversionServiceConfiguration.setName(getServiceDescription());
        conversionServiceConfiguration.attributes = new ArrayList<>(ObjectConverter.generateConfigurationAttributes(getDataTypeType(0)));
        conversionServiceConfiguration.setDestinationAttributes(ObjectConverter.generateConfigurationAttributes(getDataTypeType(1)));
        return conversionServiceConfiguration;
    }

    public Class getDataTypeType(int index) {
        if (index > 1 || index < 0) throw new RuntimeException("Index must be zero(0) or one(1)");
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) parameterizedType.getActualTypeArguments()[index];
    }

}
