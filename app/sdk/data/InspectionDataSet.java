package sdk.data;

import org.joda.time.DateTime;

import java.util.Collection;

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
}
