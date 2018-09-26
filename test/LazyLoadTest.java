import org.junit.Assert;
import sdk.annotations.Attribute;
import sdk.annotations.Relationship;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;

import java.util.ArrayList;
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

    @org.junit.Test
    public void testPath() {
        Test test = new Test();
        test.age = 1;
        test.name = "AppTree";
        test.id = 9000 + 1;
        test.friends = new ArrayList<>();
        DataSetItem dataSetItem = new DataSetItem(generateConfigurationAttributes(Test.class));
        ObjectConverter.copyToRecord(dataSetItem, test);
        Assert.assertEquals("/load/me", dataSetItem.getPathForIndex(3));
    }


    class Test {
        @Attribute(index = 0)
        private int id;
        @Attribute(index = 1)
        private String name;
        @Attribute(index = 2)
        private double age;
        @Relationship(index = 3, eager = false, path = "/load/me")
        private List<Test> friends;
    }
}
