import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import sdk.annotations.Attribute;
import sdk.data.DataSetItemAttachment;
import sdk.data.RelatedServiceConfiguration;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.list.ListItem;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.models.AttributeType;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Orozco on 7/19/17.
 */
public class SampleObject {
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

    @Attribute(index = 11, relationShipClass = SampleRelationship.class, dataType = AttributeType.SingleRelationship)
    public SampleRelationship sampleListItemSingleRelationship;

    @Attribute(index = 12, relationShipClass = SampleRelationship.class, dataType = AttributeType.Relation)
    public List<SampleRelationship> sampleListItemRelationship;


    public static ServiceConfiguration getServiceConfiguration() {
            return new ServiceConfiguration.Builder("SampleObject").withAttributes(getServiceConfigurationAttributes()).build();
    }



    private static List<ServiceConfigurationAttribute> getServiceConfigurationAttributes(){
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
        attributes.add( new ServiceConfigurationAttribute.Builder(10).name("sampleListItem").asListItem(SampleListItem.getListServiceConfigurationAttributes()).build());
        attributes.add( new ServiceConfigurationAttribute.Builder(11).name("sampleListItemSingleRelationship").asSingleRelationship(new RelatedServiceConfiguration("sampleListItemSingleRelationship", SampleListItem.getServiceConfigurationAttributes())).build());
        attributes.add( new ServiceConfigurationAttribute.Builder(12).name("sampleListItemRelationship").asRelationship(new RelatedServiceConfiguration("sampleListItemSingleRelationship", SampleListItem.getServiceConfigurationAttributes())).build());
        return attributes;
    }















}