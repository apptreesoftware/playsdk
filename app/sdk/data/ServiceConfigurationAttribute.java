package sdk.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import sdk.list.ListServiceConfiguration;
import sdk.list.ListServiceConfigurationAttribute;
import sdk.models.AttributeType;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by alexis on 5/3/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceConfigurationAttribute {
    String name;
    RelatedServiceConfiguration relatedService;
    ListServiceConfiguration relatedListService;
    AttributeType attributeType;
    HashMap<String, Object> userInfo;
    public boolean create;
    public boolean createRequired;
    public boolean update;
    public boolean updateRequired;
    public boolean search;
    public boolean searchRequired;

    private int mAttributeIndex;

    /**
     * Creates a service configuration attribute
     */
    public ServiceConfigurationAttribute() {}

    /**
     * Creates a service configuration attribute
     * @param attributeIndex The index of the attribute
     */
    public ServiceConfigurationAttribute(int attributeIndex) {
        mAttributeIndex = attributeIndex;
        this.name = "attribute" + mAttributeIndex;
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
    public void setRelatedService(RelatedServiceConfiguration serviceConfiguration) {
        relatedService = serviceConfiguration;
    }

    public RelatedServiceConfiguration getRelatedService() {
        return relatedService;
    }

    public void setRelatedListServiceConfiguration(ListServiceConfiguration configuration) {
        relatedListService = configuration;
    }

    public ListServiceConfiguration getRelatedListServiceConfiguration() {
        return relatedListService;
    }

    public static class Builder {
        AttributeType mAttributeType = AttributeType.String;
        String mAttributeName;
        RelatedServiceConfiguration mRelatedService;
        private int mAttributeIndex;
        ListServiceConfiguration mRelatedListService;
        boolean canUpdate;
        boolean updateRequired;
        boolean canCreate;
        boolean createRequired;
        boolean canSearch;
        boolean searchRequired;

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
        public Builder asListItem(Set<ListServiceConfigurationAttribute> listAttributes) {
            mRelatedListService = new ListServiceConfiguration("");
            mRelatedListService.getAttributes().addAll(listAttributes);
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
        public Builder asRelationship(RelatedServiceConfiguration relatedService) {
            mAttributeType = AttributeType.Relation;
            mRelatedService = relatedService;
            return this;
        }

        public Builder asSingleRelationship(RelatedServiceConfiguration relatedService) {
            mAttributeType = AttributeType.SingleRelationship;
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

        public Builder asText() {
            mAttributeType = AttributeType.String;
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

        public Builder canUpdate() {
            canUpdate = true;
            updateRequired = false;
            return this;
        }

        public Builder canUpdateAndRequired() {
            canUpdate = true;
            updateRequired = true;
            return this;
        }

        public Builder canCreate() {
            canCreate = true;
            createRequired = false;
            return this;
        }

        public Builder canCreateAndRequired() {
            canCreate = true;
            createRequired = true;
            return this;
        }

        public Builder canSearch() {
            canSearch = true;
            searchRequired = false;
            return this;
        }

        public Builder canSearchAndRequired() {
            canSearch = true;
            searchRequired = true;
            return this;
        }

        /**
         * Builds a service configuration attribute from the builder parameters
         * @return service configuration attribute
         */
        public ServiceConfigurationAttribute build() {
            ServiceConfigurationAttribute attribute = new ServiceConfigurationAttribute(mAttributeIndex);
            attribute.setName(mAttributeName);
            attribute.setAttributeType(mAttributeType);
            attribute.create = canCreate;
            attribute.createRequired = createRequired;
            attribute.update = canUpdate;
            attribute.updateRequired = updateRequired;
            attribute.search = canSearch;
            attribute.searchRequired = searchRequired;
            attribute.setRelatedService(mRelatedService);
            attribute.setRelatedListServiceConfiguration(mRelatedListService);
            return attribute;
        }
    }
}
