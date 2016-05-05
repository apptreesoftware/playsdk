package com.apptree.models;

import org.json.JSONObject;

/**
 * Created by Alexis Andreason on 11/6/14.
 */
public class ATConfigurationResponse extends ATResponse {
    private ATServiceConfiguration mConfiguration;

    /**
     * Creates a configuration response
     * @param success A boolean indicating the success of the call
     * @param message A String message about the call optional
     * @param configuration A service configuration containing the attributes
     */
    public ATConfigurationResponse(boolean success, String message, ATServiceConfiguration configuration) {
        super(success, message);
        mConfiguration = configuration;
    }

    /**
     *
     * @return The configuration of the service
     */
    public ATServiceConfiguration getConfiguration() { return mConfiguration; }

    /**
     * Sets the configuration of a service
     * @param configuration
     */
    public void setConfiguration(ATServiceConfiguration configuration) { this.mConfiguration = configuration; }

    /**
     *
     * @return A JSONObject of the configuration response
     */
    public JSONObject toJSON() {
        if ( mConfiguration != null ) {
            setRecord(mConfiguration.toJSON());
        }
        return super.toJSON();
    }
}