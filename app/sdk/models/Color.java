package sdk.models;

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

    /**
     *
     * @return The int value for red
     */
    public int getRed() {
        return r;
    }

    /**
     * Sets the red value
     * @param red int value for red (0-255)
     */
    public void setRed(int red) {
        this.r = red;
    }

    /**
     *
     * @return The int value for green
     */
    public int getGreen() {
        return g;
    }

    /**
     * Sets the green value
     * @param green an int value (0-255)
     */
    public void setGreen(int green) {
        this.g = green;
    }

    /**
     *
     * @return The int value for blue
     */
    public int getBlue() {
        return b;
    }

    /**
     * Sets the blue value
     * @param blue an int value (0-255)
     */
    public void setBlue(int blue) {
        this.b = blue;
    }

    /**
     *
     * @return The int value for the alpha channel
     */
    public int getAlpha() {
        return a;
    }

    /**
     * Sets the alpha channel value
     * @param alpha an int value (0-255)
     */
    public void setAlpha(int alpha) {
        this.a = alpha;
    }
}
