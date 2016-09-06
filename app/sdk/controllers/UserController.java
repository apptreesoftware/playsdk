package sdk.controllers;

import play.mvc.*;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.user.User;
import sdk.datasources.base.UserDataSource;
import sdk.datasources.UserDataSource_Internal;
import sdk.utils.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by matthew on 5/12/16.
 */
@With({ValidateRequestAction.class})
public class UserController extends Controller {
    public CompletionStage<Result> getUserInfo(String userID) {
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        return dataSource.getUserInfo(userID, authenticationInfo, parameters)
                .thenApply(userInfo -> ok(JsonUtils.toJson(userInfo)))
                .exceptionally(ResponseExceptionHandler::handleException);

    }

    public CompletionStage<Result> getUserImage(String userID) {
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        return dataSource.getUserImage()
                .thenApply(Results::ok);
    }

    public CompletionStage<Result> getUserInfoKeys() {
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        return dataSource.getUserKeys(authenticationInfo)
                .thenApply(sources -> new UserStringListResponse(true, sources, null))
                .thenApply(response -> ok(JsonUtils.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> checkForUserInfo() {
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        CheckForUserRequest infoRequest = JsonUtils.fromJson(request().body().asJson(), CheckForUserRequest.class);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        return dataSource.checkForUser(infoRequest.externalUserID, infoRequest.source, authenticationInfo, parameters)
                .thenApply(userInfoResponse -> ok(JsonUtils.toJson(userInfoResponse)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> createUser() {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        User user = JsonUtils.fromJson(request.body().asJson(), User.class);
        return dataSource.createUserEvent(user, authenticationInfo, parameters)
                .thenApply(value -> ok(JsonUtils.toJson(Response.success())))
                .exceptionally(ResponseExceptionHandler::handleException);

    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> updateUser() {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        User user = JsonUtils.fromJson(request.body().asJson(), User.class);
        return dataSource.updateUserEvent(user, authenticationInfo, parameters)
                .thenApply(value -> ok(JsonUtils.toJson(Response.success())))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> deleteUser() {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        User user = JsonUtils.fromJson(request.body().asJson(), User.class);
        return dataSource.deleteUserEvent(user, authenticationInfo, parameters)
                .thenApply(value -> ok(JsonUtils.toJson(Response.success())))
                .exceptionally(ResponseExceptionHandler::handleException);
    }


    private class UserStringListResponse {
        public boolean success;
        public List<String> records;
        public String message;

        UserStringListResponse(boolean success, List<String> sources, String message) {
            this.success = success;
            this.records = sources;
            this.message = message;
        }
    }
}

class CheckForUserRequest {
    public String externalUserID;
    public String source;
}
