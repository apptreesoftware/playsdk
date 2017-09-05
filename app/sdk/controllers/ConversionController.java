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

import java.util.Collections;
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
        DataSetItem dataSetItem = dataSetItemFromRequest(dataSource_internal.getConfiguration(), request);
        Object destination = dataSource_internal.getSourceType().newInstance();
        DataSetItem newDataSetItem = dataSource_internal.convert(dataSetItem, destination);
        return ok(newDataSetItem.toJSON());
    }

    public Result getConversionConfiguration(String endpointName) {
        ConversionDataSource_Internal dataSource_internal = AppTree.lookupConversionHandler(endpointName);
        if (dataSource_internal == null) return notFound();
        JsonNode jsonNode = Json.toJson(dataSource_internal.getConfiguration());
        return ok(jsonNode);
    }


    protected DataSetItem dataSetItemFromRequest(ServiceConfiguration configuration, Http.Request request) {
        ObjectNode node = (ObjectNode) request.body().asJson();
        DataSet newDataSet = new DataSet(configuration.getAttributes());
        ObjectNode sourceItem = (ObjectNode) node.findValue("dataSetItem");
        return dataSetItemForJSON(sourceItem, newDataSet, false, new HashMap<>());

    }

    DataSetItem dataSetItemForJSON(ObjectNode json, DataSet dataSet, boolean search, HashMap<String, Http.MultipartFormData.FilePart> attachmentMap) {
        DataSetItem dataSetItem = dataSet.addNewDataSetItem();
        dataSetItem.updateFromJSON(json, attachmentMap, search);
        return dataSetItem;
    }


}
