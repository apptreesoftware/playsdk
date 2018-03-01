import org.junit.Assert;
import org.junit.Test;
import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;

public class PrimaryKeyTest {



    @Test
    public void testPrimaryKeyNotOverwrite(){
        DemoObject demoObject = new DemoObject();
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(DemoObject.class));
        String value1 = "testValue1";
        String value2 = "testValue2";
        String primaryKey = "testOverWrite";
        dataSetItem.setPrimaryKey(primaryKey);
        dataSetItem.setString(value1, 0);
        dataSetItem.setString(value2, 1);

        ObjectConverter.copyFromRecord(dataSetItem, demoObject, true);
        Assert.assertTrue(!demoObject.testPrimaryKey.equals(primaryKey));
        Assert.assertTrue(demoObject.testPrimaryKey.equals(value1));
    }


    @Test
    public void testPrimaryKeyOverwrite(){
        DemoObject demoObject = new DemoObject();
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(DemoObject.class));
        String value1 = "testValue1";
        String value2 = "testValue2";
        String primaryKey = "testOverWrite";
        dataSetItem.setPrimaryKey(primaryKey);
        dataSetItem.setString(value1, 0);
        dataSetItem.setString(value2, 1);

        ObjectConverter.copyFromRecord(dataSetItem, demoObject, false);
        Assert.assertTrue(demoObject.testPrimaryKey.equals(primaryKey));
        Assert.assertTrue(!demoObject.testPrimaryKey.equals(value1));
    }


    public class DemoObject {
        @Attribute(index = 0)
        @PrimaryKey
        public String testPrimaryKey;

        @Attribute(index = 1)
        public String testSecondValue;

        public DemoObject() {
        }
    }
}
