package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by matthew on 12/26/14.
 */
public class ATValidateTokenResponse {
    boolean mSuccess;
    String mMessage;

    /**
     *
     * @param success A boolean indicating the success of the call
     * @param message A message about the call optional
     */
    public ATValidateTokenResponse(boolean success, String message) {
        mSuccess = success;
        mMessage = message;
    }

    /**
     *
     * @return The JSONObject for the validate token response
     */
    public JSONObject toJSON() {
        JSONObject json;
        json = new JSONObject();
        json.put("success",mSuccess);
        json.put("message",mMessage);
        return json;
    }

}
