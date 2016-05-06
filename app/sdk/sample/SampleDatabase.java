package sdk.sample;

import com.avaje.ebean.Ebean;
import sdk.sample.model.WorkOrder;

import java.util.List;

/**
 * Created by matthew on 5/5/16.
 */
public class SampleDatabase {
    private static SampleDatabase instance = null;

    public static SampleDatabase getInstance() {
        if ( instance == null ) {
            instance = new SampleDatabase();
            instance.createSampleData();
        }
        return instance;
    }

    private SampleDatabase() {}

    public List<WorkOrder> fetchWorkOrders() {
        return WorkOrder.find.all();
    }

    private void createSampleData() {
        //EbeanServer server = EbeanServerFactory.create("sample");
        WorkOrder workOrder = new WorkOrder();
        workOrder.assignedTo = "Matthew Smith";
        workOrder.description = "This is a sample work order";
        workOrder.number = "WO000001";
        workOrder.requestorId = "1";


        Ebean.beginTransaction();
        workOrder.insert();
        Ebean.commitTransaction();
    }
}
