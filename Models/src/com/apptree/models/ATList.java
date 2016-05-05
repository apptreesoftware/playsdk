package com.apptree.models;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Matthew Smith on 11/5/14.
 *
 */
public class ATList {
    private ArrayList<ATListItem> mListItems;

    /**
     * Creates a list 
     */
    public ATList() {
        mListItems = new ArrayList<ATListItem>();
    }

    /**
     * Adds a list item
     * @param listItem The list item to add
     */
    public void addListItem(ATListItem listItem) {
        if ( listItem != null ) {
            mListItems.add(listItem);
        }
    }

    /**
     * Adds a collection of list items
     * @param listItems The collection of list items to add
     */
    public void addListItems(Collection<ATListItem> listItems) {
        mListItems.addAll(listItems);
    }

    /**
     * removes a list items
     * @param listItem The list item to remove
     */
    public void removeListItem(ATList listItem) {
        mListItems.remove(listItem);
    }

    /**
     * Converts the list to a json object
     * @return The json object
     */
    public JSONArray toJSONArray() {
        JSONArray jsonArray;
        jsonArray = new JSONArray();

        if ( mListItems != null ) {
            for ( ATListItem item : mListItems ) {
                jsonArray.put(item.toJSON());
            }
        }
        return jsonArray;
    }
}
