package com.apptree.models;

import org.joda.time.DateTime;
import org.json.JSONObject;

/**
 * Created by alexis on 11/3/15.
 */
public class ATDateRange {
    private DateTime fromDate;
    private DateTime toDate;

    /**
     * Constructs an ATDataRange object
     * @param date1 The earliest date
     * @param date2 The latest date
     */
    public ATDateRange(DateTime date1, DateTime date2) {
        fromDate = date1;
        toDate = date2;
    }

    /**
     * Constructs an ATDateRange object
     * @param json JSONObject to get values from
     */
    public ATDateRange(JSONObject json) {
        if ( json == null ) return;
        String dateString = json.optString("from", null);
        if ( dateString != null ) {
            fromDate = DateTime.parse(dateString, ATDataSetItemAttribute.AppTreeDateFormat);
        }
        dateString = json.optString("to", null);
        if ( dateString != null ) {
            toDate = DateTime.parse(dateString, ATDataSetItemAttribute.AppTreeDateFormat);
        }
    }

    /**
     *
     * @return a DateTime of the earliest date in range
     */
    public DateTime getFromDate() {
        return fromDate;
    }

    /**
     * Sets the earliest date in range
     * @param date DateTime
     */
    public void setFromDate(DateTime date) {
        fromDate = date;
    }

    /**
     *
     * @return a DateTime of the latest date in range
     */
    public DateTime getToDate() {
        return toDate;
    }

    /**
     * Sets the latest date in range
     * @param date DateTime
     */
    public void setToDate(DateTime date) {
        toDate = date;
    }

    /**
     * Converts to a JSONObject
     * @return
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("from", fromDate != null ? fromDate.toString(ATDataSetItemAttribute.AppTreeDateFormat) : null);
        json.put("to", toDate != null ? toDate.toString(ATDataSetItemAttribute.AppTreeDateFormat) : null);
        return json;
    }

}
