package sdk.converter.attachment;

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
}
