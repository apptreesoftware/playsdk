package sdk.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DashboardItem {
    @JsonProperty("type")
    private String type;
    @JsonProperty("title")
    private String title;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @JsonProperty("body")
    private String body;
    @JsonProperty("links")
    private List<DashboardLink> links;
    @JsonProperty("layoutOptions")
    private LayoutOptions layoutOptions;


    public DashboardItem(String title, String body, String imageUrl, List<DashboardLink> links, LayoutOptions layoutOptions){
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.links = links;
        this.type = "IMAGE";
        this.layoutOptions = layoutOptions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addLink(DashboardLink link) {
        getLinks().add(link);
    }

    public List<DashboardLink> getLinks() {
        if(links == null) links = new ArrayList<>();
        return links;
    }

    public void setLinks(List<DashboardLink> links) {
        this.links = links;
    }

    public LayoutOptions getLayoutOptions() {
        return layoutOptions;
    }

    public void setLayoutOptions(LayoutOptions layoutOptions) {
        this.layoutOptions = layoutOptions;
    }
}
