package sdk.models;

import com.fasterxml.jackson.databind.JsonNode;
import sdk.utils.JsonUtils;

/**
 * Created by alexis on 5/3/16.
 */
public class Color {
    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int a = 0;

    public Color() {}

    /**
     * Creates an ATColor object with an alpha channel value
     * @param r int value of red
     * @param g int value of green
     * @param b int value of blue
     * @param a int value of the alpha channel
     */
    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Creates an ATColor object
     * @param r int value of red
     * @param g int value of green
     * @param b int value of blue
     */
    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 255;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public JsonNode toJSON() {
        return JsonUtils.toJson(this);
    }
}
