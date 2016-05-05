package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by alexis on 12/9/15.
 */
public class ATDataCollectionConfigurationResponse extends ATResponse {
    ATDataCollectionServiceConfiguration configuration;
    int exceptionAttributeIndex;
    boolean exceptionListDefined;

    /**
     * Creates an ATDataCollectionConfigurationResponse
     * @param success a boolean indicating the success of the call
     * @param message a message pertaining to the call
     * @param configuration the ATDataCollectionConfiguration (null if the call was not successful)
     * @param exceptionAttributeIndex the index of the attribute in the configuration used for storing the exception message/type
     * @param exceptionListDefined a boolean indicating whether or not an exception list was defined for this data collection
     */
    public ATDataCollectionConfigurationResponse(boolean success, String message, ATDataCollectionServiceConfiguration configuration, int exceptionAttributeIndex, boolean exceptionListDefined) {
        super(success, message);
        this.configuration = configuration;
        this.exceptionAttributeIndex = exceptionAttributeIndex;
        this.exceptionListDefined = exceptionListDefined;
    }

    /**
     *
     * @return the ATDataCollectionServiceConfiguration for this response
     */
    public ATDataCollectionServiceConfiguration getConfiguration() { return configuration; }

    /**
     * Converts the response to a JSONObject
     * @return
     */
    public JSONObject toJSON() {
        JSONObject recordObject;
        if ( configuration != null ) {
            recordObject = configuration.toJSON();
            recordObject.putOpt("exceptionAttributeIndex", exceptionAttributeIndex);
            recordObject.putOpt("exceptionListDefined", exceptionListDefined);
            setRecord(recordObject);
        }
        return super.toJSON();
    }
}
