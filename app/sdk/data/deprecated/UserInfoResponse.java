package sdk.data.deprecated;

/**
 * Created by Matthew Smith on 10/13/16.
 * Copyright AppTree Software, Inc.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import sdk.utils.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Matthew Smith on 9/9/15.
 * Copyright AppTree Software, Inc.
 */
public class UserInfoResponse extends Response {
    @JsonProperty("id")
    public String userID;
    public List<Integer> roleIDs = new ArrayList<>();
    @JsonProperty("additionalUserInfo")
    public HashMap<String,String> userInfo = new HashMap<>();
    public String firstName;
    public String lastName;
    public String source;
    public String email;
    public String phone;
    public String username;

    public UserInfoResponse(boolean success, String message) {
        super(success, message);
    }

    public static class Builder {
        String userID;
        String message = "";
        boolean success = true;
        boolean authorizationError = false;
        private List<Integer> roleIDs;
        private HashMap<String,String> userInfo = new HashMap<>();
        private String firstName;
        private String lastName;
        private String source;
        private String email;
        private String phone;
        private String username;

        /**
         * Creates a new user info response builder
         */
        public Builder() {

        }

        /**
         * Sets the success flag to true
         * @param userID a String user ID
         * @return the builder with the user ID
         */
        public Builder withSuccess(String userID) {
            this.userID = userID;
            this.success = true;
            return this;
        }

        /**
         * Sets the success flag to false
         * @param failureMessage a String message about the failure of the call
         * @return the builder
         */
        public Builder withFailure(String failureMessage) {
            this.success = false;
            this.message = failureMessage;
            return this;
        }

        /**
         * Sets the role IDs
         * @param roleIDs a list of Integers indicating the roles granted to this user
         * @return the builder
         */
        public Builder withRoleIDs(List<Integer> roleIDs) {
            this.roleIDs = roleIDs;
            return this;
        }

        /**
         * Adds a user info value
         * @param key the key for this value
         * @param value the value
         * @return the builder
         */
        public Builder withUserInfoValue(String key, String value) {
            if ( key != null && value != null ) {
                userInfo.put(key,value);
            }
            return this;
        }

        /**
         * Adds user info values
         * @param userInfoValues a HashMap of keys and values to be added to the user info
         * @return the builder
         */
        public Builder withUserInfoValues(HashMap<String,String> userInfoValues) {
            userInfo.putAll(userInfoValues);
            return this;
        }

        /**
         * Adds the user ID
         * @param userID
         * @return the builder
         */
        public Builder withUserID(String userID) {
            this.userID = userID;
            return this;
        }

        /**
         * Adds a first name
         * @param firstName
         * @return the builder
         */
        public Builder withFirstName(String firstName) {
            this.firstName = firstName != null ? firstName : "";
            return this;
        }

        /**
         * Adds a last name
         * @param lastName
         * @return the builder
         */
        public Builder withLastName(String lastName) {
            this.lastName = lastName != null ? lastName : "";
            return this;
        }

        /**
         * Adds a source
         * @param source
         * @return the builder
         */
        public Builder fromSource(String source) {
            this.source = source != null ? source : "";
            return this;
        }

        /**
         * Adds an email
         * @param email
         * @return the builder
         */
        public Builder withEmail(String email) {
            this.email = email != null ? email : "";
            return this;
        }

        /**
         * Adds a phone number
         * @param phone
         * @return the builder
         */
        public Builder withPhoneNumber(String phone) {
            this.phone = phone != null ? phone : "";
            return this;
        }

        /**
         * Adds a username
         * @param username
         * @return the builder
         */
        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        /**
         * Creates and returns a user info response with the builder set values
         * @return
         */
        public UserInfoResponse build() {
            UserInfoResponse response = new UserInfoResponse(success, message);
            response.userID = userID;

            response.userInfo = this.userInfo;
            response.roleIDs = roleIDs;
            response.firstName = firstName;
            response.lastName = lastName;
            response.email = email;
            response.source = source;
            response.phone = phone;
            response.username = username;
            return response;
        }
    }
}

