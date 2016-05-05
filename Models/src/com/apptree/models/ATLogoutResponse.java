package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by matthew on 12/26/14.
 */
public class ATLogoutResponse {
    boolean mSuccess;
    String mMessage;

    /**
     * The string message for the response
     * @return
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * Creates an ATLogoutResponse
     * @param success a boolean indicating logout success
     * @param message a message related to logout
     */
    public ATLogoutResponse(boolean success, String message) {
        mSuccess = success;
        mMessage = message;
    }

    /**
     * Converts the response to a JSONObject
     * @return
     */
    public JSONObject toJSON() {
        JSONObject jsonObject;

        jsonObject = new JSONObject();
        jsonObject.put("success",mSuccess);
        jsonObject.put("message", mMessage);
        return jsonObject;
    }
}
