package models.sdk.AttributeDataTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.mvc.Http;

/**
 * Created by alexis on 5/3/16.
 */
public class Image {
    public String imageURL;
    public String uploadKey;
    @JsonIgnore Http.MultipartFormData.FilePart attachmentFile;
}
