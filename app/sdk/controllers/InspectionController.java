package sdk.controllers;

import play.mvc.Http;
import play.mvc.Result;
import sdk.AppTree;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.InspectionSource_Internal;
import sdk.inspection.InspectionConfiguration;
import sdk.utils.*;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by Matthew Smith on 8/30/16.
 * Copyright AppTree Software, Inc.
 */
public class InspectionController extends DataController {

    public CompletionStage<Result> getConfiguration(String dataSetName) {
        Http.Request request = request();
        InspectionSource_Internal dataSource = AppTree.lookupInspectionHandler(dataSetName);
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound());
        }
        return getConfiguration(dataSource, request)
                .thenApply(response -> ok(JsonUtils.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

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


}
