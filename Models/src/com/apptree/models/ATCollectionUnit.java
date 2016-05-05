package com.apptree.models;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by alexis on 12/8/15.
 */
public class ATCollectionUnit {
    private ArrayList<ATCollectionUnitItem> mUnitItems;

    /**
     * Creates a new ATCollectionUnit for use with ATDataCollections
     */
    public ATCollectionUnit() {
        mUnitItems = new ArrayList<ATCollectionUnitItem>();
    }

    /**
     * Adds a collection unit item to this collection
     * @param unitItem
     */
    public void addCollectionUnitItem(ATCollectionUnitItem unitItem) {
        if ( unitItem != null ) {
            mUnitItems.add(unitItem);
        }
    }

    /**
     * Adds a collection of collection unit items to this collection unit
     * @param unitItems
     */
    public void addCollectionUnitItems(Collection<ATCollectionUnitItem> unitItems) {
        mUnitItems.addAll(unitItems);
    }

    /**
     * Removes the specified collection unit item from this collection unit
     * @param unitItem
     */
    public void removeCollectionUnitItem(ATCollectionUnitItem unitItem) {
        mUnitItems.remove(unitItem);
    }

    /**
     * Returns the collection unit items
     * @return an ArrayList of ATCollectionUnitItems
     */
    public ArrayList<ATCollectionUnitItem> getUnitItems() { return mUnitItems; }

    /**
     * Converts the collection unit to a JSONObject
     * @return A JSONObject of the collection unit
     */
    public JSONArray toJSONArray() {
        JSONArray jsonArray = new JSONArray();

        if ( mUnitItems != null ) {
            for ( ATCollectionUnitItem item : mUnitItems ) {
                jsonArray.put(item.toJSON());
            }
        }

        return jsonArray;
    }
}
