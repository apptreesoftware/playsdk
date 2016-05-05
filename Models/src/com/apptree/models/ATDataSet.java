package com.apptree.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matthew on 11/5/14.
 */
public class ATDataSet {
    private String mDataSetName;
    private boolean mMoreRecordsAvailable;
    private ArrayList<ATDataSetItem> mDataSetItems;
    private HashMap<Integer, ATServiceConfigurationAttribute> attributeConfigurationForIndexMap = new HashMap<Integer, ATServiceConfigurationAttribute>();

    private int mTotalRecords;

    /**
     * Creates a data set with an empty set list of data set items
     */
    public ATDataSet(List<ATServiceConfigurationAttribute> configurationAttributes) {
        mDataSetItems = new ArrayList<ATDataSetItem>();
        if (configurationAttributes!=null) {
            for (ATServiceConfigurationAttribute attribute : configurationAttributes) {
                attributeConfigurationForIndexMap.put(attribute.getAttributeIndex(), attribute);
            }
        }
    }

    /***
     * Creates a new ATDataSetItem and adds it to the data set.
     * @return The newly created data set item
     */
    public ATDataSetItem addNewDataSetItem() {
        ATDataSetItem item = new ATDataSetItem(attributeConfigurationForIndexMap);
        mDataSetItems.add(item);
        return item;
    }

    /**
     * Removes a data set item
     * @param dataSetItem The data set item to be removed
     */
    public void removeDataSetItem(ATDataSetItem dataSetItem) {
        mDataSetItems.remove(dataSetItem);
    }

    /**
     * Converts a data set to a json object
     * @return A json object of the data set
     * @throws ATInvalidPrimaryKeyException
     */
    public JSONObject toJSON() throws ATInvalidPrimaryKeyException {
        int recordCount;
        int totalRecords;
        JSONObject json;
        JSONArray records;

        totalRecords = mTotalRecords > 0 ? mTotalRecords : mDataSetItems.size();
        recordCount = mDataSetItems.size();

        json = new JSONObject();
        json.put("totalRecords", totalRecords);
        json.put("numberOfRecords", recordCount);
        json.put("moreRecordsAvailable", mMoreRecordsAvailable);
        records = new JSONArray();
        for ( ATDataSetItem dataSetItem : mDataSetItems ) {
            records.put(dataSetItem.toJSONWithPrimaryKey());
        }
        json.put("records", records);

        return json;
    }

    /**
     * Gets a boolean indicating whether there are more data set items that were not returned in this call
     * @return A boolean indicating whether there are more data set items available
     */
    public boolean isMoreRecordsAvailable() {
        return mMoreRecordsAvailable;
    }

    /**
     * Sets whether there are more data set items than are being returned for this call
     * @param moreRecordsAvailable
     */
    public void setMoreRecordsAvailable(boolean moreRecordsAvailable) {
        mMoreRecordsAvailable = moreRecordsAvailable;
    }

    /**
     * Gets the number of total records
     * @return The count of data set items
     */
    public int getTotalRecords() {
        return mTotalRecords;
    }

    /**
     * Gets the data set items
     * @return A list of data set items
     */
    public ArrayList<ATDataSetItem> getDataSetItems() {
        return mDataSetItems;
    }

    /**
     * Sets the number of total records
     * @param totalRecords A count of the number of data set items
     */
    public void setTotalRecords(int totalRecords) {
        mTotalRecords = totalRecords;
    }
}
