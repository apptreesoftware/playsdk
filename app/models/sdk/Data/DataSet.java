package models.sdk.Data;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alexis on 5/3/16.
 */
public class DataSet {
    private boolean moreRecordsAvailable;
    private ArrayList<DataSetItem> dataSetItems;
    private HashMap<Integer, ServiceConfigurationAttribute> attributeConfigurationForIndexMap = new HashMap<>();
    private String message;
    private int totalRecords;

    /**
     * Creates a data set with an empty set list of data set items
     */
    public DataSet(List<ServiceConfigurationAttribute> configurationAttributes) {
        dataSetItems = new ArrayList<>();
        if (configurationAttributes!=null) {
            for (ServiceConfigurationAttribute attribute : configurationAttributes) {
                attributeConfigurationForIndexMap.put(attribute.getAttributeIndex(), attribute);
            }
        }
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

    /**
     * Removes a data set item
     * @param dataSetItem The data set item to be removed
     */
    public void removeDataSetItem(DataSetItem dataSetItem) {
        dataSetItems.remove(dataSetItem);
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

    public void setMessage(String message) {
        this.message = message;
    }

}
