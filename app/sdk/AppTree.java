package sdk;

import org.jetbrains.annotations.Nullable;
import play.Configuration;
import play.Play;
import sdk.datasources.base.AttachmentDataSource;
import sdk.auth.AuthenticationSource;
import sdk.datacollection.DataCollectionSource;
import sdk.datasources.*;
import sdk.datasources.base.DataSource;
import sdk.datasources.base.UserDataSource;
import sdk.utils.Constants;

import java.util.HashMap;
import java.util.Optional;

public class AppTree {
    public static HashMap<String, DataSourceBase> dataSources = new HashMap<>();
    public static HashMap<String, ListDataSource> listSources = new HashMap<>();
    public static HashMap<String, DataCollectionSource> dataCollectionSources = new HashMap<>();
    public static HashMap<String, InspectionSourceBase> inspectionSources = new HashMap<>();
    public static HashMap<String, ConversionDataSourceBase> conversionSources = new HashMap<>();

    private static AuthenticationSource authenticationSource;
    private static UserDataSource_Internal userDataSource;
    private static AttachmentDataSource_Internal attachmentDataSource;

    public static String getPlatformVersion() {
        return Constants.PLATFORM_VERSION;
    }

    public static void registerDataSourceWithName(String name, DataSourceBase dataSource) {
        dataSources.putIfAbsent(name, dataSource);
    }

    public static void registerConversionDataSourceWithName(String name, ConversionDataSourceBase dataSource) {
        conversionSources.putIfAbsent(name, dataSource);
    }

    public static void registerListDataSourceWithName(String name, ListDataSource dataSource) {
        listSources.putIfAbsent(name, dataSource);
    }

    public static void registerDataCollectionSourceWithName(String name, DataCollectionSource dataCollectionSource) {
        dataCollectionSources.putIfAbsent(name, dataCollectionSource);
    }

    @Nullable
    public static DataSource_Internal lookupDataSetHandler(String name) {
        DataSourceBase dataSourceBase = dataSources.get(name);
        if (dataSourceBase == null) {
            return null;
        }
        return new DataSource_Internal(dataSourceBase);
    }

    @Nullable
    public static ConversionDataSource_Internal lookupConversionHandler(String name) {
        ConversionDataSourceBase dataSourceBase = conversionSources.get(name);
        if (dataSourceBase == null) {
            return null;
        }
        return new ConversionDataSource_Internal(dataSourceBase);
    }

    public static void registerInspectionSource(String name, InspectionSourceBase inspectionSource) {
        inspectionSources.putIfAbsent(name, inspectionSource);
    }

    public static InspectionSource_Internal lookupInspectionHandler(String name) {
        InspectionSourceBase inspectionSourceBase = inspectionSources.get(name);
        if (inspectionSourceBase == null) {
            return null;
        }
        return new InspectionSource_Internal(inspectionSourceBase);
    }

    public static Optional<DataCollectionSource> lookupDataCollectionHandler(String name) {
        return Optional.ofNullable(dataCollectionSources.get(name));
    }

    public static Optional<ListDataSource_Internal> lookupListHandler(String name) {
        ListDataSource dataSource = listSources.get(name);
        if (dataSource != null) {
            return Optional.of(new ListDataSource_Internal(dataSource));
        }
        return Optional.empty();
    }

    public static void registerAuthenticationSource(AuthenticationSource source) {
        authenticationSource = source;
    }

    public static AuthenticationSource getAuthenticationSource() {
        return authenticationSource;
    }

    public static void registerUserDataSource(UserDataSource source) {
        userDataSource = new UserDataSource_Internal(source);
    }

    public static void registerUserDataSource(sdk.datasources.rx.UserDataSource source) {
        userDataSource = new UserDataSource_Internal(source);
    }

    public static void registerUserDataSource(sdk.datasources.future.UserDataSource source) {
        userDataSource = new UserDataSource_Internal(source);
    }

    public static UserDataSource_Internal getUserDataSource_internal() {
        return userDataSource;
    }

    public static AttachmentDataSource_Internal getAttachmentDataSource_internal() {
        return attachmentDataSource;
    }

    public static void setAttachmentDataSource(AttachmentDataSource attachmentDataSource) {
        AppTree.attachmentDataSource = new AttachmentDataSource_Internal(attachmentDataSource);
    }

    public static void setAttachmentDataSource(sdk.datasources.future.AttachmentDataSource attachmentDataSource) {
        AppTree.attachmentDataSource = new AttachmentDataSource_Internal(attachmentDataSource);
    }

    public static void setAttachmentDataSource(sdk.datasources.rx.AttachmentDataSource attachmentDataSource) {
        AppTree.attachmentDataSource = new AttachmentDataSource_Internal(attachmentDataSource);
    }

    public static boolean needsAPIKeyValidation() {
        Boolean bool = Play.application().configuration().getBoolean("apptree.crypto.validate");
        if (bool == null) {
            throw new RuntimeException("apptree.crypto.validate has not been defined in your application.conf");
        }
        return bool;
    }

    public static String getApplicationSecret() {
        return Play.application().configuration().getString("play.crypto.secret");
    }

    public static String getCoreFormatVersion() {
        String format = Constants.PLATFORM_VERSION;
        Configuration configuration = Play.application().configuration().getConfig("apptree");
        if (configuration != null) {
            if (configuration.getString("platform.version") != null) {
                format = configuration.getString("platform.version");
            }
        }
        return format;
    }

    public static String getHost() {
        return Play.application().configuration().getString("host");
    }

    public static Configuration getConfiguration() {
        return Play.application().configuration();
    }
}
