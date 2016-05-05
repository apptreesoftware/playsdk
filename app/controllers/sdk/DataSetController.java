package controllers.sdk;

import ch.qos.logback.core.net.SocketConnector;
import models.sdk.Data.ConfigurationResponse;
import models.sdk.Data.DataSet;
import models.sdk.Data.DataSource;
import models.sdk.Data.DataSourceResponse;
import models.sdk.Utils.AuthenticationInfo;
import models.sdk.Utils.Parameters;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by alexis on 5/3/16.
 */
public class DataSetController extends Controller {

    public CompletionStage<Result> getDataSet(String dataSetName) {
        Http.Request request = request();
        return CompletableFuture.supplyAsync(() -> {
            AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
            Parameters parameters = new Parameters(request.queryString());
            DataSource dataSource = AppTree.lookupDataSetHandler(dataSetName);
            if ( dataSource == null ) throw new RuntimeException("Invalid Data Set");
            return new DataSetAuthHelper(authenticationInfo, parameters, dataSource);
        })
        .thenApply(dataSetAuthHelper -> dataSetAuthHelper.dataSource.getDataSet(dataSetAuthHelper.authInfo, dataSetAuthHelper.parameters))
        .thenApply(dataSourceResponse -> ok(Json.toJson(dataSourceResponse)))
        .exceptionally(exception -> {
            DataSourceResponse response = new DataSourceResponse.Builder().setSuccess(false).setMessage(exception.getMessage()).createDataSourceResponse();
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
            return new DataSetAuthHelper(authenticationInfo, parameters, dataSource);
        })
        .thenApply(dataAuthHelper -> {
            ConfigurationResponse response = dataAuthHelper.dataSource.getConfiguration(dataAuthHelper.authInfo, dataAuthHelper.parameters);
            return ok(Json.toJson(response));
        })
        .exceptionally(exception -> {
            ConfigurationResponse response = new ConfigurationResponse.Builder().setSuccess(false).setMessage(exception.getMessage()).createConfigurationResponse();
            return ok(Json.toJson(response));
        });
    }

    private class DataSetAuthHelper {
        AuthenticationInfo authInfo;
        Parameters parameters;
        DataSource dataSource;

        public DataSetAuthHelper(AuthenticationInfo authInfo, Parameters parameters, DataSource dataSource) {
            this.authInfo = authInfo;
            this.parameters = parameters;
            this.dataSource = dataSource;
        }
    }
}
