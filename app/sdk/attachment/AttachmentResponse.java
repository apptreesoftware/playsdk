package sdk.attachment;

import sdk.utils.Response;

import java.io.InputStream;

/**
 * Created by matthew on 5/16/16.
 */
public class AttachmentResponse {
    public InputStream inputStream;
    public String contentType;

    public AttachmentResponse(InputStream inputStream, String contentType) {
        this.inputStream = inputStream;
        this.contentType = contentType;
    }
}
