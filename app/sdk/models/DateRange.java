package sdk.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import sdk.utils.Constants;
import org.joda.time.DateTime;
import play.libs.Json;

/**
 * Created by alexis on 5/3/16.
 */
public class DateRange extends SDKDateRange {
    public DateRange() {
    }

    /**
     * Constructs an ATDataRange object
     *
     * @param date1 The earliest date
     * @param date2 The latest date
     */
    public DateRange(DateTime date1, DateTime date2) {
        super.setFrom(date1);
        super.setTo(date2);
    }


    @Override
    public ObjectNode toJSON() {
        ObjectNode json = Json.newObject();
        if (super.getFrom() != null) {
            json.put("from", Constants.AppTreeDateFormat.print(super.getFrom()));
        }
        if (super.getTo() != null) {
            json.put("to", Constants.AppTreeDateFormat.print(super.getTo()));
        }
        return json;
    }
}
