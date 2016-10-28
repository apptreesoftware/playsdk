package sdk.data;

import org.joda.time.DateTime;
import sdk.list.ListItem;
import sdk.models.*;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Matthew Smith on 10/11/16.
 * Copyright AppTree Software, Inc.
 */


public class User extends DataSetItem {

    private static int FIRST_NAME_INDEX = 0;
    private static int LAST_NAME_INDEX = 1;
    private static int DISPLAY_NAME_INDEX = 2;
    private static int USERNAME_INDEX = 3;
    private static int EMAIL_INDEX = 4;
    private static int PHONE_INDEX = 5;

    public static int CUSTOM_ATTRIBUTE_START_INDEX = 40;

    /***
     * Creates an DataSetItem using the configuration attributes provided. The service attributes passed in will be used to validate what data is being set on this DataSetItem.

     * @param attributeConfigurationMap The service configuration attributes to use for validation. This should come from your DataSource getViewUpdateServiceAttributes, getCreateServiceAttributes, or getSearchServiceAttributes depending on what this DataSetItem is being used for.
     */
    User(HashMap<Integer, ServiceConfigurationAttribute> attributeConfigurationMap
    ) {
        super(attributeConfigurationMap);
    }

    public User(Collection<ServiceConfigurationAttribute> attributes) {
        super(attributes);
    }

    public void setFirstName(String firstName) {
        super.setStringForAttributeIndex(firstName, FIRST_NAME_INDEX);
    }

    public String getFirstName() {
        return super.getStringAttributeAtIndex(FIRST_NAME_INDEX);
    }

    public void setLastName(String lastName) {
        super.setStringForAttributeIndex(lastName, LAST_NAME_INDEX);
    }

    public String getLastName() {
        return super.getStringAttributeAtIndex(LAST_NAME_INDEX);
    }

    public void setEmail(String email) {
        super.setStringForAttributeIndex(email, EMAIL_INDEX);
    }

    public String getEmail() {
        return super.getStringAttributeAtIndex(EMAIL_INDEX);
    }

    public void setDisplayName(String displayName) {
        super.setStringForAttributeIndex(displayName, DISPLAY_NAME_INDEX);
    }

    public String getDisplayName() {
        return super.getStringAttributeAtIndex(DISPLAY_NAME_INDEX);
    }

    public void setUsername(String username) {
        super.setStringForAttributeIndex(username, USERNAME_INDEX);
    }

    public String getUsername() {
        return super.getStringAttributeAtIndex(USERNAME_INDEX);
    }

    public void setPhone(String phone) {
        super.setStringForAttributeIndex(phone, PHONE_INDEX);
    }

    public String getPhone() {
        return super.getStringAttributeAtIndex(PHONE_INDEX);
    }

    public void setUserID(String userID) {
        this.primaryKey = userID;
    }

    public String getUserID() {
        return this.primaryKey;
    }

    public HashMap<String, String> getCustomAttributeMap() {
        HashMap<String, String> map = new HashMap<>();
        for ( int i = CUSTOM_ATTRIBUTE_START_INDEX; i <= 80; i++ ) {
            ServiceConfigurationAttribute attribute = configurationMap.get(i);
            if ( attribute != null ) {
                String value = getStringAttributeAtIndex(attribute.getAttributeIndex() - CUSTOM_ATTRIBUTE_START_INDEX);
                if ( value != null ) {
                    map.put(attribute.getName(), value);
                }
            }
        }
        return map;
    }

