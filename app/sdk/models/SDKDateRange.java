package sdk.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;

/**
 * Created by Orozco on 9/19/17.
 */
public abstract class SDKDateRange {
    private DateTime from;
    private DateTime to;


    abstract ObjectNode toJSON();

    public boolean isEmpty() {
        return (from == null || to == null);
    }

    public DateTime getFrom() {
        return from;
    }

    public void setFrom(DateTime from) {
        this.from = from;
    }

    public DateTime getTo() {
        return to;
    }

    public void setTo(DateTime to) {
        this.to = to;
    }
}
