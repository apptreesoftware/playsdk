package sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import play.libs.Json;
import sdk.utils.Constants;

import javax.annotation.Nullable;

/**
 * Created by alexis on 5/3/16.
 */
public class Location {
    private double latitude = 0;
    private double longitude = 0;
    private double bearing = 0;
    private double speed = 0;
    private double accuracy = 0;
    private double elevation = 0;
    private DateTime timestamp;

    public Location() {

    }

    @Nullable
    public static Location fromLatLngString(String locationString) {
        if ( locationString == null ) return null;
        String components[] = locationString.split(",");
        if ( components.length == 2 ) {
            double lat = Double.parseDouble(components[0]);
            double lng = Double.parseDouble(components[1]);
            return new Location(lat, lng);
        }
        return null;
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

    public ObjectNode toJSON() {
        ObjectNode json = Json.newObject();
        json.put("latitude", latitude);
        json.put("longitude", longitude);
        json.put("bearing", bearing);
        json.put("speed", speed);
        json.put("accuracy", accuracy);
        json.put("elevation", elevation);
        if ( timestamp != null ) {
            json.put("timestamp", Constants.AppTreeDateTimeFormat.print(timestamp));
        }
        return json;
    }

    @JsonIgnore
    public String getLatLngString() {
        return String.format("%f,%f", latitude, longitude);
    }

}
