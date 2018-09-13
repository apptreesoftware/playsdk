package sdk.datasources.future;

import sdk.converter.ObjectConverter;
import sdk.converter.ParserContext;
import sdk.data.*;
import sdk.datasources.DataSourceUtils;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static sdk.converter.ConfigurationManager.inferName;
import static sdk.converter.ObjectConverter.getInspectionDataSetFromCollection;

public abstract class TypedInspectionDataSource<T> implements InspectionSource {
    private final Class<T> datasourceType = DataSourceUtils.getDataSourceType(getClass());
    private final String serviceName = inferName(datasourceType.getSimpleName());

    @Override
    public CompletableFuture<InspectionDataSet> startInspection(
        DataSetItem inspectionSearchDataSetItem, AuthenticationInfo authenticationInfo,
        Parameters parameters) {
        T newInstance = DataSourceUtils.getNewInstance(datasourceType);
        ParserContext context = ObjectConverter.copyFromRecord(inspectionSearchDataSetItem,
                                                               newInstance,
                                                               false,
                                                               null);
        setContextValues(context, authenticationInfo);
        CompletableFuture<InspectionData<T>> response =
            start(newInstance, authenticationInfo, parameters, context);
        // copy all attributes from collection in response to the back data set in InspectDataSet

        CompletableFuture<InspectionDataSet> inspectDataSet = response.thenApply(
            future -> getInspectionDataSetFromCollection(future.getItems(),
                                                         getInspectionItemAttributes()));
        return inspectDataSet.thenCombine(response, (dataSet, resp) -> {
            dataSet.setStartDate(resp.getStartDate());
            dataSet.setEndDate(resp.getEndDate());
            dataSet.setStatus(resp.getStatus());
            dataSet.setContext(resp.getContext());
            dataSet.setMessage(resp.getMessage());
            dataSet.setSuccess(resp.isSuccess());
            dataSet.setShowMessageAsAlert(resp.isShowMessageAsAlert());
            return dataSet;
        });
    }

    @Override
    public CompletableFuture<DataSet> completeInspection(InspectionDataSet completedDataSet,
                                                         AuthenticationInfo authenticationInfo,
                                                         Parameters parameters) {
        return null;
    }


    protected abstract CompletableFuture<InspectionData<T>> start(T item,
                                                                  AuthenticationInfo authenticationInfo,
                                                                  Parameters parameters,
                                                                  ParserContext context);

    protected abstract CompletableFuture<Collection<T>> complete(InspectionData<T> data,
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


    /**
     * Sets username and appid on parser context
     *
     * @param context
     * @param info
     */
    private void setContextValues(ParserContext context, AuthenticationInfo info) {
        context.setAppId(DataSourceUtils.getAppId(info));
        context.setUserId(info.getUserID());
    }
}
