package sdk.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import sdk.utils.Constants;
import org.joda.time.DateTime;
import play.libs.Json;

/**
 * Created by alexis on 5/3/16.
 */
public class DateTimeRange extends SDKDateRange {

    public DateTimeRange() {
    }

    /**
     * Constructs an ATDateTimeRange object
     *
     * @param date1 the earliest date in range
     * @param date2 the latest date in range
     */
    public DateTimeRange(DateTime date1, DateTime date2) {
        super.setFrom(date1);
        super.setTo(date2);
    }

    @Override
    public ObjectNode toJSON() {
        ObjectNode json = Json.newObject();
        if (getFrom() != null) {
            json.put("from", Constants.AppTreeDateTimeFormat.print(getFrom()));
        }
        if (getTo() != null) {
            json.put("to", Constants.AppTreeDateTimeFormat.print(getTo()));
        }
        return json;
    }
}
