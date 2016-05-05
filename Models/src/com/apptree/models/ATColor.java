package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by Matthew Smith on 9/28/15.
 * Copyright AppTree Software, Inc.
 */
public class ATColor {
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private int alpha = 0;


    /**
     * Creates an ATColor object with an alpha channel value
     * @param r int value of red
     * @param g int value of green
     * @param b int value of blue
     * @param a int value of the alpha channel
     */
    public ATColor(int r, int g, int b, int a) {
        red = r;
        green = g;
        blue = b;
        alpha = a;
    }

    /**
     * Creates an ATColor object
     * @param r int value of red
     * @param g int value of green
     * @param b int value of blue
     */
    public ATColor(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
        alpha = 255;
    }

    /**
     * Creates an ATColor object from a JSONObject
     * @param json A JSONObject with values for red, green, blue, and alpha channel using r,g,b,a as keys respectively
     */
    public ATColor(JSONObject json) {
        this.red = json.optInt("r", 0);
        this.blue = json.optInt("b", 0);
        this.green = json.optInt("g", 0);
        this.alpha = json.optInt("a",0);
    }

    /**
     *
     * @return The int value for red
     */
    public int getRed() {
        return red;
    }

    /**
     * Sets the red value
     * @param red int value for red (0-255)
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     *
     * @return The int value for green
     */
    public int getGreen() {
        return green;
    }

    /**
     * Sets the green value
     * @param green an int value (0-255)
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     *
     * @return The int value for blue
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Sets the blue value
     * @param blue an int value (0-255)
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     *
     * @return The int value for the alpha channel
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * Sets the alpha channel value
     * @param alpha an int value (0-255)
     */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    /**
     * Converts the ATColor to a JSONObject
     * @return a JSONObject
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("r", this.red);
        json.put("b", this.blue);
        json.put("g", this.green);
        json.put("a", this.alpha);
        return json;
    }
}
