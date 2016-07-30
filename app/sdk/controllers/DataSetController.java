package sdk.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.attachment.AttachmentDataSource;
import sdk.data.*;
import sdk.serializers.DataSetModule;
import sdk.serializers.DateTimeModule;
import sdk.utils.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by alexis on 5/3/16.
 */
@With({ValidateRequestAction.class})
public class DataSetController extends Controller {

    @Inject
    WSClient wsClient;

    Executor executor = Executors.newFixedThreadPool(10);

    public DataSetController() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new DataSetModule());
        objectMapper.registerModule(new DateTimeModule());
        Json.setObjectMapper(objectMapper);
    }

    public CompletionStage<Result> getDataSet(String dataSetName) {
        Http.Request request = request();
        String callbackURL = request.getHeader(Constants.CORE_CALLBACK_URL);
        return CompletableFuture
                .supplyAsync(() -> {
                    AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
                    Parameters parameters = new Parameters(request.queryString());
                    DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName).orElseThrow(() -> new RuntimeException("Invalid Data Set"));
                    if ( callbackURL != null ) {
                        generateDataSourceResponse(dataSource, callbackURL, authenticationInfo, parameters);
                        return ok(Json.toJson(Response.asyncSuccess()));
                    } else {
                        DataSet dataSet = dataSource.getDataSet(authenticationInfo, parameters);
                        return ok(dataSet.toJSON());
                    }
                })
                .exceptionally(throwable -> ResponseExceptionHandler.handleException(throwable, callbackURL != null));
    }

    public CompletionStage<Result> searchDataSet(String dataSetName) {
        Http.Request request = request();
        String callbackURL = request.getHeader(Constants.CORE_CALLBACK_URL);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return dataSetItemFromRequest(dataSetName, request, true)
                .thenApply(dataSetItem -> {
                    DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName).orElseThrow(() -> new RuntimeException("Invalid Data Set"));
                    if ( callbackURL != null ) {
                        generateDataSourceSearchResponse(dataSource, dataSetItem, callbackURL, authenticationInfo, parameters);
                        return ok(Json.toJson(Response.asyncSuccess()));
                    } else {
                        DataSet dataSet = dataSource.queryDataSet(dataSetItem, authenticationInfo, parameters);
                        return ok(dataSet.toJSON());
                    }
                })
                .exceptionally(throwable -> ResponseExceptionHandler.handleException(throwable, callbackURL != null));
    }


    private void generateDataSourceResponse(DataSource dataSource, String callbackURL, AuthenticationInfo authenticationInfo, Parameters parameters) {
        CompletableFuture.supplyAsync(() -> dataSource.getDataSet(authenticationInfo, parameters))
                .thenApply(dataSet -> sendDataSetResponse(dataSet, callbackURL))
                .exceptionally(throwable -> {
                    WSRequest request = wsClient.url(callbackURL);
                    ResponseExceptionHandler.updateCallbackWithException(request, throwable);
                    return request.post("");
                });
    }

    private void generateDataSourceSearchResponse(DataSource dataSource, DataSetItem dataSetItem, String callbackURL, AuthenticationInfo authenticationInfo, Parameters parameters) {
        CompletableFuture.supplyAsync(() -> dataSource.queryDataSet(dataSetItem, authenticationInfo, parameters))
                .thenApply(dataSet -> sendDataSetResponse(dataSet, callbackURL))
                .exceptionally(throwable -> {
                    WSRequest request = wsClient.url(callbackURL);
                    ResponseExceptionHandler.updateCallbackWithException(request, throwable);
                    return request.post("");
                });
    }

    private CompletionStage<WSResponse> sendDataSetResponse(DataSet dataSet, String callbackURL) {
         WSRequest request = wsClient.url(callbackURL);
        if ( dataSet.isSuccess() ) {
            request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_SUCCESS);
        } else {
            request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_WARNING);
            request.setHeader(Constants.CORE_CALLBACK_MESSAGE, dataSet.getMessage() != null ? dataSet.getMessage() : "");
        }
        return request.post(dataSet.toJSON());
    }


    public CompletionStage<Result> getDataConfiguration(String dataSetName) {
        Http.Request request = request();
        return CompletableFuture
                .supplyAsync(() -> {
                    DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName).orElseThrow(() -> new RuntimeException("Invalid Data Set"));
                    AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
                    Parameters parameters = new Parameters(request.queryString());
                    return dataSource.getConfiguration(authenticationInfo, parameters);
                })
                .thenApply(response -> ok(Json.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }


    public CompletionStage<Result> createDataSetItem(String dataSetName) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return dataSetItemFromRequest(dataSetName, request, false)
                .thenApply(dataSetItem -> {
                    DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName).orElseThrow(() -> new RuntimeException("Invalid Data Set"));
                    return dataSource.createDataSetItem(dataSetItem, authenticationInfo, parameters);
                })
                .thenApply(dataSet -> ok(dataSet.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> updateDataSetItem(String dataSetName) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return dataSetItemFromRequest(dataSetName, request, false)
                .thenApply(dataSetItem -> {
                    DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName).orElseThrow(() -> new RuntimeException("Invalid Data Set"));
                    return dataSource.updateDataSetItem(dataSetItem, authenticationInfo, parameters);
                })
                .thenApply(dataSet -> ok(dataSet.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> bulkUpdate(String dataSetName) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        Http.MultipartFormData body = request.body().asMultipartFormData();
        Map<String, String[]> bodyMap = body.asFormUrlEncoded();
        String recordsJSONArray = bodyMap.get("records")[0];
        ArrayNode idsArray = (ArrayNode) Json.parse(recordsJSONArray);
        List<String> ids = new ArrayList<>();
        for ( JsonNode node : idsArray ) {
            ids.add(node.asText());
        }
        return dataSetItemFromRequest(dataSetName, request, false)
                .thenApply(dataSetItem -> {
                    DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName).orElseThrow(() -> new RuntimeException("Invalid Data Set"));
                    return dataSource.bulkUpdateDataSetItems(ids, dataSetItem, authenticationInfo, parameters);
                })
                .thenApply(dataSet -> ok(dataSet.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> getDataSetItem(String dataSetName, String primaryKey) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> (DataSource) AppTree.lookupDataSetHandler(dataSetName).orElseThrow(() -> new RuntimeException("Invalid DataSet")))
                .thenApply(dataSource -> dataSource.getDataSetItem(authenticationInfo, primaryKey, parameters))
                .thenApply(dataSet -> ok(dataSet.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> getAttachment(String attachmentID) {
        AttachmentDataSource source = AppTree.getAttachmentDataSource();
        if ( source == null ) {
            return CompletableFuture.completedFuture(notFound("No attachment source provided"));
        }
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> source.getAttachment(attachmentID, authenticationInfo, parameters))
                .thenApply(attachmentResponse -> {
                    String mimeType = attachmentResponse.contentType != null ? attachmentResponse.contentType : "application/octet-stream";
                    String filename = attachmentResponse.fileName != null ? attachmentResponse.fileName : UUID.randomUUID().toString();
                    return ok(attachmentResponse.inputStream)
                            .withHeader("Filename", filename )
                            .withHeader("Content-Type", mimeType);
                });
    }

    public CompletionStage<Result> postEvent(String dataSetName, String dataSetItemID) {
        JsonNode json = request().body().asJson();
        if (json == null) return CompletableFuture.completedFuture(badRequest("No event information was provided"));
        Event event = Json.fromJson(json, Event.class);
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> {
                    DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName).orElseThrow(() -> new RuntimeException("Invalid Data Set"));
                    return dataSource.updateEventForDataSetItem(dataSetItemID, event, authenticationInfo, parameters);
                })
                .thenApply(response -> ok(Json.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    private CompletionStage<DataSetItem> dataSetItemFromRequest(String dataSetName, Http.Request request, boolean search) {
        return CompletableFuture.supplyAsync(() -> (DataSource) AppTree.lookupDataSetHandler(dataSetName)
                .orElseThrow(() -> new RuntimeException("Invalid Data Set")))
                .thenApply(dataSource -> getServiceConfiguration(dataSource, request))
                .thenApply(serviceConfiguration -> new DataSet(serviceConfiguration.getAttributes()))
                .thenApply(dataSet -> {
                    if ( !search ) {
                        Http.MultipartFormData body = request.body().asMultipartFormData();
                        Map<String, String[]> bodyMap = body.asFormUrlEncoded();
                        List<Http.MultipartFormData.FilePart> files = body.getFiles();
                        String formJSON = bodyMap.get("formJSON")[0];
                        HashMap<String, Http.MultipartFormData.FilePart> attachmentMap = new HashMap<>();
                        for (Http.MultipartFormData.FilePart file : files) {
                            attachmentMap.put(file.getKey(), file);
                        }
                        ObjectNode json = (ObjectNode) Json.parse(formJSON);
                        return dataSetItemForJSON(json, dataSet,search, attachmentMap);
                    } else {
                        ObjectNode json = (ObjectNode) request.body().asJson();
                        return dataSetItemForJSON(json, dataSet,search, new HashMap<>());
                    }
                });
    }

    private DataSetItem dataSetItemForJSON(ObjectNode json, DataSet dataSet, boolean search, HashMap<String, Http.MultipartFormData.FilePart> attachmentMap) {
        DataSetItem dataSetItem = dataSet.addNewDataSetItem();
        dataSetItem.updateFromJSON(json, attachmentMap, search);
        return dataSetItem;
    }

    private ServiceConfiguration getServiceConfiguration(DataSource dataSource, Http.Request request) {
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return dataSource.getConfiguration(authenticationInfo, parameters);
    }
}
