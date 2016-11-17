package sdk.data;

import java.io.InputStream;

/**
 * Created by matthew on 5/16/16.
 */
public class AttachmentResponse {
    public InputStream inputStream;
    public String contentType;
    public String fileName;

    public AttachmentResponse(InputStream inputStream, String contentType, String fileName) {
        this.inputStream = inputStream;
        this.contentType = contentType;
        this.fileName = fileName;
    }
}
