package com.apptree.models;

import org.apache.commons.fileupload.FileItem;

import java.lang.Exception;
import java.util.HashMap;

/**
 * Created by matthew on 1/20/15.
 */


public class ATDataSetAttachmentItem extends ATDataSetItem {

    private static int AttachmentAttributeTitle = 1;
    private static int AttachmentAttributeLink = 2;
    private static int AttachmentAttributeImage = 3;
    private static int AttachmentAttributeText = 4;
    private static int AttachmentAttributeTextEntity = 5;
    private static int AttachmentAttributeVideo = 6;
    private static int AttachmentAttributeFile = 7;
    private static int AttachmentAttributeEditable = 8;
    private static int AttachmentAttributeDeletable = 9;

    FileItem attachmentFileItem;
    private static HashMap<Integer, ATServiceConfigurationAttribute> configurationAttributeMap = new HashMap<Integer, ATServiceConfigurationAttribute>();

    static {
        configurationAttributeMap.put(AttachmentAttributeTitle, new ATServiceConfigurationAttribute.Builder(AttachmentAttributeTitle).build());
        configurationAttributeMap.put(AttachmentAttributeLink, new ATServiceConfigurationAttribute.Builder(AttachmentAttributeLink).build());
        configurationAttributeMap.put(AttachmentAttributeImage, new ATServiceConfigurationAttribute.Builder(AttachmentAttributeImage).build());
        configurationAttributeMap.put(AttachmentAttributeText, new ATServiceConfigurationAttribute.Builder(AttachmentAttributeText).build());
        configurationAttributeMap.put(AttachmentAttributeVideo, new ATServiceConfigurationAttribute.Builder(AttachmentAttributeVideo).build());
        configurationAttributeMap.put(AttachmentAttributeFile, new ATServiceConfigurationAttribute.Builder(AttachmentAttributeFile).build());
        configurationAttributeMap.put(AttachmentAttributeEditable, new ATServiceConfigurationAttribute.Builder(AttachmentAttributeEditable).asBool().build());
        configurationAttributeMap.put(AttachmentAttributeDeletable, new ATServiceConfigurationAttribute.Builder(AttachmentAttributeDeletable).asBool().build());
    }


    ATDataSetAttachmentItem() {
        super(configurationAttributeMap);
    }

    /**
     * Sets the file item
     * @param fileItem
     */
    public void setAttachmentFile(FileItem fileItem) {
        attachmentFileItem = fileItem;
    }

    /**
     * Gets the file item
     * @return attachment file item
     */
    public FileItem getAttachmentFile() {
        return attachmentFileItem;
    }

    /**
     * Sets the attachment URL used when the attachment is an image
     * @param imageAttachmentURL The url for the attachment stream
     */
    public void setImageAttachmentURL(String imageAttachmentURL) {
        try {
            setStringForAttributeIndex(imageAttachmentURL, AttachmentAttributeImage);
        } catch (Exception e) {}
    }

    /**
     * Gets the image attachment URL
     * @return
     */
    public String getImageAttachmentURL() {
        return getStringAttributeAtIndex(AttachmentAttributeImage);
    }

    /**
     * Sets the text when the attachment is a note
     * @param note The url for the attachment stream
     */
    public void setNoteAttachment(String note) {
        try {
            setStringForAttributeIndex(note, AttachmentAttributeText);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the note
     * @return
     */
    public String getNoteAttachment() {
        return getStringAttributeAtIndex(AttachmentAttributeText);
    }

    /**
     * Sets the link string when the attachment is a link
     * @param link
     */
    public void setLinkAttachment(String link) {
        try {
            setStringForAttributeIndex(link, AttachmentAttributeLink);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the link
     * @return
     */
    public String getLinkAttachment() {
        return getStringAttributeAtIndex(AttachmentAttributeLink);
    }

    /**
     * Sets the attachment URL used when the attachment is an video
     * @param videoURL The url for the attachment stream
     */
    public void setVideoAttachmentURL(String videoURL) {
        try {
            setStringForAttributeIndex(videoURL, AttachmentAttributeVideo);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the video attachment url
     * @return
     */
    public String getVideoAttachmentURL() {
        return getStringAttributeAtIndex(AttachmentAttributeVideo);
    }

    /**
     * Sets the attachment URL used when the attachment is a file
     * @param fileAttachmentURL The url for the attachment stream
     */
    public void setFileAttachmentURL(String fileAttachmentURL) {
        try {
            setStringForAttributeIndex(fileAttachmentURL, AttachmentAttributeFile);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the attachment URL used when the attachment is a file
     */
    public String getFileAttachmentURL() {
        return getStringAttributeAtIndex(AttachmentAttributeFile);
    }

    /**
     * Sets the title of the attachment
     * @param title
     */
    public void setTitle(String title) {
        try {
            setStringForAttributeIndex(title, AttachmentAttributeTitle);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the title
     * @return
     */
    public String getTitle() {
        return getStringAttributeAtIndex(AttachmentAttributeTitle);
    }

    /**
     * Returns the attachment type
     * @return
     */

    public Boolean isDeletable() {
        return getBooleanAttributeAtIndex(AttachmentAttributeDeletable);
    }

    public void setIsDeletable(boolean deletable) {
        try {
            setBooleanForAttributeIndex(deletable, AttachmentAttributeDeletable);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    public Boolean isEditable() {
        return getBooleanAttributeAtIndex(AttachmentAttributeDeletable);
    }

    public void setIsEditable(boolean editable) {
        try {
            setBooleanForAttributeIndex(editable, AttachmentAttributeEditable);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    public Type getItemType() {
        return Type.Attachment;
    }

}
