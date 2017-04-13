package sdk.datasources.rx;

import rx.Observable;
import sdk.datasources.AttachmentDataSourceBase;
import sdkmodels.data.AttachmentResponse;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;

/**
 * Created by Matthew Smith on 11/17/16.
 * Copyright AppTree Software, Inc.
 */
public interface AttachmentDataSource extends AttachmentDataSourceBase {
    Observable<AttachmentResponse> getAttachment(String attachmentID, AuthenticationInfo authenticationInfo, Parameters parameters);
}
