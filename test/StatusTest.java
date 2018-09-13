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
        model.name = "COMPLETE";
        model.status = "COMPLETE";

        DataSetItem dataSetItem =
            new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestModel.class));
        ObjectConverter.copyToRecord(dataSetItem, model);
        System.out.println(dataSetItem.getStatus());
        if (!dataSetItem.getStatus().toString().equalsIgnoreCase(model.status)) {
            Assert.fail();
        }

        TestModel modelTwo = new TestModel();
        ObjectConverter.copyFromRecord(dataSetItem, modelTwo, false, null);
        System.out.println(modelTwo.status);
    }


    @Test
    public void mapMethodTest() {
        TypeManager.getMethodMap();
        Map<String, Method> methodMap = ObjectConverter.getAllMethodsFromClass(TestModel.class);
        if (methodMap.get("status") == null) {
            Assert.fail();
        }
    }

    class TestModel {
        @Status
        private String status;

        private String name;
    }
}