    public String getStringAttributeAtIndex(int attributeIndex) {
        return super.getStringAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setStringForAttributeIndex(String value, int attributeIndex) throws InvalidAttributeValueException {
        super.setStringForAttributeIndex(value, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<String> getOptionalStringAttributeAtIndex(int attributeIndex) {
        return super.getOptionalStringAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public int getIntAttributeAtIndex(int attributeIndex) {
        return super.getIntAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setIntForAttributeIndex(int value, int attributeIndex) throws InvalidAttributeValueException {
        super.setIntForAttributeIndex(value, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<Integer> getOptionalIntAttributeAtIndex(int attributeIndex) {
        return super.getOptionalIntAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setDoubleForAttributeIndex(double value, int attributeIndex) throws InvalidAttributeValueException {
        super.setDoubleForAttributeIndex(value, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public double getDoubleAttributeAtIndex(int attributeIndex) {
        return super.getDoubleAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<Double> getOptionalDoubleAttributeAtIndex(int attributeIndex) {
        return super.getOptionalDoubleAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setListItemForAttributeIndex(ListItem listItem, int attributeIndex) throws InvalidAttributeValueException {
        super.setListItemForAttributeIndex(listItem, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Color getColorAttributeAtIndex(int attributeIndex) {
        return super.getColorAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public long getTimeIntervalAttributeAtIndex(int attributeIndex) {
        return super.getTimeIntervalAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public ListItem getListItemAttributeAtIndex(int attributeIndex) {
        return super.getListItemAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<ListItem> getOptionalListItemAttributeAtIndex(int attributeIndex) {
        return super.getOptionalListItemAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public DateTime getDateAttributeAtIndex(int attributeIndex) {
        return super.getDateAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<DateTime> getOptionalDateAttributeAtIndex(int attributeIndex) {
        return super.getOptionalDateAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public DateTime getDateTimeAttributeAtIndex(int attributeIndex) {
        return super.getDateTimeAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<DateTime> getOptionalDateTimeAttributeAtIndex(int attributeIndex) {
        return super.getOptionalDateTimeAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Nullable
    @Override
    public DateRange getDateRangeAttributeAtIndex(int attributeIndex) {
        return super.getDateRangeAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<DateRange> getOptionalDateRangeAttributeAtIndex(int attributeIndex) {
        return super.getOptionalDateRangeAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Nullable
    @Override
    public DateTimeRange getDateTimeRangeAttributeAtIndex(int attributeIndex) {
        return super.getDateTimeRangeAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<DateTimeRange> getOptionalDateTimeRangeAttributeAtIndex(int attributeIndex) {
        return super.getOptionalDateTimeRangeAttributeAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public boolean getBoolValueAtIndex(int attributeIndex) {
        return super.getBoolValueAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Nullable
    @Override
    public Boolean getBooleanValueAtIndex(int attributeIndex) {
        return super.getBooleanValueAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Nullable
    @Override
    public List<DataSetItem> getDataSetItemsAtIndex(int attributeIndex) {
        return super.getDataSetItemsAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Nullable
    @Override
    public DataSetItem getDataSetItemAtIndex(int attributeIndex) {
        return super.getDataSetItemAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Location getLocationAtIndex(int attributeIndex) {
        return super.getLocationAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<Location> getOptionalLocationAtIndex(int attributeIndex) {
        return super.getOptionalLocationAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Image getImageAtIndex(int attributeIndex) {
        return super.getImageAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public Optional<Image> getOptionalImageAtIndex(int attributeIndex) {
        return super.getOptionalImageAtIndex(attributeIndex);
    }

    @Override
    public List<DataSetItemAttachment> getAttachmentItemsAtIndex(int attributeIndex) {
        return super.getAttachmentItemsAtIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setColorForAttributeIndex(Color value, int attributeIndex) throws InvalidAttributeValueException {
        super.setColorForAttributeIndex(value, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setLocationForAttributeIndex(Location location, int attributeIndex) throws InvalidAttributeValueException {
        super.setLocationForAttributeIndex(location, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setImageForAttributeIndex(Image image, int attributeIndex) throws InvalidAttributeValueException {
        super.setImageForAttributeIndex(image, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setBooleanForAttributeIndex(boolean value, int attributeIndex) throws InvalidAttributeValueException {
        super.setBooleanForAttributeIndex(value, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setTimeIntervalForAttributeIndex(long timeInterval, int attributeIndex) throws InvalidAttributeValueException {
        super.setTimeIntervalForAttributeIndex(timeInterval, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setDateForAttributeIndex(DateTime date, int attributeIndex) throws InvalidAttributeValueException {
        super.setDateForAttributeIndex(date, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public void setDateTimeForAttributeIndex(DateTime date, int attributeIndex) throws InvalidAttributeValueException {
        super.setDateTimeForAttributeIndex(date, attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public DataSetItem addNewDataSetItemForAttributeIndex(int attributeIndex) {
        return super.addNewDataSetItemForAttributeIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    @Override
    public DataSetItemAttachment addNewAttachmentForAttributeIndex(int attributeIndex) throws InvalidAttributeValueException {
        return super.addNewAttachmentForAttributeIndex(attributeIndex + CUSTOM_ATTRIBUTE_START_INDEX);
    }

    public static Collection<ServiceConfigurationAttribute> getConfigurationAttributesWithCustomAttributes(Collection<ServiceConfigurationAttribute> customAttributes) {
        HashSet<ServiceConfigurationAttribute> attributes = new HashSet<>();
        attributes.add(new ServiceConfigurationAttribute.Builder(FIRST_NAME_INDEX).name("First Name").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(LAST_NAME_INDEX).name("Last Name").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(DISPLAY_NAME_INDEX).name("Display Name").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(USERNAME_INDEX).name("Username").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(EMAIL_INDEX).name("Email").canCreate().canUpdate().build());
        attributes.add(new ServiceConfigurationAttribute.Builder(PHONE_INDEX).name("Phone").canCreate().canUpdate().build());
        if ( customAttributes != null ) {
            for (ServiceConfigurationAttribute attribute : customAttributes) {
                attribute.setAttributeIndex(attribute.getAttributeIndex() + CUSTOM_ATTRIBUTE_START_INDEX);
                attributes.add(attribute);
            }
        }
        return attributes;
    }
}
