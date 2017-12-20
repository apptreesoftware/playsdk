package sdk.datasources;

import sdk.AppTreeSource;

public interface DashboardSourceBase extends AppTreeSource {
    /**
     * Return the human readable name you want to provide for this service
     *
     * @return
     */
    String getServiceDescription();
}
