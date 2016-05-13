package sdk.utils;

import java.util.HashMap;
import java.util.Map;

import static sdk.utils.Constants.AUTH_TOKEN_HEADER;

/**
 * Created by alexis on 5/3/16.
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

    public AuthenticationInfo(Map<String, String[]> authInfo) {
        authInfo.forEach((k, v) -> {
            if ( k.equalsIgnoreCase(AUTH_TOKEN_HEADER) ) {
                setToken(v[0]);
            }
            extraAuthInfo.putIfAbsent(k, v[0]);
        });
    }
}
