package sdk.datasources;

import sdk.dashboard.Dashboard;
import sdk.datasources.base.DashboardSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.concurrent.CompletableFuture;

public class DashboardSource_Internal extends BaseSource_Internal {
    DashboardSource baseDataSource;
    sdk.datasources.future.DashboardSource futureSource;
    sdk.datasources.rx.DashboardSource rxSource;

    public DashboardSource_Internal(DashboardSourceBase base) {
        if (base instanceof DashboardSource) {
            baseDataSource = (DashboardSource) base;
        } else if (base instanceof sdk.datasources.future.DashboardSource) {
            futureSource = (sdk.datasources.future.DashboardSource) base;
        } else if (base instanceof sdk.datasources.rx.DashboardSource) {
            rxSource = (sdk.datasources.rx.DashboardSource) base;
        }
    }


    public CompletableFuture<Dashboard> getItems(AuthenticationInfo authenticationInfo, Parameters parameters) {
        if(baseDataSource != null) {
            return CompletableFuture.supplyAsync(() -> baseDataSource.getItems(authenticationInfo, parameters));
        } else if (futureSource != null) {
            return futureSource.getItems(authenticationInfo, parameters);
        } else if (rxSource != null) {
            return observableToFuture(rxSource.getItems(authenticationInfo, parameters));
        }
        throw new RuntimeException("No data source available");
    }
}
