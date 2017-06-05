package sdk.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.data.*;
import sdk.datasources.InspectionSource_Internal;
import sdk.inspection.InspectionConfiguration;
import sdk.utils.*;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static sdk.utils.Constants.AppTreeDateTimeFormat;

/**
 * Created by Matthew Smith on 8/30/16.
 * Copyright AppTree Software, Inc.
 */
public class InspectionController extends DataController {

    public CompletionStage<Result> getInspectionConfiguration(String dataSetName) {
        InspectionSource_Internal dataSource = AppTree.lookupInspectionHandler(dataSetName);
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.supplyAsync(dataSource::getInspectionConfiguration)
                .thenApply(response -> ok(JsonUtils.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> getInspectionSearchConfiguration(String dataSetName) {
        InspectionSource_Internal dataSource = AppTree.lookupInspectionHandler(dataSetName);
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound());
        }
        return CompletableFuture.supplyAsync(dataSource::getInspectionSearchConfiguration)
                .thenApply(response -> ok(JsonUtils.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @BodyParser.Of(BodyParser.Json.class)
    @With({ValidateRequestAction.class})
    public CompletionStage<Result> startInspection(String dataSetName) {
        Http.Request request = request();
        InspectionSource_Internal dataSource = AppTree.lookupInspectionHandler(dataSetName);
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound());
        }
        String callbackURL = request.getHeader(Constants.CORE_CALLBACK_URL);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return getConfiguration(dataSource, request)
                .thenCompose(inspectionConfiguration -> dataSetItemFromRequest(inspectionConfiguration.getInspectionSearchConfiguration(), request, true))
                .thenCompose(dataSetItem -> {
                    if ( callbackURL != null ) {
                        generateInspection(dataSource, dataSetItem, callbackURL, authenticationInfo, parameters);
                        return CompletableFuture.completedFuture(ok(JsonUtils.toJson(Response.asyncSuccess())));
                    } else {
                        return dataSource.startInspection(dataSetItem,authenticationInfo, parameters).thenApply(dataSet -> ok(dataSet.toJSON()).withHeader(Constants.CORE_ITEM_COUNT_HEADER, dataSet.getTotalRecords()+""));
                    }
                })
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @With({ValidateRequestAction.class})
    public CompletionStage<Result> updateInspectionItem(String dataSetName) {
        Http.Request request = request();
        InspectionSource_Internal dataSource = AppTree.lookupInspectionHandler(dataSetName);
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound());
        }
        Map<String,String> contextMap = getContextFromRequest(request);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return getConfiguration(dataSource, request)
                .thenCompose(inspectionConfiguration -> dataSetItemFromRequest(inspectionConfiguration.getInspectionServiceConfiguration(), request, false))
                .thenCompose(dataSetItem -> dataSource.updateInspectionItem(dataSetItem,contextMap, authenticationInfo, parameters))
                .thenApply(dataSet -> ok(dataSet.toJSON()).withHeader(Constants.CORE_ITEM_COUNT_HEADER, dataSet.getTotalRecords()+""))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> searchInspectionItem(String dataSetName) {
        InspectionSource_Internal dataSource = AppTree.lookupInspectionHandler(dataSetName);
        Http.Request request = request();
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound());
        }
        InspectionSearchRequest searchRequest = JsonUtils.fromJson(request.body().asJson(), InspectionSearchRequest.class);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        String callbackURL = request.getHeader(Constants.CORE_CALLBACK_URL);
        Map<String,String> contextMap = getContextFromRequest(request);
        if (callbackURL != null) {
            searchInspectionItem(dataSource, searchRequest,contextMap, callbackURL, authenticationInfo, parameters);
            return CompletableFuture.completedFuture(ok(JsonUtils.toJson(Response.asyncSuccess())));
        } else {
            return dataSource.searchInspectionItem(searchRequest.id, contextMap, authenticationInfo, parameters)
                    .thenApply(response -> ok(JsonUtils.toJson(response)).withHeader(Constants.CORE_ITEM_COUNT_HEADER, response.getTotalRecords()+""));
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    @With({ValidateRequestAction.class})
    public CompletionStage<Result> completeInspection(String dataSetName) {
        System.out.println("The complete call has reached the connector");
        Http.Request request = request();
        InspectionSource_Internal dataSource = AppTree.lookupInspectionHandler(dataSetName);
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound());
        }
        String callbackURL = request.getHeader(Constants.CORE_CALLBACK_URL);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return getConfiguration(dataSource, request)
                .thenApply(InspectionConfiguration::getInspectionServiceConfiguration)
                .thenCompose(inspectionConfiguration -> completedInspectionDataSet(inspectionConfiguration, request))
                .thenCompose(dataSet -> {
                    if ( callbackURL != null ) {
                        completeInspection(dataSource, dataSet, callbackURL, authenticationInfo, parameters);
                        return CompletableFuture.completedFuture(ok(JsonUtils.toJson(Response.asyncSuccess())));
                    } else {
                        return dataSource.completeInspection(dataSet, authenticationInfo, parameters).thenApply(returnDataSet -> ok(returnDataSet.toJSON()).withHeader(Constants.CORE_ITEM_COUNT_HEADER, returnDataSet.getTotalRecords()+""));
                    }
                })
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    private void completeInspection(InspectionSource_Internal dataSource, InspectionDataSet completedDataSet, String callbackURL, AuthenticationInfo authenticationInfo, Parameters parameters) {
        dataSource.completeInspection(completedDataSet, authenticationInfo, parameters)
                .whenComplete((dataSet, throwable) -> {
                    if ( throwable != null ) {
                        sendDataSetExceptionCallback(throwable, callbackURL);
                    } else {
                        sendDataSetResponse(dataSet, callbackURL);
                    }
                });
    }

    private void generateInspection(InspectionSource_Internal dataSource, DataSetItem dataSetItem, String callbackURL, AuthenticationInfo authenticationInfo, Parameters parameters) {
        dataSource.startInspection(dataSetItem, authenticationInfo, parameters)
                .whenComplete((dataSet, throwable) -> {
                    if ( throwable != null ) {
                        sendDataSetExceptionCallback(throwable, callbackURL);
                    } else {
                        sendDataSetResponse(dataSet, callbackURL);
                    }
                });
    }

    private void searchInspectionItem(InspectionSource_Internal dataSource, InspectionSearchRequest searchRequest, Map<String,String> context, String callbackUrl, AuthenticationInfo authenticationInfo, Parameters parameters) {
        dataSource.searchInspectionItem(searchRequest.id, context, authenticationInfo, parameters)
                .whenComplete((dataSet, throwable) -> {
                    if ( throwable != null ) {
                        sendDataSetExceptionCallback(throwable, callbackUrl);
                    } else {
                        sendDataSetResponse(dataSet, callbackUrl);
                    }
                });
    }

    private CompletionStage<InspectionConfiguration> getConfiguration(InspectionSource_Internal dataSource, Http.Request request) {
        return CompletableFuture.supplyAsync(() -> {
            AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
            Parameters parameters = new Parameters(request.queryString());
            Collection<ServiceConfigurationAttribute> searchAttributes = dataSource.getInspectionSearchAttributes(authenticationInfo, parameters);
            Collection<ServiceConfigurationAttribute> inspectionAttributes = dataSource.getInspectionItemAttributes(authenticationInfo, parameters);
            if (searchAttributes == null || inspectionAttributes == null ) {
                throw new RuntimeException("You must implement getInspectionSearchAttributes and getInspectionSearchAttributes");
            }
            ServiceConfiguration inspectionSearchConfiguration = new ServiceConfiguration.Builder("Search")
                    .withAttributes(searchAttributes)
                    .build();
            ServiceConfiguration inspectionDataSetConfiguration = new ServiceConfiguration.Builder("Inspection")
                    .withAttributes(inspectionAttributes)
                    .build();
            return new InspectionConfiguration(inspectionDataSetConfiguration, inspectionSearchConfiguration, dataSource.shouldSendIncrementalUpdates());
        });
    }

    private CompletionStage<InspectionDataSet> completedInspectionDataSet(ServiceConfiguration configuration, Http.Request request) {
        JsonNode jsonNode = request.body().asJson();
        InspectionDataSet dataSet = new InspectionDataSet(configuration.attributes);
        dataSet.setTotalRecords(jsonNode.get("totalRecords").asInt(0));
        String dateString = jsonNode.get("startDate").asText();
        if ( dateString != null ) {
            try {
                dataSet.setStartDate(AppTreeDateTimeFormat.parseDateTime(dateString));
            } catch (Exception ignored) {}
        }
        dateString = jsonNode.get("endDate").asText();
        if ( dateString != null ) {
            try {
                dataSet.setEndDate(AppTreeDateTimeFormat.parseDateTime(dateString));
            } catch (Exception ignored) {}
        }
        String collectionStateString = jsonNode.get("state").asText();
        if (collectionStateString != null) {
            dataSet.setStatus(InspectionDataSet.CollectionState.valueOf(collectionStateString));
        }
        JsonNode context = jsonNode.get("context");
        context.fields().forEachRemaining((entry) -> {
            dataSet.setContextValue(entry.getKey(), entry.getValue().textValue());
        });

        JsonNode records = jsonNode.get("records");
        if ( records != null && records.isArray() ) {
            for ( JsonNode dataSetItemNode : records ) {
                if ( dataSetItemNode.isArray() ) {
                    dataSetItemNode = dataSetItemNode.get(0);
                }
                dataSetItemForJSON((ObjectNode)dataSetItemNode, dataSet, false, null);
            }
        }
        return CompletableFuture.completedFuture(dataSet);
        //return dataSet;
    }

    private @Nullable Map<String,String> getContextFromRequest(Http.Request request) {
        String contextString = request.getQueryString("context");
        if ( contextString == null ) return null;
        String[] entries = contextString.split(",");
        Map<String,String> entryMap = new HashMap<>();
        for (String entry : entries) {
            String[] components = entry.split(":");
            if (components.length == 2) {
                entryMap.put(components[0], components[1]);
            }
        }
        return entryMap;
    }

}
