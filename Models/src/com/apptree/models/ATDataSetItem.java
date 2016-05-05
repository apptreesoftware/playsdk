package com.apptree.models;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.List;

/**
 * Created by Matthew Smith on 11/5/14.
 * Copyright AppTree Software, Inc.
 */


public class ATDataSetItem {

    HashMap<Integer, ATDataSetItemAttribute> mAttributeMap = new HashMap<Integer, ATDataSetItemAttribute>();
    String mPrimaryKey;
    String mClientKey;
    int mMaxAttributeIndex = -1;
    CRUDStatus mCRUDStatus = CRUDStatus.Read;
    DataCollectionStatus mDataCollectionStatus = DataCollectionStatus.None;
    private HashMap<Integer, ATServiceConfigurationAttribute> attributeConfigurationForIndexMap;
    WorkFlowState mWorkFlowState = WorkFlowState.None;

    public HashMap<Integer, ATServiceConfigurationAttribute> getAttributeConfigurationForIndexMap() {
        return attributeConfigurationForIndexMap;
    }

    public Collection<ATServiceConfigurationAttribute> getConfigurationAttributes() {
        return attributeConfigurationForIndexMap.values();
    }

    public enum Type {
        Record("RECORD"),
        Attachment("ATTACHMENT");

        private final String stringValue;

        /**
         * The type of data set item
         * @param text The name of the data set type, either RECORD or ATTACHMENT
         */
        private Type(final String text) {
            this.stringValue = text;
        }

        /**
         * Gets the type from the name
         * @param typeString The name of the type
         * @return
         */
        public static Type fromString(String typeString) {
            typeString = typeString.toUpperCase();
            if ( typeString.equals(Record.stringValue) ) {
                return Record;
            } else if ( typeString.equals(Attachment.stringValue) ) {
                return Attachment;
            }
            return Record;
        }
    }

    public enum CRUDStatus {
        None("NONE"),
        Create("CREATE"),
        Update("UPDATE"),
        Delete("DELETE"),
        Read("READ");

        private final String stringValue;

        /**
         * Sets the CRUD status of a data set item
         * @param text
         */
        private CRUDStatus(final String text) {
            this.stringValue = text;
        }

        @Override
        public String toString() {
            return stringValue;
        }

        /**
         * Sets the CRUD type from a String
         * @param crudString The CRUD type string
         * @return
         */
        public static CRUDStatus fromString(String crudString) {
            crudString = crudString.toUpperCase();
            if ( crudString.equals(Create.toString()) ) {
                return Create;
            } else if ( crudString.equals(Update.toString()) ) {
                return Update;
            } else if ( crudString.equals(Delete.toString()) ) {
                return Delete;
            } else if ( crudString.equals(Read.toString()) ) {
                return Read;
            }
            return None;
        }
    }

    public enum WorkFlowState {
        None("NONE"),
        Pending("PENDING"),
        Rejected("REJECTED"),
        Success("Success");

        private final String stringValue;

        private WorkFlowState(final String text) { this.stringValue = text; }

        @Override
        public String toString() { return stringValue; }

        /**
         * Work flow state from name
         * @param workFlowString The name of the work flow state
         * @return
         */
        public static WorkFlowState fromString(String workFlowString) {
            workFlowString = workFlowString.toUpperCase();
            if ( workFlowString.equals(Pending.toString()) ) {
                return Pending;
            } else if ( workFlowString.equals(Rejected.toString()) ) {
                return Rejected;
            } else if ( workFlowString.equals(Success.toString()) ) {
                return Success;
            }
            return None;
        }
    }

    public enum DataCollectionStatus {
        None("NONE"),
        Complete("COMPLETE"),
        Exception("EXCEPTION");

        private final String stringValue;
        private DataCollectionStatus(final String text) {
            this.stringValue = text;
        }

        @Override
        public String toString() {
            return stringValue;
        }

