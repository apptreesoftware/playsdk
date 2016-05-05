package controllers.sdk;

import models.sdk.Data.DataSource;
import models.sdk.List.ListDataSource;

import java.util.HashMap;

/**
 * Created by alexis on 5/3/16.
 */
public class AppTree {
    public static HashMap<String, DataSource> dataSources = new HashMap<>();
    public static HashMap<String, ListDataSource> listSources = new HashMap<>();

    public static void registerDataSourceWithName(String name, DataSource dataSource) {
        dataSources.putIfAbsent(name, dataSource);
    }

    public static void registerListDataSourceWithName(String name, ListDataSource dataSource) {
        listSources.putIfAbsent(name, dataSource);
    }

    public static DataSource lookupDataSetHandler(String name) {
        return dataSources.get(name);
    }

    public static ListDataSource lookupListHandler(String name) {
        return listSources.get(name);
    }
}
