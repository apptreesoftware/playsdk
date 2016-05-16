package sdk.sample;

import com.avaje.ebean.ExpressionList;
import sdk.data.*;
import sdk.sample.model.WorkOrder;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.ArrayList;
import java.util.List;

import static sdk.sample.model.WorkOrder.*;

/**
 * Created by matthew on 5/5/16.
 */
public class WorkOrderDataSource implements DataSource {

    @Override
    public DataSet getDataSet(AuthenticationInfo authenticationInfo, Parameters params) {
        DataSet dataSet = newEmptyDataSet(authenticationInfo, params);

        List<WorkOrder> workOrders = SampleDatabase.getServer().find(WorkOrder.class).findList();
        for ( WorkOrder workOrder : workOrders ) {
            DataSetItem dataSetItem = dataSet.addNewDataSetItem();
            workOrder.copyIntoDataSetItem(dataSetItem);
        }
        return dataSet.withSuccessMessage("Sync complete");
    }

    @Override
    public DataSet getDataSetItem(AuthenticationInfo authenticationInfo, String id, Parameters params) {
        DataSet dataSet = newEmptyDataSet(authenticationInfo, params);
        WorkOrder workOrder = SampleDatabase.getServer().find(WorkOrder.class,id);
        if ( workOrder != null ) {
            DataSetItem item = dataSet.addNewDataSetItem();
            workOrder.copyIntoDataSetItem(item);
        } else {
            dataSet.withFailureMessage("Item with primary key " + id + " not found.");
        }
        return dataSet;
    }

    @Override
    public DataSet queryDataSet(DataSetItem queryDataItem, AuthenticationInfo authenticationInfo, Parameters params) {
        String description = queryDataItem.getStringAttributeAtIndex(DescriptionIndex);
        String woNumber = queryDataItem.getStringAttributeAtIndex(NumberIndex);
        ExpressionList<WorkOrder> expression = SampleDatabase.getServer().find(WorkOrder.class).where();
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
        return dataSet;
    }

    @Override
    public DataSet createDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.copyFromDataSetItem(dataSetItem);
        SampleDatabase.getInstance().performTransaction(server -> {
            server.insert(workOrder);
            server.refresh(workOrder);
        });
        DataSet dataSet = newEmptyDataSet(authenticationInfo, params);
        DataSetItem responseItem = dataSet.addNewDataSetItem();
        workOrder.copyIntoDataSetItem(responseItem);
        return dataSet.withSuccessMessage("Record Created");
    }

    @Override
    public DataSet updateDataSetItem(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        WorkOrder workOrder = SampleDatabase.getServer().find(WorkOrder.class, dataSetItem.getPrimaryKey());
        if ( workOrder == null ) {
            throw new RuntimeException("Record not found");
        }
        workOrder.copyFromDataSetItem(dataSetItem);
        SampleDatabase.getInstance().performTransaction(server -> {
           server.save(workOrder);
            server.refresh(workOrder);
        });
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
