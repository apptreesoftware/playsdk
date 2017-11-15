import org.joda.time.DateTime;
import sdk.annotations.Attribute;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfigurationAttribute;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Orozco on 7/25/17.
 */
public class SampleRelationship {

    @Attribute(index = 0)
    public String woNumber;

    @Attribute(index = 1)
    public int testInt;

    @Attribute(index = 2)
    public Integer testIntObject;

    @Attribute(index = 3)
    public float testFloat;

    @Attribute(index = 4)
    public Float testFloatObject;

    @Attribute(index = 5)
    public double testDouble;

    @Attribute(index = 6)
    public Double testDoubleObject;

    @Attribute(index = 7)
    public Date testDate;

    @Attribute(index = 8)
    public DateTime testJodaTimeDate;

    @Attribute(index = 9)
    public java.sql.Date testSqlDate;

    @Attribute(index = 10)
    public SampleListItem sampleListItem;


    public void copyToDataSetItem(DataSetItem dataSetItem) {
        dataSetItem.setPrimaryKey(woNumber);
        dataSetItem.setString(woNumber, 0);
        dataSetItem.setInt(testInt, 1);
        dataSetItem.setInt(testIntObject, 2);
        dataSetItem.setDouble(testFloat, 3);
        dataSetItem.setDouble(testFloatObject, 4);
        dataSetItem.setDouble(testDouble, 5);
        dataSetItem.setDouble(testDoubleObject, 6);
        dataSetItem.setDateTime(testJodaTimeDate, 7);
        dataSetItem.setDateTime(testJodaTimeDate, 8);
        dataSetItem.setDateTime(testJodaTimeDate, 9);
//        dataSetItem.setListItem(sampleListItem.toListItem(), 10);
    }

    public static List<ServiceConfigurationAttribute> getServiceConfigurationAttributes() {
        List<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add( new ServiceConfigurationAttribute.Builder(0).name("woNumber").build());
        attributes.add( new ServiceConfigurationAttribute.Builder(1).name("testInt").asInt().build());
        attributes.add( new ServiceConfigurationAttribute.Builder(2).name("testIntObject").asInt().build());
        attributes.add( new ServiceConfigurationAttribute.Builder(3).name("testFloat").asDouble().build());
        attributes.add( new ServiceConfigurationAttribute.Builder(4).name("testFloatObject").asDouble().build());
        attributes.add( new ServiceConfigurationAttribute.Builder(5).name("testDouble").asDouble().build());
        attributes.add( new ServiceConfigurationAttribute.Builder(6).name("testDoubleObject").asDouble().build());
        attributes.add( new ServiceConfigurationAttribute.Builder(7).name("testDate").asDateTime().build());
        attributes.add( new ServiceConfigurationAttribute.Builder(8).name("testJodaTimeDate").asDateTime().build());
        attributes.add( new ServiceConfigurationAttribute.Builder(9).name("testSqlDate").asDateTime().build());
        attributes.add( new ServiceConfigurationAttribute.Builder(10).name("sample list item").asListItem(SampleListItem.getListServiceConfigurationAttributes()).build());
        return attributes;
    }
}
