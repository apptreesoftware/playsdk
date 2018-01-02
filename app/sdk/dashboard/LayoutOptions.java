package sdk.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LayoutOptions {
    @JsonProperty("type")
    private String type;

    @JsonProperty("minWidth")
    private int minWidth;

    @JsonProperty("maxWidth")
    private int maxWidth;

    public LayoutOptions() {}

    public LayoutOptions(String type, int minWidth, int maxWidth) {
        this.setType(type);
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
    }

    public static LayoutOptions row(int minWidth, int maxWidth) {
        return new LayoutOptions("row", minWidth, maxWidth);
    }

    public static LayoutOptions column(int minWidth, int maxWidth) {
        return new LayoutOptions("col", minWidth, maxWidth);
    }

    public static LayoutOptions empty(){
        return new LayoutOptions();
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }
}
