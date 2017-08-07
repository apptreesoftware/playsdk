package sdk.annotations;


import org.joda.time.DateTime;

/**
 * Author: Karis Sponsler
 * Date: 8/7/17
 */

public interface CustomLocation {
    double latitude = 0;
    double longitude = 0;
    double bearing = 0;
    double speed = 0;
    double accuracy = 0;
    double elevation = 0;
    DateTime timestamp = null;

    public void setLatitude(double latitude);
    public double getLatitude();
    public void setLongitude(double longitude);
    public double getLongitude();
}
