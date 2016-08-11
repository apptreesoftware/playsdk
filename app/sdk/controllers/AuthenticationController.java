package sdk.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import sdk.AppTree;
import sdk.auth.AuthenticationSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.JsonUtils;
import sdk.utils.ResponseExceptionHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by matthew on 5/12/16.
 */
public class AuthenticationController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> login() {
        AuthenticationSource source = AppTree.getAuthenticationSource();
        if ( source == null ) {
            return CompletableFuture.completedFuture(notFound("No auth endpoint registered"));
        }

        JsonNode json = request().body().asJson();
        String username = json.path("username").textValue();
        String password = json.path("password").textValue();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());

        return CompletableFuture
                .supplyAsync(() -> source.login(username, password, authenticationInfo))
                .thenApply(loginResponse -> ok(JsonUtils.toJson(loginResponse)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> logout() {
        AuthenticationSource source = AppTree.getAuthenticationSource();
        if ( source == null ) {
            return CompletableFuture.completedFuture(notFound("No auth endpoint registered"));
        }
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());

        return CompletableFuture
                .supplyAsync(() -> source.logout(authenticationInfo))
                .thenApply(response -> ok(JsonUtils.toJson(response)));
    }

    public CompletionStage<Result> validateToken() {
        AuthenticationSource source = AppTree.getAuthenticationSource();
        if ( source == null ) {
            return CompletableFuture.completedFuture(notFound("No auth endpoint registered"));
        }
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());

        return CompletableFuture
                .supplyAsync(() -> source.validateAuthenticationInfo(authenticationInfo))
                .thenApply(response -> ok(JsonUtils.toJson(response)));
    }
}
