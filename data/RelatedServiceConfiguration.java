package sdk.data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by alexis on 5/3/16.
 */
public class RelatedServiceConfiguration extends ServiceConfiguration {
    /**
     * Creates a related service
     * @param name The name of the service
     * @param attributes The attributes of the service
     */
    public RelatedServiceConfiguration(String name, List<ServiceConfigurationAttribute> attributes) {
        super(name,attributes,null,null);
    }

    private HashMap<Integer, ServiceConfigurationAttribute> attributeConfigurationForIndexMap;

    public HashMap<Integer, ServiceConfigurationAttribute> getAttributeConfigurationForIndexMap() {
        if ( attributeConfigurationForIndexMap == null ) {
            attributeConfigurationForIndexMap = new HashMap<Integer, ServiceConfigurationAttribute>();
            for ( ServiceConfigurationAttribute attribute : getAttributes() ) {
                attributeConfigurationForIndexMap.put(attribute.getAttributeIndex(), attribute);
            }
        }
        return attributeConfigurationForIndexMap;
    }

    public static class Builder {
        String mServiceName;
        List<ServiceConfigurationAttribute> mAttributes;

        /**
         * Creates a related service builder
         * @param serviceName The name of the service
         */
        public Builder(String serviceName) {
            mServiceName = serviceName;
        }

        /**
         * Sets the attributes
         * @param attributes The attributes for the related service
         * @return The builder with the specified attributes
         */
        public Builder withAttributes(List<ServiceConfigurationAttribute>attributes) {
            mAttributes = attributes;
            return this;
        }

        /**
         * Creates a related service with the specified name and attributes
         * @return
         */
        public RelatedServiceConfiguration build() {
            return new RelatedServiceConfiguration(mServiceName,mAttributes);
        }
    }
}
