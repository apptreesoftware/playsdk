package sdk.annotations;


import org.joda.time.DateTime;

/**
 * Author: Karis Sponsler
 * Date: 8/7/17
 */

public interface CustomLocationInterface {

    public void setLatitude(double latitude);
    public double getLatitude();
    public void setLongitude(double longitude);
    public double getLongitude();
    public void setAccuracy(double latitude);
    public double getAccuracy();
    public void setElevation(double longitude);
    public double getElevation();
    public void setBearing(double bearing);
    public double getBearing();
    public void setSpeed(double speed);
    public double getSpeed();
    public void setTimestamp(DateTime timestamp);
    public DateTime getTimestamp();
}
