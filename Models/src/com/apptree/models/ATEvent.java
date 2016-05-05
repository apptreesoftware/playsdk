package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by alexis on 12/2/15.
 */
public class ATEvent {
    String id;
    String dataSetItemPrimaryKey;
    EventType eventType;
    ATActivity activity;

    public enum EventType {
        None("NONE"),
        Activity("ACTIVITY"),
        Location("LOCATION"),
        Viewed("VIEWED");

        private final String stringValue;

        private EventType(final String text) { this.stringValue = text; }

        /**
         * Returns an event type from a string value
         * @param string the String value of the event type
         * @return
         */
        public static EventType fromString(String string) {
            if ( string.equalsIgnoreCase("NONE") ) {
                return None;
            } else if ( string.equalsIgnoreCase("ACTIVITY") ) {
                return Activity;
            } else if ( string.equalsIgnoreCase("LOCATION") ) {
                return Location;
            } else if ( string.equalsIgnoreCase("VIEWED") ) {
                return Viewed;
            }
            return None;
        }
    }

    /**
     * Constructs an event
     * @param dataSetItemPrimaryKey the String primary key of the data set item this event is happening with
     */
    public ATEvent(String dataSetItemPrimaryKey) {
        this.dataSetItemPrimaryKey = dataSetItemPrimaryKey;
    }

    /**
     *
     * @return The ID of the event
     */
    public String getID() { return this.id; }

    /**
     *
     * @return the EventType of the event
     */
    public EventType getEventType() { return this.eventType; }

    /**
     *
     * @return The activity associated with this event
     */
    public ATActivity getActivity() { return this.activity; }

    /**
     * Updates the event from a JSONObject
     * @param json a JSONObject with the event values
     */
    public void updateFromJSON(JSONObject json) {
        this.id = json.optString("id");
        this.eventType = EventType.fromString(json.optString("eventType"));

        if ( json.has("activity") && !json.isNull("activity") ) {
            activity = new ATActivity();
            activity.updateFromJSON(json.optJSONObject("activity"));
        }
    }
}
