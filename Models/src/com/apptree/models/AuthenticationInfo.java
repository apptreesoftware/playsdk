package com.apptree.models;

import java.util.HashMap;

/**
 * Created by Matthew Smith on 3/5/16.
 * Copyright AppTree Software, Inc.a
 */
public class AuthenticationInfo {
    private String token;
    private HashMap<String, String> extraAuthInfo = new HashMap<String,String>();

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    /***
     * Get any custom authentication value that is being passed from the client.
     * @param key
     * @return The custom authentication value
     */
    public String getCustomAuthenticationParameter(String key) {
        return extraAuthInfo.get(key);
    }

    public void setCustomAuthenticationParameter(String key, String value) {
        extraAuthInfo.put(key, value);
    }

    public void removeCustomAuthenticationParameter(String key) { extraAuthInfo.remove(key); }

    public HashMap<String, String> getExtraAuthInfo() { return extraAuthInfo; }
 }
