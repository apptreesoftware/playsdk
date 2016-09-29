package sdk.inspection;

import org.jetbrains.annotations.NotNull;
import sdk.data.ServiceConfiguration;

/**
 * Created by Matthew Smith on 8/30/16.
 * Copyright AppTree Software, Inc.
 */
public class InspectionConfiguration {
    private ServiceConfiguration inspectionServiceConfiguration;
    private ServiceConfiguration inspectionSearchConfiguration;
    private boolean shouldSendIncrementalUpdates;

    public InspectionConfiguration(@NotNull ServiceConfiguration inspectionServiceConfiguration, @NotNull ServiceConfiguration inspectionSearchConfiguration, boolean incrementalUpdates) {
        this.inspectionSearchConfiguration = inspectionSearchConfiguration;
        this.inspectionServiceConfiguration = inspectionServiceConfiguration;
        this.shouldSendIncrementalUpdates = incrementalUpdates;
    }

    public ServiceConfiguration getInspectionServiceConfiguration() {
        return inspectionServiceConfiguration;
    }

    public ServiceConfiguration getInspectionSearchConfiguration() {
        return inspectionSearchConfiguration;
    }

    public boolean isShouldSendIncrementalUpdates() {
        return shouldSendIncrementalUpdates;
    }
}
