package sdk.converter.attachment;

import java.io.InputStream;

/**
 * Created by Orozco on 8/10/17.
 */
public interface ApptreeAttachment {

    String getMimeType();

    void setMimeType(String mimeType);

    void setAttachmentURL(String attachmentURL);

    String getAttachmentURL();

    void setTitle(String title);

    String getTitle();

//    Called when a new attachment is uploaded. You must read from this stream to wherever you want to store the attachment.
//    Be sure to close the stream when you're done.
     void setInputStream(InputStream inputStream);
}
