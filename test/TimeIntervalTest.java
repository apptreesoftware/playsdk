import org.junit.Assert;
import org.junit.Test;
import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
import sdk.converter.ObjectConverter;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.models.AttributeType;

import javax.validation.constraints.AssertTrue;
import java.util.*;

public class TimeIntervalTest {


    public class TimeIntTest {
        @PrimaryKey
        @Attribute(index = 0)
        public int id;
        @Attribute(index = 1)
        public double value;
        @Attribute(index = 2)
        public String name;
        @Attribute(index = 3, dataType = AttributeType.TimeInterval)
        public long time;


        public List<ServiceConfigurationAttribute> getAttributes() {
            List<ServiceConfigurationAttribute> attributes = new ArrayList<>();
            attributes.add(new ServiceConfigurationAttribute.Builder(0).name("Id").asInt().canCreate().canSearch().canUpdate().build());
            attributes.add(new ServiceConfigurationAttribute.Builder(1).name("Value").asDouble().canCreate().canSearch().canUpdate().build());
            attributes.add(new ServiceConfigurationAttribute.Builder(2).name("Name").canCreate().canSearch().canUpdate().build());
            attributes.add(new ServiceConfigurationAttribute.Builder(3).name("Time").asTimeInterval().canCreate().canSearch().canUpdate().build());
            return attributes;
        }

    }


    @Test
    public void testTimeIntervalConfiguration() {
        ServiceConfiguration configuration = ObjectConverter.generateConfiguration(TimeIntTest.class);
        TimeIntTest timeIntTest = new TimeIntTest();
        ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
        serviceConfiguration.setName("TimeIntervalTest$TimeIntTest");
        serviceConfiguration.attributes = timeIntTest.getAttributes();
        Assert.assertTrue(configuration.equals(serviceConfiguration));
    }


    @Test
    public void testFromDataSetItem() {
        DataSetItem dataSetItem = new DataSetItem(ObjectConverter.generateConfigurationAttributes(TimeIntTest.class));
        dataSetItem.setInt(1, 0);
        dataSetItem.setDouble(1.0, 1);
        dataSetItem.setString("Testing", 2);
        dataSetItem.setTimeInterval(100L, 3);

        TimeIntTest timeIntTest = new TimeIntTest();
        ObjectConverter.copyFromRecord(dataSetItem, timeIntTest, false);
        Assert.assertTrue(dataSetItem.getInt(0) == timeIntTest.id);
        Assert.assertTrue(dataSetItem.getDouble(1) == timeIntTest.value);
        Assert.assertTrue(dataSetItem.getString(2).equals(timeIntTest.name));
        Assert.assertTrue(dataSetItem.getTimeInterval(3) == timeIntTest.time);

        DataSetItem dataSetItem1 = new DataSetItem(ObjectConverter.generateConfigurationAttributes(TimeIntTest.class));
        ObjectConverter.copyToRecord(dataSetItem1, timeIntTest);

        Assert.assertTrue(dataSetItem.getInt(0) == dataSetItem1.getInt(0));
        Assert.assertTrue(dataSetItem.getDouble(1) == dataSetItem1.getDouble(1));
        Assert.assertTrue(dataSetItem.getString(2).equals(dataSetItem1.getString(2)));
        Assert.assertTrue(dataSetItem.getTimeInterval(3) == dataSetItem1.getTimeInterval(3));
    }


}
