package sdk.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by alexis on 5/3/16.
 */
public class Event {
    String id;
    String dataSetItemPrimaryKey;
    EventType eventType;
    Activity activity;

    public Event() {

    }

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
        @JsonCreator
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

        @JsonValue
        public String value() {
            return stringValue;
        }
    }

    /**
     * Constructs an event
     * @param dataSetItemPrimaryKey the String primary key of the data set item this event is happening with
     */
    public Event(String dataSetItemPrimaryKey) {
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
    public Activity getActivity() { return this.activity; }
}
