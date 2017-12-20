package sdk.datasources;

import sdk.AppTreeSource;

public interface DashBoardSourceBase extends AppTreeSource {
    /**
     * Return the human readable name you want to provide for this service
     *
     * @return
     */
    String getServiceDescription();
}
