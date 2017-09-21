package sdk.datasources;

import rx.Observable;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.base.ConversionDataSource;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Orozco on 9/5/17.
 */
public class ConversionDataSource_Internal<S, D> extends BaseSource_Internal {
    ConversionDataSource<S, D> conversionDataSource;
    sdk.datasources.rx.ConversionDataSource<S, D> rxConversionDataSource;
    sdk.datasources.future.ConversionDataSource<S, D> futureConversionDataSource;
    Collection<ServiceConfigurationAttribute> destinationAttributes;
    Collection<ServiceConfigurationAttribute> sourceAttributes;

    public ConversionDataSource_Internal(ConversionDataSourceBase conversionDataSourceBase) {
        if (conversionDataSourceBase instanceof sdk.datasources.future.ConversionDataSource) {
            futureConversionDataSource = (sdk.datasources.future.ConversionDataSource<S, D>) conversionDataSourceBase;
        }
        if (conversionDataSourceBase instanceof sdk.datasources.rx.ConversionDataSource) {
            rxConversionDataSource = (sdk.datasources.rx.ConversionDataSource<S, D>) conversionDataSourceBase;
        }
        if (conversionDataSourceBase instanceof sdk.datasources.base.ConversionDataSource) {
            conversionDataSource = (sdk.datasources.base.ConversionDataSource<S, D>) conversionDataSourceBase;
        }
    }

    public Class getDestionationType() {
        if (futureConversionDataSource != null) {
            return futureConversionDataSource.getDestinationType();
        }
        if (rxConversionDataSource != null) {
            return rxConversionDataSource.getDestinationType();
        }
        if (conversionDataSource != null) {
            return conversionDataSource.getDestinationType();
        }
        throw new RuntimeException("No data source available");
    }

    public Class getSourceType() {
        if (futureConversionDataSource != null) {
            return futureConversionDataSource.getSourceType();
        }
        if (rxConversionDataSource != null) {
            return rxConversionDataSource.getSourceType();
        }
        if (conversionDataSource != null) {
            return conversionDataSource.getSourceType();
        }
        throw new RuntimeException("No data source available");
    }


    public CompletableFuture<DataSetItem> convert(DataSetItem dataSetItem, S sourceObject) {
        if (futureConversionDataSource != null) {
            CompletableFuture<D> object = futureConversionDataSource.convertRecord(dataSetItem, sourceObject);
            return object.thenApply(value -> {
                DataSetItem item = new DataSetItem(getDestinationAttributes());
                ObjectConverter.copyToRecord(item, object);
                return item;
            });

        }
        if (rxConversionDataSource != null) {
            Observable<D> object = rxConversionDataSource.convertRecord(dataSetItem, sourceObject);
            return observableToFuture(object.map(value -> {
                DataSetItem item = new DataSetItem(getDestinationAttributes());
                ObjectConverter.copyToRecord(item, object);
                return item;
            }));

        }
        if (conversionDataSource != null) {
            D object = conversionDataSource.convertRecord(dataSetItem, sourceObject);
            DataSetItem item = new DataSetItem(getDestinationAttributes());
            ObjectConverter.copyToRecord(item, object);
            return CompletableFuture.supplyAsync(() -> item);
        }
        throw new RuntimeException("No data source available");
    }

    public CompletableFuture<DataSetItem> convert(DataSetItem previousDataSetItem, DataSetItem dataSetItem, S previousSourceObject, S sourceObject) {
        if (futureConversionDataSource != null) {
            CompletableFuture<D> object = futureConversionDataSource.convertRecord(previousDataSetItem, dataSetItem, previousSourceObject, sourceObject);
            return object.thenApply(value -> {
                DataSetItem item = new DataSetItem(getDestinationAttributes());
                ObjectConverter.copyToRecord(item, object);
                return item;
            });
        }
        if (rxConversionDataSource != null) {
            Observable<D> object = rxConversionDataSource.convertRecord(previousDataSetItem, dataSetItem, previousSourceObject, sourceObject);
            return observableToFuture(object.map(value -> {
                DataSetItem item = new DataSetItem(getDestinationAttributes());
                ObjectConverter.copyToRecord(item, object);
                return item;
            }));
        }
        if (conversionDataSource != null) {
            D object = conversionDataSource.convertRecord(previousDataSetItem, dataSetItem, previousSourceObject, sourceObject);
            DataSetItem item = new DataSetItem(getDestinationAttributes());
            ObjectConverter.copyToRecord(item, object);
            return CompletableFuture.supplyAsync(() -> item);
        }
        throw new RuntimeException("No data source available");
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
            return conversionDataSource.getConfiguration(conversionDataSource.getSourceType(), conversionDataSource.getDestinationType());
        }
        if (rxConversionDataSource != null) {
            return rxConversionDataSource.getConfiguration(rxConversionDataSource.getSourceType(), rxConversionDataSource.getDestinationType());
        }
        if (futureConversionDataSource != null) {
            return futureConversionDataSource.getConfiguration(futureConversionDataSource.getSourceType(), futureConversionDataSource.getDestinationType());
        }
        throw new RuntimeException("Unable to load configuration");
    }

    public Class getDataTypeType(int index) {
        if (index > 1 || index < 0) throw new RuntimeException("Index must be zero(0) or one(1)");
        ParameterizedType parameterizedType = null;
        if(conversionDataSource != null){
             parameterizedType = (ParameterizedType) conversionDataSource.getClass().getGenericSuperclass();
        }
        if(rxConversionDataSource != null) {
             parameterizedType = (ParameterizedType) rxConversionDataSource.getClass().getGenericSuperclass();
        }
        if(futureConversionDataSource != null) {
             parameterizedType = (ParameterizedType) futureConversionDataSource.getClass().getGenericSuperclass();
        }
        if(parameterizedType == null) throw new RuntimeException("No datasource available");
        return (Class) parameterizedType.getActualTypeArguments()[index];
    }

}
