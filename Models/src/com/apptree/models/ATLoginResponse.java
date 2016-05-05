package com.apptree.models;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by matthew on 12/26/14.
 */



public class ATLoginResponse {
    private String mToken;
    private boolean mSuccess = false;
    private String mMessage;
    private String mUserID;
    private String mUsername;
    private JSONObject mExtraHeaders;

    /**
     *
     * @param success A boolean indicating the success of the login
     * @param userID
     * @param token
     */
    public ATLoginResponse(boolean success, String userID, String token, String username, JSONObject extraHeaders) {
        mToken = token;
        mSuccess = success;
        mUserID = userID;
        mUsername = username;
        mExtraHeaders = extraHeaders;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public boolean getStatus() {
        return mSuccess;
    }

    public void setStatus(boolean status) {
        mSuccess = status;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }

    public JSONObject getExtraHeaders() { return mExtraHeaders; }

    public void setExtraHeaders(JSONObject extraHeaders) { mExtraHeaders = extraHeaders; }

    /**
     *
     * @return A JSONObject of the login response
     */
    public JSONObject toJSON() {
        JSONObject jsonObject;
        jsonObject = new JSONObject();
        jsonObject.put("success",mSuccess);
        jsonObject.putOpt("token", mToken);
        jsonObject.putOpt("username", mUsername);
        jsonObject.putOpt("message", mMessage);
        jsonObject.putOpt("id", mUserID);
        jsonObject.putOpt("extraHeaders", mExtraHeaders);
        return jsonObject;
    }

    public static class Builder {
        private boolean mSuccess;
        private String mToken;
        private String mUsername;
        private List<Integer> mRoles;
        private String mUserID;
        private HashMap<String,String> mUserInfo;
        private String mMessage;
        private JSONObject mExtraHeaders;

        /**
         * Creates a Builder object
         */
        public Builder() {

        }

        /**
         *
         * @param token A String token from the login response
         * @return A builder with the success boolean set to true and the provided token
         */
        public Builder withSuccess(String token, String userID, String username, JSONObject extraHeaders) {
            mSuccess = true;
            mToken = token;
            mUserID = userID;
            mUsername = username;
            mExtraHeaders = extraHeaders;
            return this;
        }

        /**
         *
         * @param message
         * @return a builder with the given message
         */
        public Builder withMessage(String message) {
            mMessage = message;
            return this;
        }

        /**
         *
         * @return A login response with the specified builder parameters set
         */
        public ATLoginResponse build() {
            ATLoginResponse loginResponse = new ATLoginResponse(mSuccess,mUserID,mToken,mUsername, mExtraHeaders);
            loginResponse.setMessage(mMessage);
            return loginResponse;
        }
    }

}

