import org.joda.time.DateTime;
import sdk.annotations.Attribute;
import sdk.data.RelatedServiceConfiguration;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.models.AttributeType;
import sdk.models.Color;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Orozco on 7/19/17.
 */
public class SampleObject {
    @Attribute(index = 0, useGetterAndSetter = false)
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

    @Attribute(index = 11, relationshipClass = SampleRelationship.class, dataType = AttributeType.SingleRelationship)
    public SampleRelationship sampleListItemSingleRelationship;

    @Attribute(index = 12, relationshipClass = SampleRelationship.class, dataType = AttributeType.Relation)
    public List<SampleRelationship> sampleListItemRelationship;

    @Attribute(index = 17)
    public Color color;


    public static ServiceConfiguration getServiceConfiguration() {
        return new ServiceConfiguration.Builder("SampleObject").withAttributes(getServiceConfigurationAttributes()).build();
    }


    public static List<ServiceConfigurationAttribute> getServiceConfigurationAttributes() {
        List<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(0).name("woNumber").build());
        attributes.add(new ServiceConfigurationAttribute.Builder(1).name("testInt").asInt().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(2).name("testIntObject").asInt().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(3).name("testFloat").asDouble().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(4).name("testFloatObject").asDouble().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(5).name("testDouble").asDouble().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(6).name("testDoubleObject").asDouble().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(7).name("testDate").asDateTime().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(8).name("testJodaTimeDate").asDateTime().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(9).name("testSqlDate").asDateTime().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(10).name("sampleListItem").asListItem(SampleListItem.getListServiceConfigurationAttributes()).build());
        attributes.add(new ServiceConfigurationAttribute.Builder(11).name("sampleListItemSingleRelationship").asSingleRelationship(new RelatedServiceConfiguration("sampleListItemSingleRelationship", SampleListItem.getServiceConfigurationAttributes())).build());
        attributes.add(new ServiceConfigurationAttribute.Builder(12).name("sampleListItemRelationship").asRelationship(new RelatedServiceConfiguration("sampleListItemSingleRelationship", SampleListItem.getServiceConfigurationAttributes())).build());
        attributes.add(new ServiceConfigurationAttribute.Builder(17).name("color").asColor().build());
        return attributes;
    }

    @Attribute(index = 13)
    public String getWoNumber() {
        return woNumber;
    }

    public void setWoNumber(String woNumber) {
        this.woNumber = woNumber;
    }

    public int getTestInt() {
        return testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public Integer getTestIntObject() {
        return testIntObject;
    }

    public void setTestIntObject(Integer testIntObject) {
        this.testIntObject = testIntObject;
    }

    public float getTestFloat() {
        return testFloat;
    }

    public void setTestFloat(float testFloat) {
        this.testFloat = testFloat;
    }

    public Float getTestFloatObject() {
        return testFloatObject;
    }

    public void setTestFloatObject(Float testFloatObject) {
        this.testFloatObject = testFloatObject;
    }

    public double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(double testDouble) {
        this.testDouble = testDouble;
    }

    public Double getTestDoubleObject() {
        return testDoubleObject;
    }

    public void setTestDoubleObject(Double testDoubleObject) {
        this.testDoubleObject = testDoubleObject;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public DateTime getTestJodaTimeDate() {
        return testJodaTimeDate;
    }

    public void setTestJodaTimeDate(DateTime testJodaTimeDate) {
        this.testJodaTimeDate = testJodaTimeDate;
    }

    public java.sql.Date getTestSqlDate() {
        return testSqlDate;
    }

    public void setTestSqlDate(java.sql.Date testSqlDate) {
        this.testSqlDate = testSqlDate;
    }

    public SampleListItem getSampleListItem() {
        return sampleListItem;
    }

    public void setSampleListItem(SampleListItem sampleListItem) {
        this.sampleListItem = sampleListItem;
    }

    @Attribute(index = 14, dataType = AttributeType.SingleRelationship)
    public SampleRelationship getSampleListItemSingleRelationship() {
        return sampleListItemSingleRelationship;
    }

    public void setSampleListItemSingleRelationship(SampleRelationship sampleListItemSingleRelationship) {
        this.sampleListItemSingleRelationship = sampleListItemSingleRelationship;
    }

    public List<SampleRelationship> getSampleListItemRelationship() {
        return sampleListItemRelationship;
    }

    public void setSampleListItemRelationship(List<SampleRelationship> sampleListItemRelationship) {
        this.sampleListItemRelationship = sampleListItemRelationship;
    }

    @Attribute(index = 15, dataType = AttributeType.SingleRelationship)
    public String setSampleListItemRelationships() {
        return "";
    }


    @Attribute(index = 16)
    public String getTestRandom() {
        return "hello sir";
    }


    public void setTestRandom(List<SampleRelationship> sampleListItemRelationship) {
        this.sampleListItemRelationship = sampleListItemRelationship;
    }
}