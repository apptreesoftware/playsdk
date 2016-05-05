package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by alexis on 12/8/15.
 */
public class ATDataCollectionStartResponse extends ATResponse {
    private String mDataCollectionID;

    /**
     * Constructor for an ATDataCollectionStartResponse
     * @param success a boolean indicating the success of the call
     * @param dataCollectionID The id of the generated data collection session
     * @param message a String message pertaining to the data collection beginning
     */
    public ATDataCollectionStartResponse(boolean success, String dataCollectionID, String message) {
        super(success, message);
        mDataCollectionID = dataCollectionID;
    }

    /**
     *
     * @return the String ID of this data collection session
     */
    public String getDataCollectionID() { return mDataCollectionID; }

    /**
     *
     * @return a JSONObject of the data collection start response
     */
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.putOpt("dataCollectionID", mDataCollectionID);
        return jsonObject;
    }
}
