import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;
import sdk.data.DataSetItem;

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

    @Attribute(index = 4)
    private int privateInt;

    @Attribute(index = 5)
    private double privateDouble;

    @Attribute(index = 6)
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


    public static boolean equivalent(DataSetItem item, SamplePrimitivesObject obj) {
        return (obj.anInt == item.getInt(2) &&
                Double.compare(obj.aDouble, item.getDouble(0)) < 0.001 &&
                Double.compare(obj.aFloat, item.getDouble(1)) < 0.001 &&
                obj.getPrivateInt() == item.getInt(4) &&
                Double.compare(obj.getPrivateDouble(), item.getDouble(5)) < 0.001 &&
                Double.compare(obj.getPrivateFloat(), item.getDouble(6)) < 0.001);
    }
}
