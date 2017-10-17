import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
import sdk.controllers.DataController;
import sdk.controllers.DataSetController;
import sdk.converter.ObjectConverter;
import sdk.converter.ParserContext;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.models.AttributeType;
import sdk.models.DateRange;
import sdk.models.DateTimeRange;
import sdk.utils.JsonUtils;

import java.util.*;

/**
 * Created by Orozco on 9/19/17.
 */
public class DateRangeTest {


    class DateRangeTestClass {
        @Attribute(index = 0)
        private DateTime createDate;

        public DateTime getCreateDate() {
            return createDate;
        }

        public void setCreateDate(DateTime createDate) {
            this.createDate = createDate;
        }
    }


    @Test
    public void testDateRange() {
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(DateRangeTestClass.class));
        DateTimeRange dateRange = new DateTimeRange();
        dateRange.setFrom(new DateTime());
        dateRange.setTo(new DateTime());
        dataSetItem.setDateTimeRangeForAttributeIndex(dateRange, 0);
        DateRangeTestClass dateRangeTestClass = new DateRangeTestClass();
        ParserContext parserContext = ObjectConverter.copyFromRecord(dataSetItem, dateRangeTestClass);
        Assert.assertTrue((!parserContext.getDateTimeRangeForField(0).isEmpty()));
        Assert.assertTrue((parserContext.getDateTimeRangeForField(0).getFrom() != null));
        Assert.assertTrue((parserContext.getDateTimeRangeForField(0).getTo() != null));
        Assert.assertTrue((!parserContext.getDateTimeRangeForField("createDate").isEmpty()));
        Assert.assertTrue((parserContext.getDateTimeRangeForField("createDate").getFrom() != null));
        Assert.assertTrue((parserContext.getDateTimeRangeForField("createDate").getTo() != null));
    }


    @Test
    public void testParseDataSet() {
        String testJsonDateRange = "{\n" +
                "    \"primaryKey\": \"\",\n" +
                "    \"clientKey\": \"72850601-190e-472c-8a2b-7ab3f4468061\",\n" +
                "    \"CRUDStatus\": \"CREATE\",\n" +
                "    \"recordType\": \"RECORD\",\n" +
                "    \"status\": \"NONE\",\n" +
                "    \"attributes\": [\n" +
                "        \"12\",\n" +
                "        null,\n" +
                "        null,\n" +
                "        \"{\\\"from\\\":\\\"2017-10-10 22:48:00\\\",\\\"to\\\":\\\"2017-10-11 22:48:00\\\"}\"\n" +
                "    ]\n" +
                "}";
        Optional<JsonNode> node = JsonUtils.parseOptional(testJsonDateRange);
        ObjectNode dataSetItemObject = (ObjectNode) node.get();
        Collection<ServiceConfigurationAttribute> serviceConfiguration = ObjectConverter.generateConfigurationAttributes(TestDateRangeObject.class);
        DataSetItem dataSetitem = new DataSetItem(serviceConfiguration);
        dataSetitem.updateFromJSON(dataSetItemObject, new HashMap<>(), true);
        TestDateRangeObject testDateRangeObject = new TestDateRangeObject();
        ParserContext context = ObjectConverter.copyFromRecord(dataSetitem, testDateRangeObject);
        DateTimeRange dateTime = context.getDateTimeRangeForField("dateTime");
        Assert.assertTrue(dateTime != null);
        Assert.assertTrue(dateTime.getFrom() != null);
        Assert.assertTrue(dateTime.getTo() != null);
    }


    @Test
    public void testParseDataSetDate() {
        String testJsonDateRange = "{\n" +
                "    \"primaryKey\": \"\",\n" +
                "    \"clientKey\": \"72850601-190e-472c-8a2b-7ab3f4468061\",\n" +
                "    \"CRUDStatus\": \"CREATE\",\n" +
                "    \"recordType\": \"RECORD\",\n" +
                "    \"status\": \"NONE\",\n" +
                "    \"attributes\": [\n" +
                "        \"12\",\n" +
                "        null,\n" +
                "        null,\n" +
                "        \"2017-10-10 22:48:00\"\n" +
                "    ]\n" +
                "}";
        Optional<JsonNode> node = JsonUtils.parseOptional(testJsonDateRange);
        ObjectNode dataSetItemObject = (ObjectNode) node.get();
        Collection<ServiceConfigurationAttribute> serviceConfiguration = ObjectConverter.generateConfigurationAttributes(TestDateRangeObject.class);
        DataSetItem dataSetitem = new DataSetItem(serviceConfiguration);
        dataSetitem.updateFromJSON(dataSetItemObject, new HashMap<>(), false);
        TestDateRangeObject testDateRangeObject = new TestDateRangeObject();
        ParserContext context = ObjectConverter.copyFromRecord(dataSetitem, testDateRangeObject);
        Assert.assertTrue(testDateRangeObject != null);
        Assert.assertTrue(testDateRangeObject.dateTime != null);
    }


    public class TestDateRangeObject {
        @PrimaryKey
        @Attribute(index = 0)
        private int id;
        @Attribute(index = 1)
        private String name;
        @Attribute(index = 2)
        private String value;
        @Attribute(index = 3)
        private DateTime dateTime;
    }


}
