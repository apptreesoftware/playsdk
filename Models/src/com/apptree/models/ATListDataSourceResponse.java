package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by londynnmetten on 11/10/14.
 */
public class ATListDataSourceResponse extends ATResponse {
    private ATList mList;

    /**
     * Create a list data source response
     * @param success A boolean indicating the success of the call
     * @param list The list returned from the call
     * @param message A Strign message about the call optional
     */
    public ATListDataSourceResponse(boolean success, ATList list, String message) {
        super(success,message);
        mSuccess = success;
        mList = list;
    }

    /**
     * Creates a list data source response
     * @param success A boolean indicating the success of the call
     * @param message A String message about the call
     * @param authorizationError A boolean indicating whether there was an authorization error
     */
    public ATListDataSourceResponse(boolean success, String message, boolean authorizationError) {
        super(success,message,authorizationError);
    }

    /**
     *
     * @return Returns a list data source response which indicates the authentication was invalid
     */
    public static ATListDataSourceResponse withInvalidAuthentication() {
        return new ATListDataSourceResponse(false,"Invalid Authentication",true);
    }

    /**
     *
     * @return The list
     */
    public ATList getList() {
        return mList;
    }

    /**
     *
     * @return The JSONObject for the list data source response
     */
    public JSONObject toJSON() {
        if ( mList != null ) {
            setRecords(mList.toJSONArray());
        }
        return super.toJSON();
    }
}
