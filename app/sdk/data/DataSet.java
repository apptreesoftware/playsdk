package sdk.data;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import sdk.datasources.RecordActionResponse;
import sdk.utils.Response;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by alexis on 5/3/16.
 */
public class DataSet extends Response {
    private boolean moreRecordsAvailable;
    private ArrayList<DataSetItem> dataSetItems;
    private HashMap<Integer, ServiceConfigurationAttribute> attributeConfigurationForIndexMap = new HashMap<>();
    private int totalRecords;

    /**
     * Creates a data set with an empty set list of data set items
     */
    public DataSet(Collection<ServiceConfigurationAttribute> configurationAttributes) {
        dataSetItems = new ArrayList<>();
        if (configurationAttributes!=null) {
            for (ServiceConfigurationAttribute attribute : configurationAttributes) {
                attributeConfigurationForIndexMap.put(attribute.getAttributeIndex(), attribute);
            }
        }
    }

    public DataSet(DataSetItem item) {
        dataSetItems = new ArrayList<>();
        assert item.getConfigurationAttributes() != null;
        for (ServiceConfigurationAttribute attribute : item.getConfigurationAttributes()) {
            attributeConfigurationForIndexMap.put(attribute.getAttributeIndex(), attribute);
        }
        dataSetItems.add(item);
    }

    public DataSet(RecordActionResponse response) {
        dataSetItems = new ArrayList<>();
        DataSetItem item = response.getDataSetItem();
        if ( item != null ) {
            assert item.getConfigurationAttributes() != null;
            for (ServiceConfigurationAttribute attribute : item.getConfigurationAttributes()) {
                attributeConfigurationForIndexMap.put(attribute.getAttributeIndex(), attribute);
            }
            dataSetItems.add(item);
        }
        setShowMessageAsAlert(response.isShowAsAlert());
        setMessage(response.getMessage());
        setSuccess(true);
    }

    @Nullable
    ServiceConfigurationAttribute getAttributeConfigurationForIndex(int index) {
        return attributeConfigurationForIndexMap.get(index);
    }

    public Collection<ServiceConfigurationAttribute> getConfigurationAttributes() {
        return attributeConfigurationForIndexMap.values();
    }

    /***
     * Creates a new ATDataSetItem and adds it to the data set.
     * @return The newly created data set item
     */
    public DataSetItem addNewDataSetItem() {
        DataSetItem item = new DataSetItem(attributeConfigurationForIndexMap);
        dataSetItems.add(item);
        return item;
    }

    public void add(DataSetItem dataSetItem) {

        dataSetItem.validateAttributes();
        dataSetItems.add(dataSetItem);
    }

    private void validateDataSetItem(DataSetItem dataSetItem) {
    }

    /**
     * Converts a data set to a json object
     * @return A json object of the data set
     * @throws InvalidPrimaryKeyException
     */
    public ObjectNode toJSON() throws InvalidPrimaryKeyException {
        totalRecords = this.totalRecords > 0 ? this.totalRecords : dataSetItems.size();
        int recordCount = dataSetItems.size();
        ObjectNode json = Json.newObject();
        json.put("success", success);
        json.put("message", message);
        json.put("showMessageAsAlert", showMessageAsAlert);
        json.put("totalRecords", totalRecords);
        json.put("numberOfRecords", recordCount);
        json.put("moreRecordsAvailable", moreRecordsAvailable);
        ArrayNode records = json.putArray("records");
        for ( DataSetItem dataSetItem : dataSetItems) {
            records.add(dataSetItem.toJSONWithPrimaryKey());
        }
        return json;
    }

    /**
     * Gets a boolean indicating whether there are more data set items that were not returned in this call
     * @return A boolean indicating whether there are more data set items available
     */
    public boolean isMoreRecordsAvailable() {
        return moreRecordsAvailable;
    }

    /**
     * Sets whether there are more data set items than are being returned for this call
     * @param moreRecordsAvailable
     */
    public void setMoreRecordsAvailable(boolean moreRecordsAvailable) {
        this.moreRecordsAvailable = moreRecordsAvailable;
    }

    /**
     * Gets the number of total records
     * @return The count of data set items
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * Gets the data set items
     * @return A list of data set items
     */
    public ArrayList<DataSetItem> getDataSetItems() {
        return dataSetItems;
    }

    @Override
    public boolean isSuccess() {
        return super.isSuccess();
    }

    @Override
    public boolean isShowMessageAsAlert() {
        return super.isShowMessageAsAlert();
    }


    /**
     * Sets the number of total records
     * @param totalRecords A count of the number of data set items
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getMessage() {
        return message;
    }

    public DataSet withSuccessMessage(String message) {
        this.message = message;
        success = true;
        return this;
    }

    public DataSet withFailureMessage(String message) {
        this.message = message;
        success = false;
        return this;
    }

}
