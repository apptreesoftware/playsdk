package sdk.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.data.DataSet;
import sdk.data.DataSetItem;
import sdk.data.ServiceConfiguration;
import sdk.data.User;
import sdk.datasources.UserDataSource_Internal;
import sdk.utils.AuthenticationInfo;
import sdk.utils.JsonUtils;
import sdk.utils.Parameters;
import sdk.utils.ResponseExceptionHandler;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by matthew on 5/12/16.
 */
@With({ValidateRequestAction.class})
public class UserController extends DataController {
    public CompletionStage<Result> getUserInfo(String userID) {
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        return dataSource.getUser(userID, authenticationInfo, parameters)
                .thenApply(userDataSet -> ok(userDataSet.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> getConfiguration() {
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        return CompletableFuture
                .supplyAsync(dataSource::getConfiguration)
                .thenApply(response -> ok(JsonUtils.toJson(response)))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    @Override
    DataSetItem dataSetItemForJSON(ObjectNode json, DataSet dataSet, boolean search, HashMap<String, Http.MultipartFormData.FilePart> attachmentMap) {
        User user = new User(dataSet.getConfigurationAttributes());
        user.updateFromJSON(json, attachmentMap, search);
        dataSet.add(user);
        return user;
    }

    public CompletionStage<Result> createUser() {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        ServiceConfiguration configuration = dataSource.getConfiguration();
        return dataSetItemFromRequest(configuration, request, false)
                .thenCompose(dataSetItem -> dataSource.createUser((User)dataSetItem, authenticationInfo, parameters))
                .thenApply(dataSet -> ok(dataSet.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }

    public CompletionStage<Result> updateUser() {
        Http.Request request = request();
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request().headers());
        Parameters parameters = new Parameters(request().queryString());
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        ServiceConfiguration configuration = dataSource.getConfiguration();
        return dataSetItemFromRequest(configuration, request, false)
                .thenCompose(dataSetItem -> dataSource.updateUser((User)dataSetItem, authenticationInfo, parameters))
                .thenApply(dataSet -> ok(dataSet.toJSON()))
                .exceptionally(ResponseExceptionHandler::handleException);
    }
}
