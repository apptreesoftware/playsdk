import org.joda.time.DateTime;
import sdk.annotations.*;
import sdk.data.RelatedServiceConfiguration;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.models.AttributeType;
import sdk.models.Color;
import sdk.models.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Orozco on 7/19/17.
 */
public class SampleObject {
    @PrimaryKey
    @PrimaryValue
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

    @Relationship(index = 12)
    public List<SampleRelationship> sampleListItemRelationship;


    @Attribute(index = 17)
    public Color color;

    @Attribute(index = 18)
    public Location sdkLocation;

    @Attribute(index = 19)
    public SampleLocation customLocation;

    public static class TestLocation implements CustomLocation {
        public double latitude;
        public double longitude;

        public TestLocation() {}

        @Override
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        @Override
        public double getLatitude() {
            return this.latitude;
        }

        @Override
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @Override
        public double getLongitude() {
            return this.longitude;
        }
    }


    public static ServiceConfiguration getServiceConfiguration() {
        return new ServiceConfiguration.Builder("SampleObject").withAttributes(getServiceConfigurationAttributes()).build();
    }


    public static List<ServiceConfigurationAttribute> getServiceConfigurationAttributes() {
        List<ServiceConfigurationAttribute> attributes = new ArrayList<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(0).name("Wo Number").canCreate().canSearch().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(1).name("Test Int").canCreate().canSearch().canUpdate().asInt().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(2).name("Test Int Object").canCreate().canSearch().canUpdate().asInt().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(3).name("Test Float").canCreate().canSearch().canUpdate().asDouble().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(4).name("Test Float Object").canCreate().canSearch().canUpdate().asDouble().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(5).name("Test Double").canCreate().canSearch().canUpdate().asDouble().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(6).name("Test Double Object").canCreate().canSearch().canUpdate().asDouble().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(7).name("Test Date").canCreate().canSearch().canUpdate().asDateTime().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(8).name("Test Joda Time Date").canCreate().canSearch().canUpdate().asDateTime().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(9).name("Test Sql Date").canCreate().canSearch().canUpdate().asDateTime().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(10).name("Sample List Item").canCreate().canSearch().canUpdate().asListItem(SampleListItem.getListServiceConfigurationAttributes()).build());
        attributes.add(new ServiceConfigurationAttribute.Builder(11).name("Sample List Item Single Relationship").canCreate().canSearch().canUpdate().asSingleRelationship(new RelatedServiceConfiguration("sampleListItemSingleRelationship", SampleRelationship.getServiceConfigurationAttributes())).build());
        attributes.add(new ServiceConfigurationAttribute.Builder(12).name("Sample List Item Relationship").canCreate().canSearch().canUpdate().asRelationship(new RelatedServiceConfiguration("sampleListItemSingleRelationship", SampleRelationship.getServiceConfigurationAttributes())).build());
        attributes.add(new ServiceConfigurationAttribute.Builder(17).name("Color").asColor().canCreate().canUpdate().canSearch().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(18).name("Sdk Location").asLocation().canUpdate().canCreate().canSearch().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(19).name("Custom Location").asLocation().canUpdate().canCreate().canSearch().build());
        return attributes;
    }

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
    public String setSampleListItemRelationships() {
        return "";
    }



    public void setTestRandom(List<SampleRelationship> sampleListItemRelationship) {
        this.sampleListItemRelationship = sampleListItemRelationship;
    }
}