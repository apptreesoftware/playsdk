package sdk.data;

import org.joda.time.DateTime;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InspectionData<T> {
    private DateTime startDate;
    private DateTime endDate;
    private InspectionDataSet.CollectionState status;
    private Map<String, String> context = new HashMap<>();
    private Collection<T> items;

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

    public InspectionDataSet.CollectionState getStatus() {
        return status;
    }

    public void setStatus(InspectionDataSet.CollectionState status) {
        this.status = status;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }
}
