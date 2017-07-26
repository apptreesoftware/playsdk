import org.joda.time.DateTime;
import sdk.annotations.Attribute;
import sdk.data.ServiceConfigurationAttribute;
import sdk.list.ListServiceConfigurationAttribute;

import java.util.*;

/**
 * Created by Orozco on 7/20/17.
 */
public class SampleListItem {
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





    public static Set<ListServiceConfigurationAttribute> getListServiceConfigurationAttributes(){
        Set<ListServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add( new ListServiceConfigurationAttribute.Builder(0).name("woNumber").build());
        attributes.add( new ListServiceConfigurationAttribute.Builder(1).name("testInt").asInt().build());
        attributes.add( new ListServiceConfigurationAttribute.Builder(2).name("testIntObject").asInt().build());
        attributes.add( new ListServiceConfigurationAttribute.Builder(3).name("testFloat").asDouble().build());
        attributes.add( new ListServiceConfigurationAttribute.Builder(4).name("testFloatObject").asDouble().build());
        attributes.add( new ListServiceConfigurationAttribute.Builder(5).name("testDouble").asDouble().build());
        attributes.add( new ListServiceConfigurationAttribute.Builder(6).name("testDoubleObject").asDouble().build());
        attributes.add( new ListServiceConfigurationAttribute.Builder(7).name("testDate").asDateTime().build());
        attributes.add( new ListServiceConfigurationAttribute.Builder(8).name("testJodaTimeDate").asDateTime().build());
        attributes.add( new ListServiceConfigurationAttribute.Builder(9).name("testSqlDate").asDateTime().build());
        return attributes;
    }



    public static List<ServiceConfigurationAttribute> getServiceConfigurationAttributes(){
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
        return attributes;
    }












}
