package sdk.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.With;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.datasources.UserDataSource_Internal;
import sdk.utils.ResponseExceptionHandler;
import sdkmodels.data.*;
import sdkmodels.data.deprecated.UserInfoResponse;
import sdkmodels.utils.AuthenticationInfo;
import sdkmodels.utils.JsonUtils;
import sdkmodels.utils.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                .thenApply(userDataSet -> {
                    if ( AppTree.getCoreFormatVersion().equals("5.4") ) {
                        User user = (User) userDataSet.getDataSetItems().get(0);
                        UserInfoResponse.Builder builder = new UserInfoResponse.Builder()
                                .withSuccess(user.getUserID())
                                .withUsername(user.getUsername())
                                .withEmail(user.getEmail())
                                .withFirstName(user.getFirstName())
                                .withLastName(user.getLastName())
                                .withPhoneNumber(user.getPhone());
                        builder.withUserInfoValues(user.getCustomAttributeMap());
                        return JsonUtils.toJson(builder.build());
                    } else {
                        return userDataSet.toJSON();
                    }
                })
                .thenApply(Results::ok)
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

    public CompletionStage<Result> getUserInfoKeys() {
        UserDataSource_Internal dataSource = AppTree.getUserDataSource_internal();
        if (dataSource == null) {
            return CompletableFuture.completedFuture(notFound("No user data source has been provided"));
        }
        return CompletableFuture
                .supplyAsync(dataSource::getConfiguration)
                .thenApply(serviceConfiguration -> {
                    ArrayList<String> keys = new ArrayList<String>();
                    for (ServiceConfigurationAttribute attribute : serviceConfiguration.getAttributes() ) {
                        if ( attribute.getAttributeIndex() >= User.CUSTOM_ATTRIBUTE_START_INDEX ) {
                            keys.add(attribute.getName());
                        }
                    }
                    return new UserStringListResponse(true, keys, "");
                })
                .thenApply(userStringListResponse -> ok(JsonUtils.toJson(userStringListResponse)));

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

    private class UserStringListResponse {
        public boolean success;
        public List<String> records;
        public String message;
        UserStringListResponse(boolean success, List<String> sources, String message) {
            this.success = success;
            this.records = sources;
            this.message = message;
        }
    }
}
