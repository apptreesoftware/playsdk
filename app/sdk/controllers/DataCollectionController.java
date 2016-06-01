package sdk.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import sdk.AppTree;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.Event;
import sdk.data.ServiceConfiguration;
import sdk.datacollection.DataCollectionSource;
import sdk.serializers.DataSetModule;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;
import sdk.utils.Response;
import sdk.utils.ResponseExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by Matthew on 5/24/2016.
 */
public class DataCollectionController extends Controller {

    public DataCollectionController() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new DataSetModule());
        Json.setObjectMapper(objectMapper);
    }

    public CompletionStage<Result> getDataCollectionConfiguration(String endpoint) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> {
                    DataCollectionSource dataSource = AppTree.lookupDataCollectionHandler(endpoint).orElseThrow(() -> new RuntimeException("No data collection handler exists for " + endpoint));
                    return dataSource.getConfiguration(authenticationInfo, parameters);
                })
                .thenApply(response -> ok(Json.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> getDataSet(String endpoint, String sessionID) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> {
                    DataCollectionSource dataSource = AppTree.lookupDataCollectionHandler(endpoint).orElseThrow(() -> new RuntimeException("No data collection handler exists for " + endpoint));
                    return dataSource.getDataSet(sessionID, authenticationInfo, parameters);
                })
                .thenApply(dataSourceResponse -> ok(dataSourceResponse.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> startDataCollection(String endpoint) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        JsonNode json = request.body().asJson();
        String inspectionItemID = json.get("collectionItem").asText();
        if ( inspectionItemID == null ) return CompletableFuture.completedFuture(badRequest("No primary key was passed for the item you want to collect data against."));
        return CompletableFuture
                .supplyAsync(() -> {
                    DataCollectionSource dataSource = AppTree.lookupDataCollectionHandler(endpoint).orElseThrow(() -> new RuntimeException("No data collection handler exists for " + endpoint));
                    return dataSource.startDataCollectionSession(inspectionItemID, authenticationInfo, parameters);
                })
                .thenApply(StartResponse::new)
                .thenApply(response -> ok(Json.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> cancelDataCollection(String endpoint, String sessionID) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> {
                    DataCollectionSource dataSource = AppTree.lookupDataCollectionHandler(endpoint).orElseThrow(() -> new RuntimeException("No data collection handler exists for " + endpoint));
                    return dataSource.cancelDataCollection(sessionID, authenticationInfo, parameters);
                })
                .thenApply(response -> ok(Json.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> completeDataCollection(String endpoint, String sessionID) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> {
                    DataCollectionSource dataSource = AppTree.lookupDataCollectionHandler(endpoint).orElseThrow(() -> new RuntimeException("No data collection handler exists for " + endpoint));
                    return dataSource.endDataCollection(sessionID, authenticationInfo, parameters);
                })
                .thenApply(response -> ok(Json.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> saveDataCollection(String endpoint, String sessionID) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> {
                    DataCollectionSource dataSource = AppTree.lookupDataCollectionHandler(endpoint).orElseThrow(() -> new RuntimeException("No data collection handler exists for " + endpoint));
                    return dataSource.saveDataCollection(sessionID, authenticationInfo, parameters);
                })
                .thenApply(response -> ok(Json.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> searchForExternalItem(String endpoint, String sessionID, String dataSetPrimaryKey) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> {
                    DataCollectionSource dataSource = AppTree.lookupDataCollectionHandler(endpoint).orElseThrow(() -> new RuntimeException("No data collection handler exists for " + endpoint));
                    return dataSource.searchExternalItem(sessionID, dataSetPrimaryKey, authenticationInfo, parameters);
                })
                .thenApply(dataSourceResponse -> ok(dataSourceResponse.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> postEvent(String endpoint, String dataSetItemID) {
        JsonNode json = request().body().asJson();
        if (json == null) return CompletableFuture.completedFuture(badRequest("No event information was provided"));
        Event event = Json.fromJson(json, Event.class);
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> {
                    DataCollectionSource dataSource = AppTree.lookupDataCollectionHandler(endpoint).orElseThrow(() -> new RuntimeException("Invalid Data Set"));
                    return dataSource.updateEventForDataSetItem(dataSetItemID, event, authenticationInfo, parameters);
                })
                .thenApply(response -> ok(Json.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }


    public CompletionStage<Result> submit(String endpoint, String sessionID) {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return CompletableFuture
                .supplyAsync(() -> {
                    DataCollectionSource dataSource = AppTree.lookupDataCollectionHandler(endpoint).orElseThrow(() -> new RuntimeException("Invalid Data Set"));
                    ServiceConfiguration serviceConfiguration = dataSource.getConfiguration(authenticationInfo, parameters);
                    DataSet dataSet = new DataSet(serviceConfiguration.getAttributes());
                    Http.MultipartFormData body = request.body().asMultipartFormData();
                    Map<String, String[]> bodyMap = body.asFormUrlEncoded();
                    List<Http.MultipartFormData.FilePart> files = body.getFiles();
                    String formJSON = bodyMap.get("formJSON")[0];
                    HashMap<String, Http.MultipartFormData.FilePart> attachmentMap = new HashMap<>();
                    for (Http.MultipartFormData.FilePart file : files) {
                        attachmentMap.put(file.getKey(), file);
                    }
                    ObjectNode json = (ObjectNode) Json.parse(formJSON);
                    DataSetItem dataSetItem = dataSetItemForJSON(json, dataSet, attachmentMap);
                    return dataSource.updateDataSetItem(sessionID, dataSetItem, authenticationInfo, parameters);
                })
                .thenApply(dataSet -> ok(dataSet.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    private DataSetItem dataSetItemForJSON(ObjectNode json, DataSet dataSet, HashMap<String, Http.MultipartFormData.FilePart> attachmentMap) {
        DataSetItem dataSetItem = dataSet.addNewDataSetItem();
        dataSetItem.updateFromJSON(json, attachmentMap, false);
        return dataSetItem;
    }


}

class StartResponse extends Response {
    public String sessionID;
    StartResponse(String sessionID) {
        this.sessionID = sessionID;
    }
}
