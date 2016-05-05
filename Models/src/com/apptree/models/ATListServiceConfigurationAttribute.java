package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by alexis on 11/3/15.
 */
public class ATListServiceConfigurationAttribute {
    private int attributeIndex;
    private String attributeDescription;
    private AttributeType attributeType;
    private ATListServiceConfiguration relatedList;


    public ATListServiceConfigurationAttribute() {
    }

    public void setAttributeIndex(int index) { attributeIndex = index; }

    public int getAttributeIndex() { return attributeIndex; }

    public void setAttributeDescription(String description) {
        attributeDescription = description;
    }

    public String getAttributeDescription() { return attributeDescription; }

    public void setAttributeType(AttributeType type) {
        attributeType = type;
    }

    public AttributeType getAttributeType() { return attributeType; }

    public void setRelatedList(ATListServiceConfiguration related) {
        relatedList = related;
    }

    public ATListServiceConfiguration getRelatedList() { return relatedList; }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.putOpt("attributeType", attributeType.toString());
        json.putOpt("label", attributeDescription);
        json.putOpt("relatedListConfiguration", relatedList != null ? relatedList.toJSON() : null);
        json.putOpt("attributeIndex", attributeIndex);
        return json;
    }

    public static class Builder {
        AttributeType mAttributeType = AttributeType.String;
        String mAttributeDescription;
        private ATListServiceConfiguration mRelatedList = null;

        /**
         * Creates the list attribute builder with the given index
         */
        public Builder(String description) {
            mAttributeDescription = description;
        }

        /**
         * Sets the builder attribute description
         * @param description
         * @return
         */
        public Builder description(String description) {
            mAttributeDescription = description;
            return this;
        }

        /**
         * Sets the builder attribute type with an attribute
         * @param type
         * @return
         */
        public Builder attributeType(AttributeType type) {
            mAttributeType = type;
            return this;
        }

        /**
         * Sets the builder attribute type with the string value of an attribute type
         * @param type
         * @return
         */
        public Builder attributeType(String type) {
            mAttributeType = mAttributeType.fromString(type);
            return this;
        }

        /**
         * Sets the attribute type as list item
         * @return The builder with list item type
         */
        public Builder asListItem(ATListServiceConfiguration related) {
            mRelatedList = related;
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

        public Builder asTimeInterval() {
            mAttributeType = AttributeType.TimeInterval;
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

        /**
         * Builds a list configuration attribute from the builder parameters
         * @return list configuration attribute
         */
        public ATListServiceConfigurationAttribute build() {
            ATListServiceConfigurationAttribute attribute = new ATListServiceConfigurationAttribute();
            attribute.setAttributeDescription(mAttributeDescription);
            attribute.setAttributeType(mAttributeType);
            attribute.setRelatedList(mRelatedList);
            return attribute;
        }
    }
}
