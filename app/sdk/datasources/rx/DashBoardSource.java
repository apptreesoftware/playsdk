package sdk.datasources.rx;

import sdk.dashboard.DashBoard;
import sdk.datasources.DashBoardSourceBase;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import rx.Observable;

public interface DashBoardSource extends DashBoardSourceBase {

    /**
     * @param authenticationInfo An object containing all the relevant authentication information
     * @param parameters An object containing the query parameters in the request
     * @return Dash board containing one or more cards
     */
    Observable<DashBoard> getItems(AuthenticationInfo authenticationInfo, Parameters parameters);
}
