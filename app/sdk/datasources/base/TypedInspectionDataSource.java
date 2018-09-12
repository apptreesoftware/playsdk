package sdk.datasources.base;

import sdk.converter.ConfigurationManager;
import sdk.converter.ObjectConverter;
import sdk.converter.ParserContext;
import sdk.data.*;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Constants;
import sdk.utils.Parameters;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import static sdk.converter.ObjectConverter.getDataSetFromCollection;
import static sdk.converter.ObjectConverter.getInspectionDataSetFromCollection;

public abstract class TypedInspectionDataSource<T> implements InspectionSource {
    private final Class<T> datasourceType = getDataSourceType();
    private final String serviceName =
        ConfigurationManager.inferName(datasourceType.getSimpleName());


    /**
     * Typed wrapper around the inspection data source
     * <p>
     * This method will Convert the incoming data set item to an instance of type T
     * perform the start expection call
     * then transform response into InsepctionDataSet
     *
     * @param inspectionSearchDataSetItem
     * @param authenticationInfo
     * @param parameters
     * @return
     */
    @Override
    public InspectionDataSet startInspection(DataSetItem inspectionSearchDataSetItem,
                                             AuthenticationInfo authenticationInfo,
                                             Parameters parameters) {
        T newInstance = getNewInstance();
        ParserContext context = ObjectConverter.copyFromRecord(inspectionSearchDataSetItem,
                                                               newInstance,
                                                               false,
                                                               null);
        setContextValues(context, authenticationInfo);
        InspectionData<T> response = start(newInstance, authenticationInfo, parameters, context);
        // copy all attributes from collection in response to the back data set in InspectDataSet
        InspectionDataSet inspectDataSet = getInspectionDataSetFromCollection(response.getItems(),
                                                                              getInspectionItemAttributes());
        inspectDataSet.setStartDate(response.getStartDate());
        inspectDataSet.setEndDate(response.getEndDate());
        inspectDataSet.setStatus(response.getStatus());
        inspectDataSet.setContext(response.getContext());
        inspectDataSet.setMessage(response.getMessage());
        inspectDataSet.setSuccess(response.isSuccess());
        inspectDataSet.setShowMessageAsAlert(response.isShowMessageAsAlert());

        // copy response details
        return inspectDataSet;
    }

    @Override
    public DataSet completeInspection(InspectionDataSet completedDataSet,
                                      AuthenticationInfo authenticationInfo,
                                      Parameters parameters) {
        InspectionData<T> instance = new InspectionData<>();
        instance.setStartDate(completedDataSet.getStartDate());
        instance.setEndDate(completedDataSet.getEndDate());
        instance.setContext(completedDataSet.getContext());
        try {
            ParserContext context = new ParserContext();
            Collection<T> items = ObjectConverter.getCollectionFromDataSet(completedDataSet,
                                                                           datasourceType,
                                                                           context);
            instance.setItems(items);
            Collection<T> resultCollection =
                complete(instance, authenticationInfo, parameters, context);
            return getDataSetFromCollection(resultCollection, getInspectionItemAttributes());
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(String.format("Error converting %s item to data set. " +
                                                     "Underlying error: %s",
                                                     getServiceName(), e.getMessage()));
        }
    }

    protected abstract InspectionData<T> start(T item,
                                               AuthenticationInfo authenticationInfo,
                                               Parameters parameters,
                                               ParserContext context);

    protected abstract Collection<T> complete(InspectionData<T> data,
                                              AuthenticationInfo authenticationInfo,
                                              Parameters parameters,
                                              ParserContext context);

    @Override
    public Collection<ServiceConfigurationAttribute> getInspectionItemAttributes() {
        return ObjectConverter.generateConfigurationAttributes(datasourceType);
    }

    @Override
    public Collection<ServiceConfigurationAttribute> getInspectionSearchAttributes() {
        return getInspectionItemAttributes();
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }


    private Class<T> getDataSourceType() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    public T getNewInstance() {
        try {
            return getDataSourceType().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAppId(AuthenticationInfo info) {
        return info.getCustomAuthenticationParameter(Constants.APP_ID_HEADER);
    }

    /**
     * Sets username and appid on parser context
     *
     * @param context
     * @param info
     */
    private void setContextValues(ParserContext context, AuthenticationInfo info) {
        context.setAppId(getAppId(info));
        context.setUserId(info.getUserID());
    }
}
