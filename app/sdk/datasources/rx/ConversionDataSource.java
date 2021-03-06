package sdk.datasources.rx;

import rx.Observable;
import sdk.converter.ObjectConverter;
import sdk.data.ConversionServiceConfiguration;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.ConversionDataSourceBase;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Orozco on 9/5/17.
 */
public abstract class ConversionDataSource<S, D> implements ConversionDataSourceBase {

    public Observable<D> convertRecord(DataSetItem dataSetItem, S object) {
        ObjectConverter.copyFromRecord(dataSetItem, object, false, null);
        return convert(object);
    }

    public Observable<D> convertRecord(DataSetItem previousDataSetItem, DataSetItem dataSetItem, S previousObject, S object) {
        ObjectConverter.copyFromRecord(previousDataSetItem, previousObject, false, null);
        ObjectConverter.copyFromRecord(dataSetItem, object, false, null);
        return convert(previousObject, object);
    }

    abstract public Observable<D> convert(S sourceRecord);
    public Observable<D> convert(S previousSourceRecord, S sourceRecord) {
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
