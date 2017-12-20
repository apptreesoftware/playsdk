package sdk.datasources.future;

import play.mvc.With;
import sdk.ValidateRequestAction;
import sdk.dashboard.DashBoard;
import sdk.datasources.DashBoardSourceBase;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.concurrent.CompletableFuture;

public interface DashBoardSource extends DashBoardSourceBase{

    /**
     * @param authenticationInfo An object containing all the relevant authentication information
     * @param parameters An object containing the query parameters in the request
     * @return Dash board containing one or more cards
     */
    CompletableFuture<DashBoard> getItems(AuthenticationInfo authenticationInfo, Parameters parameters);
}
