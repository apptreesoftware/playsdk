package sdk.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.utils.Constants;
import sdk.utils.ResponseExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static sdk.utils.CallbackLogger.logCallbackInfo;
import static sdk.utils.CallbackLogger.logExceptionCallback;

/**
 * Created by Matthew Smith on 9/7/16.
 * Copyright AppTree Software, Inc.
 */
public abstract class DataController extends Controller {

    @Inject
    protected WSClient wsClient;

    protected CompletionStage<DataSetItem> dataSetItemFromRequest(ServiceConfiguration configuration, Http.Request request, boolean search) {
        return CompletableFuture.supplyAsync(() -> new DataSet(configuration.getAttributes()))
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

    DataSetItem dataSetItemForJSON(ObjectNode json, DataSet dataSet, boolean search, HashMap<String, Http.MultipartFormData.FilePart> attachmentMap) {
        DataSetItem dataSetItem = dataSet.addNewDataSetItem();
        dataSetItem.updateFromJSON(json, attachmentMap, search);
        return dataSetItem;
    }

    void sendDataSetExceptionCallback(Throwable throwable, String callbackURL) {
        CompletableFuture.runAsync(() -> {
            WSRequest request = wsClient.url(callbackURL);
            ResponseExceptionHandler.updateCallbackWithException(request, throwable);
            request.execute("POST")
                    .whenComplete((wsResponse, callbackThrowable) -> {
                        logExceptionCallback(wsResponse, throwable, callbackThrowable, callbackURL);
                    });
        });
    }

    CompletionStage<WSResponse> sendDataSetResponse(DataSet dataSet, String callbackURL) {
        WSRequest request = wsClient.url(callbackURL);
        if ( dataSet.isSuccess() ) {
            request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_SUCCESS);
        } else {
            request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_WARNING);
            ObjectNode json = Json.newObject();
            json.put(Constants.CORE_CALLBACK_MESSAGE, dataSet.getMessage() != null ? dataSet.getMessage() : "");
            request.setBody(json);
        }
        return request
                .post(dataSet.toJSON())
                .whenComplete((wsResponse, throwable) -> {
                    logCallbackInfo(wsResponse, throwable, callbackURL);
                });
    }
}
