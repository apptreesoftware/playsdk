package com.apptree.models;

import org.apache.commons.fileupload.FileItem;

/**
 * Created by matthew on 1/20/15.
 */
public class ATAttachmentUploadItem {
    String mFileName;
    String mID;
    FileItem mFileItem;

    /**
     * Create an attachment upload item
     * @param fileName The file name of the attachment
     * @param id The ID of the attachment
     * @param fileItem The file item of the attachment
     */
    public ATAttachmentUploadItem(String fileName, String id, FileItem fileItem) {
        mFileName = fileName;
        mID = id;
        mFileItem = fileItem;
    }

    /**
     * Gets the file name
     * @return The file name of the attachment
     */
    public String getFileName() {
            return mFileName;
        }

    /**
     * Gets the ID
     * @return The ID of the attachment
     */
    public String getID() {
        return mID;
    }

    /**
     * Gets the file item
     * @return The file item of the attachment
     */
    public FileItem getFileItem() {
        return mFileItem;
    }
}
