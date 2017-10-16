package sdk.data;

import org.apache.commons.io.IOUtils;
import play.mvc.Http;
import sdk.utils.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by matthew on 1/20/15.
 */


public class DataSetItemAttachment extends DataSetItem {

    public static final int AttachmentAttributeTitle = 1;
    public static final int AttachmentAttributeLink = 2;
    public static final int AttachmentAttributeImage = 3;
    public static final int AttachmentAttributeText = 4;
    public static final int AttachmentAttributeTextEntity = 5;
    public static final int AttachmentAttributeVideo = 6;
    public static final int AttachmentAttributeFile = 7;
    public static final int AttachmentAttributeEditable = 8;
    public static final int AttachmentAttributeDeletable = 9;
    public static final int AttachmentAttributeMimeType = 10;
    public static final int AttachmentAttributeFileName = 11;

    Http.MultipartFormData.FilePart attachmentFileItem;
    private static HashMap<Integer, ServiceConfigurationAttribute> configurationAttributeMap = new HashMap<Integer, ServiceConfigurationAttribute>();

    static {
        configurationAttributeMap.put(AttachmentAttributeTitle, new ServiceConfigurationAttribute.Builder(AttachmentAttributeTitle).build());
        configurationAttributeMap.put(AttachmentAttributeLink, new ServiceConfigurationAttribute.Builder(AttachmentAttributeLink).build());
        configurationAttributeMap.put(AttachmentAttributeImage, new ServiceConfigurationAttribute.Builder(AttachmentAttributeImage).build());
        configurationAttributeMap.put(AttachmentAttributeText, new ServiceConfigurationAttribute.Builder(AttachmentAttributeText).build());
        configurationAttributeMap.put(AttachmentAttributeVideo, new ServiceConfigurationAttribute.Builder(AttachmentAttributeVideo).build());
        configurationAttributeMap.put(AttachmentAttributeFile, new ServiceConfigurationAttribute.Builder(AttachmentAttributeFile).build());
        configurationAttributeMap.put(AttachmentAttributeEditable, new ServiceConfigurationAttribute.Builder(AttachmentAttributeEditable).asBool().build());
        configurationAttributeMap.put(AttachmentAttributeDeletable, new ServiceConfigurationAttribute.Builder(AttachmentAttributeDeletable).asBool().build());
        configurationAttributeMap.put(AttachmentAttributeMimeType, new ServiceConfigurationAttribute.Builder(AttachmentAttributeMimeType).build());
        configurationAttributeMap.put(AttachmentAttributeFileName, new ServiceConfigurationAttribute.Builder(AttachmentAttributeFileName).build());
    }


    public DataSetItemAttachment() {
        super(configurationAttributeMap);
    }

    public Http.MultipartFormData.FilePart getAttachmentFileItem() {
        return attachmentFileItem;
    }

    public byte[] getAttachmentBytes() {
        if (attachmentFileItem != null) {
            File file = (File) attachmentFileItem.getFile();
            if (file != null) {
                try {
                    return IOUtils.toByteArray(new FileInputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void resizeTo(int width, int height) {
        Http.MultipartFormData.FilePart oldPart = this.getAttachmentFileItem();
        if (oldPart == null) {
            throw new RuntimeException("The image you are trying to resize is empty.");
        }
        File currentFile = (File) this.getAttachmentFileItem().getFile();
        if (currentFile == null) {
            throw new RuntimeException("The Image file you are trying resize in null or empty");
        }
        try {
            File tempImageFile = ImageUtils.getInstance().resizeImage(currentFile, width, height);
            Http.MultipartFormData.FilePart newFilePart = new Http.MultipartFormData.FilePart(oldPart.getKey(), oldPart.getFilename(), oldPart.getContentType(), tempImageFile);
            this.setAttachmentFileItem(newFilePart);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("There was a problem resizing your image %s", e.getMessage()));
        }
    }


    public void resizeUntilLessThanDesiredSize(int width, int height, long desiredSize) {
        this.resizeTo(width, height);
        if (getFileSize() >= desiredSize) {
            Double newWidth = width - (width * .25);
            Double newHeight = height - (height * .25);
            this.resizeUntilLessThanDesiredSize(newWidth.intValue(), newHeight.intValue(), desiredSize);
        }
    }


    public long getFileSize() {
        Http.MultipartFormData.FilePart oldPart = this.getAttachmentFileItem();
        if (oldPart == null) {
            throw new RuntimeException("Image is null or empty");
        }
        File currentFile = (File) this.getAttachmentFileItem().getFile();
        if (currentFile == null) {
            throw new RuntimeException("Image file is null or empty");
        }
        return currentFile.length();
    }


    public void setAttachmentFileItem(Http.MultipartFormData.FilePart attachmentFileItem) {
        this.attachmentFileItem = attachmentFileItem;
    }

    /**
     * Sets the attachment URL used when the attachment is an image
     *
     * @param imageAttachmentURL The url for the attachment stream
     */
    public void setImageAttachmentURL(String imageAttachmentURL) {
        try {
            setStringForAttributeIndex(imageAttachmentURL, AttachmentAttributeImage);
        } catch (Exception e) {
        }
    }

    /**
     * Gets the image attachment URL
     *
     * @return
     */
    public String getImageAttachmentURL() {
        return getStringAttributeAtIndex(AttachmentAttributeImage);
    }

    /**
     * Sets the text when the attachment is a note
     *
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
     *
     * @return
     */
    public String getNoteAttachment() {
        return getStringAttributeAtIndex(AttachmentAttributeText);
    }

    /**
     * Sets the link string when the attachment is a link
     *
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
     *
     * @return
     */
    public String getLinkAttachment() {
        return getStringAttributeAtIndex(AttachmentAttributeLink);
    }

    /**
     * Sets the attachment URL used when the attachment is an video
     *
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
     *
     * @return
     */
    public String getVideoAttachmentURL() {
        return getStringAttributeAtIndex(AttachmentAttributeVideo);
    }

    /**
     * Sets the attachment URL used when the attachment is a file
     *
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
     *
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
     *
     * @return
     */
    public String getTitle() {
        return getStringAttributeAtIndex(AttachmentAttributeTitle);
    }

    /**
     * Returns the attachment type
     *
     * @return
     */

    public Boolean isDeletable() {
        return getBoolValueAtIndex(AttachmentAttributeDeletable);
    }

    public void setIsDeletable(boolean deletable) {
        try {
            setBooleanForAttributeIndex(deletable, AttachmentAttributeDeletable);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    public Boolean isEditable() {
        return getBoolValueAtIndex(AttachmentAttributeDeletable);
    }

    public void setIsEditable(boolean editable) {
        try {
            setBooleanForAttributeIndex(editable, AttachmentAttributeEditable);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    public void setMimeType(String mimeType) {
        setStringForAttributeIndex(mimeType, AttachmentAttributeMimeType);
    }

    public String getMimeType() {
        return getStringAttributeAtIndex(AttachmentAttributeMimeType);
    }

    public void setFileName(String fileName) {
        setStringForAttributeIndex(fileName, AttachmentAttributeFileName);
    }

    public String getFileName() {
        return getStringAttributeAtIndex(AttachmentAttributeFileName);
    }

    public Type getItemType() {
        return Type.Attachment;
    }

}
