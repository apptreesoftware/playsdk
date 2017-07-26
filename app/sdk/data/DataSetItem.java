package sdk.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import play.libs.Json;
import play.mvc.Http;
import sdk.annotations.Attribute;
import sdk.list.ListItem;
import sdk.models.*;
import sdk.utils.DateUtil;
import sdk.utils.JsonUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by alexis on 5/3/16.
 */
public class DataSetItem implements Record {
    private HashMap<Integer, DataSetItemAttribute> attributeMap = new HashMap<Integer, DataSetItemAttribute>();
    String primaryKey;
    private String clientKey;
    private int maxAttributeIndex = -1;
    private CRUDStatus crudStatus = CRUDStatus.Read;
    private Status status = Status.None;
    Collection<ServiceConfigurationAttribute> configurationAttributes;
    Collection<Integer> lazyLoadedRelationships;
    private Map<Integer, AttributeMeta> attributeMetaMap;

    @JsonIgnore
    protected HashMap<Integer, ServiceConfigurationAttribute> configurationMap;

    @JsonIgnore
    public Collection<ServiceConfigurationAttribute> getConfigurationAttributes() {
        return configurationAttributes;
    }

    public ServiceConfigurationAttribute getAttributeWithName(String name) {
        if (this.configurationAttributes == null) return null;
        for (ServiceConfigurationAttribute attribute : this.configurationAttributes) {
            if (attribute.name.equals(name)) {
                return attribute;
            }
        }
        return null;
    }

    public ServiceConfigurationAttribute getAttributeWithIndex(int index) {
        if (configurationMap == null) return null;
        return configurationMap.get(index);
    }

    public void useLazyLoad(int attributeIndex) {
        ServiceConfigurationAttribute attribute = getAttributeWithIndex(attributeIndex);
        if (attribute == null || (!attribute.attributeType.equals(AttributeType.Relation) && !attribute.attributeType.equals(AttributeType.Attachments))) {
            throw new RuntimeException("Attempting to use lazy loading on an attribute that isn't a relationship");
        }
        if (lazyLoadedRelationships == null) {
            lazyLoadedRelationships = new ArrayList<>();
        }
        lazyLoadedRelationships.add(attributeIndex);
    }

    @Override
    public void setDate(DateTime value, int index) {
        _setDateForAttributeIndex(value, index);
    }

