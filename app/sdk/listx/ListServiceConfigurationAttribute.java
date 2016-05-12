package sdk.listx;

import sdk.models.AttributeType;
/**
 * Created by alexis on 5/3/16.
 */
public class ListServiceConfigurationAttribute {
    private int attributeIndex;
    private String label;
    private AttributeType attributeType = AttributeType.String;
    private ListServiceConfiguration relatedListConfiguration;


    public ListServiceConfigurationAttribute() {
    }

    public void setAttributeIndex(int index) { attributeIndex = index; }

    public int getAttributeIndex() { return attributeIndex; }

    public void setLabel(String description) {
        label = description;
    }

    public String getLabel() { return label; }

    public void setAttributeType(AttributeType type) {
        attributeType = type;
    }

    public AttributeType getAttributeType() { return attributeType; }

    public void setRelatedListConfiguration(ListServiceConfiguration related) {
        relatedListConfiguration = related;
    }

    public ListServiceConfiguration getRelatedListConfiguration() { return relatedListConfiguration; }

    public static class Builder {
        AttributeType mAttributeType = AttributeType.String;
        String attributeDescription;
        int attributeIndex;
        private ListServiceConfiguration mRelatedList = null;

        /**
         * Creates the list attribute builder with the given index
         */
        public Builder(int index) {
            attributeIndex = index;
            attributeDescription = "Attribute " + attributeIndex;
        }

        /**
         * Sets the builder attribute description
         * @param description
         * @return
         */
        public Builder description(String description) {
            attributeDescription = description;
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
        public Builder asListItem(ListServiceConfiguration related) {
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
        public ListServiceConfigurationAttribute build() {
            ListServiceConfigurationAttribute attribute = new ListServiceConfigurationAttribute();
            attribute.setAttributeIndex(attributeIndex);
            attribute.setLabel(attributeDescription);
            attribute.setAttributeType(mAttributeType);
            attribute.setRelatedListConfiguration(mRelatedList);
            return attribute;
        }
    }
}
