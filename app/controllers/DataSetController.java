package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import sdk.data.ConfigurationResponse;
import sdk.data.DataSource;
import sdk.data.DataSourceResponse;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import sdk.*;
import sdk.serializers.DataSetModule;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by alexis on 5/3/16.
 */
public class DataSetController extends Controller {

    public DataSetController() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new DataSetModule());
        Json.setObjectMapper(objectMapper);
    }

    public CompletionStage<Result> getDataSet(String dataSetName) {
        Http.Request request = request();
        return CompletableFuture.supplyAsync(() -> {
            AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
            Parameters parameters = new Parameters(request.queryString());
            DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName);
            if ( dataSource == null ) throw new RuntimeException("Invalid Data Set");
            return dataSource.getDataSet(authenticationInfo, parameters);
        })
        .thenApply(dataSourceResponse -> ok(Json.toJson(dataSourceResponse)))
        .exceptionally(exception -> {
            DataSourceResponse response = new DataSourceResponse.Builder().setSuccess(false).setMessage(exception.getMessage()).build();
            return ok(Json.toJson(response));
        });
    }

    public CompletionStage<Result> getDataConfiguration(String dataSetName) {
        Http.Request request = request();
        return CompletableFuture.supplyAsync(() -> {
            DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName);
            if ( dataSource == null ) throw new RuntimeException("Invalid Data Set");
            AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
            Parameters parameters = new Parameters(request.queryString());
            return dataSource.getConfiguration(authenticationInfo, parameters);
        })
        .thenApply(response -> ok(Json.toJson(response)))
        .exceptionally(exception -> {
            ConfigurationResponse response = new ConfigurationResponse.Builder().setSuccess(false).setMessage(exception.getMessage()).createConfigurationResponse();
            return ok(Json.toJson(response));
        });
    }


    public CompletionStage<Result> createDataSetItem(String dataSetName) {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Map<String, String[]> bodyMap = body.asFormUrlEncoded();
        List<Http.MultipartFormData.FilePart> files = body.getFiles();
        return CompletableFuture.completedFuture(ok(""));
    }
}
