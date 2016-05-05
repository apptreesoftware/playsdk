package com.apptree.models;

import java.util.HashMap;

/**
 * Created by Alexis Andreason on 2/5/15.
 */
public abstract class ATDefaultDataSource {

    /***
     * Returns the REST path endpoint you want to use for this service. Any path you specify will result in a URL of http://{server-url}/apptree/{path}
     * @return A string that you want to use for the endpoint
     */
    public abstract String dataSourceRESTPath();

    /**
     *
     * @param authInfo A HashMap of any authentication parameters included in the request headers
     * @param params A HAshMap of the URL parameters included in the request
     * @return A default data source response containing default values for a data set
     */
    public abstract ATDefaultDataSourceResponse getDefaults(AuthenticationInfo authenticationInfo, HashMap<String,String> params);
}
