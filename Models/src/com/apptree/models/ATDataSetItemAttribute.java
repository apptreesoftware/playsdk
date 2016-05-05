package com.apptree.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew Smith on 11/5/14.
 * Copyright AppTree Software, Inc.
 */
public class ATDataSetItemAttribute {
    private String stringValue;
    private List<ATDataSetItem> mDataSetItems;
    private AttributeType attributeType;
    private ATLocation location;
    private ATListItem listItem;
    private ATColor color;
    private ATImage image;
    private DateTime date;
    private ATDateRange dateRange;
    private ATDateTimeRange dateTimeRange;
    private ATCollectionUnitItem collectionUnitItem;

    public static final DateTimeFormatter AppTreeDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.forID("Etc/GMT"));
    public static final DateTimeFormatter AppTreeDateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.forID("Etc/GMT"));
    /**
     * Creates an int data set item attribute
     * @param intValue
     */
    ATDataSetItemAttribute(int intValue) {
        attributeType = AttributeType.Int;
        stringValue = "" + intValue;
    }

    ATDataSetItemAttribute(long longValue) {
        attributeType = AttributeType.TimeInterval;
        stringValue = "" + longValue;
    }

    ATDataSetItemAttribute(ATColor color) {
        attributeType = AttributeType.Color;
        this.color = color;
    }

    /**
     * Creates a boolean data set item attribute
     * @param boolValue
     */
    ATDataSetItemAttribute(boolean boolValue){
        attributeType = AttributeType.Boolean;
        stringValue = boolValue ? "Y" : "N";
    }

    /**
     * Creates a date/date time attribute
     * @param date The date to be stored
     * @param dateTime a boolean indicating whether the time should be included
     */
    ATDataSetItemAttribute(DateTime date, boolean dateTime) {
        attributeType = dateTime ? AttributeType.DateTime : AttributeType.Date;
        this.date = date;
    }

    /**
     * Creates a date range attribute
     * @param range the ATDateRange to be stored
     */
    ATDataSetItemAttribute(ATDateRange range) {
        attributeType = AttributeType.DateRange;
        this.dateRange = range;
    }

    /**
     * Creates a date time range attribute
     * @param range The ATDateTimeRange to be stored
     */
    ATDataSetItemAttribute(ATDateTimeRange range) {
        attributeType = AttributeType.DateTimeRange;
        this.dateTimeRange = range;
    }

    /**
     * Creates a double data set item attribute
     * @param doubleValue
     */
    public ATDataSetItemAttribute(double doubleValue) {
        attributeType = AttributeType.Double;
        stringValue = "" + doubleValue;
    }

    /**
     * Creates a string data set item attribute
     * @param stringValue
     */
    public ATDataSetItemAttribute(String stringValue) {
        attributeType = AttributeType.String;
        this.stringValue = stringValue;
    }

    /**
     * Creates a location attribute
     * @param location The ATLocation to be stored
     */
    public ATDataSetItemAttribute(ATLocation location) {
        attributeType = AttributeType.Location;
        this.location = location;
    }

    /**
     * Adds a data set item to the data set item list
     * @param dataSetItem The data set item to add
     */
    public ATDataSetItemAttribute(ATDataSetItem dataSetItem) {
        attributeType = AttributeType.Relation;
        mDataSetItems = new ArrayList<ATDataSetItem>(1);
        mDataSetItems.add(dataSetItem);
    }

    /**
     * Sets the list of data set item attributes
     * @param dataSetItems The list of data set item attributes
     */
    public ATDataSetItemAttribute(List<ATDataSetItem>dataSetItems) {
        attributeType = AttributeType.Relation;
        mDataSetItems = dataSetItems;
    }

    /**
     * Creates a list item attribute
     * @param listItem The ATListItem to be stored
     */
    public ATDataSetItemAttribute(ATListItem listItem) {
        attributeType = AttributeType.ListItem;
        this.listItem = listItem;
    }

    /**
     * Creates an image attribute
     * @param image the ATImage to be stored
     */
    public ATDataSetItemAttribute(ATImage image) {
        attributeType = AttributeType.Image;
        this.image = image;
    }

    /**
     * Creates a collection unit item attribute (to be used in data collection data set items)
     * @param item The ATCollectionUnitItem to be stored
     */
    public ATDataSetItemAttribute(ATCollectionUnitItem item) {
        attributeType = AttributeType.CollectionUnitItem;
        this.collectionUnitItem = item;
    }

    public void addDataSetItem(ATDataSetItem dataSetItem) {
        mDataSetItems.add(dataSetItem);
    }

    /**
     * Gets the integer value of attribute
     * @return
     */
    public int getIntValue() {
        return Integer.parseInt(stringValue);
    }

    public long getLongValue() { return Long.parseLong(stringValue); }

    /**
     * Gets the double value of attribute
     * @return
     */
    public double getDoubleValue() {
        return Double.parseDouble(stringValue);
    }

    /**
     * Gets the string value of attribute
     * @return
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Gets the boolean value of attribute
     * @return
     */
    public boolean getBooleanValue(){
        if ( stringValue != null && stringValue.equalsIgnoreCase("Y") ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the ATColor value of an attribute
     * @return
     */
    public ATColor getColorValue() {
        return color;
    }

    /**
     * Gets the location value of an attribute
     * @return
     */
    public ATLocation getLocationValue() { return location; }

    /**
     * Gets the image value of an attribute
     * @return
     */
    public ATImage getImageValue() {
        return image;
    }
    /**
     * Returns the list of data set item attributes
     * @return
     */
    public List<ATDataSetItem> getDataSetItems() {
        return mDataSetItems;
    }

    /**
     * Gets the list item value of an attribute
     * @return
     */
    public ATListItem getListItem() {
        return listItem;
    }

    /**
     * Gets the data value of an attribute
     * @return
     */
    public DateTime getDateValue() {
        return date;
    }

    /**
     * Gets the date range value of an attribute
     * @return
     */
    public ATDateRange getDateRange() { return dateRange; }

    /**
     * Gets the date time range value of an attribute
     * @return
     */
    public ATDateTimeRange getDateTimeRange() { return dateTimeRange; }

    /**
     * Gets the collection unit item of an attribute
     * @return
     */
    public ATCollectionUnitItem getCollectionUnitItem() { return collectionUnitItem; }

    /**
     * Converts the data set item attribute to a json object
     * @param primaryKeyRequired A boolean indicating whether the primary key is required
     * @return a sjon object
     * @throws ATInvalidPrimaryKeyException
     */

    /*

       public enum AttributeType {
        String,
        Int,
        Double,
        Boolean,
        Date,
        DateTime,
        Image,
        Location,
        Attachments,
        Relation,
        ListItem,
        Color,
        Badge,
        None;
    }
     */

    public Object getJSONValue(boolean primaryKeyRequired) throws ATInvalidPrimaryKeyException {
        switch (attributeType) {
            case String:
                return stringValue;
            case Int:
                return stringValue;
            case Double:
                return stringValue;
            case TimeInterval:
                return stringValue;
            case ListItem:
                if ( listItem != null ) {
                    JSONObject json = listItem.toJSON();
                    if ( json != null ) {
                        return json.toString();
                    }
                }
                break;
            case CollectionUnitItem:
                if ( collectionUnitItem != null ) {
                    JSONObject json = collectionUnitItem.toJSON();
                    if ( json != null ) {
                        return json.toString();
                    }
                }
                break;
            case Date:
                if ( date != null ) {
                    return AppTreeDateFormat.print(date);
                }
                break;
            case DateTime:
                if ( date != null ) {
                    return AppTreeDateTimeFormat.print(date);
                }
                break;
            case Relation:
                return getDataSetItemsJSON(primaryKeyRequired);
            case Attachments:
                return getDataSetItemsJSON(primaryKeyRequired);
            case Boolean:
                return stringValue;
            case Color:
                if ( color != null ) {
                    return color.toJSON().toString();
                }
                break;
            case Image:
                if ( image != null ) {
                    return image.toJSON().toString();
                }
                break;
            case Location:
                if ( location != null ) {
                    return location.toJSON().toString();
                }
                break;
            case DateRange:
                if ( dateRange != null ) {
                    return dateRange.toJSON().toString();
                }
                break;
            case DateTimeRange:
                if ( dateTimeRange != null ) {
                    return dateTimeRange.toJSON().toString();
                }
                break;
            default:
                System.out.println("Unknown attribute type :" + attributeType.name());
        }
        return null;
    }

    /**
     * Converts the list of data set item attributes to a json object
     * @param primaryKeyRequired A boolean indicating whether or not the primary key is required
     * @return json object of the data set items list
     * @throws ATInvalidPrimaryKeyException
     */
    private JSONArray getDataSetItemsJSON(boolean primaryKeyRequired) throws ATInvalidPrimaryKeyException {
        JSONArray jsonArray = new JSONArray();

        for ( ATDataSetItem dataSetItem : mDataSetItems ) {
            if ( primaryKeyRequired ) {
                jsonArray.put(dataSetItem.toJSONWithPrimaryKey());
            } else {
                jsonArray.put(dataSetItem.toJSON());
            }
        }
        return jsonArray;
    }
}
