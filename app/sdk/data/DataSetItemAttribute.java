package sdk.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import sdk.models.*;
import sdk.list.ListItem;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.libs.Json;
import sdk.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexis on 5/3/16.
 */
public class DataSetItemAttribute {
    private String stringValue;
    private List<DataSetItem> dataSetItems;
    private AttributeType attributeType;
    private Location location;
    private ListItem listItem;
    private Color color;
    private Image image;
    private DateTime date;
    private DateRange dateRange;
    private DateTimeRange dateTimeRange;

    public static final DateTimeFormatter AppTreeDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.forID("Etc/GMT"));
    public static final DateTimeFormatter AppTreeDateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.forID("Etc/GMT"));
    /**
     * Creates an int data set item attribute
     * @param intValue
     */
    DataSetItemAttribute(int intValue) {
        attributeType = AttributeType.Int;
        stringValue = "" + intValue;
    }

    DataSetItemAttribute(long longValue) {
        attributeType = AttributeType.TimeInterval;
        stringValue = "" + longValue;
    }

    DataSetItemAttribute(Color color) {
        attributeType = AttributeType.Color;
        this.color = color;
    }

    /**
     * Creates a boolean data set item attribute
     * @param boolValue
     */
    DataSetItemAttribute(boolean boolValue){
        attributeType = AttributeType.Boolean;
        stringValue = boolValue ? "Y" : "N";
    }

    /**
     * Creates a date/date time attribute
     * @param date The date to be stored
     * @param dateTime a boolean indicating whether the time should be included
     */
    DataSetItemAttribute(DateTime date, boolean dateTime) {
        attributeType = dateTime ? AttributeType.DateTime : AttributeType.Date;
        this.date = date;
    }

    /**
     * Creates a date range attribute
     * @param range the ATDateRange to be stored
     */
    DataSetItemAttribute(DateRange range) {
        attributeType = AttributeType.DateRange;
        this.dateRange = range;
    }

    /**
     * Creates a date time range attribute
     * @param range The ATDateTimeRange to be stored
     */
    DataSetItemAttribute(DateTimeRange range) {
        attributeType = AttributeType.DateTimeRange;
        this.dateTimeRange = range;
    }

    /**
     * Creates a double data set item attribute
     * @param doubleValue
     */
    public DataSetItemAttribute(double doubleValue) {
        attributeType = AttributeType.Double;
        stringValue = "" + doubleValue;
    }

    /**
     * Creates a string data set item attribute
     * @param stringValue
     */
    public DataSetItemAttribute(String stringValue) {
        attributeType = AttributeType.String;
        this.stringValue = stringValue;
    }

    /**
     * Creates a location attribute
     * @param location The ATLocation to be stored
     */
    public DataSetItemAttribute(Location location) {
        attributeType = AttributeType.Location;
        this.location = location;
    }

    /**
     * Adds a data set item to the data set item list
     * @param dataSetItem The data set item to add
     */
    public DataSetItemAttribute(DataSetItem dataSetItem) {
        attributeType = AttributeType.Relation;
        dataSetItems = new ArrayList<>(1);
        dataSetItems.add(dataSetItem);
    }

    public DataSetItemAttribute(DataSetItemAttachment dataSetItem) {
        attributeType = AttributeType.Attachments;
        dataSetItems = new ArrayList<>(1);
        dataSetItems.add(dataSetItem);
    }

    /**
     * Sets the list of data set item attributes
     * @param dataSetItems The list of data set item attributes
     */
    public DataSetItemAttribute(List<DataSetItem>dataSetItems) {
        attributeType = AttributeType.Relation;
        this.dataSetItems = dataSetItems;
    }

    /**
     * Creates a list item attribute
     * @param listItem The ListItem to be stored
     */
    public DataSetItemAttribute(ListItem listItem) {
        attributeType = AttributeType.ListItem;
        this.listItem = listItem;
    }

    /**
     * Creates an image attribute
     * @param image the ATImage to be stored
     */
    public DataSetItemAttribute(Image image) {
        attributeType = AttributeType.Image;
        this.image = image;
    }

    public void addDataSetItem(DataSetItem dataSetItem) {
        dataSetItems.add(dataSetItem);
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
    public Color getColorValue() {
        return color;
    }

    /**
     * Gets the location value of an attribute
     * @return
     */
    public Location getLocationValue() { return location; }

    /**
     * Gets the image value of an attribute
     * @return
     */
    public Image getImageValue() {
        return image;
    }
    /**
     * Returns the list of data set item attributes
     * @return
     */
    public List<DataSetItem> getDataSetItems() {
        return dataSetItems;
    }

    /**
     * Gets the list item value of an attribute
     * @return
     */
    public ListItem getListItem() {
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
    public DateRange getDateRange() { return dateRange; }

    /**
     * Gets the date time range value of an attribute
     * @return
     */
    public DateTimeRange getDateTimeRange() { return dateTimeRange; }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public Object getJSONValue(boolean primaryKeyRequired) throws InvalidPrimaryKeyException {
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
                if ( listItem != null && listItem.value != null) {
                    JsonNode json = JsonUtils.toJson(listItem);
                    return json.toString();
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
                    return JsonUtils.toJson(color).toString();
                }
                break;
            case Image:
                if ( image != null ) {
                    return JsonUtils.toJson(image).toString();
                }
                break;
            case Location:
                if ( location != null ) {
                    return JsonUtils.toJson(location).toString();
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

    private ArrayNode getDataSetItemsJSON(boolean primaryKeyRequired) throws InvalidPrimaryKeyException {
        ArrayNode jsonArray = Json.newArray();

        for ( DataSetItem dataSetItem : dataSetItems) {
            if ( primaryKeyRequired ) {
                jsonArray.add(dataSetItem.toJSONWithPrimaryKey());
            } else {
                jsonArray.add(dataSetItem.toJSON());
            }
        }
        return jsonArray;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        DataSetItemAttribute that = (DataSetItemAttribute) o;

        if (stringValue != null ? !stringValue.equals(that.stringValue) : that.stringValue != null) return false;
        if (dataSetItems != null ? dataSetItems.size() != that.dataSetItems.size() : that.dataSetItems != null) return false;
        if (attributeType != that.attributeType) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (listItem != null ? !listItem.equals(that.listItem) : that.listItem != null) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (dateRange != null ? !dateRange.equals(that.dateRange) : that.dateRange != null) return false;
        return dateTimeRange != null ? dateTimeRange.equals(that.dateTimeRange) : that.dateTimeRange == null;
    }

    @Override
    public int hashCode() {
        int result = stringValue != null ? stringValue.hashCode() : 0;
        result = 31 * result + (dataSetItems != null ? dataSetItems.hashCode() : 0);
        result = 31 * result + (attributeType != null ? attributeType.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (listItem != null ? listItem.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (dateRange != null ? dateRange.hashCode() : 0);
        result = 31 * result + (dateTimeRange != null ? dateTimeRange.hashCode() : 0);
        return result;
    }
}
