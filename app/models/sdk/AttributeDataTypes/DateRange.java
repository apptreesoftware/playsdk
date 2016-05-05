package models.sdk.AttributeDataTypes;

import org.joda.time.DateTime;

/**
 * Created by alexis on 5/3/16.
 */
public class DateRange {
    private DateTime from;
    private DateTime to;

    public DateRange() {}

    /**
     * Constructs an ATDataRange object
     * @param date1 The earliest date
     * @param date2 The latest date
     */
    public DateRange(DateTime date1, DateTime date2) {
        from = date1;
        to = date2;
    }

    /**
     *
     * @return a DateTime of the earliest date in range
     */
    public DateTime getFromDate() {
        return from;
    }

    /**
     * Sets the earliest date in range
     * @param date DateTime
     */
    public void setFromDate(DateTime date) {
        from = date;
    }

    /**
     *
     * @return a DateTime of the latest date in range
     */
    public DateTime getToDate() {
        return to;
    }

    /**
     * Sets the latest date in range
     * @param date DateTime
     */
    public void setToDate(DateTime date) {
        to = date;
    }
}
