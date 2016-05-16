package sdk.data;

import sdk.models.DateTimeRange;
import sdk.models.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexis on 5/3/16.
 */
public class Activity {
    String id;
    List<Location> breadcrumb = new ArrayList<Location>();
    List<DateTimeRange> intervals = new ArrayList<DateTimeRange>();
    ActivityState state;

    public enum ActivityState {
        None("NONE"),
        Running("RUNNING"),
        Paused("PAUSED"),
        Complete("COMPLETE"),
        Deleted("DELETED");

        private final String stringValue;
        private ActivityState(final String text) {
            this.stringValue = text;
        }

        @Override
        public String toString() {
            return stringValue;
        }

        /**
         * Activity state from name
         * @param string The name of the assessment status
         * @return
         */
        public static ActivityState fromString(String string) {
            string = string.toUpperCase();
            if ( string.equalsIgnoreCase(None.toString())) {
                return None;
            } else if ( string.equalsIgnoreCase(Running.toString()) ) {
                return Running;
            } else if ( string.equalsIgnoreCase(Paused.toString()) ) {
                return Paused;
            } else if ( string.equalsIgnoreCase(Complete.toString()) ) {
                return Complete;
            } else if ( string.equalsIgnoreCase(Deleted.toString()) ) {
                return Deleted;
            }
            return None;
        }
    }

    public Activity() {

    }

    /**
     *
     * @return The ID of the activity
     */
    public String getID() { return id; }

    /**
     * Updates the ID of the activity
     * @param id
     */
    public void updateID(String id) { this.id = id; }

    /**
     *
     * @return The list of Locations for this activity
     */
    public List<Location> getLocationBreadCrumbs() { return this.breadcrumb; }

    /**
     * Sets the Location breadrumbs for this activity
     * @param locationBreadCrumbs
     */
    public void setLocationBreadCrumbs(List<Location> locationBreadCrumbs) { this.breadcrumb = locationBreadCrumbs; }

    /**
     *
     * @return Returns the list of DateTimeRange intervals for this activity
     */
    public List<DateTimeRange> getIntervals() { return this.intervals; }

    /**
     * Sets the DateRangeTime intervals for this activity
     * @param intervals
     */
    public void setIntervals(List<DateTimeRange> intervals) { this.intervals = intervals; }

    /**
     *
     * @return The state of the activity
     */
    public ActivityState getState() { return this.state; }

    /**
     * Sets the state of the activity
     * @param state
     */
    public void setState(ActivityState state) { this.state = state; }
}
