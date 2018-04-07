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
        ObjectConverter.copyFromRecord(dataSetItem, object, false, null);
        return convert(object);
    }

    public CompletableFuture<D> convertRecord(DataSetItem previousDataSetItem, DataSetItem dataSetItem, S previousObject, S object) {
        ObjectConverter.copyFromRecord(previousDataSetItem, previousObject, false, null);
        ObjectConverter.copyFromRecord(dataSetItem, object, false, null);
        return convert(previousObject, object);
    }

    abstract public CompletableFuture<D> convert(S sourceRecord);
    public CompletableFuture<D> convert(S previousSourceRecord, S sourceRecord) {
        return convert(sourceRecord);
    }

    @Override
    public String getServiceDescription() {
        return getDataTypeType(0).getSimpleName() + "->" + getDataTypeType(1).getSimpleName();
    }

    public Class getDataTypeType(int index) {
        if (index > 1 || index < 0) throw new RuntimeException("Index must be zero(0) or one(1)");
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) parameterizedType.getActualTypeArguments()[index];
    }

}
