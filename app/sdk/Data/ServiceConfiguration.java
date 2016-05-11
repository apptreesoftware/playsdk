package sdk.data;

import sdk.utils.Response;
import sdk.utils.ServiceParameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by alexis on 5/3/16.
 */
public class ServiceConfiguration extends Response {
    private String name;
    public List<ServiceConfigurationAttribute> attributes;
    private List<String> dependentListEndpoints;
    private List<ServiceParameter> serviceFilterParameters;

    /**
     * Creates a service configuration
     */
    public ServiceConfiguration() {
    }

    /**
     * Creates a service configuration
     */
    public ServiceConfiguration(String name, List<ServiceConfigurationAttribute> attributes, List<ServiceParameter> serviceFilterParameters, List<String> dependentListEndpoints) {
        this.name = name;
        this.attributes = attributes;
        this.serviceFilterParameters = serviceFilterParameters;
        this.dependentListEndpoints = dependentListEndpoints;
    }

    public String getName() { return name; }
    public List<ServiceConfigurationAttribute> getAttributes() {
        return attributes;
    }
    public List<String> getDependentLists() { return dependentListEndpoints; }

    public static class Builder {
        String name;
        private List<ServiceConfigurationAttribute> attributes;
        private List<ServiceParameter> serviceFilterParameters;
        private List<String> dependentLists = new ArrayList<String>();
        private String message;

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

        Builder withMessage(String message) {
            this.message = message;
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
                return (ServiceConfiguration) new ServiceConfiguration(this.name, null, null, null).setFailedWithMessage("Your update attributes contain an index that is not unique: Index " + index);
            }
            ServiceConfiguration configuration =  new ServiceConfiguration(name,attributes,serviceFilterParameters, dependentLists);
            configuration.message = this.message;
            return configuration;
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

    private static class InvalidServiceAttributeException extends RuntimeException {

        InvalidServiceAttributeException(String message) {
            super(message);
        }
    }
}
