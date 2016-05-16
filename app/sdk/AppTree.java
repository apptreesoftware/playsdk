package sdk;

import sdk.attachment.AttachmentDataSource;
import sdk.auth.AuthenticationSource;
import sdk.data.DataSource;
import sdk.list.ListDataSource;
import sdk.user.UserDataSource;

import java.util.HashMap;
import java.util.Optional;

public class AppTree {
    public static HashMap<String, DataSource> dataSources = new HashMap<>();
    public static HashMap<String, ListDataSource> listSources = new HashMap<>();
    private static AuthenticationSource authenticationSource;
    private static UserDataSource userDataSource;
    private static AttachmentDataSource attachmentDataSource;

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

    public static void registerAuthenticationSource(AuthenticationSource source) {
        authenticationSource = source;
    }
    public static AuthenticationSource getAuthenticationSource() {
        return authenticationSource;
    }

    public static void registerUserDataSource(UserDataSource source) {
        userDataSource = source;
    }

    public static UserDataSource getUserDataSource() { return userDataSource; }

    public static AttachmentDataSource getAttachmentDataSource() {
        return attachmentDataSource;
    }

    public static void setAttachmentDataSource(AttachmentDataSource attachmentDataSource) {
        AppTree.attachmentDataSource = attachmentDataSource;
    }
}
