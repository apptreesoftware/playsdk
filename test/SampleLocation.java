import sdk.annotations.CustomLocation;

/**
 * Created by Orozco on 8/14/17.
 */
public class SampleLocation implements CustomLocation{
    private double latitude;
    private double longitude;
    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }
}
