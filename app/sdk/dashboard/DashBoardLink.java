package sdk.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashBoardLink {
    @JsonProperty("title")
    private String title;
    @JsonProperty("href")
    private String href;
    public DashBoardLink(String title, String href) {
        this.title = title;
        this.href = href;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
