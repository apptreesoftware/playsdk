package sdk.models;

import org.joda.time.DateTime;

/**
 * Created by alexis on 5/3/16.
 */
public class Location {
    private double latitude;
    private double longitude;
    private double bearing;
    private double speed;
    private double accuracy;
    private double elevation;
    private DateTime timestamp;

    public Location() {

    }

    /**
     * Constructs an ATLocation object
     * @param latitude the latitude as double
     * @param longitude the longitude as double
     */
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
}
