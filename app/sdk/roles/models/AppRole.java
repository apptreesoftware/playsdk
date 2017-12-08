package sdk.roles.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppRole {
    @PrimaryKey
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("roleID")
    @Attribute(index = 0)
    private int roleID;
    @JsonProperty("appID")
    private String appID;

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }
}
