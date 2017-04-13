package sdk.datasources.rx;

import rx.Observable;
import sdkmodels.data.User;
import sdk.datasources.UserDataSourceBase;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.Parameters;

/**
 * Created by Matthew Smith on 9/2/16.
 * Copyright AppTree Software, Inc.
 */
public interface UserDataSource extends UserDataSourceBase {

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
