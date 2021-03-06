package sdk.data;

import org.joda.time.DateTime;
import sdk.list.ListItem;
import sdk.models.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Orozco on 7/20/17.
 */
public interface Record {

    void setDate(DateTime value, int index);

    DateTime getDate(int index);

    Optional<DateTime> getOptionalDate(int index);

    void setString(String value, int index);

    String getString(int index);

    Optional<String> getOptionalString(int index);

    void setInt(int value, int index);

    int getInt(int index);

    Optional<Integer> getOptionalInt(int index);

    void setBool(boolean value, int index);

    boolean getBool(int index);

    Optional<Boolean> getOptionalBoolean(int index);

    void setColor(Color value, int index);

    Color getColor(int index);

    Optional<Color> getOptionalColor(int index);

    void setDouble(double value, int index);

    double getDouble(int index);

    Optional<Double> getOptionalDouble(int index);

    void setDateTime(DateTime value, int index);

    DateTime getDateTime(int index);

    Optional<DateTime> getOptionalDateTime(int index);

    void setLocation(Location value, int index);

    Location getLocation(int index);

    Optional<Location> getOptionalLocation(int index);

    void setImage(Image value, int index);

    Image getImage(int index);

    Optional<Image> getOptionalImage(int index);

    void setListItem(ListItem value, int index);

    ListItem getListItem(int index);

    Optional<ListItem> getOptionalListItem(int index);

    DataSetItem getDataSetItem(int index);

    java.util.List<DataSetItem> getDataSetItems(int index);

    DataSetItem addNewDataSetItem(int index);

    DataSetItemAttachment addNewDataSetItemAttachment(int index);

    List<DataSetItemAttachment> getAttachmentItemsForIndex(int index);

    ServiceConfigurationAttribute getAttribute(int index);

    AttributeMeta getAttributeMeta(int index);

    void setAttributeMeta(int index, AttributeMeta attributeMeta);

    void setPrimaryKey(String primaryKey);

    String getPrimaryKey();

    void setValue(String value);

    String getValue();

    boolean isValueSet();

    DataSetItem.CRUDStatus getCRUDStatus();

    boolean supportsCRUDStatus();

    boolean isListItem();

    void useLazyLoad(int index);

    void setParentValue(String value);

    DateTimeRange getDateTimeRange(int attributeIndex);

    DateRange getDateRange(int attributeIndex);

    void setTimeInterval(long value, int index);

    long getTimeInterval(int index);

    Optional<Long> getOptionalTimeInterval(int index);

    void setStatus(String status);

    DataSetItem.Status getStatus();

}
