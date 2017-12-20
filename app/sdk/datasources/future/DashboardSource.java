package sdk.datasources.future;

import sdk.dashboard.Dashboard;
import sdk.datasources.DashboardSourceBase;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.concurrent.CompletableFuture;

public interface DashboardSource extends DashboardSourceBase {

    /**
     * @param authenticationInfo An object containing all the relevant authentication information
     * @param parameters An object containing the query parameters in the request
     * @return Dash board containing one or more cards
     */
    CompletableFuture<Dashboard> getItems(AuthenticationInfo authenticationInfo, Parameters parameters);
}
