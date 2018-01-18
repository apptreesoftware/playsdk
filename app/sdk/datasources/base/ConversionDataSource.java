package sdk.datasources.base;

import sdk.converter.ObjectConverter;
import sdk.data.ConversionServiceConfiguration;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.datasources.ConversionDataSourceBase;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 * Created by Orozco on 9/5/17.
 */
public abstract class ConversionDataSource<S, D> implements ConversionDataSourceBase {

    public D convertRecord(DataSetItem dataSetItem, S object) {
        ObjectConverter.copyFromRecord(dataSetItem, object, false);
        return convert(object);
    }

    public D convertRecord(DataSetItem previousDataSetItem, DataSetItem dataSetItem, S previousObject, S object) {
        ObjectConverter.copyFromRecord(previousDataSetItem, previousObject, false);
        ObjectConverter.copyFromRecord(dataSetItem, object, false);
        return convert(previousObject, object);
    }

    abstract public D convert(S sourceRecord);
    public D convert(S previousSourceRecord, S sourceRecord) {
        return convert(sourceRecord);
    }

    @Override
    public String getServiceDescription() {
        return getDataTypeType(0).getSimpleName() + "->" + getDataTypeType(1).getSimpleName();
    }

    public Class getDestionationType() {
        return getDataTypeType(1);
    }

    public Class getSourceType() {
        return getDataTypeType(0);
    }

    public Class getDataTypeType(int index) {
        if (index > 1 || index < 0) throw new RuntimeException("Index must be zero(0) or one(1)");
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) parameterizedType.getActualTypeArguments()[index];
    }

}
