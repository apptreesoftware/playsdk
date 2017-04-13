package sdk.auth;

import sdk.AppTreeSource;
import sdkmodels.auth.LoginResponse;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Response;

/**
 * Created by matthew on 5/12/16.
 */
public interface AuthenticationSource extends AppTreeSource {
    LoginResponse login(String username, String password, AuthenticationInfo authenticationInfo);
    Response logout(AuthenticationInfo authenticationInfo);
    boolean validateAuthenticationInfo(AuthenticationInfo authenticationInfo);
}
