import org.junit.Test;
import sdk.annotations.Status;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;

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


    class TestModel {
        @Status
        private String status;

        private String name;
    }
}
