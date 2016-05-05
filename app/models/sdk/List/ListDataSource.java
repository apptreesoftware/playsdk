package models.sdk.List;

import models.sdk.Utils.AuthenticationInfo;
import models.sdk.Utils.Parameters;

/**
 * Created by alexis on 5/4/16.
 */
public abstract class ListDataSource {
    /**
     *
     * @return A list service configuration containing the attributes of a list
     */
    public abstract ListServiceConfiguration getDescription();

    /**
     *
     * @param queryText A String to be searched for in the list
     * @param barcodeSearch A boolean indicating if the list is searching a barcode
     * @param authenticationInfo A HashMap of any authentication parameters included in the request headers
     * @param params A HashMap of the URL parameters included in the request
     * @return A list data source response containing list items that satisfy the search oarameters
     */
    public abstract List queryList(String queryText, boolean barcodeSearch, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     *
     * @param authenticationInfo A HashMap of any authentication parameters that were included in the request headers
     * @param params A HashMap of the URL parameters included in the request
     * @return A list data source response which contains the list items
     */
    public abstract List getList(AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Can this list be cached to the device. Returning true to this means you must implement getList
     * @returna boolean indicating whether this list can be cached
     */
    public abstract boolean canCache();

    /**
     * Can this list be searched. Returning true to this means you must implement queryList
     * @return a boolean indicating whether this list can be searched
     */
    public abstract boolean canSearch();

    /**
     * Use this method to specify which list attribute is the user ID. Returns -1 by default.
     * Returning an attribute index will indicate to the client that this list is associated to a list of users.
     * The client will attempt to combine the data in this list with any user information stored in the
     * core providing a richer user experience when selecting a user from a list
     * @return int specifying the index of the userID data.
     */

    public int userIDIndex() {
        return -1;
    }

    /**
     *
     * @return the name of the list service
     */
    public abstract String getServiceName();
}
