package sdk.converter.attachment;

/**
 * Created by Orozco on 8/8/17.
 */
public abstract class AbstractAttachment implements ApptreeAttachment {
    abstract public String getMimeType();

    abstract public void setMimeType(String mimeType);

    abstract public void setAttachmentURL(String attachmentURL);

    abstract public String getAttachmentURL();

    abstract public void setTitle(String title);

    abstract public String getTitle();
}
