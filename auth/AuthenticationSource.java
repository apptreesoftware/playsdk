package sdk.auth;

import sdk.AppTreeSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Response;

/**
 * Created by matthew on 5/12/16.
 */
public interface AuthenticationSource extends AppTreeSource {
    LoginResponse login(String username, String password, AuthenticationInfo authenticationInfo);
    Response logout(AuthenticationInfo authenticationInfo);
    Response validateAuthenticationInfo(AuthenticationInfo authenticationInfo);
}
