package sdk.roles.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import sdk.annotations.Attribute;
import sdk.annotations.PrimaryKey;

public class AppRole {
    @JsonProperty("roleID")
    @PrimaryKey
    private int roleID;
    @Attribute(index = 0)
    @JsonProperty("roleName")
    private String roleName;
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
