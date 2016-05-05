package com.apptree.models;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Alexis Andreason on 2/5/15.
 */
public class ATDefaults {
    private ArrayList<ATDefaultItem> mFormDefaultItems;

    /**
     * Creates defaults
     */
    public ATDefaults() {
        mFormDefaultItems = new ArrayList<ATDefaultItem>();
    }

    /**
     * Adds a colleciton of default items to the defaults
     * @param defaultItems The collection of default items
     */
    public void addFormDefaults(Collection<ATDefaultItem> defaultItems) {
        mFormDefaultItems.addAll(defaultItems);
    }

    /**
     * Adda a default item to the defaults
     * @param item The default item to add
     */
    public void addFormDefaultItem(ATDefaultItem item) { mFormDefaultItems.add(item); }

    /**
     * Removes a default item
     * @param formDefaultItem The default item to remove
     */
    public void removeFormDefaultItem(ATDefaultItem formDefaultItem) {
        mFormDefaultItems.remove(formDefaultItem);
    }

    /**
     * Converts the form defaults to a json object
     * @return A json object from the form defaults
     */
    public JSONArray toJSONArray() {
        JSONArray jsonArray;

        jsonArray = new JSONArray();
        if ( mFormDefaultItems != null ) {
            for ( ATDefaultItem item : mFormDefaultItems ) {
                jsonArray.put(item.toJSON());
            }
        }
        return jsonArray;
    }
}
