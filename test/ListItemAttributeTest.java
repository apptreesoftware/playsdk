import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;
import sdk.annotations.Attribute;
import sdk.annotations.ParentValue;
import sdk.annotations.PrimaryKey;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;
import sdk.list.ListItem;
import sdk.utils.JsonUtils;

/**
 * Created by Orozco on 9/15/17.
 */
public class ListItemAttributeTest {


    @Test
    public void testListItemAttributes() {
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestDataSetItemObject.class));
        TestDataSetItemObject testDataSetItemObject = getTestDataSetItemObject();
        ObjectConverter.copyToRecord(dataSetItem, testDataSetItemObject);
        ObjectNode node = dataSetItem.toJSONWithPrimaryKey();
        DataSetItem newDataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(TestDataSetItemObject.class));
        newDataSetItem.updateFromJSON(node, null, false);
        Assert.assertTrue(dataSetItem.equals(newDataSetItem));
    }


    @Test
    public void testListItemParentValue() {
        TestListItemObject testListItemObject = getTestListItemObject();
        ListItem listItem = new ListItem();
        ObjectConverter.copyToRecord(listItem, testListItemObject);

        if (!listItem.parentID.equals("44")) {
            Assert.fail();
        } else {
            Assert.assertTrue(true);
        }
    }


    private TestListItemObject getTestListItemObject() {
        TestListItemObject testListItemObject = new TestListItemObject();
        testListItemObject.setDescription("This is the item description");
        testListItemObject.setId(4);
        testListItemObject.setName("List Item");
        testListItemObject.setParentId(44);
        return testListItemObject;
    }


    private TestDataSetItemObject getTestDataSetItemObject() {
        TestDataSetItemObject testDataSetItemObject = new TestDataSetItemObject();
        testDataSetItemObject.setId(4);
        testDataSetItemObject.setName("Data Set Item");
        testDataSetItemObject.setTestListItemObject(getTestListItemObject());
        return testDataSetItemObject;
    }


    class TestDataSetItemObject {

        @PrimaryKey
        @Attribute(index = 0)
        private int id;

        @Attribute(index = 1)
        private String name;

        @Attribute(index = 2)
        private TestListItemObject testListItemObject;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TestListItemObject getTestListItemObject() {
            return testListItemObject;
        }

        public void setTestListItemObject(TestListItemObject testListItemObject) {
            this.testListItemObject = testListItemObject;
        }
    }


    class TestListItemObject {

        @PrimaryKey
        private int id;

        @Attribute(index = 0)
        private String name;

        @Attribute(index = 1)
        private String description;

        @ParentValue
        @Attribute(index = 2)
        private int parentId;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }


}
