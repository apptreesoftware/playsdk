package sdk.datasources.future;

import sdkmodels.data.User;
import sdk.datasources.UserDataSourceBase;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;

import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/5/16.
 */
public interface UserDataSource extends UserDataSourceBase {
    CompletableFuture<User> getUser(String userID, AuthenticationInfo authenticationInfo, Parameters params);
    CompletableFuture<User> createUser(User user);
    CompletableFuture<User> updateUser(User user);
    CompletableFuture<Boolean> deleteUser(User user);

}
