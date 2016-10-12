package sdk.datasources;

import sdk.data.DataSet;
import sdk.data.ServiceConfiguration;
import sdk.data.User;
import sdk.datasources.rx.UserDataSource;
import sdk.utils.AuthenticationInfo;
import sdk.utils.Parameters;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Matthew Smith on 9/2/16.
 * Copyright AppTree Software, Inc.
 */
public class UserDataSource_Internal extends BaseSource_Internal {
    private UserDataSource rxDataSource;
    private sdk.datasources.base.UserDataSource dataSource;
    private sdk.datasources.future.UserDataSource futureDataSource;

    public UserDataSource_Internal(UserDataSource dataSource) {
        this.rxDataSource = dataSource;
    }

    public UserDataSource_Internal(sdk.datasources.base.UserDataSource dataSource) {
        this.dataSource = dataSource;
    }
    public UserDataSource_Internal(sdk.datasources.future.UserDataSource dataSource) { this.futureDataSource = dataSource; }

    public ServiceConfiguration getConfiguration() {
        if ( dataSource != null ) {
            return dataSource.getConfiguration();
        } else if ( rxDataSource != null ) {
            return rxDataSource.getConfiguration();
        } else if ( futureDataSource != null ) {
            return futureDataSource.getConfiguration();
        }
        throw new RuntimeException("No data source available");
    }

    private DataSet newDataSet(AuthenticationInfo authenticationInfo, Parameters parameters) {
        if ( dataSource != null ) {
            return dataSource.newEmptyDataSet();
        } else if ( rxDataSource != null ) {
            return rxDataSource.newEmptyDataSet();
        } else if ( futureDataSource != null ) {
            return futureDataSource.newEmptyDataSet();
        }
        throw new RuntimeException("No data source available");
    }

    public CompletableFuture<DataSet> getUser(String userID, AuthenticationInfo authenticationInfo, Parameters parameters) {
        CompletableFuture<User> userFuture = null;
        if ( rxDataSource != null ) {
            userFuture = observableToFuture(rxDataSource.getUser(userID, authenticationInfo, parameters));
        } else if ( dataSource != null ) {
            userFuture = CompletableFuture.supplyAsync(() -> dataSource.getUser(userID, authenticationInfo, parameters));
        } else if ( futureDataSource != null ) {
            userFuture = futureDataSource.getUser(userID, authenticationInfo, parameters);
        } else {
            throw new RuntimeException("No data source available");
        }
        return userFuture.thenApply(user -> {
            DataSet dataSet = newDataSet(authenticationInfo, parameters);
            dataSet.add(user);
            return dataSet;
        });
    }

    public CompletableFuture<DataSet> createUser(User user, AuthenticationInfo authenticationInfo, Parameters parameters) {
        CompletableFuture<User> userFuture = null;
        if ( rxDataSource != null ) {
            userFuture = observableToFuture(rxDataSource.createUser(user));
        } else if ( dataSource != null ) {
            userFuture = CompletableFuture.supplyAsync(() -> dataSource.createUser(user));
        } else if ( futureDataSource != null ) {
            userFuture = futureDataSource.createUser(user);
        } else {
            throw new RuntimeException("No data source available");
        }
        return userFuture.thenApply(updatedUser -> {
            DataSet dataSet = newDataSet(authenticationInfo, parameters);
            dataSet.add(user);
            return dataSet;
        });
    }

    public CompletableFuture<DataSet> updateUser(User user, AuthenticationInfo authenticationInfo, Parameters parameters) {
        CompletableFuture<User> userFuture = null;
        if ( rxDataSource != null ) {
            userFuture = observableToFuture(rxDataSource.updateUser(user));
        } else if ( dataSource != null ) {
            userFuture = CompletableFuture.supplyAsync(() -> dataSource.updateUser(user));
        } else if ( futureDataSource != null ) {
            userFuture = futureDataSource.updateUser(user);
        } else {
            throw new RuntimeException("No data source available");
        }
        return userFuture.thenApply(updatedUser -> {
            DataSet dataSet = newDataSet(authenticationInfo, parameters);
            dataSet.add(user);
            return dataSet;
        });
    }

    public CompletableFuture<Boolean> deleteUser(User user) {
        CompletableFuture<Boolean> userFuture = null;
        if ( rxDataSource != null ) {
            userFuture = observableToFuture(rxDataSource.deleteUser(user));
        } else if ( dataSource != null ) {
            userFuture = CompletableFuture.supplyAsync(() -> dataSource.deleteUser(user));
        } else if ( futureDataSource != null ) {
            userFuture = futureDataSource.deleteUser(user);
        } else {
            throw new RuntimeException("No data source available");
        }
        return userFuture;
    }
}
