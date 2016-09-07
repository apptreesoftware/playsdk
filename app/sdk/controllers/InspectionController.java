package sdk.controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import sdk.AppTree;
import sdk.data.ServiceConfiguration;
import sdk.data.ServiceConfigurationAttribute;
import sdk.datasources.InspectionSource_Internal;
import sdk.inspection.InspectionConfiguration;
import sdk.datasources.base.InspectionSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.JsonUtils;
import sdk.utils.Parameters;
import sdk.utils.ResponseExceptionHandler;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by Matthew Smith on 8/30/16.
 * Copyright AppTree Software, Inc.
 */
public class InspectionController extends Controller {
    public CompletionStage<Result> getConfiguration(String dataSetName) {
        Http.Request request = request();
        InspectionSource_Internal dataSource = AppTree.lookupInspectionHandler(dataSetName);
        if ( dataSource == null ) {
            return CompletableFuture.completedFuture(notFound());
        }

        return CompletableFuture
                .supplyAsync(() -> {
                    AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
                    Parameters parameters = new Parameters(request.queryString());
                    List<ServiceConfigurationAttribute> searchAttributes = dataSource.getInspectionSearchAttributes(authenticationInfo, parameters);
                    List<ServiceConfigurationAttribute> inspectionAttributes = dataSource.getInspectionItemAttributes(authenticationInfo, parameters);
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
                })
                .thenApply(response -> ok(JsonUtils.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }
}
