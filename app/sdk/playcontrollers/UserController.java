package sdk.playcontrollers;

import play.libs.Json;
import play.mvc.*;
import sdk.AppTree;
import sdk.user.User;
import sdk.user.UserDataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;
import sdk.utils.Response;
import sdk.utils.ResponseExceptionHandler;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by matthew on 5/12/16.
 */
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
                .thenApply(userInfo -> ok(Json.toJson(userInfo)))
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
                .thenApply(response -> ok(Json.toJson(response)))
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
                .thenApply(sources -> new UserStringListResponse(true, sources, null))
                .thenApply(response -> ok(Json.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> checkForUserInfo() {
        UserDataSource dataSource = AppTree.getUserDataSource();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        CheckForUserRequest infoRequest = Json.fromJson(request().body().asJson(), CheckForUserRequest.class);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        return CompletableFuture
                .supplyAsync(() -> dataSource.checkForUser(infoRequest.externalUserID, infoRequest.source, authenticationInfo, parameters))
                .thenApply(userInfoResponse -> ok(Json.toJson(userInfoResponse)))
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
        User user = Json.fromJson(request.body().asJson(), User.class);
        return CompletableFuture
                .runAsync(() -> {
                    dataSource.createUserEvent(user, authenticationInfo, parameters);
                })
                .thenApply(value -> ok(Json.toJson(Response.success())))
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
        User user = Json.fromJson(request.body().asJson(), User.class);
        return CompletableFuture
                .runAsync(() -> {
                    dataSource.updateUserEvent(user, authenticationInfo, parameters);
                })
                .thenApply(value -> ok(Json.toJson(Response.success())))
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
        User user = Json.fromJson(request.body().asJson(), User.class);
        return CompletableFuture
                .runAsync(() -> {
                    dataSource.deleteUserEvent(user, authenticationInfo, parameters);
                })
                .thenApply(value -> ok(Json.toJson(Response.success())))
                .exceptionally(ResponseExceptionHandler::handleException);
    }




    private class UserStringListResponse {
        boolean success;
        List<String> records;
        String message;
        UserStringListResponse(boolean success, List<String> sources, String message) {
            this.success = success;
            this.records = sources;
            this.message = message;
        }
    }

    private class CheckForUserRequest {
        public String externalUserID;
        public String source;
    }
}
