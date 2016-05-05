package com.apptree.models;

import com.apptree.models.ATResponse;
import com.apptree.models.ATDefaults;
import org.json.JSONObject;

/**
 * Created by Alexis Andreason on 2/5/15.
 */
public class ATDefaultDataSourceResponse extends ATResponse {
    private ATDefaults mFormDefaults;

    /**
     * Creates a default data source response
     * @param success A boolean indicating the success of the call
     * @param message A String message about the call optional
     * @param defaults the defaults to be returned
     */
    public ATDefaultDataSourceResponse(boolean success, String message, ATDefaults defaults) {
        super(success, message);
        mFormDefaults = defaults;
    }

    /**
     *
     * @return The defaults to be returned
     */
    public ATDefaults getFormDefaults() {
        return mFormDefaults;
    }

    /**
     * Sets the form defaults
     * @param formDefaults
     */
    public void setFormDefaults(ATDefaults formDefaults) {
        mFormDefaults = formDefaults;
    }

    /**
     *
     * @return A JSONObject of the default data source response
     */
    public JSONObject toJSON() {
        if ( mFormDefaults != null ) {
            setRecords(mFormDefaults.toJSONArray());
        }
        return super.toJSON();
    }
}
