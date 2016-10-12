package sdk.datasources.rx;

import rx.Observable;
import sdk.data.ServiceConfigurationAttribute;
import sdk.data.User;
import sdk.datasources.DataSourceBase;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.Collection;

/**
 * Created by Matthew Smith on 9/2/16.
 * Copyright AppTree Software, Inc.
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

    Observable<User> getUser(String userID, AuthenticationInfo authenticationInfo, Parameters params);

    default Observable<User> createUser(User user) {
        throw new UnsupportedOperationException("User creation not supported");
    }

    default Observable<User> updateUser(User user) {
        throw new UnsupportedOperationException("User updating not supported");
    }

    default Observable<Boolean> deleteUser(User user) {
        throw new UnsupportedOperationException("User deletion not supported");
    }

}
