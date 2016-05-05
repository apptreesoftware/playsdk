package com.apptree.models;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alexis on 12/9/15.
 */
public class ATStringListDataSource extends ATListDataSource {

    private String restPath;
    private String serviceName;
    private String label;
    private List<String> values;

    /**
     * Constructs a new ATStringListDataSource
     * @param restPath the RESTPath() for this list
     * @param serviceName the service name
     * @param label The string value of the label
     * @param values The list of values for the list items
     */
    private ATStringListDataSource(String restPath, String serviceName, String label, List<String> values) {
        this.restPath = restPath;
        this.serviceName = serviceName;
        this.label = label;
        this.values = values;
    }

    @Override
    public ATListDataSourceResponse getList(AuthenticationInfo authenticationInfo, Parameters params) {
        ATList list = new ATList();
        for ( String value : values ) {
            ATListItem listItem = new ATListItem(value);
            listItem.setAttributeForIndex(value, ATListItem.ATTRIBUTE_1);
            list.addListItem(listItem);
        }
        return new ATListDataSourceResponse(true,list,null);
    }

    @Override
    public ATListServiceConfiguration getDescription() {
        ATListServiceConfiguration configuration = new ATListServiceConfiguration(serviceName);
        configuration.setAttribute1(new ATListServiceConfigurationAttribute.Builder(label).build());
        return configuration;
    }

    @Override
    public ATListDataSourceResponse queryList(String queryText, boolean barcodeSearch, JSONObject searchContext, AuthenticationInfo authenticationInfo, Parameters params) {
        return null;
    }

    @Override
    public boolean canCache() {
        return true;
    }

    @Override
    public boolean canSearch() {
        return false;
    }

    @Override
    public String dataSourceRESTPath() {
        return restPath;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    static public class Builder {
        private String serviceName;
        private String restPath;
        private String label;
        private List<String> values;

        /**
         * Creates a string list builder
         * @param restPath the String rest path for the list service
         */
        public Builder(String restPath) {
            this.restPath = restPath;
            this.serviceName = restPath;
        }

        /**
         *
         * @param serviceName The name for the list
         * @return the builder with the given name
         */
        public Builder withServiceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        /**
         *
         * @param label the String label for the list
         * @return the builder with the given label set
         */
        public Builder withLabel(String label) {
            this.label = label;
            return this;
        }

        /**
         *
         * @param values a list of strings which are the possible values to be chosen from the list
         * @return the builder with the set values
         */
        public Builder withValues(List<String> values) {
            this.values = values;
            return this;
        }

        /**
         *
         * @param values a String... which are the possible values to be chosen from the list
         * @return the builder with the set values
         */
        public Builder withValues(String... values) {
            this.values = Arrays.asList(values);
            return this;
        }

        /**
         * Creates and returns an ATListStringDataSource from the builder values
         * @return
         */
        public ATStringListDataSource build() {
            return new ATStringListDataSource(restPath, serviceName, label, values);
        }
    }
}
