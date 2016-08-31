package sdk;

import play.Configuration;
import play.Play;
import sdk.attachment.AttachmentDataSource;
import sdk.auth.AuthenticationSource;
import sdk.data.DataSource;
import sdk.datacollection.DataCollectionSource;
import sdk.inspection.InspectionSource;
import sdk.list.ListDataSource;
import sdk.user.UserDataSource;

import java.util.HashMap;
import java.util.Optional;

public class AppTree {
    public static HashMap<String, DataSource> dataSources = new HashMap<>();
    public static HashMap<String, ListDataSource> listSources = new HashMap<>();
    public static HashMap<String, DataCollectionSource> dataCollectionSources = new HashMap<>();
    public static HashMap<String, InspectionSource> inspectionSources = new HashMap<>();

    private static AuthenticationSource authenticationSource;
    private static UserDataSource userDataSource;
    private static AttachmentDataSource attachmentDataSource;

    public static void registerDataSourceWithName(String name, DataSource dataSource) {
        dataSources.putIfAbsent(name, dataSource);
    }

    public static void registerListDataSourceWithName(String name, ListDataSource dataSource) {
        listSources.putIfAbsent(name, dataSource);
    }

    public static void registerDataCollectionSourceWithName(String name, DataCollectionSource dataCollectionSource) {
        dataCollectionSources.putIfAbsent(name, dataCollectionSource);
    }

    public static Optional<DataSource> lookupDataSetHandler(String name) {
        return Optional.ofNullable(dataSources.get(name));
    }

    public static void registerInspectionSource(String name, InspectionSource inspectionSource) {
        inspectionSources.putIfAbsent(name, inspectionSource);
    }

    public static Optional<InspectionSource> lookupInspectionHandler(String name) {
        return Optional.ofNullable(inspectionSources.get(name));
    }

    public static Optional<DataCollectionSource> lookupDataCollectionHandler(String name) {
        return Optional.ofNullable(dataCollectionSources.get(name));
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

    public static boolean needsAPIKeyValidation() {
        Boolean bool = Play.application().configuration().getBoolean("apptree.crypto.validate");
        if ( bool == null ) {
            throw new RuntimeException("apptree.crypto.validate has not been defined in your application.conf");
        }
        return bool;
    }

    public static String getApplicationSecret() {
        return Play.application().configuration().getString("play.crypto.secret");
    }

    public static String getHost() {
        return Play.application().configuration().getString("host");
    }

    public static Configuration getConfiguration() { return Play.application().configuration(); }
}
