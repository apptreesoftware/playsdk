package com.apptree.models;

import java.util.HashSet;
import java.util.List;

/**
 * Created by alexis on 12/10/15.
 */
public class ATDataCollectionServiceConfiguration extends ATServiceConfiguration {

    /**
     * Constructor for ATDataCollecitonServiceConfiguration
     * @param configurationName The name of the data collection service
     * @param attributes a List of ATServiceConfigurationAttributes in the data collection service
     */
    public ATDataCollectionServiceConfiguration(String configurationName, List<ATServiceConfigurationAttribute> attributes, List<String> dependentListEndpoints) {
        super(configurationName, attributes, null, null, null, dependentListEndpoints);
    }

    /**
     * Returns the list of ATServiceConfigurationAttributes for this data collection
     * @return
     */
    public List<ATServiceConfigurationAttribute> getAttributes() { return getUpdateAttributes(); }

    /**
     * A builder class for an ATDataCollectionServiceConfiguration
     */
    public static class Builder {
        String name;
        private List<ATServiceConfigurationAttribute> attributes;
        private List<String> dependentLists;

        /**
         * Creates a new data collection service configuration builder
         * @param serviceName the name of the data collection service
         */
        public Builder(String serviceName) { name = serviceName; }

        /**
         * Sets the list of ATServiceConfigurationAttributes and returns this builder object
         * @param attributes the list of attributes
         * @return a Builder object with these attributes
         */
        public Builder withAttributes(List<ATServiceConfigurationAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Defines the RESTPaths of any lists that are used for this service. This is used to auto register these lists
         * when this data source is registered for use in the builder.
         * @param dependentLists a list of Strings which are the RESTPaths of any list data source this data source uses
         * @return a Builder object with these list RESTPaths
         */
        public Builder withDependentListRESTPaths(List<String> dependentLists) {
            this.dependentLists = dependentLists;
            return this;
        }

        /**
         * Creates and returns an ATDataCollectionServiceConfiguration
         * @return
         * @throws InvalidServiceAttributeException
         */
        public ATDataCollectionServiceConfiguration build() throws InvalidServiceAttributeException {
            int index;

            if ( (index = checkIndexUniqueness(this.attributes)) != -1 ) {
                throw new InvalidServiceAttributeException("Your attributes contain an index that is not unique: Index " + index);
            }
            return new ATDataCollectionServiceConfiguration(name, attributes, dependentLists);
        }

        private int checkIndexUniqueness(List<ATServiceConfigurationAttribute> attributes) {
            if ( attributes == null ) {
                return -1;
            }
            HashSet<Integer> indexes = new HashSet<Integer>();
            for ( ATServiceConfigurationAttribute attribute : attributes ) {
                if ( indexes.contains(attribute.getAttributeIndex()) ) {
                    return attribute.getAttributeIndex();
                }
                indexes.add(attribute.getAttributeIndex());
            }
            return -1;
        }
    }
}
