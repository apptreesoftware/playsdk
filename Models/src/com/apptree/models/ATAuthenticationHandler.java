package com.apptree.models;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by matthew on 12/26/14.
 */
public abstract class ATAuthenticationHandler {

    /**
     * Logs in a user
     * @param username
     * @param password
     * @param json
     * @return A login response which includes a token
     */
    public abstract ATLoginResponse login(String username, String password, JSONObject json, AuthenticationInfo authenticationInfo);

    /**
     * Logs out a user
     * @param authenticationInfo The token to be logged out
     * @return A logout response
     */
    public abstract ATLogoutResponse logout(AuthenticationInfo authenticationInfo);

    /**
     * Checks the validity of a token
     * @param authenticationInfo A HashMap of any authorization parameters sent with the request
     * @return A validate token response indicating whether the token is valid
     */
    public abstract ATValidateTokenResponse validateAuthentication(AuthenticationInfo authenticationInfo);
}
