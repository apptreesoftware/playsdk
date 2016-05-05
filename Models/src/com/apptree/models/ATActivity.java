package com.apptree.models;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew Smith on 11/1/15.
 * Copyright AppTree Software, Inc.
 */
public class ATActivity {
    String id;
    List<ATLocation> locationBreadCrumbs = new ArrayList<ATLocation>();
    List<ATDateTimeRange> intervals = new ArrayList<ATDateTimeRange>();
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

    ATActivity() {

    }

    /**
     * Updates this ATActivity from a JSONObject
     * @param json A JSONObject containing the values to be updated
     */
    public void updateFromJSON(JSONObject json) {
        this.id = json.optString("id");

        locationBreadCrumbs.clear();
        if ( !json.isNull("breadcrumb") ) {
            parseBreadCrumbLocations(json.optJSONArray("breadcrumb"));
        }

        intervals.clear();
        if ( !json.isNull("intervals") ) {
            parseIntervals(json.optJSONArray("intervals"));
        }

        this.state = json.optString("state") != null ? ActivityState.fromString(json.optString("state")) : ActivityState.None;
    }

    private void parseBreadCrumbLocations(JSONArray locations) {
        JSONObject locationObject;
        ATLocation location;

        if ( locations != null && locations.length() > 0 ) {
            for ( int i = 0; i < locations.length(); i++ ) {
                locationObject = locations.optJSONObject(i);
                if ( locationObject != null ) {
                    location = new ATLocation(locationObject);
                    locationBreadCrumbs.add(location);
                }
            }
        }
    }

    private void parseIntervals(JSONArray timeIntervals) {
        JSONObject intervalObject;
        ATDateTimeRange dateTimeRange;

        if ( timeIntervals != null && timeIntervals.length() > 0 ) {
            for ( int i = 0; i < timeIntervals.length(); i++ ) {
                intervalObject = timeIntervals.optJSONObject(i);
                if ( intervalObject != null ) {
                    dateTimeRange = new ATDateTimeRange(intervalObject);
                    intervals.add(dateTimeRange);
                }
            }
        }
    }

    /**
     * Converts the ATActivity to a JSONObject
     * @return
     */
    public JSONObject toJSON() {
        JSONObject activityObject = new JSONObject();
        JSONArray breadcrumbsArray = new JSONArray();
        JSONArray intervalsArray = new JSONArray();
        JSONObject tempObject;

        activityObject.putOpt("id", this.id);
        activityObject.putOpt("state", this.state.toString());

        for ( ATLocation location : locationBreadCrumbs ) {
            tempObject = location.toJSON();
            breadcrumbsArray.put(tempObject);
        }
        activityObject.putOpt("breadcrumb", breadcrumbsArray);

        for ( ATDateTimeRange range : intervals ) {
            tempObject = range.toJSON();
            intervalsArray.put(tempObject);
        }
        activityObject.putOpt("intervals", intervalsArray);

        return activityObject;
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
     * @return The list of ATLocations for this activity
     */
    public List<ATLocation> getLocationBreadCrumbs() { return this.locationBreadCrumbs; }

    /**
     * Sets the ATLocation breadrumbs for this activity
     * @param locationBreadCrumbs
     */
    public void setLocationBreadCrumbs(List<ATLocation> locationBreadCrumbs) { this.locationBreadCrumbs = locationBreadCrumbs; }

    /**
     *
     * @return Returns the list of ATDateTimeRange intervals for this activity
     */
    public List<ATDateTimeRange> getIntervals() { return this.intervals; }

    /**
     * Sets the ATDateRangeTime intervals for this activity
     * @param intervals
     */
    public void setIntervals(List<ATDateTimeRange> intervals) { this.intervals = intervals; }

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
