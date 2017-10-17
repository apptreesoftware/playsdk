package sdk.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import sdk.utils.Constants;
import org.joda.time.DateTime;
import play.libs.Json;

/**
 * Created by alexis on 5/3/16.
 */
public class DateTimeRange {
    private DateTime from;
    private DateTime to;

    public DateTimeRange() {}

    /**
     * Constructs an ATDateTimeRange object
     * @param date1 the earliest date in range
     * @param date2 the latest date in range
     */
    public DateTimeRange(DateTime date1, DateTime date2) {
        from = date1;
        to = date2;
    }

    /**
     *
     * @return a DateTime of the earliest date in range
     */
    public DateTime getFrom() {
        return from;
    }

    /**
     * Sets the earliest date in range
     * @param date DateTime
     */
    public void setFrom(DateTime date) {
        from = date;
    }

    /**
     *
     * @return a DateTime of the latest date in range
     */
    public DateTime getTo() {
        return to;
    }

    /**
     * Sets the latest date in range
     * @param date DateTime
     */
    public void setTo(DateTime date) {
        to = date;
    }

    public ObjectNode toJSON() {
        ObjectNode json = Json.newObject();
        if ( from != null ) {
            json.put("from", Constants.AppTreeDateTimeFormat.print(from));
        }
        if ( to != null ) {
            json.put("to", Constants.AppTreeDateTimeFormat.print(to));
        }
        return json;
    }
}
