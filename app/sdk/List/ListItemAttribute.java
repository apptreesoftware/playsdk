package sdk.list;

import sdk.models.*;
import org.joda.time.DateTime;

/**
 * Created by alexis on 5/3/16.
 */
public class ListItemAttribute {
    private String stringValue;
    private Location location;
    private ListItem listItem;
    private Color color;
    private Image image;
    private DateTime date;
    private DateRange dateRange;
    private DateTimeRange dateTimeRange;
    private AttributeType attributeType;

    public ListItemAttribute(int intValue) {
        attributeType = AttributeType.Int;
        stringValue = "" + intValue;
    }

    public ListItemAttribute(Color color) {
        attributeType = AttributeType.Color;
        this.color = color;
    }

    public ListItemAttribute(boolean boolValue) {
        attributeType = AttributeType.Boolean;
        this.stringValue = boolValue ? "Y" : "N";
    }

    public ListItemAttribute(DateTime dateTime, boolean time) {
        attributeType = time ? AttributeType.DateTime : AttributeType.Date;
        date = dateTime;
    }

    public ListItemAttribute(DateRange range) {
        attributeType = AttributeType.DateRange;
        dateRange = range;
    }

    public ListItemAttribute(DateTimeRange range) {
        attributeType = AttributeType.DateTimeRange;
        dateTimeRange = range;
    }

    public ListItemAttribute(String value) {
        attributeType = AttributeType.String;
        stringValue = value;
    }

    public ListItemAttribute(double doubleValue) {
        attributeType = AttributeType.Double;
        stringValue = "" + doubleValue;
    }

    public ListItemAttribute(long longValue) {
        attributeType = AttributeType.TimeInterval;
        stringValue = "" + longValue;
    }

    public ListItemAttribute(Location location) {
        attributeType = AttributeType.Location;
        this.location = location;
    }

    public ListItemAttribute(ListItem listItem) {
        attributeType = AttributeType.ListItem;
        this.listItem = listItem;
    }

    public ListItemAttribute(Image image) {
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
    public Color getColorValue() {
        return color;
    }

    /**
     * Gets the location value of attribute
     * @return
     */
    public Location getLocationValue() { return location; }

    /**
     * Gets the image value of attribute
     * @return
     */
    public Image getImageValue() {
        return image;
    }

    /**
     * Gets the list item value of attribute
     * @return
     */
    public ListItem getListItem() {
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
    public DateRange getDateRange() { return dateRange; }

    /**
     * gets the date time range value of attribute
     * @return
     */
    public DateTimeRange getDateTimeRange() { return dateTimeRange; }
}
