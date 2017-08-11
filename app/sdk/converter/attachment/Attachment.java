package sdk.converter.attachment;

import java.io.FileInputStream;

/**
 * Created by Orozco on 8/10/17.
 */
public class Attachment implements ApptreeAttachment {

    String mimeType;
    String attachmentURL;
    String title;


    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }

    @Override
    public String getAttachmentURL() {
        return attachmentURL;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setFileInputStream(FileInputStream inputStream) {

    }
}
