package com.apptree.models;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Alexis Andreason on 11/6/14.
 */
public class ATServiceConfigurationAttribute {

    String name;
    boolean required, readonly;
    ATRelatedServiceConfiguration relatedService;
    ATListServiceConfiguration relatedListService;
    AttributeType attributeType;
    HashMap<String, Object> userInfo;

    private int mAttributeIndex;

    /**
     * Creates a service configuration attribute
     */
    public ATServiceConfigurationAttribute() {}

    /**
     * Creates a service configuration attribute
     * @param attributeIndex The index of the attribute
     */
    public ATServiceConfigurationAttribute(int attributeIndex) {
        mAttributeIndex = attributeIndex;
        this.name = "attribute" + mAttributeIndex;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public void setUserInfoValue(String key, Object value) {
        if ( value == null ) {
            return;
        }
        if ( userInfo == null ) {
            userInfo = new HashMap<String,Object>();
        }
        userInfo.put(key, value);
    }

    public boolean getBoolUserInfoValue(String key) {
        if ( userInfo == null ) {
            return false;
        }
        if ( userInfo.containsKey(key) ) {
            return (Boolean)userInfo.get(key);
        }
        return false;
    }

    public Object getUserInfoValue(String key) {
        if ( userInfo == null ) {
            return null;
        }
        return userInfo.get(key);
    }

    public boolean hasUserInfoKey(String key) {
        if ( userInfo == null ) {
            return false;
        }
        return userInfo.containsKey(key);
    }

    /**
     * Sets the name of the attribute
     * @param name
     */
    public void setName (String name) { this.name = name; }

    /**
     * Gets the name of the attribute
     * @return
     */
    public String getName () { return name; }

    /**
     * Get the index of the attribute
     * @return
     */
    public int getAttributeIndex() {
        return mAttributeIndex;
    }

    /**
     * Sets the index of the attributes
     * @param attributeIndex
     */
    public void setAttributeIndex(int attributeIndex) {
        mAttributeIndex = attributeIndex;
    }

    /**
     * Sets the attribute type
     * @param attributeType
     */
    public void setAttributeType (AttributeType attributeType) { this.attributeType = attributeType; }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    /**
     * Sets the related service
     * @param serviceConfiguration
     */
    public void setRelatedService(ATRelatedServiceConfiguration serviceConfiguration) {
        relatedService = serviceConfiguration;
    }

    public ATRelatedServiceConfiguration getRelatedService() {
        return relatedService;
    }

    public void setRelatedListServiceConfiguration(ATListServiceConfiguration configuration) {
        relatedListService = configuration;
    }

    public ATListServiceConfiguration getRelatedListServiceConfiguration() {
        return relatedListService;
    }

    /**
     * Converts an attribute to a json object
     * @return
     */
    public JSONObject toJSON() {
        JSONObject jsonObject;

        jsonObject = new JSONObject();
        jsonObject.putOpt("name", name);
        jsonObject.putOpt("readonly", readonly);
        jsonObject.putOpt("required", required);
        jsonObject.putOpt("attributeIndex",mAttributeIndex);
        jsonObject.putOpt("attributeType", attributeType.toString());
        if ( relatedService != null ) {
            jsonObject.putOpt("relatedService", relatedService.toJSON());
        }
        jsonObject.putOpt("relatedListService", relatedListService != null ? relatedListService.toJSON() : null);
        return jsonObject;
    }

    public static class Builder {
        AttributeType mAttributeType = AttributeType.String;
        String mAttributeName;
        ATRelatedServiceConfiguration mRelatedService;
        boolean mReadOnly, mRequired;
        private int mAttributeIndex;
        ATListServiceConfiguration mRelatedListService;

        /**
         * Creates a service attribute builder
         * @param attributeIndex The index of the attribute
         */
        public Builder(int attributeIndex) {
            mAttributeIndex = attributeIndex;
            mAttributeName = "attribute" + mAttributeIndex;
        }

        /**
         * Sets the name of the attribute
         * @param attributeName The attribute name
         * @return The builder with the name
         */
        public Builder name(String attributeName) {
            mAttributeName = attributeName;
            return this;
        }

        /**
         * Sets the attribute as read only
         * @return The builder with readonly parameter
         */
        public Builder readonly() {
            mReadOnly = true;
            return this;
        }

        /**
         * Sets the attribute as required
         * @return The builder with required parameter
         */
        public Builder required() {
            mRequired = true;
            return this;
        }

        /**
         * Sets the attribute type
         * @param type The type of the attribute
         * @return The builder with the attribute type
         */
        public Builder attributeType(AttributeType type) {
            mAttributeType = type;
            return this;
        }

        /**
         * Sets the attribute type as list item
         * @return The builder with list item type
         */
        public Builder asListItem(ATListServiceConfiguration configuration) {
            mRelatedListService = configuration;
            mAttributeType = AttributeType.ListItem;
            return this;
        }

        /**
         * Sets the attribute type as location
         * @return the builder with location type
         */
        public Builder asLocation() {
            mAttributeType = AttributeType.Location;
            return this;
        }

        /**
         * Sets the attribute type as relationship
         * @return the builder with relationship type
         */
        public Builder asRelationship(ATRelatedServiceConfiguration relatedService) {
            mAttributeType = AttributeType.Relation;
            mRelatedService = relatedService;
            return this;
        }

        /**
         * Sets the attribute type as attachments
         * @return the builder with attachments type
         */
        public Builder asAttachments() {
            mAttributeType = AttributeType.Attachments;
            return this;
        }

        /**
         * Sets the attribute type as date time
         * @return the builder with date time type
         */
        public Builder asDateTime() {
            mAttributeType = AttributeType.DateTime;
            return this;
        }

        /**
         * Sets the attribute type as int
         * @return the builder with int type
         */
        public Builder asInt() {
            mAttributeType = AttributeType.Int;
            return this;
        }


        /**
         * Sets the attribute type as double
         * @return the builder with double type
         */
        public Builder asDouble() {
            mAttributeType = AttributeType.Double;
            return this;
        }

        /**
         * Sets the attribute type as date
         * @return the builder with date type
         */
        public Builder asDate() {
            mAttributeType = AttributeType.Date;
            return this;
        }

        /**
         * Sets the attribute type as bool
         * @return the builder with bool type
         */
        public Builder asBool() {
            mAttributeType = AttributeType.Boolean;
            return this;
        }

        /**
         * Sets the attribute type as color
         * @return the builder with color type
         */
        public Builder asColor() {
            mAttributeType = AttributeType.Color;
            return this;
        }

        /**
         * Sets the attribute type as image
         * @return the builder with image type
         */
        public Builder asImage() {
            mAttributeType = AttributeType.Image;
            return this;
        }

        /**
         * Sets the attribute type as date range
         * @return the builder with date range type
         */
        public Builder asDateRange() {
            mAttributeType = AttributeType.DateRange;
            return this;
        }

        /**
         * Sets the attribute type as date time range
         * @return the builder with date time range type
         */
        public Builder asDateTimeRange() {
            mAttributeType = AttributeType.DateTimeRange;
            return this;
        }

        public Builder asTimeInterval() {
            mAttributeType = AttributeType.TimeInterval;
            return this;
        }

        /**
         * Builds a service configuration attribute from the builder parameters
         * @return service configuration attribute
         */
        public ATServiceConfigurationAttribute build() {
            ATServiceConfigurationAttribute attribute = new ATServiceConfigurationAttribute(mAttributeIndex);
            attribute.setName(mAttributeName);
            attribute.setAttributeType(mAttributeType);
            attribute.readonly = mReadOnly;
            attribute.required = mRequired;
            attribute.setRelatedService(mRelatedService);
            attribute.setRelatedListServiceConfiguration(mRelatedListService);
            return attribute;
        }
    }
}
