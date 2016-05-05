package com.apptree.models;

import org.joda.time.DateTime;
import org.json.JSONObject;

/**
 * Created by Matthew Smith on 9/28/15.
 * Copyright AppTree Software, Inc.
 */
public class ATLocation {
    private double latitude;
    private double longitude;
    private double bearing;
    private double speed;
    private double accuracy;
    private double elevation;
    private DateTime timestamp;

    public ATLocation() {

    }

    /**
     * Constructs an ATLocation object
     * @param latitude the latitude as double
     * @param longitude the longitude as double
     */
    public ATLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Updates the location object from a JSONObject
     * @param json
     */
    public ATLocation(JSONObject json) {
        if ( json == null ) {
            return;
        }
        this.latitude = json.optDouble("latitude",0);
        this.longitude = json.optDouble("longitude",0);
        this.bearing = json.optDouble("bearing",0);
        this.speed = json.optDouble("speed",0);
        this.accuracy = json.optDouble("accuracy",0);
        this.elevation = json.optDouble("elevation",0);
        String timestampString = json.optString("timestamp");
        if ( timestampString != null ) {
            try {
                this.timestamp = ATDataSetItemAttribute.AppTreeDateTimeFormat.parseDateTime(timestampString);
            } catch ( Exception ignored) {}
        }
    }

    /**
     *
     * @return the latitude as a double
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude
     * @param latitude a double value for latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return the longitude as a double
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude
     * @param longitude a double value for longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return the bearing as a double
     */
    public double getBearing() {
        return bearing;
    }

    /**
     * Sets the bearing
     * @param bearing
     */
    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    /**
     *
     * @return the speed as a double
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed
     * @param speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     *
     * @return the accuracy as a double
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * Sets the accuracy
     * @param accuracy
     */
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    /**
     *
     * @return the elevation as a double
     */
    public double getElevation() {
        return elevation;
    }

    /**
     * Sets the elevation
     * @param elevation
     */
    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    /**
     *
     * @return the timestamp as a DateTime
     */
    public DateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the time stamp
     * @param timestamp
     */
    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Converts the location object to a JSONObject
     * @return
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.putOpt("latitude", this.latitude);
        json.putOpt("longitude", this.longitude);
        json.putOpt("bearing", this.bearing);
        json.putOpt("speed", this.speed);
        json.putOpt("accuracy", this.accuracy);
        json.putOpt("elevation", this.elevation);
        if ( timestamp != null ) {
            String timestampString = ATDataSetItemAttribute.AppTreeDateTimeFormat.print(timestamp);
            json.putOpt("timestamp", timestampString);
        }
        return json;
    }
}
