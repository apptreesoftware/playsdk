import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import sdk.annotations.Attribute;
import sdk.converter.ObjectConverter;
import sdk.converter.ParserContext;
import sdk.data.DataSetItem;
import sdk.models.DateRange;
import sdk.models.DateTimeRange;

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


}
