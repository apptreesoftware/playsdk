import sdk.annotations.Attribute;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.list.ListServiceConfiguration;
import sdk.list.ListServiceConfigurationAttribute;

import java.util.*;

/**
 * Created by Orozco on 7/19/17.
 */
public class SampleConfig {

    @Attribute(index = 0)
    public String name;

    @Attribute(index = 1)
    public int age;

    @Attribute(index = 2)
    public double weight;


    public Set<ServiceConfigurationAttribute> getServiceConfigurationAttributes(){
        Set<ServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(0).name("Name").asText().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(1).name("Age").asInt().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(2).name("Weight").asDouble().build());
        return attributes;
    }


    public Set<ListServiceConfigurationAttribute> getListServiceConfiguration(){
        Set<ListServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ListServiceConfigurationAttribute.Builder(0).name("Name").build());
        attributes.add(new ListServiceConfigurationAttribute.Builder(1).name("Age").asInt().build());
        attributes.add(new ListServiceConfigurationAttribute.Builder(2).name("Weight").asDouble().build());
        return attributes;
    }

}
