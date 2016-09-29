package sdk.datasources.base;

import rx.Observable;
import sdk.AppTreeSource;
import sdk.user.User;
import sdk.user.UserInfoResponse;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.io.InputStream;
import java.util.List;

/**
 * Created by matthew on 5/12/16.
 */
public interface UserDataSource extends AppTreeSource {

    /**
     * Get the list of available user attributes that you want to provide
     * @return List of Strings representing the attributes you want to provide. These values will be available in the builder.
     */
    public abstract List<UserInfoKey> getUserInfoKeys(AuthenticationInfo authenticationInfo);

    /**
     * Get the user information given a userID. This is your opportunity to return user attributes like email, phone number etc.
     * @param userID the user ID that the information is being requested for
     * @param authenticationInfo authentication information, use this to validate the request has access to this user info
     * @param params any additional urlParams
     * @return a ATUserInfoResponse containing information about the requested user
     */

    public abstract UserInfoResponse getUserInfo(String userID, AuthenticationInfo authenticationInfo, Parameters params);

    /***
     * Get the users 'avatar' image
     * @return an InputStream for the users image data. JPG an PNG are supported by the client.
     */
    public abstract InputStream getUserImage();

    /**
     * Checks to see if a user exists and returns the user information
     * @param userID the user ID to look for
     * @param source the source to look for the user in
     * @param authenticationInfo authentication information, use this to validate the request has access to this user info
     * @param params any additional url parameters
     * @return an ATUserInfoResponse containing information about the requested user
     */
    public abstract UserInfoResponse checkForUser(String userID, String source, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * A notification that a user has been created in the portal
     * @param user the ATUser that was created
     * @param authenticationInfo authentication information
     * @param params any additional url parameters
     */
    public abstract void createUserEvent(User user, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * A notification that a user has been updated in the portal
     * @param user the ATUser that was updated
     * @param authenticationInfo authentication information
     * @param params any additional url parameters
     */
    public abstract void updateUserEvent(User user, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * A notification that a user has been deleted in the portal
     * @param user the ATUser that was created
     * @param authenticationInfo authentication information
     * @param params any additional url parameters
     */
    public abstract void deleteUserEvent(User user, AuthenticationInfo authenticationInfo, Parameters params);

    /**
     * Gets the available source types for users
     * @param authenticationInfo authentication information
     * @return
     */
    public abstract List<String> getUserSources(AuthenticationInfo authenticationInfo);
}