        /**
         * Assessment status from name
         * @param string The name of the assessment status
         * @return
         */
        public static DataCollectionStatus fromString(String string) {
            string = string.toUpperCase();
            if ( string.equalsIgnoreCase(None.toString())) {
                return None;
            } else if ( string.equalsIgnoreCase(Complete.toString()) ) {
                return Complete;
            } else if ( string.equalsIgnoreCase(Exception.toString()) ) {
                return Exception;
            }
            return None;
        }
    }

    /***
     * Creates an ATDataSetItem using the configuration attributes provided. The service attributes passed in will be used to validate what data is being set on this ATDataSetItem.
     * @param attributeConfigurationMap The service configuration attributes to use for validation. This should come from your ATDataSource getViewUpdateServiceAttributes, getCreateServiceAttributes, or getSearchServiceAttributes depending on what this ATDataSetItem is being used for.
     */
    ATDataSetItem(HashMap<Integer, ATServiceConfigurationAttribute> attributeConfigurationMap) {
        this.attributeConfigurationForIndexMap = attributeConfigurationMap;
    }

    /**
     * Sets the primary key
     * @param primaryKey The String primary key
     */
    public void setPrimaryKey(String primaryKey) {
        mPrimaryKey = primaryKey;
    }

    /**
     * Gets the primary key
     * @return
     */
    public String getPrimaryKey() {
        return mPrimaryKey;
    }

    /**
     * Gets the item type
     * @return
     */
    public Type getItemType() {
        return Type.Record;
    }

    /**
     * Sets the CRUD status
     * @param status The status of the data set item
     */
    public void setCRUDStatus(CRUDStatus status) {
        mCRUDStatus = status;
    }

    /**
     * Returns the work flow state
     * @return
     */
    public WorkFlowState getWorkFlowState() { return mWorkFlowState; }

    /**
     * Sets the work flow state
     * @param state The work flow state of the item
     */
    public void setWorkFlowState(WorkFlowState state) {
        mWorkFlowState = state;
    }

    /**
     * Gets the CRUD status
     * @return
     */
    public CRUDStatus getCRUDStatus() {
        return mCRUDStatus;
    }

    /**
     * Gets the client key
     * @return
     */
    public String getClientKey() {
        return mClientKey;
    }

    /**
     * Sets the client key
     * @param clientKey
     */
    public void setClientKey(String clientKey) {
        mClientKey = clientKey;
    }

    /**
     * Gets the assessment status
     * @return
     */
    public DataCollectionStatus getDataCollectionStatus() {
        return mDataCollectionStatus;
    }

    /**
     * Sets the assessment status
     * @param DataCollectionStatus
     */
    public void setDataCollectionStatus(DataCollectionStatus DataCollectionStatus) {
        mDataCollectionStatus = DataCollectionStatus;
    }

