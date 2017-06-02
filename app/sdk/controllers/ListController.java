package sdk.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.datasources.ListDataSource_Internal;
import sdk.list.CacheListSQLGenerator;
import sdk.list.ListDataSourceResponse;
import sdk.list.ListServiceConfiguration;
import sdk.utils.*;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static sdk.utils.CallbackLogger.logCallbackInfo;
import static sdk.utils.CallbackLogger.logExceptionCallback;
import static sdk.utils.Constants.CORE_CALLBACK_URL;

/**
 * Created by alexis on 5/4/16.
 */
@With({ValidateRequestAction.class})
public class ListController extends Controller {

    @Inject
    WSClient wsClient;

    public CompletionStage<Result> getListConfiguration(String listName) {
        return CompletableFuture.supplyAsync(() -> {
            ListDataSource_Internal dataSource = AppTree.lookupListHandler(listName).orElseThrow(() -> new RuntimeException("Invalid List Data Source"));
            ListServiceConfiguration configuration = dataSource.getListServiceConfiguration();
            return ok(JsonUtils.toJson(configuration));
        }).exceptionally(exception -> {
            Response response = new Response(false, exception.getMessage());
            return ok(JsonUtils.toJson(response));
        });
    }

    public CompletionStage<Result> getListData(String listName) {
        Http.Request request = request();
        boolean useJSON = false;
        if (request.getQueryString("json") != null && request.getQueryString("json").equalsIgnoreCase("true")) {
            useJSON = true;
        }
        String callbackURL = request.getHeader(CORE_CALLBACK_URL);
        final boolean json = useJSON;
        ListDataSource_Internal dataSource = AppTree.lookupListHandler(listName).orElseThrow(() -> new RuntimeException("Invalid List Data Source " + listName));
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        if (callbackURL != null) {
            generateListDataResponse(dataSource, callbackURL, authenticationInfo, parameters);
            return CompletableFuture.completedFuture(ok(JsonUtils.toJson(Response.asyncSuccess())));
        } else {
            return dataSource.getList(authenticationInfo, parameters)
                    .thenApply(list -> {
                        if (json) {
                            ListDataSourceResponse response = new ListDataSourceResponse.Builder().setSuccess(true).setRecords(list).createListDataSourceResponse();
                            return ok(JsonUtils.toJson(response)).withHeader(Constants.CORE_ITEM_COUNT_HEADER, response.getRecords().size()+"");
                        } else {
                            File sqlFile = CacheListSQLGenerator.generateDatabaseForList(list);
                            return ok(sqlFile).withHeader(Constants.CORE_ITEM_COUNT_HEADER, list.listItems.size()+"");
                        }
                    }).exceptionally(exception -> {
                        if (json) {
                            exception = ResponseExceptionHandler.findRootCause(exception);
                            ListDataSourceResponse response = new ListDataSourceResponse.Builder().setSuccess(false).setMessage(exception.getMessage()).createListDataSourceResponse();
                            return ok(JsonUtils.toJson(response));
                        } else {
                            return internalServerError().withHeader("ListError", exception.getMessage());
                        }
                    });
        }
    }

    private void generateListDataResponse(ListDataSource_Internal dataSource, String callbackURL, AuthenticationInfo authenticationInfo, Parameters parameters) {
        dataSource.getList(authenticationInfo, parameters)
                .thenApply(list -> {
                    File sqlFile = CacheListSQLGenerator.generateDatabaseForList(list);
                    WSRequest request = wsClient.url(callbackURL);
                    request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_SUCCESS);
                    request.setHeader(Constants.CORE_ITEM_COUNT_HEADER, list.listItems.size()+"");
                    return request
                            .post(sqlFile)
                            .whenComplete((wsResponse, throwable) -> {
                                logCallbackInfo(wsResponse, throwable, callbackURL);
                            });
                })
                .exceptionally(throwable -> {
                    WSRequest request = wsClient.url(callbackURL);
                    Logger.debug("Sending error back to " + callbackURL);
                    ResponseExceptionHandler.updateCallbackWithException(request, throwable);
                    return request
                            .execute("POST")
                            .whenComplete((wsResponse, callbackThrowable) -> {
                                logExceptionCallback(wsResponse, throwable, callbackThrowable, callbackURL);
                            });
        });
    }

    public CompletionStage<Result> searchListData(String listName) {
        Http.Request request = request();
        JsonNode json = request.body().asJson();
        String searchTerm = json.path("searchTerm").textValue();
        JsonNode context = json.path("context");
        boolean barcodeSearch = json.path("barcodeSearch").booleanValue();
        Map<String, Object> searchContext = Json.mapper().convertValue(context, Map.class);
        ListDataSource_Internal dataSource = AppTree.lookupListHandler(listName).orElseThrow(() -> new RuntimeException("Invalid List Data Source"));
        AuthenticationInfo info = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());

        return dataSource.queryList(searchTerm, barcodeSearch, searchContext, info, parameters)
                .thenApply(list -> {
                    ListDataSourceResponse response = new ListDataSourceResponse.Builder().setSuccess(true).setRecords(list).createListDataSourceResponse();
                    return ok(JsonUtils.toJson(response)).withHeader(Constants.CORE_ITEM_COUNT_HEADER, response.getRecords().size()+"");
                })
                .exceptionally(ResponseExceptionHandler::handleException);
//                .exceptionally(exception -> {
//                    ListDataSourceResponse response = new ListDataSourceResponse.Builder().setSuccess(false).setMessage(exception.getMessage()).createListDataSourceResponse();
//                    return ok(JsonUtils.toJson(response));
//                });
    }
}
