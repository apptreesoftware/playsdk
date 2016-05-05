package com.apptree.models;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by matthew on 11/5/14.
 */
public class ATListItem {

    public String id;
    public String parentID;
    public int orderBy = -1;
    public String value;
    private ATListItemAttribute attribute01;
    private ATListItemAttribute attribute02;
    private ATListItemAttribute attribute03;
    private ATListItemAttribute attribute04;
    private ATListItemAttribute attribute05;
    private ATListItemAttribute attribute06;
    private ATListItemAttribute attribute07;
    private ATListItemAttribute attribute08;
    private ATListItemAttribute attribute09;
    private ATListItemAttribute attribute10;
    public String latitude;
    public String longitude;

    private ATListServiceConfiguration attributeConfiguration;

    public ATListServiceConfiguration getConfiguration() {
        return attributeConfiguration;
    }

    public static int
            ATTRIBUTE_1 = 1,
            ATTRIBUTE_2 = 2,
            ATTRIBUTE_3 = 3,
            ATTRIBUTE_4 = 4,
            ATTRIBUTE_5 = 5,
            ATTRIBUTE_6 = 6,
            ATTRIBUTE_7 = 7,
            ATTRIBUTE_8 = 8,
            ATTRIBUTE_9 = 9,
            ATTRIBUTE_10 = 10;

    public ATListItem(String value) {
        this.value = value;
        this.id = value;
    }

    public ATListItem(ATListServiceConfiguration configuration) {
        attributeConfiguration = configuration;
    }

