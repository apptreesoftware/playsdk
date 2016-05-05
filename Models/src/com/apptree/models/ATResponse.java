package com.apptree.models;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Matthew Smith, AppTree Software LLC on 4/18/15.
 */
public class ATResponse {

    protected boolean mSuccess;
    protected String mMessage;
    protected boolean mShowMessageAsAlert;
    public boolean mAuthorizationError;
    private JSONObject mRecord;
    private JSONArray mRecords;

    /**
     *
     * @param success A boolean indicating whether or not the call was successful
     * @param message A message about the call optional
     */
    public ATResponse(boolean success, String message) {
        mSuccess = success;
        mMessage = message;
    }

    /**
     *
     * @return A response indicating that the call failed because it was unable to authenticate
     */
    public static ATResponse withInvalidAuthentication() {
        return new ATResponse(false,"Invalid Authentication",true);
    }

    public void setFailedWithMessage(String message) {
        setSuccess(false);
        setMessage(message);
    }
    /**
     *
     * @param success A boolean indincating the success of the call
     * @param message A message about the call
     * @param authorizationError A boolean indicating whether there was an authorization error
     */
    public ATResponse(boolean success, String message, boolean authorizationError) {
        mSuccess = success;
        mMessage = message;
        mAuthorizationError = authorizationError;
    }

    /**
     *
     * @return A boolean indicating the success of the call
     */
    public boolean isSuccess() {
        return mSuccess;
    }

    /**
     * Sets the boolean indicating the success of the call
     * @param success
     */
    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    /**
     *
     * @return A String message about the call
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * Sets a String message about the call
     * @param message
     */
    public void setMessage(String message) {
        mMessage = message;
    }

    /**
     *
     * @return A JSONObject record
     */
    public JSONObject getRecord() {
        return mRecord;
    }

    /**
     * Sets a JSONObject record
     * @param record
     */
    public void setRecord(JSONObject record) {
        mRecord = record;
    }

    /**
     *
     * @return A JSONArray of the records for a call
     */
    public JSONArray getRecords() {
        return mRecords;
    }

    /**
     * Sets a JSONArray of the records for a call
     * @param records
     */
    public void setRecords(JSONArray records) {
        mRecords = records;
    }

    /**
     *
     * @return A boolean indicating whether there was an authorization error
     */
    public boolean isAuthorizationError() {
        return mAuthorizationError;
    }

    /**
     * Sets a boolean indicating whether there was an authorization error
     * @param authorizationError
     */
    public void setAuthorizationError(boolean authorizationError) {
        mAuthorizationError = authorizationError;
    }

    /**
     *
     * @return A boolean indicating whether the message should be shown as an alert on the device
     */
    public boolean isShowMessageAsAlert() {
        return mShowMessageAsAlert;
    }

    /**
     * Sets a boolean indicating whether the message should be shown as an alert on the device
     * @param showMessageAsAlert
     */
    public void setShowMessageAsAlert(boolean showMessageAsAlert) {
        mShowMessageAsAlert = showMessageAsAlert;
    }

    /**
     *
     * @return A JSONObject of the response item
     */
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", mSuccess);
        jsonObject.putOpt("message", mMessage);
        jsonObject.put("showMessageAsAlert",mShowMessageAsAlert);
        JSONArray array = getRecords();
        if ( getRecord() != null && array == null ) {
            array = new JSONArray();
            array.put(getRecord());
        }
        jsonObject.putOpt("records",array);
        return jsonObject;
    }
}
