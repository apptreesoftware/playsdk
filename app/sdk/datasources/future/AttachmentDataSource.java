package sdk.datasources.future;

import sdk.datasources.AttachmentDataSourceBase;
import sdkmodels.data.AttachmentResponse;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Matthew Smith on 11/17/16.
 * Copyright AppTree Software, Inc.
 */
public interface AttachmentDataSource extends AttachmentDataSourceBase {
    CompletableFuture<AttachmentResponse> getAttachment(String attachmentID, AuthenticationInfo authenticationInfo, Parameters parameters);
}