    /**
     * Sets the list item attribute
     * @param value The string list item attribute value
     * @param index The attribute to be set 1-10
     */
    public void setAttributeForIndex(String value, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(value);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(value);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(value);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(value);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(value);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(value);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(value);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(value);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(value);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(value);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets the list item attribute
     * @param intValue The int value
     * @param index The attribute to be set 1-10
     */
    public void setAttributeForIndex(int intValue, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(intValue);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(intValue);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(intValue);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(intValue);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(intValue);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(intValue);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(intValue);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(intValue);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(intValue);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(intValue);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets the lit item attribute
     * @param listItem The list item value
     * @param index The attribute to be set 1-5
     */
    public void setAttributeForIndex(ATListItem listItem, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(listItem);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(listItem);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(listItem);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(listItem);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(listItem);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(listItem);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(listItem);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(listItem);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(listItem);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(listItem);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets a boolean value for the indicated list item attribute
     * @param boolValue the boolean value
     * @param index the index of the attribute to be set
     */
    public void setAttributeForIndex(boolean boolValue, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(boolValue);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(boolValue);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(boolValue);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(boolValue);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(boolValue);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(boolValue);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(boolValue);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(boolValue);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(boolValue);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(boolValue);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets a date time at the indicate index
     * @param date the DateTime value
     * @param time a boolean indicating if the time should be included
     * @param index the index of the attribute to be set
     */
    public void setAttributeForIndex(DateTime date, boolean time, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(date, time);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(date, time);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(date, time);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(date, time);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(date, time);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(date, time);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(date, time);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(date, time);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(date, time);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(date, time);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets a color attribute at the indicated index
     * @param color an ATColor value
     * @param index the index of the attribute to be set
     */
    public void setAttributeForIndex(ATColor color, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(color);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(color);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(color);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(color);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(color);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(color);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(color);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(color);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(color);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(color);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets a date range on the indicated attribute
     * @param range the ATDateRange value
     * @param index the index of the attribute to be set
     */
    public void setAttributeForIndex(ATDateRange range, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(range);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(range);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(range);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(range);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(range);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(range);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(range);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(range);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(range);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(range);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets a date time range value on the indicated attribute
     * @param range the ATDateTimeRange value
     * @param index the index of the attribute to be set
     */
    public void setAttributeForIndex(ATDateTimeRange range, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(range);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(range);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(range);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(range);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(range);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(range);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(range);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(range);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(range);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(range);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets a double at the indicated index
     * @param doubleValue the double value
     * @param index the index of the attribute to be set
     */
    public void setAttributeForIndex(double doubleValue, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(doubleValue);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(doubleValue);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(doubleValue);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(doubleValue);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(doubleValue);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(doubleValue);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(doubleValue);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(doubleValue);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(doubleValue);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(doubleValue);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets a location value at the indicated attribute
     * @param location the ATLocation value
     * @param index the index of the attribute to be set
     */
    public void setAttributeForIndex(ATLocation location, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(location);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(location);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(location);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(location);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(location);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(location);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(location);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(location);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(location);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(location);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets an image value at the indicated attribute
     * @param image the ATImage value
     * @param index the index of the attribute to be set
     */
    public void setAttributeForIndex(ATImage image, int index) {
        switch (index) {
            case 1:
                attribute01 = new ATListItemAttribute(image);
                break;
            case 2:
                attribute02 = new ATListItemAttribute(image);
                break;
            case 3:
                attribute03 = new ATListItemAttribute(image);
                break;
            case 4:
                attribute04 = new ATListItemAttribute(image);
                break;
            case 5:
                attribute05 = new ATListItemAttribute(image);
                break;
            case 6:
                attribute06 = new ATListItemAttribute(image);
                break;
            case 7:
                attribute07 = new ATListItemAttribute(image);
                break;
            case 8:
                attribute08 = new ATListItemAttribute(image);
                break;
            case 9:
                attribute09 = new ATListItemAttribute(image);
                break;
            case 10:
                attribute10 = new ATListItemAttribute(image);
                break;
            default:
                System.out.println("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * REturns the attribute
     * @param index The attribute to get 0-9
     * @return
     */
    public ATListItemAttribute getAttributeForIndex(int index) {
        switch(index) {
            case 1:
                return attribute01;
            case 2:
                return attribute02;
            case 3:
                return attribute03;
            case 4:
                return attribute04;
            case 5:
                return attribute05;
            case 6:
                return attribute06;
            case 7:
                return attribute07;
            case 8:
                return attribute08;
            case 9:
                return attribute09;
            case 10:
                return attribute10;
            default:
                System.out.println("Requesting an attribute index ( " + index + ") that is beyond the allowed attribute indexes ( 1 - 10 )");
        }
        return null;
    }

    /**
     * Converts the list item to a json object
     * @return A json object
     */
    public JSONObject toJSON() {
        JSONObject json;
        json = new JSONObject();
        if ( value == null || value.length() == 0 ) {
            return null;
        }
        json.put("value",value);
        json.put("id", id);
        json.putOpt("attribute01", attribute01 != null ? attribute01.getJSONValue() : null);
        json.putOpt("attribute02", attribute02 != null ? attribute02.getJSONValue() : null);
        json.putOpt("attribute03", attribute03 != null ? attribute03.getJSONValue() : null);
        json.putOpt("attribute04", attribute04 != null ? attribute04.getJSONValue() : null);
        json.putOpt("attribute05", attribute05 != null ? attribute05.getJSONValue() : null);
        json.putOpt("attribute06", attribute06 != null ? attribute06.getJSONValue() : null);
        json.putOpt("attribute07", attribute07 != null ? attribute07.getJSONValue() : null);
        json.putOpt("attribute08", attribute08 != null ? attribute08.getJSONValue() : null);
        json.putOpt("attribute09", attribute09 != null ? attribute09.getJSONValue() : null);
        json.putOpt("attribute10", attribute10 != null ? attribute10.getJSONValue() : null);
        json.putOpt("latitude",latitude);
        json.putOpt("longitude",longitude);
        json.putOpt("parentID",this.parentID);
        if ( orderBy != -1 ) {
            json.put("orderBy",orderBy);
        }
        return json;
    }

    /**
     * Updates a list item from a json object
     * @param json The json object containing the list item attributes
     */
    public void updateFromJSON(JSONObject json) {
        String stringValue;
        JSONArray subListItems;
        ATListServiceConfiguration relatedListService;

        value = JSONUtils.getString(json,"value");
        id = json.optString("id", value);
        latitude = JSONUtils.getString(json,"latitude");
        longitude = JSONUtils.getString(json,"longitude");
        parentID = JSONUtils.getString(json,"parentID");
        orderBy = JSONUtils.getInt(json,"orderBy");

        attribute01 = setAttributeFromJSON(json, "attribute01", attributeConfiguration.getAttribute1());
        attribute02 = setAttributeFromJSON(json, "attribute02", attributeConfiguration.getAttribute2());
        attribute03 = setAttributeFromJSON(json, "attribute03", attributeConfiguration.getAttribute3());
        attribute04 = setAttributeFromJSON(json, "attribute04", attributeConfiguration.getAttribute4());
        attribute05 = setAttributeFromJSON(json, "attribute05", attributeConfiguration.getAttribute5());
        attribute06 = setAttributeFromJSON(json, "attribute06", attributeConfiguration.getAttribute6());
        attribute07 = setAttributeFromJSON(json, "attribute07", attributeConfiguration.getAttribute7());
        attribute08 = setAttributeFromJSON(json, "attribute08", attributeConfiguration.getAttribute8());
        attribute09 = setAttributeFromJSON(json, "attribute09", attributeConfiguration.getAttribute9());
        attribute10 = setAttributeFromJSON(json, "attribute10", attributeConfiguration.getAttribute10());
    }

    private ATListItemAttribute setAttributeFromJSON(JSONObject json, String attributeString, ATListServiceConfigurationAttribute configurationAttribute) {
        String stringValue;
        JSONObject subListItems;
        ATListServiceConfiguration relatedListService;

        if ( configurationAttribute == null ) {
            return null;
        }

        switch ( configurationAttribute.getAttributeType() ) {
            case String:
                return new ATListItemAttribute(json.optString(attributeString));
            case Int:
                return new ATListItemAttribute(json.optInt(attributeString));
            case Double:
                return new ATListItemAttribute(json.optDouble(attributeString));
            case TimeInterval:
                return new ATListItemAttribute(json.optLong(attributeString));
            case ListItem:
                subListItems = json.optJSONObject(attributeString);
                if ( subListItems == null ) {
                    break;
                }
                if ( configurationAttribute.getRelatedList() != null ) {
                    relatedListService = configurationAttribute.getRelatedList();
                    ATListItem relatedListItem = new ATListItem(relatedListService);
                    relatedListItem.updateFromJSON(subListItems);
                    return new ATListItemAttribute(relatedListItem);
                }
                break;
            case Date:
                stringValue = json.optString(attributeString);
                if ( stringValue != null ) {
                    return new ATListItemAttribute(ATDataSetItemAttribute.AppTreeDateFormat.parseDateTime(stringValue), false);
                }
                break;
            case DateTime:
                stringValue = json.optString(attributeString);
                if ( stringValue != null ) {
                    return new ATListItemAttribute(ATDataSetItemAttribute.AppTreeDateFormat.parseDateTime(stringValue), true);
                }
                break;
            case Boolean:
                return new ATListItemAttribute(json.optBoolean(attributeString));
            case Color:
                if ( json.optJSONObject(attributeString) != null ) {
                    return new ATListItemAttribute(new ATColor(json.optJSONObject(attributeString)));
                }
                break;
            case Image:
                if ( json.optJSONObject(attributeString) != null ) {
                    return new ATListItemAttribute(new ATImage(json.optJSONObject(attributeString)));
                }
                break;
            case Location:
                if ( json.optJSONObject(attributeString) != null ) {
                    return new ATListItemAttribute(new ATLocation(json.optJSONObject(attributeString)));
                }
                break;
            case DateRange:
                if ( json.optJSONObject(attributeString) != null ) {
                    return new ATListItemAttribute(new ATDateRange(json.optJSONObject(attributeString)));
                }
                break;
            case DateTimeRange:
                if ( json.optJSONObject(attributeString) != null ) {
                    return new ATListItemAttribute(new ATDateTimeRange(json.optJSONObject(attributeString)));
                }
                break;
            default:
                System.out.println("Unknown attribute type: " + configurationAttribute.getAttributeType().toString());
        }

        return null;
    }
}
