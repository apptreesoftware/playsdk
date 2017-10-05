package sdk.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import play.Logger;
import sdk.data.*;
import sdk.models.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by alexis on 5/3/16.
 */
@JsonSerialize(using = ListItem.ListItemSerializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListItem implements Record {
    public String id;
    public String parentID;
    public String value;
    private HashMap<Integer, ListItemAttribute> itemAttributes = new HashMap<>();
    public double latitude = -1000;
    public double longitude = -1000;
    private int maxAttributeIndex = -1;
    private Map<Integer, AttributeMeta> attributeMetaMap;
    private ListServiceConfiguration attributeConfiguration;
    private boolean valueIsSet;

    public ListServiceConfiguration getConfiguration() {
        return attributeConfiguration;
    }

    public ListItem() {
    }

    public ListItem(String value) {
        this.value = value;
        this.id = value;
    }

    public ListItem(ListServiceConfiguration configuration) {
        attributeConfiguration = configuration;
    }

    /**
     * Sets the list item attribute
     *
     * @param value The string list item attribute value
     * @param index The attribute to be set 1-10
     */
    public void setAttributeForIndex(@Nullable Object value, int index) {
        ListItemAttribute attribute = null;

        if (index > 79 || index < 0)
            Logger.warn("The index you specified (" + index + ") is beyond the allowed number of attributes ( 0 - 79 )");
        if (value != null) {
            if (value instanceof String) {
                attribute = new ListItemAttribute((String) value);
            } else if (value instanceof Color) {
                attribute = new ListItemAttribute((Color) value);
            } else if (value instanceof Integer) {
                attribute = new ListItemAttribute((Integer) value);
            } else if (value instanceof Location) {
                attribute = new ListItemAttribute((Location) value);
            } else if (value instanceof DateTime) {
                Logger.warn("setAttributeForIndex(Object value, int index) with a value type of DateTime assumes that the date contains a time component. Use setDateAttributeAtIndex(DateTime date, boolean time) to be more explicit.");
                attribute = new ListItemAttribute((DateTime) value, true);
            } else if (value instanceof DateRange) {
                attribute = new ListItemAttribute((DateRange) value);
            } else if (value instanceof DateTimeRange) {
                attribute = new ListItemAttribute((DateTimeRange) value);
            } else if (value instanceof Double) {
                attribute = new ListItemAttribute((Double) value);
            } else if (value instanceof Image) {
                attribute = new ListItemAttribute((Image) value);
            } else if (value instanceof Long) {
                attribute = new ListItemAttribute((Long) value);
            } else if (value instanceof Boolean) {
                attribute = new ListItemAttribute((Boolean) value);
            } else {
                throw new UnsupportedOperationException("List does not support a value of type " + value.getClass().getCanonicalName());
            }
        }
        itemAttributes.put(index, attribute);
        maxAttributeIndex = maxAttributeIndex > index ? maxAttributeIndex : index;
    }

    /**
     * Sets a date time at the indicate index
     *
     * @param date  the DateTime value
     * @param time  a boolean indicating if the time should be included
     * @param index the index of the attribute to be set
     */
    public void setDateAttributeForIndex(DateTime date, boolean time, int index) {
        if (index > 79 || index < 0)
            Logger.warn("The index you specified (" + index + ") is beyond the allowed number of attributes ( 0 - 79 )");
        ListItemAttribute attribute = null;
        if (date != null) {
            attribute = new ListItemAttribute(date, time);
        }
        itemAttributes.put(index, attribute);
        maxAttributeIndex = maxAttributeIndex > index ? maxAttributeIndex : index;
    }

    /**
     * Returns the attribute
     *
     * @param index The attribute to get 0-9
     * @return
     */

    @Deprecated
    public ListItemAttribute getAttributeForIndex(int index) {
        return itemAttributes.get(index);
    }

    @Deprecated
    public String getStringAttributeForIndex(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getStringValue();
        }
        return null;
    }

    @Deprecated
    public void setStringForAttributeIndex(String value, int index) {
        setAttributeForIndex(value, index);
    }

    @Deprecated
    public void setIntForAttributeIndex(int value, int index) {
        setAttributeForIndex(value, index);
    }

    @Deprecated
    public void setBoolForAttributeIndex(boolean value, int index) {
        setAttributeForIndex(value, index);
    }

    @Deprecated
    public void setDoubleForAttributeIndex(Double value, int index) {
        setAttributeForIndex(value, index);
    }


    public void setColorForAttributeIndex(Color color, int index) {
        setAttributeForIndex(color, index);
    }

    @Deprecated
    public Optional<String> getOptionalStringAttributeForIndex(int index) {
        return Optional.ofNullable(getStringAttributeForIndex(index));
    }

    @Deprecated
    public int getIntAttributeForIndex(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getIntValue();
        }
        return 0;
    }

    @Deprecated
    public Optional<Integer> getOptionalIntAttributeForIndex(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getIntValue());
        }
        return Optional.empty();
    }

    @Deprecated
    public double getDoubleAttributeForIndex(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getDoubleValue();
        }
        return 0;
    }

    @Deprecated
    public Optional<Double> getOptionalDoubleAttributeForIndex(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getDoubleValue());
        }
        return Optional.empty();
    }

    @Deprecated
    public boolean getBoolAttributeForIndex(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        return attribute != null && attribute.getBooleanValue();
    }

    @Deprecated
    public Optional<Boolean> getOptionalBoolAttributeForIndex(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getBooleanValue());
        }
        return Optional.empty();
    }

    @Deprecated
    public DateTime getDateTimeAttributeForIndex(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getDateValue();
        }
        return null;
    }

    @Deprecated
    public Optional<DateTime> getOptionalDateTimeAttributeForIndex(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.ofNullable(attribute.getDateValue());
        }
        return Optional.empty();

    }

    @Override
    public void setDate(DateTime value, int index) {
        if (index > 79 || index < 0)
            Logger.warn("The index you specified (" + index + ") is beyond the allowed number of attributes ( 0 - 79 )");
        ListItemAttribute attribute = null;
        if (value != null) {
            attribute = new ListItemAttribute(value, false);
        }
        itemAttributes.put(index, attribute);
        maxAttributeIndex = maxAttributeIndex > index ? maxAttributeIndex : index;
    }

    @Override
    public DateTime getDate(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getDateValue();
        }
        return null;
    }

    @Override
    public Optional<DateTime> getOptionalDate(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getDateValue());
        }
        return Optional.empty();
    }

    @Override
    public void setString(String value, int index) {
        setAttributeForIndex(value, index);
    }

    @Override
    public String getString(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getStringValue();
        }
        return null;
    }

    @Override
    public Optional<String> getOptionalString(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getStringValue());
        }
        return Optional.empty();
    }

    @Override
    public void setInt(int value, int index) {
        setAttributeForIndex(value, index);
    }

    @Override
    public int getInt(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getIntValue();
        }
        return 0;
    }

    @Override
    public Optional<Integer> getOptionalInt(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getIntValue());
        }
        return Optional.empty();
    }

    @Override
    public void setBool(boolean value, int index) {
        setAttributeForIndex(value, index);
    }

    @Override
    public boolean getBool(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getBooleanValue();
        }
        return false;
    }

    @Override
    public Optional<Boolean> getOptionalBoolean(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getBooleanValue());
        }
        return Optional.empty();
    }

    @Override
    public void setColor(Color value, int index) {
        setAttributeForIndex(value, index);
    }

    @Override
    public Color getColor(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getColorValue();
        }
        return null;
    }

    @Override
    public Optional<Color> getOptionalColor(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getColorValue());
        }
        return Optional.empty();
    }

    @Override
    public void setDouble(double value, int index) {
        setAttributeForIndex(value, index);
    }

    @Override
    public double getDouble(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getDoubleValue();
        }
        return 0.0;
    }

    @Override
    public Optional<Double> getOptionalDouble(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getDoubleValue());
        }
        return Optional.empty();
    }

    @Override
    public void setDateTime(DateTime value, int index) {
        if (index > 79 || index < 0)
            Logger.warn("The index you specified (" + index + ") is beyond the allowed number of attributes ( 0 - 79 )");
        ListItemAttribute attribute = null;
        if (value != null) {
            attribute = new ListItemAttribute(value, true);
        }
        itemAttributes.put(index, attribute);
        maxAttributeIndex = maxAttributeIndex > index ? maxAttributeIndex : index;
    }

    @Override
    public DateTime getDateTime(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getDateValue();
        }
        return null;
    }

    @Override
    public Optional<DateTime> getOptionalDateTime(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getDateValue());
        }
        return Optional.empty();
    }

    @Override
    public void setLocation(Location value, int index) {
        setAttributeForIndex(value, index);
    }

    @Override
    public Location getLocation(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getLocationValue();
        }
        return null;
    }

    @Override
    public Optional<Location> getOptionalLocation(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getLocationValue());
        }
        return Optional.empty();
    }

    @Override
    public void setImage(Image value, int index) {
        setAttributeForIndex(value, index);
    }

    @Override
    public Image getImage(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return attribute.getImageValue();
        }
        return null;
    }

    @Override
    public Optional<Image> getOptionalImage(int index) {
        ListItemAttribute attribute = itemAttributes.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getImageValue());
        }
        return Optional.empty();
    }

    @Override
    public void setListItem(ListItem value, int index) {
        throw new RuntimeException("Can not set list item on a list item");
    }

    @Override
    public ListItem getListItem(int index) {
        throw new RuntimeException("Can not get list item from a list item");
    }

    @Override
    public Optional<ListItem> getOptionalListItem(int index) {
        throw new RuntimeException("Can not get list item from a list item");
    }

    @Override
    public DataSetItem getDataSetItem(int index) {
        throw new RuntimeException("Unable to get Data Set Item from list item");
    }

    @Override
    public List<DataSetItem> getDataSetItems(int index) {
        throw new RuntimeException("Unable to get Data Set Item from list item");
    }

    @Override
    public DataSetItem addNewDataSetItem(int index) {
        throw new RuntimeException("Unable to get Data Set Item from Data Set Item");
    }

    @Override
    public DataSetItemAttachment addNewDataSetItemAttachment(int index) {
        throw new RuntimeException("Attachments are not supported for this type of record.");
    }

    @Override
    public List<DataSetItemAttachment> getAttachmentItemsForIndex(int index) {
        throw new RuntimeException("Attachments are not supported for this type of record.");
    }

    @Override
    public ServiceConfigurationAttribute getAttribute(int index) {
        throw new RuntimeException("Unable to get attribute index from list item");
    }

    @Override
    public AttributeMeta getAttributeMeta(int index) {
        if (getAttributeMetaMap().containsKey(index)) {
            return getAttributeMeta(index);
        }
        if (attributeConfiguration == null) return null;
        ServiceConfigurationAttribute attribute = null;
        for (ServiceConfigurationAttribute attr : attributeConfiguration.getAttributes()) {
            if (attr.getAttributeIndex() == index) attribute = attr;
        }
        if (attribute == null) return null;
        return new AttributeMeta(attribute.getAttributeType(), attribute.getAttributeIndex());
    }

    @Override
    public void setAttributeMeta(int index, AttributeMeta attributeMeta) {
        this.getAttributeMetaMap().put(index, attributeMeta);
    }

    @Override
    public void setPrimaryKey(String primaryKey) {
        this.id = primaryKey;
    }

    @Override
    public String getPrimaryKey() {
        return id;
    }

    @Override
    public void setValue(String value) {
        valueIsSet = true;
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isValueSet() {
        return valueIsSet;
    }

    @Override
    public DataSetItem.CRUDStatus getCRUDStatus() {
        throw new RuntimeException("This type of record does not support CRUD status");
    }

    @Override
    public boolean supportsCRUDStatus() {
        return false;
    }

    @Override
    public boolean isListItem() {
        return true;
    }

    @Override
    public void useLazyLoad(int index) {
        throw new RuntimeException("This type of record does not support lazy loading.");
    }

    @Override
    public void setParentValue(String value) {
        this.parentID = value;
    }

    public Map<Integer, AttributeMeta> getAttributeMetaMap() {
        if (attributeMetaMap == null) {
            attributeMetaMap = new HashMap<>();
        }
        return attributeMetaMap;
    }

    public void setAttributeMetaMap(Map<Integer, AttributeMeta> attributeMetaMap) {
        this.attributeMetaMap = attributeMetaMap;
    }


    static class ListItemSerializer extends JsonSerializer<ListItem> {

        @Override
        public void serialize(ListItem value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeStartObject();
            gen.writeStringField("id", value.id);
            gen.writeStringField("value", value.value);
            gen.writeStringField("parentID", value.parentID);
            if (value.latitude != -1000) {
                gen.writeNumberField("latitude", value.latitude);
            }
            if (value.longitude != -1000) {
                gen.writeNumberField("longitude", value.longitude);
            }
            for (int i = 0; i <= value.maxAttributeIndex; i++) {
                ListItemAttribute attribute = value.getAttributeForIndex(i);
                if (attribute != null) {
                    if (i < 9) {
                        gen.writeStringField("attribute0" + (i + 1), attribute.getStringValue());
                    } else {
                        gen.writeStringField("attribute" + (i + 1), attribute.getStringValue());
                    }
                }
            }
            gen.writeEndObject();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListItem listItem = (ListItem) o;

        if (Double.compare(listItem.latitude, latitude) != 0) return false;
        if (Double.compare(listItem.longitude, longitude) != 0) return false;
        if (maxAttributeIndex != listItem.maxAttributeIndex) return false;
        if (id != null ? !id.equals(listItem.id) : listItem.id != null) return false;
        if (parentID != null ? !parentID.equals(listItem.parentID) : listItem.parentID != null) return false;
        if (value != null ? !value.equals(listItem.value) : listItem.value != null) return false;
        return attributeConfiguration != null ? attributeConfiguration.equals(listItem.attributeConfiguration) : listItem.attributeConfiguration == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (parentID != null ? parentID.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (itemAttributes != null ? itemAttributes.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + maxAttributeIndex;
        result = 31 * result + (attributeConfiguration != null ? attributeConfiguration.hashCode() : 0);
        return result;
    }
}
