package sdk.sample;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import sdk.sample.model.Priority;
import sdk.sample.model.Session;
import sdk.sample.model.User;
import sdk.sample.model.WorkOrder;

import java.util.List;

/**
 * Created by matthew on 5/5/16.
 */
public class SampleDatabase {
    interface Transaction {
        void execute();
    }

    private static SampleDatabase instance = null;
    private Model.Finder<String, WorkOrder> workOrderFinder;
    private Model.Finder<String, User> userFinder;
    private Model.Finder<String, Session> sessionFinder;

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

    public Model.Finder<String, User> getUserFinder() {
        if ( userFinder == null ) {
            userFinder = new Model.Finder<>(User.class);
        }
        return userFinder;
    }

    public Model.Finder<String, Session> getSessionFinder() {
        if ( sessionFinder == null ) {
            sessionFinder = new Model.Finder<>(Session.class);
        }
        return sessionFinder;
    }

    private void createSampleData() {
        if ( getWorkOrderFinder().findRowCount() > 0 ) {
            return;
        }
        //EbeanServer server = EbeanServerFactory.create("sample");
        WorkOrder workOrder = new WorkOrder();
        workOrder.assignedTo = "Matthew Smith";
        workOrder.description = "This is a sample work order";
        workOrder.number = "WO000001";
        workOrder.requestorId = "1";

        User user = new User();
        user.username = "matthewsmith";
        user.password = "test123";
        user.firstName = "Matthew";
        user.lastName = "Smith";
        user.email = "facilitiesmaniac@hotmail.com";
        user.phone = "1800 ATYOURSERVICE";
        user.supervisorID = 2;
        user.employeeID = "ABC123";

        Ebean.beginTransaction();
        workOrder.insert();
        user.insert();
        Ebean.commitTransaction();
    }

    public void performTransaction(Transaction transaction) {
        Ebean.beginTransaction();
        transaction.execute();
        Ebean.endTransaction();
    }
}