    /**
     * Returns a color at the specified index of the attribute map
     * @param attributeIndex The index of the attribute to get
     * @return Color
     */
    public ATColor getColorAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getColorValue();
        }
        return null;
    }

    /**
     * Gets an int at the specified index of the attribute map
     * @param attributeIndex The index of the attribute to get
     * @return int
     */
    public int getIntAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        int intAttribute;

        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            try {
                return attribute.getIntValue();
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * Gets a double at the specified index of the attribute map
     * @param attributeIndex The index of the attribute
     * @return double
     */
    public double getDoubleAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        double doubleAttribute;

        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getDoubleValue();
        }
        return 0;
    }

    public long getTimeIntervalAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getLongValue();
        }
        return 0;
    }

    /**
     * Gets a list item at the specified index of the attribute map
     * @param attributeIndex The index of the attribute to get
     * @return list item
     */
    public ATListItem getListItemAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;

        attribute = mAttributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getListItem();
        }
        return null;
    }

    /**
     * Gets a string attriubte at the specified index of the attribute map
     * @param attributeIndex The index to get the attribute at
     * @return string
     */
    public String getStringAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getStringValue();
        }
        return null;
    }

    /**
     * Gets a date attribute at the specified index of the attribute map
     * @param attributeIndex The index to get the attribute at
     * @return date
     */
    public DateTime getDateAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getDateValue();
        }
        return null;
    }

    /**
     * Gets a date time attribute at the specified index of the attribute map
     * @param attributeIndex The index to get the attribute at
     * @return date time
     */
    public DateTime getDateTimeAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getDateValue();
        }
        return null;
    }

    /**
     * Get a date range at the specified index of the attribute map
     * @param attributeIndex The index to get the attribute at
     * @return date range
     */
    public ATDateRange getDateRangeAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getDateRange();
        }
        return null;
    }

    /**
     * Gets a date time range at the specified index of the attribute map
     * @param attributeIndex The index to get the attribute at
     * @return date time range
     */
    public ATDateTimeRange getDateTimeRangeAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return  attribute.getDateTimeRange();
        }
        return null;
    }

    /**
     * Gets a boolean attribute at the specified index of the attribute map
     * @param attributeIndex The index to get the attribute at
     * @return boolean
     */
    public boolean getBooleanAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        attribute = mAttributeMap.get(attributeIndex);
        if (attribute!=null) {
            return attribute.getBooleanValue();
        }else{
            return false;
        }

    }

    /**
     * Gets the list of data set item attribute at the specified index of the attribute map
     * @param attributeIndex The index to get the attribute at
     * @return list of data set items
     */
    public List<ATDataSetItem> getDataSetItemsAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;

        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getDataSetItems();
        }
        return null;
    }

    /**
     * Gets a data set item attribute at the specified index of the attribute map
     * @param attributeIndex The index to get the attribute at
     * @return data set item
     */
    public ATDataSetItem getDataSetItemAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        List<ATDataSetItem> dataSetItems;

        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            dataSetItems = attribute.getDataSetItems();
            if ( dataSetItems != null && dataSetItems.size() == 1 ) {
                return dataSetItems.get(0);
            }
        }
        return null;
    }

    public ATLocation getLocationAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getLocationValue();
        }
        return null;
    }

    public ATImage getImageAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getImageValue();
        }
        return null;
    }

    /**
     * Gets a list of attachment item attribute at the specified index of the attribute map
     * @param attributeIndex The index to get the attribute at
     * @return list of attachment items
     */
    public List<ATDataSetAttachmentItem> getAttachmentItemsAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;
        ArrayList<ATDataSetAttachmentItem> attachmentItems = new ArrayList<ATDataSetAttachmentItem>();

        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            List<ATDataSetItem> items = attribute.getDataSetItems();
            if ( items != null ) {
                for ( ATDataSetItem item : items ) {
                    if ( item instanceof ATDataSetAttachmentItem ) {
                        ATDataSetAttachmentItem attachmentItem = (ATDataSetAttachmentItem)item;
                        attachmentItems.add(attachmentItem);
                    }
                }
                return attachmentItems;
            }
        }
        return null;
    }

    public ATCollectionUnitItem getCollectionUnitItemAttributeAtIndex(int attributeIndex) {
        ATDataSetItemAttribute attribute;

        attribute = mAttributeMap.get(attributeIndex);
        if ( attribute != null ) {
            return attribute.getCollectionUnitItem();
        }
        return null;
    }

    private boolean validateGetterAttributeTypeForIndex(AttributeType attributeType, int attributeIndex) throws InvalidAttributeValueException {
        ATServiceConfigurationAttribute attributeConfig = attributeConfigurationForIndexMap.get(attributeIndex);
        if ( attributeConfig == null ) {
            System.out.println("Warning: This data set is not configured for attribute " + attributeIndex + ". Setting or Getting the value at this index will always produce null");
            return false;
        }
        AttributeType configuredAttributeType = attributeConfig.attributeType;

        if ( configuredAttributeType == attributeType ) {
            return true;
        }
        String methodName = setterNameForAttributeType(configuredAttributeType);
        if ( methodName != null ) {
            throw new InvalidAttributeValueException("Attempting to set a " + attributeType.name() + " on a field configured as " + configuredAttributeType.name() + ". Consider using the method " + methodName + " instead. Attribute Index: " + attributeIndex);
        } else
            throw new InvalidAttributeValueException("Attempting to set a " + attributeType.name() + " on a field configured as " + configuredAttributeType.name() + ". No method is specified for type " + configuredAttributeType.name() + " instead.  Attribute Index:" + attributeIndex);
    }

    private String setterNameForAttributeType(AttributeType type) {
        switch (type) {
            case String:
                return "public void setStringForAttributeIndex(String value, int attributeIndex)";
            case ListItem:
                return "public void setListItemForAttributeIndex(ATListItem value, int attributeIndex)";
            case Color:
                return "public void setColorForAttributeIndex(ATColor value, int attributeIndex)";
            case Image:
                return "public void setImageForAttributeIndex(Image value, int attributeIndex)";
            case Location:
                return "public void setLocationForAttibuteIndex(ATLocation location, int attributeIndex)";
            case Double:
                return "public void setDoubleForAttributeIndex(double value, int attributeIndex)";
            case Int:
                return "public void setIntForAttributeIndex(int value, int attributeIndex)";
            case TimeInterval:
                return "public void setTimeIntervalForAttributeIndex(long value, int attributeIndex)";
            case Relation:
                return "setDataSetItemsForAttributeIndex(ATDataSetItem value, int attributeIndex) or setDataSetItemForAttributeIndex(List<ATDataSetItem> value, int attributeIndex)";
            case Date:
                return "public void setDateForAttributeIndex(DateTime date, int attributeIndex)";
            case DateTime:
                return "public void setDateTimeForAttributeIndex(DateTime date, int attributeIndex)";
            case Boolean:
                return "public void setBooleanForAttributeIndex(boolean value, int attributeIndex)";
            case CollectionUnitItem:
                return "public void setCollectionUnitItemForAttributeIndex(ATCollectionUnitItem item, int attributeIndex)";
        }
        return null;
    }

    private String getterNameForAttributeType(AttributeType type) {
        switch (type) {
            case String:
                return "public String getStringAttributeAtIndex(int attributeIndex)";
            case ListItem:
                return "public String getListItemAttributeAtIndex(int attributeIndex)";
            case Color:
                return "public Color getColorAttributeAtIndex(int attributeIndex)";
            case Image:
                return "public String getStringAttributeAtIndex(int attributeIndex)";
            case Location:
                return "public String getStringAttributeAtIndex(int attributeIndex)";
            case Double:
                return "public double getDoubleAttributeAtIndex(int attributeIndex)";
            case Int:
                return "public int getIntAttributeAtIndex(int attributeIndex)";
            case Relation:
                return "public List<ATDataSetItem> getDataSetItemsAtIndex(int attributeIndex) or public ATDataSetItem getDataSetItemAtIndex(int attributeIndex)";
            case Date:
                return "public DateTime getDateAttributeAtIndex(int attributeIndex)";
            case DateTime:
                return "public DateTime getDateTimeAttributeAtIndex(int attributeIndex)";
            case TimeInterval:
                return "public TimeInterval getTimeIntervalAttributeAtIndex(int attributeIndex)";
            case CollectionUnitItem:
                return "public ATCollectionUnitItem getCollectionUnitItemAttributeAtIndex(int attributeIndex)";
        }
        return null;
    }

    /**
     * Sets a color value at the specified index of the attribute map
     * @param value The color to set
     * @param attributeIndex The index of the attribute
     */
    public void setColorForAttributeIndex(ATColor value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Color, attributeIndex)) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets an int value at the specified index of the attribute map
     * @param value The int to set
     * @param attributeIndex The index of the attribute
     */
    public void setIntForAttributeIndex(int value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Int, attributeIndex)) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a list item value at the specified index of the attribute map
     * @param listItem The list item to set
     * @param attributeIndex The index of the attribute
     */
    public void setListItemForAttributeIndex(ATListItem listItem, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.ListItem, attributeIndex)) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(listItem));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a location value at the specified index of the attribute map
     * @param location the location to set
     * @param attributeIndex The index of the attribute
     */
    public void setLocationForAttributeIndex(ATLocation location, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Location, attributeIndex)) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(location));
        updateMaxAttribute(attributeIndex);
    }

    public void setImageForAttributeIndex(ATImage image, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Image, attributeIndex)) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(image));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a string value at the specified index of the attribute map
     * @param value The string to set
     * @param attributeIndex The index of the attribute
     */
    public void setStringForAttributeIndex(String value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.String, attributeIndex)) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a boolean value at the specified index of the attribute map
     * @param value The boolean to set
     * @param attributeIndex The index of the attribute
     */
    public void setBooleanForAttributeIndex(boolean value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Boolean, attributeIndex)) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a date range value at the specified index of the attribute map
     * @param dateRange The date range to set
     * @param attributeIndex The index of the attribute
     * @throws InvalidAttributeValueException
     */
    public void setDateRangeForAttributeIndex(ATDateRange dateRange, int attributeIndex) throws InvalidAttributeValueException {
        if ( !validateGetterAttributeTypeForIndex(AttributeType.DateRange, attributeIndex) ) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(dateRange));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a date time range at the specified index of the attribute map
     * @param dateTimeRange The date time range to set
     * @param attributeIndex The index of the attribute
     * @throws InvalidAttributeValueException
     */
    public void setDateTimeRangeForAttributeIndex(ATDateTimeRange dateTimeRange, int attributeIndex) throws InvalidAttributeValueException {
        if ( !validateGetterAttributeTypeForIndex(AttributeType.DateTimeRange, attributeIndex) ) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(dateTimeRange));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a double value at the specified index of the attribute map
     * @param value The cdouble to set
     * @param attributeIndex The index of the attribute
     */
    public void setDoubleForAttributeIndex(double value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Double, attributeIndex)) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }

    public void setCollectionUnitItemForAttributeIndex(ATCollectionUnitItem item, int attributeIndex) throws InvalidAttributeValueException {
        if ( !validateGetterAttributeTypeForIndex(AttributeType.CollectionUnitItem, attributeIndex) ) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(item));
        updateMaxAttribute(attributeIndex);
    }

    public ATDataSetItem addNewDataSetItemForAttributeIndex(int attributeIndex) throws InvalidAttributeValueException {
        ATDataSetItemAttribute attribute = mAttributeMap.get(attributeIndex);
        ATServiceConfigurationAttribute configurationAttribute = attributeConfigurationForIndexMap.get(attributeIndex);
        if ( configurationAttribute == null || configurationAttribute.relatedService == null ) {
            throw new InvalidAttributeValueException("You have configured attribute " + attributeIndex + " as a relationship but you have not defined a related service. Please update your configuration to include a related service for this attribute.");
        }

        ATDataSetItem dataSetItem = new ATDataSetItem(configurationAttribute.relatedService.getAttributeConfigurationForIndexMap());
        if ( attribute == null ) {
            attribute = new ATDataSetItemAttribute(dataSetItem);
            mAttributeMap.put(attributeIndex, attribute);
        } else {
            attribute.addDataSetItem(dataSetItem);
        }
        return dataSetItem;
    }

    public ATDataSetAttachmentItem addNewAttachmentItemForAttributeIndex(int attributeIndex) throws InvalidAttributeValueException {
        validateGetterAttributeTypeForIndex(AttributeType.Attachments, attributeIndex);
        ATDataSetAttachmentItem attachmentItem = new ATDataSetAttachmentItem();
        ATDataSetItemAttribute attribute = mAttributeMap.get(attributeIndex);

        if ( attribute == null ) {
            attribute = new ATDataSetItemAttribute(attachmentItem);
            mAttributeMap.put(attributeIndex, attribute);
        } else {
            attribute.addDataSetItem(attachmentItem);
        }
        return attachmentItem;
    }
