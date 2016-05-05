package com.apptree.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by alexis on 12/22/15.
 */
public class ATUser {
    private String userID;
    private String username;
    private List<Integer> roleIDs;
    private String firstName;
    private String lastName;
    private String source;
    private String email;
    private String phone;
    private String externalUserID;

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

    public void updateFromJSON(JSONObject userObject) {
        JSONArray roleArray;
        JSONObject userInfoObject;

        userID = userObject.optString("id");
        roleArray = userObject.optJSONArray("roleIDs");
        userInfoObject = userObject.optJSONObject("additionalUserInfo");
        firstName = userObject.optString("firstName");
        lastName = userObject.optString("lastName");
        source = userObject.optString("source");
        email = userObject.optString("email");
        phone = userObject.optString("phone");
        username = userObject.optString("username");
        externalUserID = userObject.optString("externalUserID");

        if ( roleArray != null ) {
            roleIDs = new ArrayList<Integer>();
            for ( int i = 0; i < roleArray.length(); i++) {
                roleIDs.add(roleArray.optInt(i));
            }
        }

        if ( userInfoObject != null ) {
            Iterator<String> iter = userInfoObject.keys();
            while ( iter.hasNext() ) {
                String key = iter.next();
                userInfo.put(key, userInfoObject.optString(key));
            }
        }
    }

    public JSONObject toJSON() {
        JSONObject userObject;

        userObject = new JSONObject();
        userObject.putOpt("id", userID);
        userObject.putOpt("roleIDs", roleIDs);
        userObject.putOpt("additionalUserInfo", userInfo);
        userObject.putOpt("firstName", firstName);
        userObject.putOpt("lastName", lastName);
        userObject.putOpt("source", source);
        userObject.putOpt("email", email);
        userObject.putOpt("phone", phone);
        userObject.putOpt("username", username);
        userObject.putOpt("externalUserID", externalUserID);

        return userObject;
    }
}
