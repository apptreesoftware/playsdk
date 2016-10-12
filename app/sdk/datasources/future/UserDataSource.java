package sdk.datasources.future;

import sdk.data.ServiceConfigurationAttribute;
import sdk.data.User;
import sdk.datasources.DataSourceBase;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Created by matthew on 9/5/16.
 */
public interface UserDataSource extends DataSourceBase {
    @Override
    default String getServiceDescription() {
        return "User";
    }
    @Override
    default Collection<ServiceConfigurationAttribute> getDataSetAttributes() {
        return User.getConfigurationAttributesWithCustomAttributes(getCustomUserAttributes());
    }
    Collection<ServiceConfigurationAttribute> getCustomUserAttributes();
    CompletableFuture<User> getUser(String userID, AuthenticationInfo authenticationInfo, Parameters params);
    CompletableFuture<User> createUser(User user);
    CompletableFuture<User> updateUser(User user);
    CompletableFuture<Boolean> deleteUser(User user);

}
