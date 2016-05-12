package sdk.sample;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.Model;
import sdk.sample.model.Session;
import sdk.sample.model.User;
import sdk.sample.model.WorkOrder;

/**
 * Created by matthew on 5/5/16.
 */
public class SampleDatabase {
    interface Transaction {
        void execute(EbeanServer server);
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

    public static ExpressionFactory getExpressionFactory() {
        return getInstance().server().getExpressionFactory();
    }

    public static EbeanServer getServer() {
        return SampleDatabase.getInstance().server();
    }

    private SampleDatabase() {}

    EbeanServer server() {
        return Ebean.getServer("sample");
    }

    private void createSampleData() {
        if ( server().find(WorkOrder.class).findRowCount() > 0 ) {
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
        EbeanServer server = Ebean.getServer("sample");
        server.beginTransaction();
        server.insert(workOrder);
        server.insert(user);
        server.commitTransaction();
    }

    public void performTransaction(Transaction transaction) {
        EbeanServer server = Ebean.getServer("sample");
        transaction.execute(server);
        server.commitTransaction();
    }
}
