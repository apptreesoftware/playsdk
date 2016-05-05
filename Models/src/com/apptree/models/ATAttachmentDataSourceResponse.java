package com.apptree.models;

import java.io.InputStream;

/**
 * Created by alexis on 8/10/15.
 */
public class ATAttachmentDataSourceResponse extends ATResponse {
    InputStream mAttachmentStream;
    String mContentType;

    /**
     * Creates an attachment data source resposne
     * @param success A boolean indicating the success of the call
     * @param message A String message about the call
     * @param attachmentStream An input stream of the attachment
     * @param contentType A String indicating the type of attachment being sent
     */
    public ATAttachmentDataSourceResponse(boolean success, String message, InputStream attachmentStream, String contentType) {
        super(success, message);
        mAttachmentStream = attachmentStream;
        mContentType = contentType;
    }

    /**
     * Sets the input stream for the attachment
     * @param stream
     */
    public void setAttachmentStream(InputStream stream) {
        mAttachmentStream = stream;
    }

    /**
     *
     * @return The input attachment stream
     */
    public InputStream getAttachmentStream() {
        return this.mAttachmentStream;
    }

    /**
     * Sets the String indicating the type of attachment
     * @param contentType
     */
    public void setContentType(String contentType) {
        mContentType = contentType;
    }

    /**
     *
     * @return The String indicating the type of attachment
     */
    public String getContentType() {
        return this.mContentType;
    }
}
