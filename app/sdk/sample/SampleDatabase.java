package sdk.sample;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import sdk.sample.model.WorkOrder;

import java.util.List;

/**
 * Created by matthew on 5/5/16.
 */
public class SampleDatabase {
    private static SampleDatabase instance = null;
    private Model.Finder<String, WorkOrder> workOrderFinder;

    public static SampleDatabase getInstance() {
        if ( instance == null ) {
            instance = new SampleDatabase();
            instance.createSampleData();
        }
        return instance;
    }

    private SampleDatabase() {}

    public Model.Finder<String, WorkOrder> getWorkOrderFinder() {
        if ( workOrderFinder == null ) {
            workOrderFinder = new Model.Finder<>(WorkOrder.class);
        }
        return workOrderFinder;
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
