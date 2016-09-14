package sdk.controllers;

import play.libs.Json;
import play.mvc.*;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.user.User;
import sdk.user.UserDataSource;
import sdk.user.UserInfoKey;
import sdk.utils.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by matthew on 5/12/16.
 */
@With({ValidateRequestAction.class})
public class UserController extends Controller {
    public CompletionStage<Result> getUserInfo(String userID) {
        UserDataSource dataSource = AppTree.getUserDataSource();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        return CompletableFuture
                .supplyAsync(() -> dataSource.getUserInfo(userID, authenticationInfo, parameters))
                .thenApply(userInfo -> ok(JsonUtils.toJson(userInfo)))
                .exceptionally(ResponseExceptionHandler::handleException);

    }

    public CompletionStage<Result> getUserImage(String userID) {
        UserDataSource dataSource = AppTree.getUserDataSource();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        return CompletableFuture
                .supplyAsync(dataSource::getUserImage)
                .thenApply(Results::ok);
    }

    public CompletionStage<Result> getUserSources() {
        UserDataSource dataSource = AppTree.getUserDataSource();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        return CompletableFuture
                .supplyAsync(() -> dataSource.getUserSources(authenticationInfo))
                .thenApply(sources -> {
                    UserStringListResponse response;
                    if ( sources != null ) {
                        response = new UserStringListResponse(true, sources, null);
                    } else {
                        response = new UserStringListResponse(false, null, "No sources defined");
                    }
                    return response;
                })
                .thenApply(response -> ok(JsonUtils.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> getUserInfoKeys() {
        UserDataSource dataSource = AppTree.getUserDataSource();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        return CompletableFuture
                .supplyAsync(() -> dataSource.getUserInfoKeys(authenticationInfo))
                .thenApply(sources -> new UserInfoKeyListResponse(true, sources, null))
                .thenApply(response -> ok(JsonUtils.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> checkForUserInfo() {
        UserDataSource dataSource = AppTree.getUserDataSource();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        CheckForUserRequest infoRequest = JsonUtils.fromJson(request().body().asJson(), CheckForUserRequest.class);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        return CompletableFuture
                .supplyAsync(() -> dataSource.checkForUser(infoRequest.externalUserID, infoRequest.source, authenticationInfo, parameters))
                .thenApply(userInfoResponse -> ok(JsonUtils.toJson(userInfoResponse)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> createUser() {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        UserDataSource dataSource = AppTree.getUserDataSource();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        User user = JsonUtils.fromJson(request.body().asJson(), User.class);
        return CompletableFuture
                .runAsync(() -> {
                    dataSource.createUserEvent(user, authenticationInfo, parameters);
                })
                .thenApply(value -> ok(JsonUtils.toJson(Response.success())))
                .exceptionally(ResponseExceptionHandler::handleException);

    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> updateUser() {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        UserDataSource dataSource = AppTree.getUserDataSource();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        User user = JsonUtils.fromJson(request.body().asJson(), User.class);
        return CompletableFuture
                .runAsync(() -> {
                    dataSource.updateUserEvent(user, authenticationInfo, parameters);
                })
                .thenApply(value -> ok(JsonUtils.toJson(Response.success())))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> deleteUser() {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        UserDataSource dataSource = AppTree.getUserDataSource();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        User user = JsonUtils.fromJson(request.body().asJson(), User.class);
        return CompletableFuture
                .runAsync(() -> {
                    dataSource.deleteUserEvent(user, authenticationInfo, parameters);
                })
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

    private class UserInfoKeyListResponse {
        public boolean success;
        public List<UserInfoKey> records;
        public String message;
        UserInfoKeyListResponse(boolean success, List<UserInfoKey> sources, String message) {
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
