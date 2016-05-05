package models.sdk.List;

import models.sdk.AttributeDataTypes.*;
import org.joda.time.DateTime;

/**
 * Created by alexis on 5/3/16.
 */
public class ListItem {
    public String id;
    public String parentID;
    public int orderBy = -1;
    public String value;
    private ListItemAttribute attribute01;
    private ListItemAttribute attribute02;
    private ListItemAttribute attribute03;
    private ListItemAttribute attribute04;
    private ListItemAttribute attribute05;
    private ListItemAttribute attribute06;
    private ListItemAttribute attribute07;
    private ListItemAttribute attribute08;
    private ListItemAttribute attribute09;
    private ListItemAttribute attribute10;
    public double latitude;
    public double longitude;

    private ListServiceConfiguration attributeConfiguration;

    public ListServiceConfiguration getConfiguration() {
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

    public ListItem() {}

    public ListItem(String value) {
        this.value = value;
        this.id = value;
    }

    public ListItem(ListServiceConfiguration configuration) {
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
                attribute01 = new ListItemAttribute(value);
                break;
            case 2:
                attribute02 = new ListItemAttribute(value);
                break;
            case 3:
                attribute03 = new ListItemAttribute(value);
                break;
            case 4:
                attribute04 = new ListItemAttribute(value);
                break;
            case 5:
                attribute05 = new ListItemAttribute(value);
                break;
            case 6:
                attribute06 = new ListItemAttribute(value);
                break;
            case 7:
                attribute07 = new ListItemAttribute(value);
                break;
            case 8:
                attribute08 = new ListItemAttribute(value);
                break;
            case 9:
                attribute09 = new ListItemAttribute(value);
                break;
            case 10:
                attribute10 = new ListItemAttribute(value);
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
                attribute01 = new ListItemAttribute(intValue);
                break;
            case 2:
                attribute02 = new ListItemAttribute(intValue);
                break;
            case 3:
                attribute03 = new ListItemAttribute(intValue);
                break;
            case 4:
                attribute04 = new ListItemAttribute(intValue);
                break;
            case 5:
                attribute05 = new ListItemAttribute(intValue);
                break;
            case 6:
                attribute06 = new ListItemAttribute(intValue);
                break;
            case 7:
                attribute07 = new ListItemAttribute(intValue);
                break;
            case 8:
                attribute08 = new ListItemAttribute(intValue);
                break;
            case 9:
                attribute09 = new ListItemAttribute(intValue);
                break;
            case 10:
                attribute10 = new ListItemAttribute(intValue);
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
    public void setAttributeForIndex(ListItem listItem, int index) {
        switch (index) {
            case 1:
                attribute01 = new ListItemAttribute(listItem);
                break;
            case 2:
                attribute02 = new ListItemAttribute(listItem);
                break;
            case 3:
                attribute03 = new ListItemAttribute(listItem);
                break;
            case 4:
                attribute04 = new ListItemAttribute(listItem);
                break;
            case 5:
                attribute05 = new ListItemAttribute(listItem);
                break;
            case 6:
                attribute06 = new ListItemAttribute(listItem);
                break;
            case 7:
                attribute07 = new ListItemAttribute(listItem);
                break;
            case 8:
                attribute08 = new ListItemAttribute(listItem);
                break;
            case 9:
                attribute09 = new ListItemAttribute(listItem);
                break;
            case 10:
                attribute10 = new ListItemAttribute(listItem);
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
                attribute01 = new ListItemAttribute(boolValue);
                break;
            case 2:
                attribute02 = new ListItemAttribute(boolValue);
                break;
            case 3:
                attribute03 = new ListItemAttribute(boolValue);
                break;
            case 4:
                attribute04 = new ListItemAttribute(boolValue);
                break;
            case 5:
                attribute05 = new ListItemAttribute(boolValue);
                break;
            case 6:
                attribute06 = new ListItemAttribute(boolValue);
                break;
            case 7:
                attribute07 = new ListItemAttribute(boolValue);
                break;
            case 8:
                attribute08 = new ListItemAttribute(boolValue);
                break;
            case 9:
                attribute09 = new ListItemAttribute(boolValue);
                break;
            case 10:
                attribute10 = new ListItemAttribute(boolValue);
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
                attribute01 = new ListItemAttribute(date, time);
                break;
            case 2:
                attribute02 = new ListItemAttribute(date, time);
                break;
            case 3:
                attribute03 = new ListItemAttribute(date, time);
                break;
            case 4:
                attribute04 = new ListItemAttribute(date, time);
                break;
            case 5:
                attribute05 = new ListItemAttribute(date, time);
                break;
            case 6:
                attribute06 = new ListItemAttribute(date, time);
                break;
            case 7:
                attribute07 = new ListItemAttribute(date, time);
                break;
            case 8:
                attribute08 = new ListItemAttribute(date, time);
                break;
            case 9:
                attribute09 = new ListItemAttribute(date, time);
                break;
            case 10:
                attribute10 = new ListItemAttribute(date, time);
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
    public void setAttributeForIndex(Color color, int index) {
        switch (index) {
            case 1:
                attribute01 = new ListItemAttribute(color);
                break;
            case 2:
                attribute02 = new ListItemAttribute(color);
                break;
            case 3:
                attribute03 = new ListItemAttribute(color);
                break;
            case 4:
                attribute04 = new ListItemAttribute(color);
                break;
            case 5:
                attribute05 = new ListItemAttribute(color);
                break;
            case 6:
                attribute06 = new ListItemAttribute(color);
                break;
            case 7:
                attribute07 = new ListItemAttribute(color);
                break;
            case 8:
                attribute08 = new ListItemAttribute(color);
                break;
            case 9:
                attribute09 = new ListItemAttribute(color);
                break;
            case 10:
                attribute10 = new ListItemAttribute(color);
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
    public void setAttributeForIndex(DateRange range, int index) {
        switch (index) {
            case 1:
                attribute01 = new ListItemAttribute(range);
                break;
            case 2:
                attribute02 = new ListItemAttribute(range);
                break;
            case 3:
                attribute03 = new ListItemAttribute(range);
                break;
            case 4:
                attribute04 = new ListItemAttribute(range);
                break;
            case 5:
                attribute05 = new ListItemAttribute(range);
                break;
            case 6:
                attribute06 = new ListItemAttribute(range);
                break;
            case 7:
                attribute07 = new ListItemAttribute(range);
                break;
            case 8:
                attribute08 = new ListItemAttribute(range);
                break;
            case 9:
                attribute09 = new ListItemAttribute(range);
                break;
            case 10:
                attribute10 = new ListItemAttribute(range);
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
    public void setAttributeForIndex(DateTimeRange range, int index) {
        switch (index) {
            case 1:
                attribute01 = new ListItemAttribute(range);
                break;
            case 2:
                attribute02 = new ListItemAttribute(range);
                break;
            case 3:
                attribute03 = new ListItemAttribute(range);
                break;
            case 4:
                attribute04 = new ListItemAttribute(range);
                break;
            case 5:
                attribute05 = new ListItemAttribute(range);
                break;
            case 6:
                attribute06 = new ListItemAttribute(range);
                break;
            case 7:
                attribute07 = new ListItemAttribute(range);
                break;
            case 8:
                attribute08 = new ListItemAttribute(range);
                break;
            case 9:
                attribute09 = new ListItemAttribute(range);
                break;
            case 10:
                attribute10 = new ListItemAttribute(range);
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
                attribute01 = new ListItemAttribute(doubleValue);
                break;
            case 2:
                attribute02 = new ListItemAttribute(doubleValue);
                break;
            case 3:
                attribute03 = new ListItemAttribute(doubleValue);
                break;
            case 4:
                attribute04 = new ListItemAttribute(doubleValue);
                break;
            case 5:
                attribute05 = new ListItemAttribute(doubleValue);
                break;
            case 6:
                attribute06 = new ListItemAttribute(doubleValue);
                break;
            case 7:
                attribute07 = new ListItemAttribute(doubleValue);
                break;
            case 8:
                attribute08 = new ListItemAttribute(doubleValue);
                break;
            case 9:
                attribute09 = new ListItemAttribute(doubleValue);
                break;
            case 10:
                attribute10 = new ListItemAttribute(doubleValue);
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
    public void setAttributeForIndex(Location location, int index) {
        switch (index) {
            case 1:
                attribute01 = new ListItemAttribute(location);
                break;
            case 2:
                attribute02 = new ListItemAttribute(location);
                break;
            case 3:
                attribute03 = new ListItemAttribute(location);
                break;
            case 4:
                attribute04 = new ListItemAttribute(location);
                break;
            case 5:
                attribute05 = new ListItemAttribute(location);
                break;
            case 6:
                attribute06 = new ListItemAttribute(location);
                break;
            case 7:
                attribute07 = new ListItemAttribute(location);
                break;
            case 8:
                attribute08 = new ListItemAttribute(location);
                break;
            case 9:
                attribute09 = new ListItemAttribute(location);
                break;
            case 10:
                attribute10 = new ListItemAttribute(location);
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
    public void setAttributeForIndex(Image image, int index) {
        switch (index) {
            case 1:
                attribute01 = new ListItemAttribute(image);
                break;
            case 2:
                attribute02 = new ListItemAttribute(image);
                break;
            case 3:
                attribute03 = new ListItemAttribute(image);
                break;
            case 4:
                attribute04 = new ListItemAttribute(image);
                break;
            case 5:
                attribute05 = new ListItemAttribute(image);
                break;
            case 6:
                attribute06 = new ListItemAttribute(image);
                break;
            case 7:
                attribute07 = new ListItemAttribute(image);
                break;
            case 8:
                attribute08 = new ListItemAttribute(image);
                break;
            case 9:
                attribute09 = new ListItemAttribute(image);
                break;
            case 10:
                attribute10 = new ListItemAttribute(image);
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
    public ListItemAttribute getAttributeForIndex(int index) {
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
}
