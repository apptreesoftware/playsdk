package sdk.attachment;

import sdk.AppTreeSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

/**
 * Created by matthew on 5/16/16.
 */
public interface AttachmentDataSource extends AppTreeSource {
    AttachmentResponse getAttachment(String attachmentID, AuthenticationInfo authenticationInfo, Parameters parameters);
}
