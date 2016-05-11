package sdk.list;

import com.fasterxml.jackson.annotation.JsonInclude;
import play.Logger;
import sdk.models.*;
import org.joda.time.DateTime;

/**
 * Created by alexis on 5/3/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public static final int
            ATTRIBUTE_1 = 0,
            ATTRIBUTE_2 = 1,
            ATTRIBUTE_3 = 2,
            ATTRIBUTE_4 = 3,
            ATTRIBUTE_5 = 4,
            ATTRIBUTE_6 = 5,
            ATTRIBUTE_7 = 6,
            ATTRIBUTE_8 = 7,
            ATTRIBUTE_9 = 8,
            ATTRIBUTE_10 = 9;

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
    public void setAttributeForIndex(Object value, int index) {
        ListItemAttribute attribute;
        if ( value instanceof String ) {
            attribute = new ListItemAttribute((String)value);
        } else if ( value instanceof Color ) {
            attribute = new ListItemAttribute((Color)value);
        } else if ( value instanceof Integer ) {
            attribute = new ListItemAttribute((Integer) value);
        } else if ( value instanceof Location ) {
            attribute = new ListItemAttribute((Location) value);
        } else if ( value instanceof DateTime ) {
            Logger.warn("setAttributeForIndex(Object value, int index) with a value type of DateTime assumes that the date contains a time component. Use setDateAttributeAtIndex(DateTime date, boolean time) to be more explicit.");
            attribute = new ListItemAttribute((DateTime)value, true);
        } else if ( value instanceof DateRange ) {
            attribute = new ListItemAttribute((DateRange) value);
        } else if ( value instanceof DateTimeRange ) {
            attribute = new ListItemAttribute((DateTimeRange) value);
        } else if ( value instanceof Double ) {
            attribute = new ListItemAttribute((Double) value);
        } else if ( value instanceof Image ) {
            attribute = new ListItemAttribute((Image) value);
        } else if ( value instanceof Long ) {
            attribute = new ListItemAttribute((Long) value);
        } else if ( value instanceof Boolean ) {
            attribute = new ListItemAttribute((Boolean) value);
        } else {
            Logger.error("List does not support a value of type " + value.getClass().getCanonicalName());
            return;
        }
        switch (index) {
            case ATTRIBUTE_1:
                attribute01 = attribute;
                break;
            case ATTRIBUTE_2:
                attribute02 = attribute;
                break;
            case ATTRIBUTE_3:
                attribute03 = attribute;
                break;
            case ATTRIBUTE_4:
                attribute04 = attribute;
                break;
            case ATTRIBUTE_5:
                attribute05 = attribute;
                break;
            case ATTRIBUTE_6:
                attribute06 = attribute;
                break;
            case ATTRIBUTE_7:
                attribute07 = attribute;
                break;
            case ATTRIBUTE_8:
                attribute08 = attribute;
                break;
            case ATTRIBUTE_9:
                attribute09 = attribute;
                break;
            case ATTRIBUTE_10:
                attribute10 = attribute;
                break;
            default:
                Logger.warn("The index you specified (" + index + ") is beyond the allowed number of attributes ( 1 - 10 )");
        }
    }

    /**
     * Sets a date time at the indicate index
     * @param date the DateTime value
     * @param time a boolean indicating if the time should be included
     * @param index the index of the attribute to be set
     */
    public void setDateAttributeForIndex(DateTime date, boolean time, int index) {
        switch (index) {
            case ATTRIBUTE_1:
                attribute01 = new ListItemAttribute(date, time);
                break;
            case ATTRIBUTE_2:
                attribute02 = new ListItemAttribute(date, time);
                break;
            case ATTRIBUTE_3:
                attribute03 = new ListItemAttribute(date, time);
                break;
            case ATTRIBUTE_4:
                attribute04 = new ListItemAttribute(date, time);
                break;
            case ATTRIBUTE_5:
                attribute05 = new ListItemAttribute(date, time);
                break;
            case ATTRIBUTE_6:
                attribute06 = new ListItemAttribute(date, time);
                break;
            case ATTRIBUTE_7:
                attribute07 = new ListItemAttribute(date, time);
                break;
            case ATTRIBUTE_8:
                attribute08 = new ListItemAttribute(date, time);
                break;
            case ATTRIBUTE_9:
                attribute09 = new ListItemAttribute(date, time);
                break;
            case ATTRIBUTE_10:
                attribute10 = new ListItemAttribute(date, time);
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
            case ATTRIBUTE_1:
                return attribute01;
            case ATTRIBUTE_2:
                return attribute02;
            case ATTRIBUTE_3:
                return attribute03;
            case ATTRIBUTE_4:
                return attribute04;
            case ATTRIBUTE_5:
                return attribute05;
            case ATTRIBUTE_6:
                return attribute06;
            case ATTRIBUTE_7:
                return attribute07;
            case ATTRIBUTE_8:
                return attribute08;
            case ATTRIBUTE_9:
                return attribute09;
            case ATTRIBUTE_10:
                return attribute10;
            default:
                System.out.println("Requesting an attribute index ( " + index + ") that is beyond the allowed attribute indexes ( 1 - 10 )");
        }
        return null;
    }

    public String getAttribute01() {
        return attribute01 != null ? attribute01.getStringValue() : null;
    }

    public String getAttribute02() {
        return attribute02 != null ? attribute02.getStringValue() : null;
    }

    public String getAttribute03() {
        return attribute03 != null ? attribute03.getStringValue() : null;
    }

    public String getAttribute04() {
        return attribute04 != null ? attribute04.getStringValue() : null;
    }

    public String getAttribute05() {
        return attribute05 != null ? attribute05.getStringValue() : null;
    }

    public String getAttribute06() {
        return attribute06 != null ? attribute06.getStringValue() : null;
    }

    public String getAttribute07() {
        return attribute07 != null ? attribute07.getStringValue() : null;
    }

    public String getAttribute08() {
        return attribute08 != null ? attribute08.getStringValue() : null;
    }

    public String getAttribute09() {
        return attribute09 != null ? attribute09.getStringValue() : null;
    }

    public String getAttribute10() {
        return attribute10 != null ? attribute10.getStringValue() : null;
    }
}
