package com.apptree.models;

import java.util.HashMap;

/**
 * Created by Alexis Andreason on 7/22/15.
 */
public abstract class ATAttachmentDataSource {

    /**
     *
     * @param authInfo a HashMap of any authentication parameters that were included in the request
     * @param urlParams A HashMap of the URL parameters included in the request
     * @param attachmentID The attachment ID
     * @return Returns an attachment data source resposne which includes the stream for the attachment and the attachment type
     */
    public abstract ATAttachmentDataSourceResponse getAttachment(AuthenticationInfo authenticationInfo, Parameters urlParams, String attachmentID);

    /***
     * Returns the REST path endpoint you want to use for this service. Any path you specify will result in a URL of http://{server-url}/apptree/{path}
     * @return A string that you want to use for the endpoint
     */
    public abstract String dataSourceRESTPath();

    /**
     * Returns the service name you want to use for this service
     * @return a string you want to use for the service name
     */
    public abstract String getServiceName();
}
