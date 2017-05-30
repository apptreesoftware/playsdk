package sdk.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import sdk.utils.JsonUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthew on 12/6/16.
 */
public class InspectionDataSet extends DataSet {
    public enum CollectionState {
        Completed,
        Canceled
    }

    private DateTime startDate;
    private DateTime endDate;
    private CollectionState status;
    private Map<String,String> context = new HashMap<>();

    public InspectionDataSet(Collection<ServiceConfigurationAttribute> configurationAttributes) {
        super(configurationAttributes);
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public CollectionState getStatus() {
        return status;
    }

    public void setStatus(CollectionState status) {
        this.status = status;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public void setContextValue(@Nonnull String key, @Nonnull String value) {
        if ( context == null ) {
            context = new HashMap<>();
        }
        context.put(key, value);
    }

    public @Nullable String getContextForKey(String key) {
        if ( context == null ) return null;
        return context.get(key);
    }

    @Override
    public ObjectNode toJSON() throws InvalidPrimaryKeyException {
        ObjectNode node = super.toJSON();
        Map<String,String> context = this.context != null ? this.context : new HashMap<>();
        JsonNode contextNode = JsonUtils.toJson(context);
        node.set("context", contextNode);
        return node;
    }
}
