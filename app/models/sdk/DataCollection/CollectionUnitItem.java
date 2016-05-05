package models.sdk.DataCollection;

import models.sdk.AttributeDataTypes.Location;

/**
 * Created by alexis on 5/3/16.
 */
public class CollectionUnitItem {
    private Location location;
    private String value;
    private String displayValue;

    /**
     * Creates a collection unit item with the value and display value
     * @param value The internal value of a collection unit item
     * @param displayValue The value that should be displayed to the user for a collection unit item
     */
    public CollectionUnitItem(String value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public CollectionUnitItem() {}

    /**
     * Sets the location for a collection unit item
     * @param location Location for the collection unit item
     */
    public void setLocation(Location location) { this.location = location; }

    /**
     *
     * @return The Location for a collection unit item
     */
    public Location getLocation() { return this.location; }

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
}
