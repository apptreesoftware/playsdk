package sdk.datasources;

import sdk.AppTreeSource;
import sdk.data.DataSet;
import sdk.data.ServiceConfigurationAttribute;

import java.util.Collection;

/**
 * Created by matthew on 9/6/16.
 */
public interface InspectionSourceBase extends AppTreeSource {
    Collection<ServiceConfigurationAttribute> getInspectionItemAttributes();
    Collection<ServiceConfigurationAttribute> getInspectionSearchAttributes();

    default boolean shouldSendIncrementalUpdates() {
        return false;
    }

    String getServiceName();

    default DataSet newEmptyInspectionDataSet() {
        Collection<ServiceConfigurationAttribute> attributes = getInspectionItemAttributes();
        return new DataSet(attributes);
    }
}
