package sdk.datasources;

import rx.Observable;
import sdk.datasources.rx.UserDataSource;
import sdk.user.User;
import sdk.user.UserInfoResponse;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Matthew Smith on 9/2/16.
 * Copyright AppTree Software, Inc.
 */
public class UserDataSource_Internal extends BaseSource_Internal {
    private UserDataSource rxUserDataSource;
    private sdk.datasources.base.UserDataSource userDataSource;
    private sdk.datasources.future.UserDataSource futureUserDataSource;

    public UserDataSource_Internal(UserDataSource dataSource) {
        this.rxUserDataSource = dataSource;
    }

    public UserDataSource_Internal(sdk.datasources.base.UserDataSource dataSource) {
        this.userDataSource = dataSource;
    }
    public UserDataSource_Internal(sdk.datasources.future.UserDataSource dataSource) { this.futureUserDataSource = dataSource; }

    public CompletableFuture<List<String>> getUserKeys(AuthenticationInfo authenticationInfo) {
        if ( rxUserDataSource != null ) {
            return observableToFuture(rxUserDataSource.getUserInfoKeys(authenticationInfo));
        } else if ( userDataSource != null ) {
            return CompletableFuture.supplyAsync(() -> userDataSource.getUserInfoKeys(authenticationInfo));
        } else if ( futureUserDataSource != null ) {
            return futureUserDataSource.getUserInfoKeys(authenticationInfo);
        }
        throw new RuntimeException("No data source available");
    }

    public CompletableFuture<UserInfoResponse> getUserInfo(String userID, AuthenticationInfo authenticationInfo, Parameters params) {
        if (rxUserDataSource != null) {
            return observableToFuture(rxUserDataSource.getUserInfo(userID, authenticationInfo, params));
        } else if ( userDataSource != null ) {
            return CompletableFuture.supplyAsync(() -> userDataSource.getUserInfo(userID, authenticationInfo, params));
        } else if ( futureUserDataSource != null ) {
            return futureUserDataSource.getUserInfo(userID, authenticationInfo, params);
        }
        throw new RuntimeException("No data source available");
    }

    /***
     * Get the users 'avatar' image
     * @return an InputStream for the users image data. JPG an PNG are supported by the client.
     */
    public CompletableFuture<InputStream> getUserImage() {
        if (rxUserDataSource != null) {
            return observableToFuture(rxUserDataSource.getUserImage());
        } else if ( userDataSource != null ) {
            return CompletableFuture.supplyAsync(() -> userDataSource.getUserImage());
        } else if ( futureUserDataSource != null ) {
            return futureUserDataSource.getUserImage();
        }
        throw new RuntimeException("No data source available");
    }

    /**
     * Checks to see if a user exists and returns the user information
     * @param userID the user ID to look for
     * @param source the source to look for the user in
     * @param authenticationInfo authentication information, use this to validate the request has access to this user info
     * @param params any additional url parameters
     * @return an ATUserInfoResponse containing information about the requested user
     */
    public CompletableFuture<UserInfoResponse> checkForUser(String userID, String source, AuthenticationInfo authenticationInfo, Parameters params) {
        if (rxUserDataSource != null) {
            return observableToFuture(rxUserDataSource.checkForUser(userID, source, authenticationInfo, params));
        } else if ( userDataSource != null ) {
            return CompletableFuture.supplyAsync(() -> userDataSource.checkForUser(userID, source, authenticationInfo, params));
        } else if ( futureUserDataSource != null ) {
            return futureUserDataSource.checkForUser(userID, source, authenticationInfo, params);
        }
        throw new RuntimeException("No data source available");
    }

    /**
     * A notification that a user has been created in the portal
     * @param user the ATUser that was created
     * @param authenticationInfo authentication information
     * @param params any additional url parameters
     */
    public CompletableFuture<Void> createUserEvent(User user, AuthenticationInfo authenticationInfo, Parameters params) {
        if (rxUserDataSource != null) {
            return observableToFuture(rxUserDataSource.createUserEvent(user, authenticationInfo, params));
        } else if ( userDataSource != null ) {
            return CompletableFuture.runAsync(() -> userDataSource.createUserEvent(user, authenticationInfo, params));
        } else if ( futureUserDataSource != null ) {
            return futureUserDataSource.createUserEvent(user, authenticationInfo, params);
        }
        throw new RuntimeException("No data source available");
    }

    /**
     * A notification that a user has been updated in the portal
     * @param user the ATUser that was updated
     * @param authenticationInfo authentication information
     * @param params any additional url parameters
     */
    public CompletableFuture<Void> updateUserEvent(User user, AuthenticationInfo authenticationInfo, Parameters params) {
        if (rxUserDataSource != null) {
            return observableToFuture(rxUserDataSource.updateUserEvent(user, authenticationInfo, params));
        } else if ( userDataSource != null ) {
            return CompletableFuture.runAsync(() -> userDataSource.updateUserEvent(user, authenticationInfo, params));
        } else if ( futureUserDataSource != null ) {
            return futureUserDataSource.updateUserEvent(user, authenticationInfo, params);
        }
        throw new RuntimeException("No data source available");
    }

    /**
     * A notification that a user has been deleted in the portal
     * @param user the ATUser that was created
     * @param authenticationInfo authentication information
     * @param params any additional url parameters
     */
    public CompletableFuture<Void> deleteUserEvent(User user, AuthenticationInfo authenticationInfo, Parameters params) {
        if (rxUserDataSource != null) {
            return observableToFuture(rxUserDataSource.deleteUserEvent(user, authenticationInfo, params));
        } else if ( userDataSource != null ) {
            return CompletableFuture.runAsync(() -> userDataSource.deleteUserEvent(user, authenticationInfo, params));
        } else if ( futureUserDataSource != null ) {
            return futureUserDataSource.deleteUserEvent(user, authenticationInfo, params);
        }
        throw new RuntimeException("No data source available");
    }

}
