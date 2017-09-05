package sdk.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import sdk.AppTree;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.datasources.ConversionDataSource_Internal;
import sdk.datasources.DataSource_Internal;
import sdk.datasources.base.DataSource;
import sdk.utils.JsonUtils;
import sdk.utils.ResponseExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;

/**
 * Created by Orozco on 9/5/17.
 */
public class ConversionController extends Controller {
    public Result getConversion(String endpointName) throws ExecutionException, InterruptedException, IllegalAccessException, InstantiationException {
        Http.Request request = request();
        ConversionDataSource_Internal dataSource_internal = AppTree.lookupConversionHandler(endpointName);
        if (dataSource_internal == null) return notFound();
        DataSetItem dataSetItem = dataSetItemFromRequest(dataSource_internal.getConfiguration(), request, false).toCompletableFuture().get();
        Object destination = dataSource_internal.getDestionationType().newInstance();
        DataSetItem newDataSetItem = dataSource_internal.convert(dataSetItem, destination);
        return ok(Json.toJson(newDataSetItem));
    }

    public Result getConversionConfiguration(String endpointName) {
        ConversionDataSource_Internal dataSource_internal = AppTree.lookupConversionHandler(endpointName);
        if (dataSource_internal == null) return notFound();
        JsonNode jsonNode = Json.toJson(dataSource_internal.getConfiguration());
        return ok(jsonNode);
    }


    protected CompletionStage<DataSetItem> dataSetItemFromRequest(ServiceConfiguration configuration, Http.Request request, boolean search) {
        return CompletableFuture.supplyAsync(() -> new DataSet(configuration.getAttributes()))
                .thenApply(dataSet -> {
                    if (!search) {
                        Http.MultipartFormData body = request.body().asMultipartFormData();
                        Map<String, String[]> bodyMap = body.asFormUrlEncoded();
                        List<Http.MultipartFormData.FilePart> files = body.getFiles();
                        String formJSON = bodyMap.get("formJSON")[0];
                        HashMap<String, Http.MultipartFormData.FilePart> attachmentMap = new HashMap<>();
                        for (Http.MultipartFormData.FilePart file : files) {
                            attachmentMap.put(file.getKey(), file);
                        }
                        ObjectNode json = (ObjectNode) Json.parse(formJSON);
                        return dataSetItemForJSON(json, dataSet, search, attachmentMap);
                    } else {
                        ObjectNode json = (ObjectNode) request.body().asJson();
                        return dataSetItemForJSON(json, dataSet, search, new HashMap<>());
                    }
                });
    }

    DataSetItem dataSetItemForJSON(ObjectNode json, DataSet dataSet, boolean search, HashMap<String, Http.MultipartFormData.FilePart> attachmentMap) {
        DataSetItem dataSetItem = dataSet.addNewDataSetItem();
        dataSetItem.updateFromJSON(json, attachmentMap, search);
        return dataSetItem;
    }


}
