package sdk.playcontrollers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import sdk.AppTree;
import sdk.list.*;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;
import sdk.utils.Response;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by alexis on 5/4/16.
 */
public class ListController extends Controller {
    public CompletionStage<Result> getListConfiguration(String listName) {
        return CompletableFuture.supplyAsync(() -> {
            ListDataSource dataSource = AppTree.lookupListHandler(listName).orElseThrow(() -> new RuntimeException("Invalid List Data Source"));
            ListServiceConfiguration configuration = dataSource.getListServiceConfiguration();
            return ok(Json.toJson(configuration));
        }).exceptionally(exception -> {
            Response response = new Response(false, exception.getMessage());
            return ok(Json.toJson(response));
        });
    }

    public CompletionStage<Result> getListData(String listName) {
        Http.Request request = request();
        return CompletableFuture.supplyAsync(() -> {
            ListDataSource dataSource = AppTree.lookupListHandler(listName).orElseThrow(() -> new RuntimeException("Invalid List Data Source"));
            if ( !(dataSource instanceof CacheableList) ) throw new RuntimeException("This list does not support a cache response");
            AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
            Parameters parameters = new Parameters(request.queryString());
            List list = ((CacheableList)dataSource).getList(authenticationInfo, parameters);
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

    public CompletionStage<Result> searchListData(String listName) {
        Http.Request request = request();
        JsonNode json = request.body().asJson();
        String searchTerm = json.get("searchTerm").textValue();
        JsonNode context = json.get("context");
        boolean barcodeSearch = json.get("barcodeSearch").booleanValue();
        return CompletableFuture.supplyAsync(() -> {
            ListDataSource dataSource = AppTree.lookupListHandler(listName).orElseThrow(() -> new RuntimeException("Invalid List Data Source"));
            if ( !(dataSource instanceof SearchableList) ) throw new RuntimeException("This list does not support querying.");
            AuthenticationInfo info = new AuthenticationInfo(request.headers());
            Parameters parameters = new Parameters(request.queryString());
            List list = ((SearchableList)dataSource).queryList(searchTerm, barcodeSearch, context, info, parameters);
            ListDataSourceResponse response = new ListDataSourceResponse.Builder().setSuccess(true).setRecords(list).createListDataSourceResponse();
            return ok(Json.toJson(response));
        }).exceptionally(exception -> {
            ListDataSourceResponse response = new ListDataSourceResponse.Builder().setSuccess(false).setMessage(exception.getMessage()).createListDataSourceResponse();
            return ok(Json.toJson(response));
        });
    }
}
