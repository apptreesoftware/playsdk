import org.junit.Assert;
import org.junit.Test;
import sdk.annotations.Status;
import sdk.converter.ObjectConverter;
import sdk.converter.TypeManager;
import sdk.data.DataSetItem;

import java.lang.reflect.Method;
import java.util.Map;

public class StatusTest {


    @Test
    public void verifyStatusConversion() {
        TestModel model = new TestModel();
        model.setName("COMPLETE");
        model.setStatus("COMPLETE");

        DataSetItem dataSetItem =
            new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestModel.class));
        ObjectConverter.copyToRecord(dataSetItem, model);
        if (!dataSetItem.getStatus().toString().equalsIgnoreCase(model.getStatus())) {
            Assert.fail();
        }

        TestModel modelTwo = new TestModel();
        ObjectConverter.copyFromRecord(dataSetItem, modelTwo, false, null);
        if (!dataSetItem.getStatus().toString().equalsIgnoreCase(modelTwo.getStatus())) {
            Assert.fail();
        }
    }


    @Test
    public void mapMethodTest() {
        TypeManager.getMethodMap();
        Map<String, Method> methodMap = ObjectConverter.getAllMethodsFromClass(TestModel.class);
        if (methodMap.get("getfirstname") == null) {
            Assert.fail();
        } else {
            System.out.println("Success");
        }
    }

    class TestModel extends TestParent {
        @Status
        private String status;

        private String name;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class TestParent {
        private String firstName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }
}
