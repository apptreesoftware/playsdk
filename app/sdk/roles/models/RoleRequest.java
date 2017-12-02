package sdk.roles.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleRequest {
    @JsonProperty("roleNames")
    private String[] roleNames;

    public RoleRequest(String[] roleNames) {
        this.setRoleNames(roleNames);
    }

    public String[] getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String[] roleNames) {
        this.roleNames = roleNames;
    }
}
