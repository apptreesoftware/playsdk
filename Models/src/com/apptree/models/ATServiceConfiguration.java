package com.apptree.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Alexis Andreason on 11/7/14.
 */
public class ATServiceConfiguration {
    private String configurationName;
    private List<ATServiceConfigurationAttribute> mUpdateAttributes;
    private List<ATServiceConfigurationAttribute> mCreateAttributes;
    private List<ATServiceConfigurationAttribute> mSearchAttributes;
    private List<String> mDependentListEndpoints;
    private List<ATServiceParameter> mServiceFilterParameters;

    /**
     * Creates a service configuration
     */
    public ATServiceConfiguration() {
    }

    /**
     * Creates a service configuration
     * @param configurationName The configuration name
     * @param updateAttributes The attributes used to update/view
     * @param createAttributes The attributes used to create
     * @param searchAttributes The attributes used to search
     */
    public ATServiceConfiguration(String configurationName, List<ATServiceConfigurationAttribute> updateAttributes, List<ATServiceConfigurationAttribute> createAttributes, List<ATServiceConfigurationAttribute> searchAttributes, List<ATServiceParameter> serviceFilterParameters, List<String> dependentListEndpoints) {
        this.configurationName = configurationName;
        this.mUpdateAttributes = updateAttributes;
        mCreateAttributes = createAttributes;
        mSearchAttributes = searchAttributes;
        mServiceFilterParameters = serviceFilterParameters;
        mDependentListEndpoints = dependentListEndpoints;
    }

    public List<ATServiceConfigurationAttribute> getUpdateAttributes() {
        return mUpdateAttributes;
    }

    public List<ATServiceConfigurationAttribute> getCreateAttributes() {
        return mCreateAttributes;
    }

    public List<ATServiceConfigurationAttribute> getSearchAttributes() {
        return mSearchAttributes;
    }

    public List<String> getDependentLists() { return mDependentListEndpoints; }

    /**
     * Converts the service configuration to a json object
     * @return
     */
    public JSONObject toJSON() {
        JSONObject jsonObject;
        JSONArray attributeArray;

        jsonObject = new JSONObject();
        jsonObject.putOpt("name", configurationName);
        if (mCreateAttributes != null) {
            attributeArray = new JSONArray();
            Collections.sort(mCreateAttributes, new ATServiceAttributeComparator());
            //mCreateAttributes.sort(new ATServiceAttributeComparator());
            for (ATServiceConfigurationAttribute atConfigurationAttribute : mCreateAttributes) {
                attributeArray.put(atConfigurationAttribute.toJSON());
            }
            jsonObject.put("createAttributes",attributeArray);

        }
        if (mUpdateAttributes != null) {
            attributeArray = new JSONArray();
            Collections.sort(mUpdateAttributes, new ATServiceAttributeComparator());
            //mUpdateAttributes.sort(new ATServiceAttributeComparator());
            for (ATServiceConfigurationAttribute atConfigurationAttribute : mUpdateAttributes) {
                attributeArray.put(atConfigurationAttribute.toJSON());
            }
            jsonObject.put("updateAttributes",attributeArray);
        }
        if (mSearchAttributes != null) {
            attributeArray = new JSONArray();
            Collections.sort(mSearchAttributes, new ATServiceAttributeComparator());
            //mSearchAttributes.sort(new ATServiceAttributeComparator());
            for (ATServiceConfigurationAttribute atConfigurationAttribute : mSearchAttributes) {
                attributeArray.put(atConfigurationAttribute.toJSON());
            }
            jsonObject.put("searchAttributes",attributeArray);
        }
        if ( mServiceFilterParameters != null ) {
            attributeArray = new JSONArray();
            for ( ATServiceParameter parameter : mServiceFilterParameters) {
                attributeArray.put(parameter.toJSON());
            }
            jsonObject.put("serviceFilterParameters", attributeArray);
        }
        jsonObject.putOpt("dependentLists", mDependentListEndpoints);

        return jsonObject;
    }

    private static class ATServiceAttributeComparator implements Comparator<ATServiceConfigurationAttribute> {
        public int compare(ATServiceConfigurationAttribute o1, ATServiceConfigurationAttribute o2) {
            return o1.getAttributeIndex() - o2.getAttributeIndex();
        }
    }

    public static class Builder {
        String mName;
        private List<ATServiceConfigurationAttribute> mUpdateAttributes;
        private List<ATServiceConfigurationAttribute> mCreateAttributes;
        private List<ATServiceConfigurationAttribute> mSearchAttributes;
        private List<ATServiceParameter> mServiceFilterParameters;
        private List<String> mDependentListEndpoints = new ArrayList<String>();

        /**
         * Creates a service configuration builder
         * @param serviceName The configuration name
         */
        public Builder(String serviceName) {
            mName = serviceName;
        }

        /**
         * Adds the update attributes to the builder
         * @param attributes The attributes used to update/view a data set item in this service
         * @return The builder with update attributes
         */
        public Builder withUpdateAttributes(List<ATServiceConfigurationAttribute> attributes) {
            mUpdateAttributes = attributes;
            return this;
        }

        /**
         * Adds the create attributes to the builder
         * @param attributes The attributes used to create a data set item in this service
         * @return The builder with create attributes
         */
        public Builder withCreateAttributes(List<ATServiceConfigurationAttribute> attributes) {
            mCreateAttributes = attributes;
            return this;
        }

        /**
         * Adds the search attributes to the builder
         * @param attributes The attributes used to search a data set in this service
         * @return The builder with search attributes
         */
        public Builder withSearchAttributes(List<ATServiceConfigurationAttribute> attributes) {
            mSearchAttributes = attributes;
            return this;
        }

        /**
         * The service filter parameters to be sent in the parameters for this service
         * @param parameters a list of ATServiceParameters
         * @return The builder with service filter parameters
         */
        public Builder withServiceFilterParameters(List<ATServiceParameter> parameters) {
            mServiceFilterParameters = parameters;
            return this;
        }

        /**
         * The RESTPaths of all lists which are used in this data source
         * @param dependentLists a list of Strings
         * @return the builder with dependent lists
         */
        public Builder withDependentListRESTPaths(List<String> dependentLists) {
            mDependentListEndpoints = dependentLists;
            return this;
        }

        /**
         * Creates a service configuration with the specified builder parameters
         * @return
         * @throws InvalidServiceAttributeException
         */
        public ATServiceConfiguration build() throws InvalidServiceAttributeException {
            int index;
            if ( (index = checkIndexUniqueness(mUpdateAttributes)) != -1 ) {
                throw new InvalidServiceAttributeException("Your update attributes contain an index that is not unique: Index " + index);
            }
            if ( (index = checkIndexUniqueness(mCreateAttributes)) != -1 ) {
                throw new InvalidServiceAttributeException("Your create attributes contain an index that is not unique: Index " + index);
            }
            if ( (index = checkIndexUniqueness(mSearchAttributes)) != -1 ) {
                throw new InvalidServiceAttributeException("Your search attributes contain an index that is not unique: Index " + index);
            }
            return new ATServiceConfiguration(mName,mUpdateAttributes,mCreateAttributes,mSearchAttributes, mServiceFilterParameters, mDependentListEndpoints);
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

    public static class InvalidServiceAttributeException extends Exception {

        public InvalidServiceAttributeException(String message) {
            super(message);
        }
    }
}
