package sdk.datasources;

import sdk.datasources.future.AttachmentDataSource;
import sdkmodels.data.AttachmentResponse;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Matthew Smith on 11/17/16.
 * Copyright AppTree Software, Inc.
 */
public class AttachmentDataSource_Internal extends BaseSource_Internal {
    private sdk.datasources.base.AttachmentDataSource baseDataSource;
    private AttachmentDataSource futureDataSource;
    private sdk.datasources.rx.AttachmentDataSource rxDataSource;

    public AttachmentDataSource_Internal(sdk.datasources.base.AttachmentDataSource dataSource) {
        this.baseDataSource = dataSource;
    }
    public AttachmentDataSource_Internal(sdk.datasources.rx.AttachmentDataSource dataSource) {
        this.rxDataSource = dataSource;
    }
    public AttachmentDataSource_Internal(AttachmentDataSource dataSource) {
        this.futureDataSource = dataSource;
    }

    public CompletableFuture<AttachmentResponse> getAttachment(String attachmentID, AuthenticationInfo authenticationInfo, Parameters parameters) {
        CompletableFuture<AttachmentResponse> attachmentFuture = null;
        if ( rxDataSource != null ) {
            attachmentFuture = observableToFuture(rxDataSource.getAttachment(attachmentID, authenticationInfo, parameters));
        } else if ( baseDataSource != null ) {
            attachmentFuture = CompletableFuture.supplyAsync(() -> baseDataSource.getAttachment(attachmentID, authenticationInfo, parameters));
        } else if ( futureDataSource != null ) {
            attachmentFuture = futureDataSource.getAttachment(attachmentID, authenticationInfo, parameters);
        } else {
            throw new RuntimeException("No data source available");
        }
        return attachmentFuture;
    }
}
