import sdk.converter.attachment.ApptreeAttachment;
import sdk.data.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Orozco on 7/25/17.
 */
public class SampleAttachment implements ApptreeAttachment {
    String mimeType;
    String title;
    String URL;

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
        this.URL = attachmentURL;
    }

    @Override
    public String getAttachmentURL() {
        return URL;
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
    public void setInputStream(InputStream inputStream) {

    }



    public static ServiceConfiguration getServiceConfiguration(){
        ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
        return new ServiceConfiguration.Builder("SampleAttachmentWrapper").withAttributes(getRelatedServiceConfigurationAttributes()).build();
    }


    public static Collection<ServiceConfigurationAttribute> getRelatedServiceConfigurationAttributes(){
        List<ServiceConfigurationAttribute> attributes = new ArrayList();
        attributes.add(new ServiceConfigurationAttribute.Builder(0).name("Sample Attachment").asAttachments().canCreate().canUpdate().canSearch().build());
        return attributes;
    }
}
