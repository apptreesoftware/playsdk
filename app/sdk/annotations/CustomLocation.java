package sdk.annotations;

import org.joda.time.DateTime;
import sdk.models.Location;

/**
 * Author: Karis Sponsler
 * Date: 8/8/17
 *
 * All required fields are already set in this abstract base class. Additional fields will be ignored.
 */
public interface CustomLocation {

    default public <T extends CustomLocation> void toLocation(Location location, T self) {
        location.setLatitude(self.getLatitude());
        location.setLongitude(self.getLongitude());
        location.setAccuracy(self.getAccuracy());
        location.setSpeed(self.getSpeed());
        location.setElevation(self.getElevation());
        location.setBearing(self.getBearing());
        location.setTimestamp(self.getTimestamp());
    }

    default public <T extends CustomLocation> void fromLocation(Location location, T self) {
        self.setLatitude(location.getLatitude());
        self.setLongitude(location.getLongitude());
        self.setAccuracy(location.getAccuracy());
        self.setSpeed(location.getSpeed());
        self.setElevation(location.getElevation());
        self.setBearing(location.getBearing());
        self.setTimestamp(location.getTimestamp());
    }

    public void setLatitude(double latitude);
    public double getLatitude();
    public void setLongitude(double longitude);
    public double getLongitude();

    default public double getAccuracy() {
        return 0;
    }

    default public void setAccuracy(double accuracy) {}

    default public double getSpeed() {
        return 0;
    }

    default public void setSpeed(double speed) {}

    default public double getElevation() {
        return 0;
    }

    default public void setElevation(double elevation) {}

    default public double getBearing() {
        return 0;
    }

    default public void setBearing(double bearing) {}

    default public DateTime getTimestamp() {
        return null;
    }

    default public void setTimestamp(DateTime timestamp) {}
}
