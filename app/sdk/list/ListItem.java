package sdk.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import play.Logger;
import sdk.models.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by alexis on 5/3/16.
 */
@JsonSerialize(using = ListItem.ListItemSerializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListItem {
    public String id;
    public String parentID;
    public String value;
    private HashMap<Integer, ListItemAttribute> itemAttributes = new HashMap<>();
    public double latitude;
    public double longitude;
    private int maxAttributeIndex = -1;

    private ListServiceConfiguration attributeConfiguration;

    public ListServiceConfiguration getConfiguration() {
        return attributeConfiguration;
    }

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
    public void setAttributeForIndex(@Nullable Object value, int index) {
        ListItemAttribute attribute = null;

        if ( index > 79 || index < 0 ) Logger.warn("The index you specified (" + index + ") is beyond the allowed number of attributes ( 0 - 79 )");
        if ( value != null ) {
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
        }
        itemAttributes.put(index, attribute);
        maxAttributeIndex = maxAttributeIndex > index ? maxAttributeIndex : index;
    }

    /**
     * Sets a date time at the indicate index
     * @param date the DateTime value
     * @param time a boolean indicating if the time should be included
     * @param index the index of the attribute to be set
     */
    public void setDateAttributeForIndex(DateTime date, boolean time, int index) {
        if ( index > 79 || index < 0 ) Logger.warn("The index you specified (" + index + ") is beyond the allowed number of attributes ( 0 - 79 )");

        itemAttributes.put(index, new ListItemAttribute(date, time));
        maxAttributeIndex = maxAttributeIndex > index ? maxAttributeIndex : index;
    }

    /**
     * REturns the attribute
     * @param index The attribute to get 0-9
     * @return
     */
    public ListItemAttribute getAttributeForIndex(int index) {
        return itemAttributes.get(index);
    }

    static class ListItemSerializer extends JsonSerializer<ListItem> {

        @Override
        public void serialize(ListItem value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeStartObject();
            gen.writeStringField("id", value.id);
            gen.writeStringField("value", value.value);
            gen.writeNumberField("latitude", value.latitude);
            gen.writeNumberField("longitude", value.longitude);
            for ( int i = 0; i <= value.maxAttributeIndex; i++ ) {
                ListItemAttribute attribute = value.getAttributeForIndex(i);
                if ( attribute != null ) {
                    if ( i < 10 ) {
                        gen.writeStringField("attribute0" + (i + 1), attribute.getStringValue());
                    } else {
                        gen.writeStringField("attribute" + (i + 1), attribute.getStringValue());
                    }
                }
            }
            gen.writeEndObject();
        }
    }
}