    @Override
    public DateTime getDate(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return attribute.getDateValue();
        }
        return null;
    }

    @Override
    public Optional<DateTime> getOptionalDate(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getDateValue());
        }
        return Optional.empty();
    }

    @Override
    public void setString(String value, int index) {
        _setStringForAttributeIndex(value, index);
    }

    @Override
    public String getString(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return attribute.getStringValue();
        }
        return null;
    }

    @Override
    public Optional<String> getOptionalString(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getStringValue());
        }
        return Optional.empty();
    }

    @Override
    public void setInt(int value, int index) {
        _setIntForAttributeIndex(value, index);
    }

    @Override
    public int getInt(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return attribute.getIntValue();
        }
        return 0;
    }

    @Override
    public Optional<Integer> getOptionalInt(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getIntValue());
        }
        return Optional.empty();
    }

    @Override
    public void setBool(boolean value, int index) {
        setBooleanForAttributeIndex(value, index);
    }

    @Override
    public boolean getBool(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return attribute.getBooleanValue();
        }
        return false;
    }

    @Override
    public Optional<Boolean> getOptionalBoolean(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getBooleanValue());
        }
        return Optional.empty();
    }

    @Override
    public void setColor(Color value, int index) {
        setColorForAttributeIndex(value, index);
    }

    @Override
    public Color getColor(int index) {
        return null;
    }

    @Override
    public Optional<Color> getOptionalColor(int index) {
        return null;
    }

    @Override
    public void setDouble(double value, int index) {
        setDoubleForAttributeIndex(value, index);
    }

    @Override
    public double getDouble(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return attribute.getDoubleValue();
        }
        return 0.0;
    }

    @Override
    public Optional<Double> getOptionalDouble(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getDoubleValue());
        }
        return Optional.empty();
    }

    @Override
    public void setDateTime(DateTime value, int index) {
        _setDateTimeForAttributeIndex(value, index);
    }

    @Override
    public DateTime getDateTime(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return attribute.getDateValue();
        }
        return null;
    }

    @Override
    public Optional<DateTime> getOptionalDateTime(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getDateValue());
        }
        return Optional.empty();
    }

    @Override
    public void setLocation(Location value, int index) {
        _setLocationForAttributeIndex(value, index);
    }

    @Override
    public Location getLocation(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return attribute.getLocationValue();
        }
        return null;
    }

    @Override
    public Optional<Location> getOptionalLocation(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getLocationValue());
        }
        return Optional.empty();
    }

    @Override
    public void setImage(Image value, int index) {
        _setImageForAttributeIndex(value, index);
    }

    @Override
    public Image getImage(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return attribute.getImageValue();
        }
        return null;
    }

    @Override
    public Optional<Image> getOptionalImage(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getImageValue());
        }
        return Optional.empty();
    }

    @Override
    public void setListItem(ListItem value, int index) {
        _setListItemForAttributeIndex(value, index);
    }

    @Override
    public ListItem getListItem(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return attribute.getListItem();
        }
        return null;
    }

    @Override
    public Optional<ListItem> getOptionalListItem(int index) {
        DataSetItemAttribute attribute = attributeMap.get(index);
        if (attribute != null) {
            return Optional.of(attribute.getListItem());
        }
        return Optional.empty();
    }

    @Override
    public DataSetItem getDataSetItem(int index) {
        return getDataSetItemAtIndex(index);
    }

    @Override
    public List<DataSetItem> getDataSetItems(int index) {
        return getDataSetItemsAtIndex(index);
    }

    @Override
    public DataSetItem addNewDataSetItem(int index) {
        return addNewDataSetItemForAttributeIndex(index);
    }

    @Override
    public ServiceConfigurationAttribute getAttribute(int index) {
        if (configurationMap == null) return null;
        return configurationMap.get(index);
    }

    @Override
    public AttributeMeta getAttributeMeta(int index) {
        if(configurationMap == null) return null;
        ServiceConfigurationAttribute serviceConfigurationAttribute = configurationMap.get(index);
        return new AttributeMeta(serviceConfigurationAttribute.getAttributeType(), serviceConfigurationAttribute.getAttributeIndex());
    }

    @Override
    public void setAttributeMeta(int index, AttributeMeta attributeMeta) {
        this.getAttributeMetaMap().put(index, attributeMeta);
    }

    public Map<Integer, AttributeMeta> getAttributeMetaMap() {
        if(attributeMap == null) {
            attributeMap = new HashMap<>();
        }
        return attributeMetaMap;
    }

    public void setAttributeMetaMap(Map<Integer, AttributeMeta> attributeMetaMap) {
        this.attributeMetaMap = attributeMetaMap;
    }

    public enum Type {
        Record("RECORD"),
        Attachment("ATTACHMENT");

        private final String stringValue;

        /**
         * The type of data set item
         *
         * @param text The name of the data set type, either RECORD or ATTACHMENT
         */
        private Type(final String text) {
            this.stringValue = text;
        }

        /**
         * Gets the type from the name
         *
         * @param typeString The name of the type
         * @return
         */
        public static Type fromString(String typeString) {
            typeString = typeString.toUpperCase();
            if (typeString.equals(Record.stringValue)) {
                return Record;
            } else if (typeString.equals(Attachment.stringValue)) {
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
         *
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
         *
         * @param crudString The CRUD type string
         * @return
         */
        public static CRUDStatus fromString(String crudString) {
            crudString = crudString.toUpperCase();
            if (crudString.equals(Create.toString())) {
                return Create;
            } else if (crudString.equals(Update.toString())) {
                return Update;
            } else if (crudString.equals(Delete.toString())) {
                return Delete;
            } else if (crudString.equals(Read.toString())) {
                return Read;
            }
            return None;
        }
    }

    public enum Status {
        None("NONE"),
        Complete("COMPLETE"),
        Exception("EXCEPTION");

        private final String stringValue;

        private Status(final String text) {
            this.stringValue = text;
        }

        @Override
        public String toString() {
            return stringValue;
        }

        /**
         * Assessment status from name
         *
         * @param string The name of the assessment status
         * @return
         */
        public static Status fromString(String string) {
            if (string == null) {
                return None;
            }
            string = string.toUpperCase();
            if (string.equalsIgnoreCase(None.toString())) {
                return None;
            } else if (string.equalsIgnoreCase(Complete.toString())) {
                return Complete;
            } else if (string.equalsIgnoreCase(Exception.toString())) {
                return Exception;
            }
            return None;
        }
    }

    /***
     * Creates an DataSetItem using the configuration attributes provided. The service attributes passed in will be used to validate what data is being set on this DataSetItem.
     * @param attributeConfigurationMap The service configuration attributes to use for validation. This should come from your DataSource getViewUpdateServiceAttributes, getCreateServiceAttributes, or getSearchServiceAttributes depending on what this DataSetItem is being used for.
     */
    DataSetItem(HashMap<Integer, ServiceConfigurationAttribute> attributeConfigurationMap) {
        this.configurationAttributes = attributeConfigurationMap.values();
        this.configurationMap = attributeConfigurationMap;
    }

    public DataSetItem(Collection<ServiceConfigurationAttribute> attributes) {
        this.configurationAttributes = attributes;
        this.configurationMap = new HashMap<>();
        if (attributes != null) {
            for (ServiceConfigurationAttribute attribute : attributes) {
                configurationMap.put(attribute.getAttributeIndex(), attribute);
            }
        }
    }

    void validateAttributes() {
        for (Map.Entry<Integer, DataSetItemAttribute> attribute : attributeMap.entrySet()) {
            validateGetterAttributeTypeForIndex(attribute.getValue().getAttributeType(), attribute.getKey());
        }
    }

    /**
     * Sets the primary key
     *
     * @param primaryKey The String primary key
     */
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Gets the primary key
     *
     * @return Returns the primary key
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Gets the item type
     *
     * @return
     */
    public Type getItemType() {
        return Type.Record;
    }

    /**
     * Sets the CRUD status
     *
     * @param status The status of the data set item
     */
    public void setCRUDStatus(CRUDStatus status) {
        crudStatus = status;
    }

    /**
     * Gets the CRUD status
     *
     * @return
     */
    public CRUDStatus getCRUDStatus() {
        return crudStatus;
    }

    /**
     * Gets the client key
     *
     * @return
     */
    public String getClientKey() {
        return clientKey;
    }

    /**
     * Sets the client key
     *
     * @param clientKey
     */
    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    /**
     * Gets the assessment status
     *
     * @return
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the assessment status
     *
     * @param status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns a color at the specified index of the attribute map
     *
     * @param attributeIndex The index of the attribute to get
     * @return Color
     */
    @Deprecated
    public Color getColorAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getColorValue();
        }
        return null;
    }

    /**
     * Gets an int at the specified index of the attribute map
     *
     * @param attributeIndex The index of the attribute to get
     * @return int
     */
    @Deprecated
    public int getIntAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            try {
                return attribute.getIntValue();
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return 0;
    }

    @Deprecated
    public Optional<Integer> getOptionalIntAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;

        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            try {
                return Optional.ofNullable(attribute.getIntValue());
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Gets a double at the specified index of the attribute map
     *
     * @param attributeIndex The index of the attribute
     * @return double
     */
    @Deprecated
    public double getDoubleAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;

        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            try {
                return attribute.getDoubleValue();
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    @Deprecated
    public Optional<Double> getOptionalDoubleAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;

        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            try {
                return Optional.ofNullable(attribute.getDoubleValue());
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public long getTimeIntervalAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getLongValue();
        }
        return 0;
    }

    /**
     * Gets a list item at the specified index of the attribute map
     *
     * @param attributeIndex The index of the attribute to get
     * @return list item
     */
    @Deprecated
    public ListItem getListItemAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;

        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getListItem();
        }
        return null;
    }

    @Deprecated
    public Optional<ListItem> getOptionalListItemAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null && attribute.getListItem() != null) {
            return java.util.Optional.of(attribute.getListItem());
        }
        return java.util.Optional.empty();
    }

    /**
     * Gets a string attriubte at the specified index of the attribute map
     *
     * @param attributeIndex The index to get the attribute at
     * @return string
     */
    @Deprecated
    public String getStringAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getStringValue();
        }
        return null;
    }

    @Deprecated
    public Optional<String> getOptionalStringAttributeAtIndex(int attributeIndex) {
        return Optional.ofNullable(getStringAttributeAtIndex(attributeIndex));
    }

    /**
     * Gets a date attribute at the specified index of the attribute map
     *
     * @param attributeIndex The index to get the attribute at
     * @return date
     */
    @Deprecated
    public DateTime getDateAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getDateValue();
        }
        return null;
    }

    @Deprecated
    public Optional<DateTime> getOptionalDateAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return Optional.of(attribute.getDateValue());
        }
        return Optional.empty();
    }

    /**
     * Gets a date time attribute at the specified index of the attribute map
     *
     * @param attributeIndex The index to get the attribute at
     * @return date time
     */
    @Deprecated
    public DateTime getDateTimeAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getDateValue();
        }
        return null;
    }

    @Deprecated
    public Optional<DateTime> getOptionalDateTimeAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return Optional.of(attribute.getDateValue());
        }
        return Optional.empty();
    }


    /**
     * Get a date range at the specified index of the attribute map
     *
     * @param attributeIndex The index to get the attribute at
     * @return date range
     */
    @Nullable
    public DateRange getDateRangeAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getDateRange();
        }
        return null;
    }

    public Optional<DateRange> getOptionalDateRangeAttributeAtIndex(int attributeIndex) {
        return Optional.ofNullable(getDateRangeAttributeAtIndex(attributeIndex));
    }

    /**
     * Gets a date time range at the specified index of the attribute map
     *
     * @param attributeIndex The index to get the attribute at
     * @return date time range
     */
    @Nullable
    public DateTimeRange getDateTimeRangeAttributeAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getDateTimeRange();
        }
        return null;
    }

    public Optional<DateTimeRange> getOptionalDateTimeRangeAttributeAtIndex(int attributeIndex) {
        return Optional.ofNullable(getDateTimeRangeAttributeAtIndex(attributeIndex));
    }

    /**
     * Gets a boolean attribute at the specified index of the attribute map
     *
     * @param attributeIndex The index to get the attribute at
     * @return boolean
     */
    @Deprecated
    public boolean getBoolValueAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getBooleanValue();
        } else {
            return false;
        }
    }

    @Deprecated
    public Optional<Boolean> getOptionalBooleanAttributeAtIndex(int attributeIndex) {
        return Optional.ofNullable(getBooleanValueAtIndex(attributeIndex));
    }


    public @Nullable
    Boolean getBooleanValueAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getBooleanValue();
        } else {
            return null;
        }
    }

    /**
     * Gets the list of data set item attribute at the specified index of the attribute map
     *
     * @param attributeIndex The index to get the attribute at
     * @return list of data set items
     */
    @Nullable
    public List<DataSetItem> getDataSetItemsAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;

        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getDataSetItems();
        }
        return null;
    }

    /**
     * Gets a data set item attribute at the specified index of the attribute map
     *
     * @param attributeIndex The index to get the attribute at
     * @return data set item
     */
    @Nullable
    public DataSetItem getDataSetItemAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        List<DataSetItem> dataSetItems;

        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            dataSetItems = attribute.getDataSetItems();
            if (dataSetItems != null && dataSetItems.size() == 1) {
                return dataSetItems.get(0);
            }
        }
        return null;
    }

    @Deprecated
    public Location getLocationAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getLocationValue();
        }
        return null;
    }

    @Deprecated
    public Optional<Location> getOptionalLocationAtIndex(int attributeIndex) {
        return Optional.ofNullable(getLocationAtIndex(attributeIndex));
    }

    @Deprecated
    public Image getImageAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            return attribute.getImageValue();
        }
        return null;
    }

    @Deprecated
    public Optional<Image> getOptionalImageAtIndex(int attributeIndex) {
        return Optional.ofNullable(getImageAtIndex(attributeIndex));
    }

    public List<DataSetItemAttachment> getAttachmentItemsAtIndex(int attributeIndex) {
        DataSetItemAttribute attribute;
        ArrayList<DataSetItemAttachment> attachmentItems = new ArrayList<DataSetItemAttachment>();

        attribute = attributeMap.get(attributeIndex);
        if (attribute != null) {
            List<DataSetItem> items = attribute.getDataSetItems();
            if (items != null) {
                for (DataSetItem item : items) {
                    if (item instanceof DataSetItemAttachment) {
                        DataSetItemAttachment attachmentItem = (DataSetItemAttachment) item;
                        attachmentItems.add(attachmentItem);
                    }
                }
                return attachmentItems;
            }
        }
        return null;
    }


    private boolean validateGetterAttributeTypeForIndex(AttributeType attributeType, int attributeIndex) throws InvalidAttributeValueException {
        ServiceConfigurationAttribute attributeConfig = configurationMap.get(attributeIndex);
        if (attributeConfig == null) {
            System.out.println("Warning: This data set is not configured for attribute " + attributeIndex + ". Setting or Getting the value at this index will always produce null");
            return false;
        }
        AttributeType configuredAttributeType = attributeConfig.attributeType;

        if (configuredAttributeType == attributeType) {
            return true;
        }
        String methodName = setterNameForAttributeType(configuredAttributeType);
        if (methodName != null) {
            throw new InvalidAttributeValueException("Attempting to set a " + attributeType.name() + " on a field configured as " + configuredAttributeType.name() + ". Consider using the method " + methodName + " instead. Attribute Index: " + attributeIndex);
        } else
            throw new InvalidAttributeValueException("Attempting to set a " + attributeType.name() + " on a field configured as " + configuredAttributeType.name() + ". No method is specified for type " + configuredAttributeType.name() + " instead.  Attribute Index:" + attributeIndex);
    }

    private String setterNameForAttributeType(AttributeType type) {
        switch (type) {
            case String:
                return "public void setStringForAttributeIndex(String value, int attributeIndex)";
            case ListItem:
                return "public void setListItemForAttributeIndex(ListItem value, int attributeIndex)";
            case Color:
                return "public void setColorForAttributeIndex(ATColor value, int attributeIndex)";
            case Image:
                return "public void setImageForAttributeIndex(Image value, int attributeIndex)";
            case Location:
                return "public void setLocationForAttibuteIndex(Location location, int attributeIndex)";
            case Double:
                return "public void setDoubleForAttributeIndex(double value, int attributeIndex)";
            case Int:
                return "public void setIntForAttributeIndex(int value, int attributeIndex)";
            case TimeInterval:
                return "public void setTimeIntervalForAttributeIndex(long value, int attributeIndex)";
            case Relation:
                return "setDataSetItemsForAttributeIndex(DataSetItem value, int attributeIndex) or setDataSetItemForAttributeIndex(List<DataSetItem> value, int attributeIndex)";
            case Date:
                return "public void setDateForAttributeIndex(DateTime date, int attributeIndex)";
            case DateTime:
                return "public void setDateTimeForAttributeIndex(DateTime date, int attributeIndex)";
            case Boolean:
                return "public void setBooleanForAttributeIndex(boolean value, int attributeIndex)";
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
                return "public List<DataSetItem> getDataSetItemsAtIndex(int attributeIndex) or public DataSetItem getDataSetItemAtIndex(int attributeIndex)";
            case Date:
                return "public DateTime getDateAttributeAtIndex(int attributeIndex)";
            case DateTime:
                return "public DateTime getDateTimeAttributeAtIndex(int attributeIndex)";
            case TimeInterval:
                return "public TimeInterval getTimeIntervalAttributeAtIndex(int attributeIndex)";
        }
        return null;
    }

    /**
     * Sets a color value at the specified index of the attribute map
     *
     * @param value          The color to set
     * @param attributeIndex The index of the attribute
     */
    @Deprecated
    public void setColorForAttributeIndex(Color value, int attributeIndex) throws InvalidAttributeValueException {
        _setColorForAttributeIndex(value, attributeIndex);
    }

    @Deprecated
    private void _setColorForAttributeIndex(Color value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Color, attributeIndex)) {
            return;
        }
        attributeMap.put(attributeIndex, new DataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets an int value at the specified index of the attribute map
     *
     * @param value          The int to set
     * @param attributeIndex The index of the attribute
     */
    @Deprecated
    public void setIntForAttributeIndex(int value, int attributeIndex) throws InvalidAttributeValueException {
        _setIntForAttributeIndex(value, attributeIndex);
    }

    private void _setIntForAttributeIndex(int value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Int, attributeIndex)) {
            return;
        }
        attributeMap.put(attributeIndex, new DataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a list item value at the specified index of the attribute map
     *
     * @param listItem       The list item to set
     * @param attributeIndex The index of the attribute
     */
    @Deprecated
    public void setListItemForAttributeIndex(ListItem listItem, int attributeIndex) throws InvalidAttributeValueException {
        _setListItemForAttributeIndex(listItem, attributeIndex);
    }

    private void _setListItemForAttributeIndex(ListItem listItem, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.ListItem, attributeIndex)) {
            return;
        }
        attributeMap.put(attributeIndex, new DataSetItemAttribute(listItem));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a location value at the specified index of the attribute map
     *
     * @param location       the location to set
     * @param attributeIndex The index of the attribute
     */
    @Deprecated
    public void setLocationForAttributeIndex(Location location, int attributeIndex) throws InvalidAttributeValueException {
        _setLocationForAttributeIndex(location, attributeIndex);
    }

    private void _setLocationForAttributeIndex(Location location, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Location, attributeIndex)) {
            return;
        }
        attributeMap.put(attributeIndex, new DataSetItemAttribute(location));
        updateMaxAttribute(attributeIndex);
    }

    @Deprecated
    public void setImageForAttributeIndex(Image image, int attributeIndex) throws InvalidAttributeValueException {
        _setImageForAttributeIndex(image, attributeIndex);
    }

    private void _setImageForAttributeIndex(Image image, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Image, attributeIndex)) {
            return;
        }
        attributeMap.put(attributeIndex, new DataSetItemAttribute(image));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a string value at the specified index of the attribute map
     *
     * @param value          The string to set
     * @param attributeIndex The index of the attribute
     */
    @Deprecated
    public void setStringForAttributeIndex(String value, int attributeIndex) throws InvalidAttributeValueException {
        _setStringForAttributeIndex(value, attributeIndex);
    }

    private void _setStringForAttributeIndex(String value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.String, attributeIndex)) {
            return;
        }
        attributeMap.put(attributeIndex, new DataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a boolean value at the specified index of the attribute map
     *
     * @param value          The boolean to set
     * @param attributeIndex The index of the attribute
     */
    @Deprecated
    public void setBooleanForAttributeIndex(boolean value, int attributeIndex) throws InvalidAttributeValueException {
        _setBooleanForAttributeIndex(value, attributeIndex);
    }

    private void _setBooleanForAttributeIndex(boolean value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Boolean, attributeIndex)) {
            return;
        }
        attributeMap.put(attributeIndex, new DataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }


    /**
     * Sets a date range value at the specified index of the attribute map
     *
     * @param dateRange      The date range to set
     * @param attributeIndex The index of the attribute
     * @throws InvalidAttributeValueException
     */
    public void setDateRangeForAttributeIndex(DateRange dateRange, int attributeIndex) throws InvalidAttributeValueException {
        _setDateRangeForAttributeIndex(dateRange, attributeIndex);
    }

    private void _setDateRangeForAttributeIndex(DateRange dateRange, int attributeIndex) throws InvalidAttributeValueException {
        attributeMap.put(attributeIndex, new DataSetItemAttribute(dateRange));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a date time range at the specified index of the attribute map
     *
     * @param dateTimeRange  The date time range to set
     * @param attributeIndex The index of the attribute
     * @throws InvalidAttributeValueException
     */

    public void setDateTimeRangeForAttributeIndex(DateTimeRange dateTimeRange, int attributeIndex) throws InvalidAttributeValueException {
        _setDateTimeRangeForAttributeIndex(dateTimeRange, attributeIndex);
    }

    private void _setDateTimeRangeForAttributeIndex(DateTimeRange dateTimeRange, int attributeIndex) throws InvalidAttributeValueException {
        attributeMap.put(attributeIndex, new DataSetItemAttribute(dateTimeRange));
        updateMaxAttribute(attributeIndex);
    }

    /**
     * Sets a double value at the specified index of the attribute map
     *
     * @param value          The cdouble to set
     * @param attributeIndex The index of the attribute
     */
    @Deprecated
    public void setDoubleForAttributeIndex(double value, int attributeIndex) throws InvalidAttributeValueException {
        _setDoubleForAttributeIndex(value, attributeIndex);
    }

    private void _setDoubleForAttributeIndex(double value, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Double, attributeIndex)) {
            return;
        }
        attributeMap.put(attributeIndex, new DataSetItemAttribute(value));
        updateMaxAttribute(attributeIndex);
    }

    public void setTimeIntervalForAttributeIndex(long timeInterval, int attributeIndex) throws InvalidAttributeValueException {
        _setTimeIntervalForAttributeIndex(timeInterval, attributeIndex);
    }

    private void _setTimeIntervalForAttributeIndex(long timeInterval, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.TimeInterval, attributeIndex)) {
            return;
        }
        attributeMap.put(attributeIndex, new DataSetItemAttribute(timeInterval));
    }

    /**
     * Sets a date value at the specified index of the attribute map
     *
     * @param date           The date to set
     * @param attributeIndex The index of the attribute
     */
    @Deprecated
    public void setDateForAttributeIndex(DateTime date, int attributeIndex) throws InvalidAttributeValueException {
        _setDateForAttributeIndex(date, attributeIndex);
    }

    private void _setDateForAttributeIndex(DateTime date, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.Date, attributeIndex)) {
            return;
        }
        if (date != null) {
            attributeMap.put(attributeIndex, new DataSetItemAttribute(date, false));
        }
    }

    /**
     * Sets a date time value at the specified index of the attribute map
     *
     * @param date           The date time to set
     * @param attributeIndex The index of the attribute
     */
    @Deprecated
    public void setDateTimeForAttributeIndex(DateTime date, int attributeIndex) throws InvalidAttributeValueException {
        _setDateTimeForAttributeIndex(date, attributeIndex);
    }

    private void _setDateTimeForAttributeIndex(DateTime date, int attributeIndex) throws InvalidAttributeValueException {
        if (!validateGetterAttributeTypeForIndex(AttributeType.DateTime, attributeIndex)) {
            return;
        }
        if (date != null) {
            attributeMap.put(attributeIndex, new DataSetItemAttribute(date, true));
        }
    }

    private void updateMaxAttribute(int attributeIndex) {
        maxAttributeIndex = attributeIndex > maxAttributeIndex ? attributeIndex : maxAttributeIndex;
    }

    public ObjectNode toJSON() {
        try {
            return _toJSON(false);
        } catch (InvalidPrimaryKeyException exception) {
            System.out.println("Caught invalid primary key exception when primaryKeyRequired was false. THIS SHOULD NOT HAPPEN");
        }
        return null;
    }

    /**
     * Converts the data set item to a json object the primary key must be set
     *
     * @return json object of the data set item
     * @throws InvalidPrimaryKeyException
     */
    public ObjectNode toJSONWithPrimaryKey() throws InvalidPrimaryKeyException {
        if (primaryKey == null) {
            throw new InvalidPrimaryKeyException("Primary key can not be null for record: " + toJSON());
        }
        return _toJSON(true);
    }

    /**
     * Converts the data set item to a json object
     *
     * @param primaryKeyRequired A boolean indicating whether or not the primary key of the data set item must be set
     * @return
     * @throws InvalidPrimaryKeyException
     */
    private ObjectNode _toJSON(boolean primaryKeyRequired) throws InvalidPrimaryKeyException {

        ObjectNode json = Json.newObject();
        json.put("primaryKey", primaryKey);
        json.put("CRUDStatus", crudStatus.stringValue);
        json.put("clientKey", clientKey);
        json.put("recordType", getItemType().stringValue);
        json.put("status", status.stringValue);
        json.put("lazyLoadedRelationships", lazyLoadedRelationships != null ? JsonUtils.toJson(lazyLoadedRelationships) : null);

        ArrayNode attributes = json.putArray("attributes");
        int firstNullIndex = -1;
        for (int i = 0; i <= 80; i++) {
            DataSetItemAttribute attribute = attributeMap.get(i);
            if (attribute == null) {
                attributes.addNull();
                if (firstNullIndex == -1) {
                    firstNullIndex = i;
                }
                continue;
            }
            Object value = attribute.getJSONValue(primaryKeyRequired);
            if (value == null) {
                attributes.addNull();
                if (firstNullIndex == -1) {
                    firstNullIndex = i;
                }
            } else if (value instanceof ArrayNode) {
                ArrayNode arrayNode = attributes.insertArray(i);
                arrayNode.addAll((ArrayNode) value);
                firstNullIndex = -1;
            } else if (value instanceof String) {
                attributes.add((String) value);
                firstNullIndex = -1;
            }
        }
        for (int i = 80; i >= firstNullIndex; i--) {
            attributes.remove(i);
        }
        return json;
    }

    public void updateFromJSON(ObjectNode json, HashMap<String, Http.MultipartFormData.FilePart> attachmentMap, boolean search) {
        primaryKey = json.path("primaryKey").textValue();
        crudStatus = json.has("CRUDStatus") ? CRUDStatus.fromString(json.path("CRUDStatus").textValue()) : CRUDStatus.Read;
        status = Status.fromString(json.path("status").textValue());
        clientKey = json.path("clientKey").textValue();
        ArrayNode attributes = (ArrayNode) json.path("attributes");
        IntStream.range(0, attributes.size())
                .filter(i -> !attributes.get(i).isNull() && configurationMap.get(i) != null)
                .forEach(i -> {
                    ServiceConfigurationAttribute attribute = configurationMap.get(i);
                    JsonNode node = attributes.get(i);
                    switch (attribute.attributeType) {
                        case String:
                            _setStringForAttributeIndex(node.textValue(), i);
                            break;
                        case Double:
                            if (node.isTextual()) {
                                try {
                                    double doubleValue = Double.parseDouble(node.asText());
                                    _setDoubleForAttributeIndex(doubleValue, i);
                                } catch (Exception ignored) {
                                }
                            } else if (node.isDouble()) {
                                _setDoubleForAttributeIndex(node.doubleValue(), i);
                            }
                            break;
                        case Int:
                            if (node.isTextual()) {
                                try {
                                    int intValue = Integer.parseInt(node.asText());
                                    _setIntForAttributeIndex(intValue, i);
                                } catch (Exception ignored) {
                                }
                            } else if (node.isInt()) {
                                _setIntForAttributeIndex(node.intValue(), i);
                            }
                            break;
                        case Boolean:
                            _setBooleanForAttributeIndex(node.asText().equalsIgnoreCase("Y"), i);
                            break;
                        case Date:
                            if (search) {
                                JsonUtils.parseOptional(node.textValue()).ifPresent(jsonNode -> {
                                    DateRange dateRange = JsonUtils.fromJson(jsonNode, DateRange.class);
                                    if (dateRange != null) {
                                        _setDateRangeForAttributeIndex(dateRange, i);
                                    }
                                });
                            } else {
                                _setDateForAttributeIndex(DateUtil.dateFromString(node.asText()), i);
                            }
                            break;
                        case DateTime:
                            if (search) {
                                JsonUtils.parseOptional(node.textValue()).ifPresent(jsonNode -> {
                                    DateTimeRange dateRange = JsonUtils.fromJson(jsonNode, DateTimeRange.class);
                                    if (dateRange != null) {
                                        _setDateTimeRangeForAttributeIndex(dateRange, i);
                                    }
                                });
                            } else {
                                _setDateTimeForAttributeIndex(DateUtil.dateTimeFromString(node.asText()), i);
                            }
                            break;
                        case TimeInterval:
                            _setTimeIntervalForAttributeIndex(node.asLong(), i);
                            break;
                        case DateRange:
                            JsonUtils.parseOptional(node.textValue()).ifPresent(jsonNode -> {
                                DateRange dateRange = JsonUtils.fromJson(jsonNode, DateRange.class);
                                if (dateRange != null) {
                                    _setDateRangeForAttributeIndex(dateRange, i);
                                }
                            });
                            break;
                        case DateTimeRange:
                            JsonUtils.parseOptional(node.textValue()).ifPresent(jsonNode -> {
                                DateTimeRange dateRange = JsonUtils.fromJson(jsonNode, DateTimeRange.class);
                                if (dateRange != null) {
                                    _setDateTimeRangeForAttributeIndex(dateRange, i);
                                }
                            });
                            break;
                        case Image:
                            JsonUtils.parseOptional(node.textValue()).ifPresent(jsonNode -> {
                                Image image = JsonUtils.fromJson(jsonNode, Image.class);
                                _setImageForAttributeIndex(image, i);
                                if (attachmentMap != null && image.uploadKey != null) {
                                    Http.MultipartFormData.FilePart filePart = attachmentMap.get(image.uploadKey);
                                    if (filePart != null) {
                                        image.filePart = filePart;
                                    }
                                }
                            });
                            break;
                        case Location:
                            JsonUtils.parseOptional(node.textValue()).ifPresent(jsonNode -> {
                                Location location = JsonUtils.fromJson(jsonNode, Location.class);
                                _setLocationForAttributeIndex(location, i);
                            });
                            break;
                        case Attachments:
                        case Relation:
                        case SingleRelationship:
                            ArrayNode childArray = (ArrayNode) node;
                            RelatedServiceConfiguration childService = attribute.relatedService;
                            if (childArray != null) {
                                IntStream.range(0, childArray.size())
                                        .forEach(childIndex -> {
                                            ObjectNode childJsonNode = (ObjectNode) childArray.get(childIndex);
                                            String recordType = childJsonNode.path("recordType").asText();
                                            String subClientKey = childJsonNode.path("clientKey").asText();
                                            Http.MultipartFormData.FilePart filePart = null;
                                            if (Type.fromString(recordType) == Type.Attachment) {
                                                if (subClientKey != null && attachmentMap != null) {
                                                    filePart = attachmentMap.get(subClientKey);
                                                }
                                                DataSetItem subDataSetItem = _addNewAttachmentForAttributeIndex(i);
                                                subDataSetItem.updateFromJSON(childJsonNode, null, search);
                                                if (filePart != null) {
                                                    ((DataSetItemAttachment) subDataSetItem).attachmentFileItem = filePart;
                                                }
                                            } else {
                                                DataSetItem subDataSetItem = _addNewDataSetItemForAttributeIndex(i);
                                                subDataSetItem.updateFromJSON(childJsonNode, attachmentMap, search);
                                            }
                                        });
                            }
                            break;
                        case ListItem:
                            JsonUtils.parseOptional(node.textValue()).ifPresent(jsonNode -> {
                                ListItem listItem = JsonUtils.fromJson(jsonNode, ListItem.class);
                                _setListItemForAttributeIndex(listItem, i);
                            });
                            break;
                        case Color:
                            JsonUtils.parseOptional(node.textValue()).ifPresent(jsonNode -> {
                                Color color = JsonUtils.fromJson(jsonNode, Color.class);
                                _setColorForAttributeIndex(color, i);
                            });
                            break;
                    }
                });

    }

    public DataSetItem addNewDataSetItemForAttributeIndex(int attributeIndex) {
        return _addNewDataSetItemForAttributeIndex(attributeIndex);
    }

    private DataSetItem _addNewDataSetItemForAttributeIndex(int attributeIndex) {
        DataSetItemAttribute attribute = attributeMap.get(attributeIndex);
        ServiceConfigurationAttribute configurationAttribute = configurationMap.get(attributeIndex);
        if (configurationAttribute == null || configurationAttribute.relatedService == null) {
            throw new InvalidAttributeValueException("You have configured attribute " + attributeIndex + " as a relationship but you have not defined a related service. Please update your configuration to include a related service for this attribute.");
        }

        DataSetItem dataSetItem = new DataSetItem(configurationAttribute.relatedService.getAttributeConfigurationForIndexMap());
        if (attribute == null || configurationAttribute.attributeType == AttributeType.SingleRelationship) {
            attribute = new DataSetItemAttribute(dataSetItem);
            attributeMap.put(attributeIndex, attribute);
        } else {
            attribute.addDataSetItem(dataSetItem);
        }
        return dataSetItem;
    }

    public DataSetItemAttachment addNewAttachmentForAttributeIndex(int attributeIndex) throws InvalidAttributeValueException {
        return _addNewAttachmentForAttributeIndex(attributeIndex);
    }

    private DataSetItemAttachment _addNewAttachmentForAttributeIndex(int attributeIndex) throws InvalidAttributeValueException {
        validateGetterAttributeTypeForIndex(AttributeType.Attachments, attributeIndex);
        DataSetItemAttachment attachmentItem = new DataSetItemAttachment();
        DataSetItemAttribute attribute = attributeMap.get(attributeIndex);

        if (attribute == null) {
            attribute = new DataSetItemAttribute(attachmentItem);
            attributeMap.put(attributeIndex, attribute);
        } else {
            attribute.addDataSetItem(attachmentItem);
        }
        return attachmentItem;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSetItem that = (DataSetItem) o;

        if (maxAttributeIndex != that.maxAttributeIndex) return false;
        if (primaryKey != null ? !primaryKey.equals(that.primaryKey) : that.primaryKey != null) return false;
        if (clientKey != null ? !clientKey.equals(that.clientKey) : that.clientKey != null) return false;
        if (crudStatus != that.crudStatus) return false;
        if (status != that.status) return false;
        if(configurationMap.size() != that.configurationMap.size()) return false;
        if(attributeMap.size() != that.attributeMap.size()) return false;
        if(!configurationMap.keySet().equals(that.configurationMap.keySet())) return false;
        if(!attributeMap.keySet().equals(that.attributeMap.keySet())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = attributeMap != null ? attributeMap.hashCode() : 0;
        result = 31 * result + (primaryKey != null ? primaryKey.hashCode() : 0);
        result = 31 * result + (clientKey != null ? clientKey.hashCode() : 0);
        result = 31 * result + maxAttributeIndex;
        result = 31 * result + (crudStatus != null ? crudStatus.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (configurationAttributes != null ? configurationAttributes.hashCode() : 0);
        result = 31 * result + (lazyLoadedRelationships != null ? lazyLoadedRelationships.hashCode() : 0);
        result = 31 * result + (attributeMetaMap != null ? attributeMetaMap.hashCode() : 0);
        result = 31 * result + (configurationMap != null ? configurationMap.hashCode() : 0);
        return result;
    }
}
