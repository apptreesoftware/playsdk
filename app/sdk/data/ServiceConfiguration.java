package sdk.data;

import sdk.AppTree;
import sdk.utils.Response;
import sdk.utils.ServiceParameter;

import java.util.*;

/**
 * Created by alexis on 5/3/16.
 */
public class ServiceConfiguration extends Response {
    private String name;
    public List<ServiceConfigurationAttribute> attributes;
    private List<String> dependentListEndpoints;
    public List<ServiceParameter> serviceFilterParameters;
    public HashMap<String, Object> contextInfo = new HashMap<>();
    /**
     * Creates a service configuration
     */
    public ServiceConfiguration() {
    }

    /**
     * Creates a service configuration
     */
    public ServiceConfiguration(String name, Collection<ServiceConfigurationAttribute> attributes, List<ServiceParameter> serviceFilterParameters, List<String> dependentListEndpoints) {
        this.name = name;
        if ( attributes != null ) {
            this.attributes = new ArrayList<>(attributes);
        }
        this.serviceFilterParameters = serviceFilterParameters;
        this.dependentListEndpoints = dependentListEndpoints;
    }

    public String getPlatformVersion() {
        return AppTree.getPlatformVersion();
    }

    public String getName() { return name; }
    public List<ServiceConfigurationAttribute> getAttributes() {
        return attributes;
    }
    public List<String> getDependentLists() { return dependentListEndpoints; }

    public ServiceConfigurationAttribute getAttributeWithName(String name) {
        for (ServiceConfigurationAttribute attribute : this.attributes ) {
            if ( attribute.name.equals(name) ) {
                return attribute;
            }
        }
        return null;
    }

    public ServiceConfigurationAttribute getAttributeWithIndex(int index) {
        for (ServiceConfigurationAttribute attribute : this.attributes ) {
            if ( attribute.getAttributeIndex() == index ) {
                return attribute;
            }
        }
        return null;
    }

    public static class Builder {
        String name;
        private Collection<ServiceConfigurationAttribute> attributes;
        private List<ServiceParameter> serviceFilterParameters;
        private List<String> dependentLists = new ArrayList<String>();
        private String message;
        private HashMap<String, Object> contextInfo = new HashMap<>();

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
        public Builder withAttributes(Collection<ServiceConfigurationAttribute> attributes) {
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

        public Builder withContext(String key, Object value) {
            this.contextInfo.put(key, value);
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
            configuration.contextInfo = this.contextInfo;
            return configuration;
        }

        private int checkIndexUniqueness(Collection<ServiceConfigurationAttribute> attributes) {
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
