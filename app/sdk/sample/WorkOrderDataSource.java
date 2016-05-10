package sdk.sample;

import com.avaje.ebean.ExpressionList;
import javassist.tools.reflect.Sample;
import sdk.data.*;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;
import sdk.utils.Response;
import sdk.utils.ServiceParameter;
import sdk.sample.model.WorkOrder;

import java.util.ArrayList;
import java.util.List;

import static sdk.sample.model.WorkOrder.*;

/**
 * Created by matthew on 5/5/16.
 */
public class WorkOrderDataSource implements DataSource {

    @Override
    public DataSourceResponse getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        DataSet dataSet = newEmptyDataSet(authenticationInfo, params);

        List<WorkOrder> workOrders = SampleDatabase.getInstance().getWorkOrderFinder().all();
        for ( WorkOrder workOrder : workOrders ) {
            DataSetItem dataSetItem = dataSet.addNewDataSetItem();
            workOrder.copyIntoDataSetItem(dataSetItem);
        }
        return new DataSourceResponse.Builder()
                .setSuccess(true)
                .setDataSet(dataSet)
                .setMessage("Sync complete")
                .build();
    }

    @Override
    public DataSourceResponse getDataSetItem(AuthenticationInfo authenticationInfo, String id, Parameters params) {
        DataSet dataSet = newEmptyDataSet(authenticationInfo, params);
        WorkOrder workOrder = SampleDatabase.getInstance().getWorkOrderFinder().byId(id);
        DataSourceResponse.Builder responseBuilder = new DataSourceResponse.Builder();
        if ( workOrder != null ) {
            DataSetItem item = dataSet.addNewDataSetItem();
            workOrder.copyIntoDataSetItem(item);
            responseBuilder.setDataSet(dataSet).setSuccess(true);
        } else {
            responseBuilder.setDataSet(dataSet).setSuccess(false).setMessage("Item with primary key " + id + " not found.");
        }
        return responseBuilder.build();
    }

    @Override
    public DataSourceResponse queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
        String description = queryDataItem.getStringAttributeAtIndex(DescriptionIndex);
        String woNumber = queryDataItem.getStringAttributeAtIndex(NumberIndex);
        ExpressionList<WorkOrder> expression = SampleDatabase.getInstance().getWorkOrderFinder().where();
        if ( description != null ) {
            expression.contains("description", description);
        }
        if ( woNumber != null ) {
            expression.contains("number", woNumber);
        }
        DataSet dataSet = newEmptyDataSet(authenticationInfo, params);
        expression.findEach(workOrder -> {
            DataSetItem item = dataSet.addNewDataSetItem();
            workOrder.copyIntoDataSetItem(item);
        });
        return new DataSourceResponse.Builder()
                .setSuccess(true)
                .setDataSet(dataSet)
                .build();
    }

    @Override
    public DataSourceResponse createDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.copyFromDataSetItem(dataSetItem);
        workOrder.insert();
        workOrder.refresh();
        DataSet dataSet = newEmptyDataSet(authenticationInfo, params);
        DataSetItem responseItem = dataSet.addNewDataSetItem();
        workOrder.copyIntoDataSetItem(responseItem);
        return new DataSourceResponse.Builder().setMessage("Record Created").setDataSet(dataSet).setSuccess(true).build();
    }

    @Override
    public DataSourceResponse updateDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        WorkOrder workOrder = SampleDatabase.getInstance().getWorkOrderFinder().byId(dataSetItem.getPrimaryKey());
        if ( workOrder == null ) {
            throw new RuntimeException("Record not found");
        }
        workOrder.copyFromDataSetItem(dataSetItem);
        workOrder.save();
        workOrder.refresh();
        return getDataSetItem(authenticationInfo, workOrder.id + "", params);
    }

    @Override
    public String getServiceDescription() {
        return "Work Orders";
    }

    @Override
    public List<ServiceConfigurationAttribute> getDataSetAttributes(AuthenticationInfo authenticationInfo, Parameters params) {
        ArrayList<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(WorkOrder.IDIndex)
                .name("ID")
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(NumberIndex)
                .name("WO Number")
                .canSearch()
                .build());
        attributes.add(new ServiceConfigurationAttribute.Builder(DescriptionIndex)
                .name("Description")
                .canUpdateAndRequired()
                .canCreateAndRequired()
                .canSearch()
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
}
