package controllers;

import sdk.list.*;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;
import sdk.utils.Response;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import sdk.*;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by alexis on 5/4/16.
 */
public class ListController extends Controller {
    public CompletionStage<Result> getListConfiguration(String listName) {
        return CompletableFuture.supplyAsync(() -> {
            ListDataSource dataSource = AppTree.lookupListHandler(listName);
            if ( dataSource == null ) throw new RuntimeException("Invalid List");
            ListServiceConfiguration configuration = dataSource.getDescription();
            return ok(Json.toJson(configuration));
        }).exceptionally(exception -> {
            Response response = new Response(false, exception.getMessage());
            return ok(Json.toJson(response));
        });
    }

    public CompletionStage<Result> getListData(String listName) {
        Http.Request request = request();
        return CompletableFuture.supplyAsync(() -> {
            ListDataSource dataSource = AppTree.lookupListHandler(listName);
            if ( dataSource == null ) throw new RuntimeException("Invalid List");
            AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
            Parameters parameters = new Parameters(request.queryString());
            List list = dataSource.getList(authenticationInfo, parameters);
            if ( parameters.getBooleanForKey("json") ) {
                ListDataSourceResponse response = new ListDataSourceResponse.Builder().setSuccess(true).setRecords(list).createListDataSourceResponse();
                return ok(Json.toJson(response));
            } else {
                File sqlFile = CacheListSQLGenerator.generateDatabaseFromCacheListResponse(list, listName);
                return ok(sqlFile);
            }
        }).exceptionally(exception -> {
            ListDataSourceResponse response = new ListDataSourceResponse.Builder().setSuccess(false).setMessage(exception.getMessage()).createListDataSourceResponse();
            return ok(Json.toJson(response));
        });
    }
}
