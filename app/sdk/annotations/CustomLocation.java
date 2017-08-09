package sdk.annotations;

import org.joda.time.DateTime;
import sdk.models.Location;

/**
 * Author: Karis Sponsler
 * Date: 8/8/17
 *
 * All required fields are already set in this abstract base class. Additional fields will be ignored.
 */
public abstract class CustomLocation implements CustomLocationInterface {
    protected double latitude = 0;
    protected double longitude = 0;
    protected double accuracy = 0;
    protected double speed = 0;
    protected double elevation = 0;
    protected double bearing = 0;
    protected DateTime timestamp = null;

    public <T extends CustomLocation> void toLocation(Location location, T self) {
        location.setLatitude(self.getLatitude());
        location.setLongitude(self.getLongitude());
        location.setAccuracy(self.getAccuracy());
        location.setSpeed(self.getSpeed());
        location.setElevation(self.getElevation());
        location.setBearing(self.getBearing());
        location.setTimestamp(self.getTimestamp());
    }

    public <T extends CustomLocation> void fromLocation(Location location, T self) {
        self.setLatitude(location.getLatitude());
        self.setLongitude(location.getLongitude());
        self.setAccuracy(location.getAccuracy());
        self.setSpeed(location.getSpeed());
        self.setElevation(location.getElevation());
        self.setBearing(location.getBearing());
        self.setTimestamp(location.getTimestamp());
    }

    protected CustomLocation() {}

    public double getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getElevation() {
        return this.elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }
}
