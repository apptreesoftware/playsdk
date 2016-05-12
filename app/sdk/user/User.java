package sdk.user;

import java.util.HashMap;
import java.util.List;

/**
 * Created by matthew on 5/12/16.
 */
public class User {
    private String userID;
    private String username;
    private List<Integer> roleIDs;
    private String firstName;
    private String lastName;
    private String source;
    private String email;
    private String phone;
    private String externalUserID;
    private String accountStatus;

    private HashMap<String,String> userInfo = new HashMap<String, String>();

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public List<Integer> getRoleIDs() { return roleIDs; }

    public void setRoleIDs(List<Integer> roleIDs) { this.roleIDs = roleIDs; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSource() { return source; }

    public void setSource(String source) { this.source = source; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public HashMap<String, String> getUserInfo() { return userInfo; }

    public void setUserInfo(HashMap<String, String> userInfo) { this.userInfo = userInfo; }

    public String getExternalUserID() { return externalUserID; }

    public void setExternalUserID(String externalUserID) { this.externalUserID = externalUserID; }

    public String getAccountStatus() { return accountStatus; }

    public void setAccountStatus(String status) { this.accountStatus = status; }
}
