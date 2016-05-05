package com.apptree.models;

import org.apache.commons.fileupload.FileItem;
import org.json.JSONObject;

/**
 * Created by Matthew Smith on 9/28/15.
 * Copyright AppTree Software, Inc.
 */
public class ATImage {
    private FileItem attachmentFileItem;
    String imageURL;
    String uploadKey;

    /**
     * Constructs an ATImage object
     * @param imageURL a String of the URL for the image
     */
    public ATImage(String imageURL) {
        this.imageURL = imageURL;
    }

    public ATImage(JSONObject json) {
        this.uploadKey = json.optString("uploadKey");
        this.imageURL = json.optString("imageURL");
    }

    /**
     *
     * @return the FileItem of the attachment
     */
    public FileItem getAttachmentFileItem() {
        return attachmentFileItem;
    }

    /**
     * Sets the attachment FileItem
     * @param attachmentFileItem
     */
    public void setAttachmentFileItem(FileItem attachmentFileItem) {
        this.attachmentFileItem = attachmentFileItem;
    }

    /**
     *
     * @return a String of the image URL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Sets the image URL
     * @param imageURL a String of the image URL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     *
     * @return a boolean indicating if the attachment item has been updated
     */
    public boolean isUpdated() {
        return uploadKey != null && uploadKey.length() > 0;
    }

    /**
     * Converts the ATImage to a JSONObject
     * @return
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.putOpt("imageURL", this.imageURL);
        json.putOpt("uploadKey", this.uploadKey);
        return json;
    }

}
