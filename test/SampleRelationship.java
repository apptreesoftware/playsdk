import org.joda.time.DateTime;
import sdk.annotations.Attribute;
import sdk.data.DataSetItem;

import java.util.Date;

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


}
