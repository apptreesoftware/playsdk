package sdk.data;

import sdk.utils.ServiceParameter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Created by alexis on 5/3/16.
 */
public class ServiceConfiguration {
    private String configurationName;
    private List<ServiceConfigurationAttribute> attributes;
    private List<String> mDependentListEndpoints;
    private List<ServiceParameter> serviceFilterParameters;

    /**
     * Creates a service configuration
     */
    public ServiceConfiguration() {
    }

    /**
     * Creates a service configuration
     */
    public ServiceConfiguration(String configurationName, List<ServiceConfigurationAttribute> attributes, List<ServiceParameter> serviceFilterParameters, List<String> dependentListEndpoints) {
        this.configurationName = configurationName;
        this.attributes = attributes;
        this.serviceFilterParameters = serviceFilterParameters;
        mDependentListEndpoints = dependentListEndpoints;
    }

    public List<ServiceConfigurationAttribute> getAttributes() {
        return attributes;
    }


    public List<String> getDependentLists() { return mDependentListEndpoints; }

    private static class ServiceAttributeComparator implements Comparator<ServiceConfigurationAttribute> {

        @Override
        public int compare(ServiceConfigurationAttribute o1, ServiceConfigurationAttribute o2) {
            return o1.getAttributeIndex() - o2.getAttributeIndex();
        }
    }

    public static class Builder {
        String name;
        private List<ServiceConfigurationAttribute> attributes;
        private List<ServiceParameter> serviceFilterParameters;
        private List<String> dependentLists = new ArrayList<String>();

        /**
         * Creates a service configuration builder
         * @param serviceName The configuration name
         */
        public Builder(String serviceName) {
            name = serviceName;
        }

        /**
         * Adds the update attributes to the builder
         * @param attributes The attributes used to update/view a data set item in this service
         * @return The builder with update attributes
         */
        public Builder withAttributes(List<ServiceConfigurationAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * The service filter parameters to be sent in the parameters for this service
         * @param parameters a list of ATServiceParameters
         * @return The builder with service filter parameters
         */
        public Builder withServiceFilterParameters(List<ServiceParameter> parameters) {
            serviceFilterParameters = parameters;
            return this;
        }

        /**
         * The RESTPaths of all lists which are used in this data source
         * @param dependentLists a list of Strings
         * @return the builder with dependent lists
         */
        public Builder withDependentListRESTPaths(List<String> dependentLists) {
            this.dependentLists = dependentLists;
            return this;
        }

        /**
         * Creates a service configuration with the specified builder parameters
         * @return
         * @throws InvalidServiceAttributeException
         */
        public ServiceConfiguration build() throws InvalidServiceAttributeException {
            int index;
            if ( (index = checkIndexUniqueness(attributes)) != -1 ) {
                throw new InvalidServiceAttributeException("Your update attributes contain an index that is not unique: Index " + index);
            }
            return new ServiceConfiguration(name,attributes,serviceFilterParameters, dependentLists);
        }

        private int checkIndexUniqueness(List<ServiceConfigurationAttribute> attributes) {
            if ( attributes == null ) {
                return -1;
            }
            HashSet<Integer> indexes = new HashSet<Integer>();
            for ( ServiceConfigurationAttribute attribute : attributes ) {
                if ( indexes.contains(attribute.getAttributeIndex()) ) {
                    return attribute.getAttributeIndex();
                }
                indexes.add(attribute.getAttributeIndex());
            }
            return -1;
        }
    }

    public static class InvalidServiceAttributeException extends RuntimeException {

        public InvalidServiceAttributeException(String message) {
            super(message);
        }
    }
}
