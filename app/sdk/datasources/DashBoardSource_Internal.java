package sdk.datasources;

import sdk.dashboard.DashBoard;
import sdk.datasources.base.DashBoardSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.concurrent.CompletableFuture;

public class DashBoardSource_Internal extends BaseSource_Internal {
    DashBoardSource baseDataSource;
    sdk.datasources.future.DashBoardSource futureSource;
    sdk.datasources.rx.DashBoardSource rxSource;

    public DashBoardSource_Internal(DashBoardSourceBase base) {
        if (base instanceof DashBoardSource) {
            baseDataSource = (DashBoardSource) base;
        } else if (base instanceof sdk.datasources.future.DashBoardSource) {
            futureSource = (sdk.datasources.future.DashBoardSource) base;
        } else if (base instanceof sdk.datasources.rx.DashBoardSource) {
            rxSource = (sdk.datasources.rx.DashBoardSource) base;
        }
    }


    public CompletableFuture<DashBoard> getItems(AuthenticationInfo authenticationInfo, Parameters parameters) {
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
