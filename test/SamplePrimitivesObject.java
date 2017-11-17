import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;

/**
 * Author: Karis Sponsler
 * Date: 11/17/17
 */
public class SamplePrimitivesObject {
    @PrimaryKey
    public int pk;

    @Attribute(index = 0)
    public double aDouble;

    @Attribute(index = 1)
    public float aFloat;

    @Attribute(index = 2)
    public int anInt;

    @Attribute(index = 3)
    private int privateInt;

    @Attribute(index = 4)
    private double privateDouble;

    @Attribute(index = 5)
    private float privateFloat;


    public int getPrivateInt() {
        return privateInt;
    }

    public void setPrivateInt(int privateInt) {
        this.privateInt = privateInt;
    }

    public double getPrivateDouble() {
        return privateDouble;
    }

    public void setPrivateDouble(double privateDouble) {
        this.privateDouble = privateDouble;
    }

    public float getPrivateFloat() {
        return privateFloat;
    }

    public void setPrivateFloat(float privateFloat) {
        this.privateFloat = privateFloat;
    }
}
