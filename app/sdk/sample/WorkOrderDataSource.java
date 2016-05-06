package sdk.sample;

import models.sdk.Data.*;
import models.sdk.Utils.AuthenticationInfo;
import models.sdk.Utils.Parameters;
import models.sdk.Utils.Response;
import models.sdk.Utils.ServiceParameter;
import play.libs.Json;
import sdk.sample.model.WorkOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthew on 5/5/16.
 */
public class WorkOrderDataSource extends DataSource {

    @Override
    public DataSourceResponse getDataSet(AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        DataSet dataSet = newEmptyDataSet(authenticationInfo, params);

        List<WorkOrder> workOrders = SampleDatabase.getInstance().fetchWorkOrders();
        for ( WorkOrder workOrder : workOrders ) {
            DataSetItem dataSetItem = dataSet.addNewDataSetItem();
            dataSetItem.setPrimaryKey(workOrder.id + "");
            dataSetItem.setStringForAttributeIndex(workOrder.id + "", IDIndex);
            dataSetItem.setStringForAttributeIndex(workOrder.number, NumberIndex);
            dataSetItem.setStringForAttributeIndex(workOrder.description, DescriptionIndex);
            dataSetItem.setStringForAttributeIndex(workOrder.assignedTo, AssignedIndex);
            dataSetItem.setStringForAttributeIndex(workOrder.requestorId, RequestorIndex);
        }
        return new DataSourceResponse.Builder()
                .setSuccess(true)
                .setDataSet(dataSet)
                .setMessage("Sync complete")
                .build();
    }

    @Override
    public DataSourceResponse getDataSetItem(AuthenticationInfo authenticationInfo, String id, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public DataSourceResponse queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public DataSourceResponse createDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public DataSourceResponse updateDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public DataSourceResponse deleteDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }

    @Override
    public String getServiceName() {
        return "workorders";
    }

    @Override
    public List<ServiceConfigurationAttribute> getDataSetAttributes(AuthenticationInfo authenticationInfo, Parameters params) {
        ArrayList<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(IDIndex)
                .name("ID")
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(NumberIndex)
                .name("WO Number")
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(DescriptionIndex)
                .name("Description")
                .canUpdateAndRequired()
                .canCreateAndRequired()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(AssignedIndex)
                .name("AssignedTo")
                .canUpdateAndRequired()
                .canCreateAndRequired()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(RequestorIndex)
                .name("RequestorID")
                .canUpdateAndRequired()
                .canCreateAndRequired()
                .build());
        return attributes;
    }

    @Override
    public List<ServiceParameter> getServiceFilterParameters() {
        return null;
    }

    @Override
    public Response updateEventForDataSetItem(String dataSetItemID, Event event, AuthenticationInfo authenticationInfo, Parameters params) {
        return null;
    }

    @Override
    public List<String> getDependentLists() {
        return null;
    }

    @Override
    public DataSourceResponse bulkUpdateDataSetItems(List<String> primaryKeys, DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) throws InvalidAttributeValueException {
        return null;
    }
    static int IDIndex = 0;
    static int NumberIndex = 1;
    static int DescriptionIndex = 2;
    static int AssignedIndex = 3;
    static int RequestorIndex = 4;
}
