package com.apptree.models;

import org.joda.time.DateTime;

/**
 * Created by alexis on 11/3/15.
 */
public class ATListItemAttribute {
    private String stringValue;
    private ATLocation location;
    private ATListItem listItem;
    private ATColor color;
    private ATImage image;
    private DateTime date;
    private ATDateRange dateRange;
    private ATDateTimeRange dateTimeRange;
    private AttributeType attributeType;

    public ATListItemAttribute(int intValue) {
        attributeType = AttributeType.Int;
        stringValue = "" + intValue;
    }

    public ATListItemAttribute(ATColor color) {
        attributeType = AttributeType.Color;
        this.color = color;
    }

    public ATListItemAttribute(boolean boolValue) {
        attributeType = AttributeType.Boolean;
        this.stringValue = boolValue ? "Y" : "N";
    }

    public ATListItemAttribute(DateTime dateTime, boolean time) {
        attributeType = time ? AttributeType.DateTime : AttributeType.Date;
        date = dateTime;
    }

    public ATListItemAttribute(ATDateRange range) {
        attributeType = AttributeType.DateRange;
        dateRange = range;
    }

    public ATListItemAttribute(ATDateTimeRange range) {
        attributeType = AttributeType.DateTimeRange;
        dateTimeRange = range;
    }

    public ATListItemAttribute(String value) {
        attributeType = AttributeType.String;
        stringValue = value;
    }

    public ATListItemAttribute(double doubleValue) {
        attributeType = AttributeType.Double;
        stringValue = "" + doubleValue;
    }

    public ATListItemAttribute(long longValue) {
        attributeType = AttributeType.TimeInterval;
        stringValue = "" + longValue;
    }

    public ATListItemAttribute(ATLocation location) {
        attributeType = AttributeType.Location;
        this.location = location;
    }

    public ATListItemAttribute(ATListItem listItem) {
        attributeType = AttributeType.ListItem;
        this.listItem = listItem;
    }

    public ATListItemAttribute(ATImage image) {
        attributeType = AttributeType.Image;
        this.image = image;
    }

    /**
     * Gets the integer value of attribute
     * @return
     */
    public int getIntValue() {
        return Integer.parseInt(stringValue);
    }

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
     * Gets the color value of attribute
     * @return
     */
    public ATColor getColorValue() {
        return color;
    }

    /**
     * Gets the location value of attribute
     * @return
     */
    public ATLocation getLocationValue() { return location; }

    /**
     * Gets the image value of attribute
     * @return
     */
    public ATImage getImageValue() {
        return image;
    }

    /**
     * Gets the list item value of attribute
     * @return
     */
    public ATListItem getListItem() {
        return listItem;
    }

    /**
     * Gets the date value of attribute
     * @return
     */
    public DateTime getDateValue() {
        return date;
    }

    /**
     * Gets the date range value of attribute
     * @return
     */
    public ATDateRange getDateRange() { return dateRange; }

    /**
     * gets the date time range value of attribute
     * @return
     */
    public ATDateTimeRange getDateTimeRange() { return dateTimeRange; }

    public Object getJSONValue() {
        switch(attributeType) {
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
                    return listItem.toJSON();
                }
                break;
            case Date:
                if ( date != null ) {
                    return ATDataSetItemAttribute.AppTreeDateFormat.print(date);
                }
                break;
            case DateTime:
                if ( date != null ) {
                    return ATDataSetItemAttribute.AppTreeDateTimeFormat.print(date);
                }
                break;
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
                System.out.println("Unknown attribute type: " + attributeType.toString());
        }
        return null;
    }
}
