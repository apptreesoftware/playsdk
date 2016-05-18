package sdk.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import sdk.utils.Response;

import java.util.HashMap;

/**
 * Created by matthew on 5/12/16.
 */
public class LoginResponse extends Response {
    private String token;
    private String userID;
    private String username;
    private HashMap<String, String> extraHeaders;

    /**
     *
     * @param success A boolean indicating the success of the login
     * @param userID
     * @param token
     */
    public LoginResponse(boolean success, String userID, String token, String username, HashMap<String, String> extraHeaders) {
        this.token = token;
        this.success = success;
        this.userID = userID;
        this.username = username;
        this.extraHeaders = extraHeaders;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("id")
    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public HashMap<String, String> getExtraHeaders() { return extraHeaders; }
    public void setExtraHeaders(HashMap<String, String> extraHeaders) { this.extraHeaders = extraHeaders; }

    public static class Builder {
        private boolean success;
        private String token;
        private String username;
        private String userID;
        private String message;
        private HashMap<String, String> extraHeaders;

        /**
         * Creates a Builder object
         */
        public Builder() {

        }

        /**
         *
         * @param token A String token from the login response
         * @return A builder with the success boolean set to true and the provided token
         */
        public Builder withSuccess(String token, String userID, String username, HashMap<String, String> extraHeaders) {
            success = true;
            this.token = token;
            this.userID = userID;
            this.username = username;
            this.extraHeaders = extraHeaders;
            return this;
        }

        /**
         *
         * @param message
         * @return a builder with the given message
         */
        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         *
         * @return A login response with the specified builder parameters set
         */
        public LoginResponse build() {
            LoginResponse loginResponse = new LoginResponse(success, userID, token, username, extraHeaders);
            loginResponse.setMessage(message);
            return loginResponse;
        }
    }
}
