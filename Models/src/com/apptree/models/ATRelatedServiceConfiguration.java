package com.apptree.models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Matthew Smith, AppTree Software LLC on 5/1/15.
 */
public class ATRelatedServiceConfiguration extends ATServiceConfiguration {

    /**
     * Creates a related service
     * @param name The name of the service
     * @param attributes The attributes of the service
     */
    public ATRelatedServiceConfiguration(String name, List<ATServiceConfigurationAttribute> attributes) {
        super(name,attributes,null,null,null,null);
    }

    private HashMap<Integer, ATServiceConfigurationAttribute> attributeConfigurationForIndexMap;

    public HashMap<Integer, ATServiceConfigurationAttribute> getAttributeConfigurationForIndexMap() {
        if ( attributeConfigurationForIndexMap == null ) {
            attributeConfigurationForIndexMap = new HashMap<Integer, ATServiceConfigurationAttribute>();
            for ( ATServiceConfigurationAttribute attribute : getAttributes() ) {
                attributeConfigurationForIndexMap.put(attribute.getAttributeIndex(), attribute);
            }
        }
        return attributeConfigurationForIndexMap;
    }

    public List<ATServiceConfigurationAttribute> getAttributes() {
        return getUpdateAttributes();
    }

    public static class Builder {
        String mServiceName;
        List<ATServiceConfigurationAttribute> mAttributes;

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
        public Builder withAttributes(List<ATServiceConfigurationAttribute>attributes) {
            mAttributes = attributes;
            return this;
        }

        /**
         * Creates a related service with the specified name and attributes
         * @return
         */
        public ATRelatedServiceConfiguration build() {
            return new ATRelatedServiceConfiguration(mServiceName,mAttributes);
        }
    }
}
