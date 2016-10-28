package sdk.utils;

import java.util.HashMap;
import java.util.Map;

import static sdk.utils.Constants.AUTH_TOKEN_HEADER;
import static sdk.utils.Constants.USERNAME_TOKEN_HEADER;

/**
 * Created by alexis on 5/3/16.
 */
public class AuthenticationInfo {
    private String token;
    private String userID;
    private HashMap<String, String> extraAuthInfo = new HashMap<String,String>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /***
     * Get any custom authentication value that is being passed from the client.
     * @param key
     * @return The custom authentication value
     */
    public String getCustomAuthenticationParameter(String key) {
        return extraAuthInfo.get(key.toLowerCase());
    }

    public void setCustomAuthenticationParameter(String key, String value) {
        extraAuthInfo.put(key.toLowerCase(), value);
    }

    public void removeCustomAuthenticationParameter(String key) { extraAuthInfo.remove(key.toLowerCase()); }

    public HashMap<String, String> getExtraAuthInfo() { return extraAuthInfo; }

    public AuthenticationInfo(Map<String, String[]> authInfo) {
        authInfo.forEach((k, v) -> {
            if ( k.equalsIgnoreCase(AUTH_TOKEN_HEADER) ) {
                setToken(v[0]);
            } else if ( k.equalsIgnoreCase(USERNAME_TOKEN_HEADER) ) {
                setUserID(v[0]);
            } else {
                extraAuthInfo.putIfAbsent(k.toLowerCase(), v[0]);
            }
        });
    }
}