//    /**
//     * Sets a cdata set item value at the specified index of the attribute map
//     * @param value The data set item to set
//     * @param attributeIndex The index of the attribute
//     */
//    public void setDataSetItemForAttributeIndex(ATDataSetItem value, int attributeIndex) throws InvalidAttributeValueException {
//        validateGetterAttributeTypeForIndex(ATServiceConfigurationAttribute.AttributeType.Relation, attributeIndex);
//        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(value));
//        updateMaxAttribute(attributeIndex);
//    }
//
//    /**
//     * Sets a list of data set items at the specified index of the attribute map
//     * @param value The data set items to set
//     * @param attributeIndex The index of the attribute
//     */
//    public void setDataSetItemsForAttributeIndex(List<ATDataSetItem> value, int attributeIndex) throws InvalidAttributeValueException {
//        validateGetterAttributeTypeForIndex(ATServiceConfigurationAttribute.AttributeType.Relation, attributeIndex);
//        mAttributeMap.put(attributeIndex,new ATDataSetItemAttribute(value));
//        updateMaxAttribute(attributeIndex);
//    }

    public void setTimeIntervalForAttributeIndex(long timeInterval, int attributeIndex) throws InvalidAttributeValueException {
        if(!validateGetterAttributeTypeForIndex(AttributeType.TimeInterval, attributeIndex)) {
            return;
        }
        mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(timeInterval));
    }

    /**
     * Sets a date value at the specified index of the attribute map
     * @param date The date to set
     * @param attributeIndex The index of the attribute
     */
    public void setDateForAttributeIndex(DateTime date, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Date, attributeIndex)) {
            return;
        }
        if ( date != null ) {
            mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(date,false));
        }
    }

    /**
     * Sets a date time value at the specified index of the attribute map
     * @param date The date time to set
     * @param attributeIndex The index of the attribute
     */
    public void setDateTimeForAttributeIndex(DateTime date, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.DateTime, attributeIndex)) {
            return;
        }
        if ( date != null ) {
            mAttributeMap.put(attributeIndex, new ATDataSetItemAttribute(date,true));
        }
    }

    /**
     * Updates the max attribute index
     * @param attributeIndex
     */
    private void updateMaxAttribute(int attributeIndex) {
        mMaxAttributeIndex = attributeIndex > mMaxAttributeIndex ? attributeIndex : mMaxAttributeIndex;
    }

    /**
     * Gets the max attribute index
     * @return
     */
    private int maxAttributeIndex() {
        return mMaxAttributeIndex;
    }

    /**
     * Converts the data set item to a json object where the primary key does not need to be set
     * @return json object of the data set item
     */
    public JSONObject toJSON() {
        try {
            return _toJSON(false);
        } catch (ATInvalidPrimaryKeyException exception) {
            System.out.println("Caught invalid primary key exception when primaryKeyRequired was false. THIS SHOULD NOT HAPPEN");
        }
        return null;
    }

    /**
     * Converts the data set item to a json object the primary key must be set
     * @return json object of the data set item
     * @throws ATInvalidPrimaryKeyException
     */
    public JSONObject toJSONWithPrimaryKey() throws ATInvalidPrimaryKeyException {
        if ( mPrimaryKey == null ) {
            throw new ATInvalidPrimaryKeyException("Primary key can not be null for record: " + toJSON());
        }
        return _toJSON(true);
    }

    /**
     * Converts the data set item to a json object
     * @param primaryKeyRequired A boolean indicating whether or not the primary key of the data set item must be set
     * @return
     * @throws ATInvalidPrimaryKeyException
     */
    private JSONObject _toJSON(boolean primaryKeyRequired) throws ATInvalidPrimaryKeyException{
        JSONObject json;
        JSONArray attributes;
        Iterator attributeIterator;
        Map.Entry<Integer,ATDataSetItemAttribute> entry;

        json = new JSONObject();
        json.put("primaryKey",mPrimaryKey);
        json.put("CRUDStatus", mCRUDStatus);
        json.putOpt("clientKey", mClientKey);
        json.put("recordType", getItemType().stringValue);
        json.put("DataCollectionStatus",mDataCollectionStatus.stringValue);
        json.put("workFlowState", mWorkFlowState.stringValue);

        attributes = new JSONArray();
        attributeIterator = mAttributeMap.entrySet().iterator();
        while ( attributeIterator.hasNext() ) {
            entry = (Map.Entry) attributeIterator.next();
            attributes.put(entry.getKey(),entry.getValue().getJSONValue(primaryKeyRequired));
        }
        json.put("attributes",attributes);
        return json;
    }

    /**
     * Updates a data set item from a json object
     * @param jsonObject Json object containing the values of the data set item
     * @param attachmentIDMap
     */
    public void updateFromJSON(JSONObject jsonObject, HashMap<String, ATAttachmentUploadItem> attachmentIDMap) throws InvalidAttributeValueException {
        JSONArray attributes;
        String crudStatusString;
        String DataCollectionStatusString;
        Object subItem;
        JSONObject subItemJSON;
        JSONArray subAttributes;
        ATDataSetItem subDataSetItem;
        List<ATDataSetItem> subItems;
        String subClientKey;
        ATAttachmentUploadItem attachmentUploadItem;
        ATDataSetAttachmentItem attachment;
        String subRecordType;
        List<ATServiceConfigurationAttribute> relatedAttributes = null;
        ATServiceConfigurationAttribute attributeConfiguration;
        String workFlowString;
        String jsonString;

        mPrimaryKey = JSONUtils.getString(jsonObject, "primaryKey");
        crudStatusString = JSONUtils.getString(jsonObject,"CRUDStatus");

        if ( crudStatusString != null ) {
            mCRUDStatus = CRUDStatus.fromString(crudStatusString);
        }
        DataCollectionStatusString = JSONUtils.getString(jsonObject,"DataCollectionStatus");
        if ( DataCollectionStatusString != null ) {
            mDataCollectionStatus = DataCollectionStatus.fromString(DataCollectionStatusString);
        }
        workFlowString = jsonObject.optString("workFlowStatus");
        if ( workFlowString != null ) {
            mWorkFlowState = WorkFlowState.fromString(workFlowString);
        }
        mClientKey = JSONUtils.getString(jsonObject,"clientKey");
        attributes = JSONUtils.getJSONArray(jsonObject, "attributes");
        if ( attributes != null ) {
            for ( int i = 0; i < attributes.length(); i++ ) {
                if ( attributes.isNull(i) ) {
                    continue;
                }
                attributeConfiguration = attributeConfigurationForIndexMap.get(i);
                if (attributeConfiguration==null){
                    continue;
                }
                switch (attributeConfiguration.attributeType) {
                    case String:
                        setStringForAttributeIndex(attributes.optString(i,""), i);
                        break;
                    case Double:
                        setDoubleForAttributeIndex(attributes.optDouble(i,0), i);
                        break;
                    case Int:
                        setIntForAttributeIndex(attributes.optInt(i,0), i);
                        break;
                    case Boolean:
                        setBooleanForAttributeIndex(JSONUtils.getBooleanFromString(attributes.optString(i,"")), i);
                        break;
                    case CollectionUnitItem:
                        jsonString = attributes.optString(i);
                        ATCollectionUnitItem collectionUnitItem = jsonString != null ? new ATCollectionUnitItem(new JSONObject(jsonString)) : null;
                        setCollectionUnitItemForAttributeIndex(collectionUnitItem, i);
                        break;
                    case Date:
                        setDateForAttributeIndex(JSONUtils.getDate(attributes,i), i);
                        break;
                    case TimeInterval:
                        setTimeIntervalForAttributeIndex(attributes.optLong(i,0), i);
                        break;
                    case DateTime:
                        setDateTimeForAttributeIndex(JSONUtils.getDateTime(attributes, i), i);
                        break;
                    case DateRange:
                        jsonString = attributes.optString(i);
                        ATDateRange range = jsonString != null ? new ATDateRange(new JSONObject(jsonString)) : null;
                        setDateRangeForAttributeIndex(range, i);
                        break;
                    case DateTimeRange:
                        jsonString = attributes.optString(i);
                        ATDateTimeRange timeRange = jsonString != null ? new ATDateTimeRange(new JSONObject(jsonString)) : null;
                        setDateTimeRangeForAttributeIndex(timeRange, i);
                        break;
                    case Image:
                        subItemJSON = JSONUtils.jsonObjectFromString(attributes.optString(i,""));
                        if ( subItemJSON != null ) {
                            ATImage image = new ATImage(subItemJSON);
                            setImageForAttributeIndex(image, i);
                            if ( attachmentIDMap != null && image.uploadKey != null ) {
                                attachmentUploadItem = attachmentIDMap.get(image.uploadKey);
                                if (attachmentUploadItem != null ) {
                                    image.setAttachmentFileItem(attachmentUploadItem.getFileItem());
                                }
                            }
                        }
                        break;
                    case Location:
                        subItemJSON = JSONUtils.jsonObjectFromString(attributes.optString(i,""));
                        if ( subItemJSON != null ) {
                            setLocationForAttributeIndex(new ATLocation(subItemJSON), i);
                        }
                        break;
                    case Attachments:
                    case Relation:
                        attachmentUploadItem = null;
                        subAttributes = attributes.optJSONArray(i);
                        if ( subAttributes == null ) { continue; }
                        if ( attributeConfiguration.relatedService != null ) {
                            relatedAttributes = attributeConfiguration.relatedService.getAttributes();
                        } else {
                            relatedAttributes = null;
                        }
                        for ( int j = 0; j < subAttributes.length(); j++ ) {
                            subItemJSON = subAttributes.getJSONObject(j);
                            subRecordType = JSONUtils.getString(subItemJSON,"recordType");
                            subClientKey = JSONUtils.getString(subItemJSON, "clientKey");
                            if ( Type.fromString(subRecordType) == Type.Attachment ) {
                                if ( subClientKey != null && attachmentIDMap != null ) {
                                    attachmentUploadItem = attachmentIDMap.get(subClientKey);
                                }
                                subDataSetItem = this.addNewAttachmentItemForAttributeIndex(i);
                                subDataSetItem.updateFromJSON(subItemJSON,null);
                                if ( attachmentUploadItem != null ) {
                                    ((ATDataSetAttachmentItem) subDataSetItem).setAttachmentFile(attachmentUploadItem.getFileItem());
                                }
                            } else if ( relatedAttributes != null ) {
                                subDataSetItem = this.addNewDataSetItemForAttributeIndex(i);
                                subDataSetItem.updateFromJSON(subItemJSON, attachmentIDMap);
                            }
                        }
                        break;
                    case ListItem:
                        subItemJSON = JSONUtils.jsonObjectFromString(attributes.optString(i,""));
                        if ( subItemJSON != null ) {
                            ATListItem listItem = new ATListItem(attributeConfiguration.relatedListService);
                            listItem.updateFromJSON(subItemJSON);
                            setListItemForAttributeIndex(listItem, i);
                        }
                        break;
                    case Color:
                        subItemJSON = JSONUtils.jsonObjectFromString(attributes.optString(i,""));
                        if ( subItemJSON != null ) {
                            setColorForAttributeIndex(new ATColor(subItemJSON), i);
                        }
                        break;
                    case None:
                        break;
                }
            }
        }
    }
}