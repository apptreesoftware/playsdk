package sdk.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DashBoardItem {
    @JsonProperty("type")
    private String type;
    @JsonProperty("title")
    private String title;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @JsonProperty("body")
    private String body;
    @JsonProperty("links")
    private List<DashBoardLink> links;

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

    public void addLink(DashBoardLink link) {
        getLinks().add(link);
    }

    public List<DashBoardLink> getLinks() {
        if(links == null) links = new ArrayList<>();
        return links;
    }

    public void setLinks(List<DashBoardLink> links) {
        this.links = links;
    }
}
