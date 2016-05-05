package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by matthew on 11/5/14.
 */
public class ATDataSourceResponse extends ATResponse {
    private ATDataSet dataSet;

    /**
     * Creates a data source response
     * @param success A boolean indicating the success of the call
     * @param message A String message about the call optional
     * @param dataSet The data set returned from the call
     */
    public ATDataSourceResponse(boolean success, String message, ATDataSet dataSet) {
        super(success,message);
        this.dataSet = dataSet;
    }

    public ATDataSet getDataSet() {
        return dataSet;
    }

    /**
     *
     * @return A data source response indicating the authentication was invalid
     */
    public static ATDataSourceResponse withInvalidAuthentication() {
        ATDataSourceResponse response = new ATDataSourceResponse(false,"Invalid Authentication",null);
        response.setAuthorizationError(true);
        return response;
    }

    /**
     *
     * @param message a String indicating the reason for failing
     * @return A data source response indicating the there was an error
     */
    public static ATDataSourceResponse withFailure(String message) {
        ATDataSourceResponse response = new ATDataSourceResponse(false,message,null);
        return response;
    }

    @Override
    public void setFailedWithMessage(String message) {
        super.setFailedWithMessage(message);
        dataSet = null;
    }

    /**
     *
     * @return The JSONObject for the data source response
     */
    public JSONObject toJSON() {
        JSONObject json = null;
        JSONObject dataJSON = null;

        try {
            json = super.toJSON();
            if ( mSuccess ) {
                if ( dataSet != null ) {
                    dataJSON = dataSet.toJSON();
                    if ( dataJSON != null ) {
                        for ( String key : JSONObject.getNames(dataJSON) ) {
                            json.put(key, dataJSON.get(key));
                        }
                    }
                }
            }
        } catch (ATInvalidPrimaryKeyException pke ) {
            json = new JSONObject();
            json.put("success",false);
            json.put("message","The data set provided contains one or more invalid primary keys");
        }
        return json;
    }
}
