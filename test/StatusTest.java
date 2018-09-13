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
        model.status = "New";

        DataSetItem dataSetItem =
            new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestModel.class));
        ObjectConverter.copyToRecord(dataSetItem, model);
        System.out.println(dataSetItem.getStatus());
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
