package sdk.utils.branding;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Karis Sponsler
 * Date: 11/22/17
 */

public class BrandingConfiguration {
    @JsonProperty("appID")
    public String appId;
    @JsonProperty("version")
    public String appVersion;
    @JsonProperty("appName")
    public String appName;
    public String logoURL;
    @JsonProperty("logoName")
    public String logoName;
    @JsonProperty("brandingSettings")
    public BrandingSettings branding;
    
    public static class BrandingSettings {
        @JsonProperty("invertColors")
        public boolean invertColors;
        @JsonProperty("primaryIOS")
        public String primaryIOS;
        @JsonProperty("primaryLightIOS")
        public String primaryLightIOS;
        @JsonProperty("primaryDarkIOS")
        public String primaryDarkIOS;
        @JsonProperty("primaryTextIOS")
        public String primaryTextIOS;
        @JsonProperty("secondaryTextIOS")
        public String secondaryTextIOS;
        @JsonProperty("accentIOS")
        public String accentIOS;

        @JsonProperty("primaryAndroid")
        public String primaryAndroid;
        @JsonProperty("primaryLightAndroid")
        public String primaryLightAndroid;
        @JsonProperty("primaryDarkAndroid")
        public String primaryDarkAndroid;
        @JsonProperty("primaryTextAndroid")
        public String primaryTextAndroid;
        @JsonProperty("secondaryTextAndroid")
        public String secondaryTextAndroid;
        @JsonProperty("accentAndroid")
        public String accentAndroid;
    }
}
