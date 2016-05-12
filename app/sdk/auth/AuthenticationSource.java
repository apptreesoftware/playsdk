package sdk.auth;

import sdk.utilsx.AuthenticationInfo;
import sdk.utilsx.Response;

/**
 * Created by matthew on 5/12/16.
 */
public interface AuthenticationSource {
    LoginResponse login(String username, String password, AuthenticationInfo authenticationInfo);
    Response logout(AuthenticationInfo authenticationInfo);
    Response validateAuthenticationInfo(AuthenticationInfo authenticationInfo);
}
