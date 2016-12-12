package sdk.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.data.DataSetItem;
import sdk.data.InspectionDataSet;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.InspectionSource_Internal;
import sdk.inspection.InspectionConfiguration;
import sdk.utils.*;

import java.util.Collection;
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
                        return dataSource.startInspection(dataSetItem,authenticationInfo, parameters).thenApply(dataSet -> ok(dataSet.toJSON()));
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
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        return getConfiguration(dataSource, request)
                .thenCompose(inspectionConfiguration -> dataSetItemFromRequest(inspectionConfiguration.getInspectionServiceConfiguration(), request, false))
                .thenCompose(dataSetItem -> dataSource.updateInspectionItem(dataSetItem, authenticationInfo, parameters))
                .thenApply(dataSet -> ok(dataSet.toJSON()));
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
                        return dataSource.completeInspection(dataSet, authenticationInfo, parameters).thenApply(returnDataSet -> ok(returnDataSet.toJSON()));
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
}
