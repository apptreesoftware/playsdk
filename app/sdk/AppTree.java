package sdk;

import sdk.data.DataSource;
import sdk.list.ListDataSource;

import java.util.HashMap;
import java.util.Optional;

public class AppTree {
    public static HashMap<String, DataSource> dataSources = new HashMap<>();
    public static HashMap<String, ListDataSource> listSources = new HashMap<>();

    public static void registerDataSourceWithName(String name, DataSource dataSource) {
        dataSources.putIfAbsent(name, dataSource);
    }

    public static void registerListDataSourceWithName(String name, ListDataSource dataSource) {
        listSources.putIfAbsent(name, dataSource);
    }

    public static Optional<DataSource> lookupDataSetHandler(String name) {
        return Optional.ofNullable(dataSources.get(name));
    }

    public static Optional<ListDataSource> lookupListHandler(String name) {
        return Optional.ofNullable(listSources.get(name));
    }
}
