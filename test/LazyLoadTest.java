import org.junit.Assert;
import sdk.annotations.Attribute;
import sdk.annotations.Relationship;
import sdk.data.DataSetItem;

import java.util.List;

import static sdk.converter.ConfigurationManager.generateConfigurationAttributes;

public class LazyLoadTest {


    @org.junit.Test
    public void testIndexChecker() {
        String path = "path";
        int attributeIndex = 5;
        try {
            DataSetItem dataSetItem = new DataSetItem(generateConfigurationAttributes(Test.class));
            dataSetItem.useLazyLoad(attributeIndex, path);
            Assert.fail();
        } catch (Exception e) {
        }
    }


    @org.junit.Test
    public void testPathChecker() {
        String path = "";
        int attributeIndex = 1;
        try {
            DataSetItem dataSetItem = new DataSetItem(generateConfigurationAttributes(Test.class));
            dataSetItem.useLazyLoad(attributeIndex, path);
            Assert.fail();
        } catch (Exception e) {
        }
    }

    @org.junit.Test
    public void testIndexDataType() {
        String path = "path";
        int attributeIndex = 1;
        try {
            DataSetItem dataSetItem = new DataSetItem(generateConfigurationAttributes(Test.class));
            dataSetItem.useLazyLoad(attributeIndex, path);
            Assert.fail();
        } catch (Exception e) {
        }
    }


    @org.junit.Test
    public void testValidCombination() {
        String path = "path";
        int attributeIndex = 3;
        try {
            DataSetItem dataSetItem = new DataSetItem(generateConfigurationAttributes(Test.class));
            dataSetItem.useLazyLoad(attributeIndex, path);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }


    class Test {
        @Attribute(index = 0)
        private int id;
        @Attribute(index = 1)
        private String name;
        @Attribute(index = 2)
        private double age;
        @Relationship(index = 3)
        private List<Test> friends;
    }
}
