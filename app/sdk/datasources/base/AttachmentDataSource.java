package sdk.datasources.base;

import sdk.datasources.AttachmentDataSourceBase;
import sdkmodels.data.AttachmentResponse;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;

/**
 * Created by matthew on 5/16/16.
 */
public interface AttachmentDataSource extends AttachmentDataSourceBase {
    AttachmentResponse getAttachment(String attachmentID, AuthenticationInfo authenticationInfo, Parameters parameters);
}
