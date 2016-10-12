package sdk.datasources.base;

import sdk.data.ServiceConfigurationAttribute;
import sdk.data.User;
import sdk.datasources.DataSourceBase;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;

/**
 * Created by matthew on 5/12/16.
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
    User getUser(String userID, AuthenticationInfo authenticationInfo, Parameters params);
    User createUser(User user);
    User updateUser(User user);
    Boolean deleteUser(User user);
}
