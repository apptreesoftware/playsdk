package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by alexis on 12/8/15.
 */
public class ATCollectionUnitItem {
    private ATLocation location;
    private String value;
    private String displayValue;

    /**
     * Creates a collection unit item with the value and display value
     * @param value The internal value of a collection unit item
     * @param displayValue The value that should be displayed to the user for a collection unit item
     */
    public ATCollectionUnitItem(String value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    /**
     * Creates a collection unit item from a JSONObject
     * @param collectionObject
     */
    public ATCollectionUnitItem(JSONObject collectionObject) {
        this.location = new ATLocation(collectionObject.optJSONObject("location"));
        this.value = collectionObject.optString("value");
        this.displayValue = collectionObject.optString("displayValue");
    }

    /**
     * Sets the location for a collection unit item
     * @param location ATLocation for the collection unit item
     */
    public void setLocation(ATLocation location) { this.location = location; }

    /**
     *
     * @return The ATLocation for a collection unit item
     */
    public ATLocation getLocation() { return this.location; }

    /**
     * Sets the value of a data collection item
     * @param value a string of the internal value
     */
    public void setValue(String value) { this.value = value; }

    /**
     *
     * @return The string value of a collection unit item
     */
    public String getValue() { return this.value; }

    /**
     * Sets the display value of a collection unit item
     * @param displayValue a string display value
     */
    public void setDisplayValue(String displayValue) { this.displayValue = displayValue; }

    /**
     *
     * @return The string display value of a collection unit item
     */
    public String getDisplayValue() { return this.displayValue; }

    /**
     * Converts the data collection unit item to a JSONObject
     * @return a JSONObject of the data collection unit item
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.putOpt("location", location != null ? location.toJSON() : null);
        json.putOpt("value", value);
        json.putOpt("displayValue", displayValue);

        return json;
    }
}
