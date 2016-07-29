package sdk.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

/**
 * Created by ptseng on 7/20/16.
 */
public class File {
    private String fileUrl;
    private String thumbnailUrl;
    private String mimeType;
    private String name;
    private Integer size;

    public File() {}

    public File(String fileUrl, String thumbnailUrl, String mimeType, String name, Integer size) {
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.mimeType = mimeType;
        this.name = name;
        this.size = size;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public ObjectNode toJSON() {
        ObjectNode json = Json.newObject();
        if ( fileUrl != null ) {
            json.put("fileURL", fileUrl);
        }
        if ( thumbnailUrl != null ) {
            json.put("thumbnailUrl", thumbnailUrl);
        }
        if ( mimeType != null ) {
            json.put("mimeType", mimeType);
        }
        if ( name != null ) {
            json.put("name", name);
        }
        if ( size != null ) {
            json.put("size", size);
        }
        return json;
    }
}
