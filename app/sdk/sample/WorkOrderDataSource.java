package sdk.sample;

import com.avaje.ebean.ExpressionList;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.base.DataSource;
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
        DataSet dataSet = newEmptyDataSet();

        List<WorkOrder> workOrders = SampleDatabase.getServer().find(WorkOrder.class).findList();
        for ( WorkOrder workOrder : workOrders ) {
            DataSetItem dataSetItem = dataSet.addNewDataSetItem();
            workOrder.copyIntoDataSetItem(dataSetItem);
        }
        return dataSet.withSuccessMessage("Sync complete");
    }

    @Override
    public DataSetItem getRecord(String id, AuthenticationInfo authenticationInfo, Parameters params) {
        WorkOrder workOrder = SampleDatabase.getServer().find(WorkOrder.class,id);
        if ( workOrder != null ) {
            DataSetItem dataSetItem = new DataSetItem(getAttributes());
            workOrder.copyIntoDataSetItem(dataSetItem);
            return dataSetItem;
        }
        throw new RuntimeException("Item with key " + id + " does not exist");
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
        DataSet dataSet = newEmptyDataSet();
        expression.findEach(workOrder -> {
            DataSetItem item = dataSet.addNewDataSetItem();
            workOrder.copyIntoDataSetItem(item);
        });
        return dataSet;
    }

    @Override
    public DataSetItem createRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.copyFromDataSetItem(dataSetItem);
        SampleDatabase.getInstance().performTransaction(server -> {
            server.insert(workOrder);
            server.refresh(workOrder);
        });
        DataSetItem responseItem = new DataSetItem(getAttributes());
        workOrder.copyIntoDataSetItem(responseItem);
        return responseItem;
    }

    @Override
    public DataSetItem updateRecord(DataSetItem dataSetItem, AuthenticationInfo authenticationInfo, Parameters params) {
        WorkOrder workOrder = SampleDatabase.getServer().find(WorkOrder.class, dataSetItem.getPrimaryKey());
        if ( workOrder == null ) {
            throw new RuntimeException("Record not found");
        }
        workOrder.copyFromDataSetItem(dataSetItem);
        SampleDatabase.getInstance().performTransaction(server -> {
           server.save(workOrder);
            server.refresh(workOrder);
        });
        return getRecord(workOrder.id + "",authenticationInfo, params);
    }

    @Override
    public String getServiceDescription() {
        return "Work Orders";
    }

    @Override
    public List<ServiceConfigurationAttribute> getAttributes() {
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
