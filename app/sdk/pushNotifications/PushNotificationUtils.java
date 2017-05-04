package sdk.pushNotifications;

import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.ConfigFactory;
import play.Play;
import play.libs.ws.WSRequest;
import sdk.AppTreeSource;
import sdk.utils.Constants;
import sdk.utils.JsonUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Created by alexis on 9/7/16.
 */
public class PushNotificationUtils implements AppTreeSource {
    private String coreURL;
    private static PushNotificationUtils instance;

    public static PushNotificationUtils getDefaultInstance() {
        if ( instance == null ) {
            String defaultCoreURL = Play.application().configuration().getString("coreURL");
            if ( defaultCoreURL == null ) defaultCoreURL = Constants.defaultCoreURL;
            instance = new PushNotificationUtils(defaultCoreURL);
        }
        return instance;
    }

    public static PushNotificationUtils getNewInstance(String url) {
        return new PushNotificationUtils(url);
    }

    private PushNotificationUtils(String url) {
        coreURL = url;
    }

    private CompletionStage<PushNotificationResponse> sendPushNotification(PushRequest pushRequest, String applicationAPIKey, String appID) {
        WSRequest request = getWSClient().url(String.format("%s/pushNotification/sendConnectorPush", coreURL)).setRequestTimeout(300000);
        request.setHeader(Constants.APPLICATION_API_KEY_HEADER, applicationAPIKey);
        request.setHeader(Constants.APP_ID_HEADER, appID);
        request.setMethod("POST");
        request.setBody(JsonUtils.toJson(pushRequest));
        return request.execute()
                .thenApply(wsResponse -> {
                    if ( wsResponse.getStatus() != 200 ) throw new RuntimeException("Core returned response code " + wsResponse.getStatus());
                    else {
                        JsonNode node = wsResponse.asJson();
                        if ( node == null ) throw new RuntimeException("There was an error communicating with the core");
                        return JsonUtils.fromJson(node, PushNotificationResponse.class);
                    }
                });
    }

    public CompletionStage<PushNotificationResponse> sendPushToUsers(List<String> userIDs, String title, String message, @NotNull String applicationAPIKey, @NotNull String appID) {
        PushRequest request = new PushRequest();
        request.message = message;
        request.title = title;
        request.userIDs = userIDs;
        return sendPushNotification(request, applicationAPIKey, appID);
    }

    public CompletionStage<PushNotificationResponse> sendPushToUsersFromDataSet(List<String> userIDs, String title, String message, String dataSetName, String primaryKey, String navigationID, @NotNull String applicationAPIKey, @NotNull String appID) {
        PushRequest request = new PushRequest();
        request.message = message;
        request.title = title;
        request.userIDs = userIDs;
        request.dataSetName = dataSetName;
        request.primaryKey = primaryKey;
        request.navigationID = navigationID;
        return sendPushNotification(request, applicationAPIKey, appID);
    }

    public CompletionStage<PushNotificationResponse> sendPushBasedOnRoles(List<String> roles, String title, String message, @NotNull String applicationAPIKey, @NotNull String appID) {
        PushRequest request = new PushRequest();
        request.message = message;
        request.title = title;
        request.roles = roles;
        return sendPushNotification(request, applicationAPIKey, appID);
    }

    public CompletionStage<PushNotificationResponse> sendPushBasedOnRolesFromDataSet(List<String> roles, String title, String message, String dataSetName, String primaryKey, String navigationID, @NotNull String applicationAPIKey, @NotNull String appID) {
        PushRequest request = new PushRequest();
        request.message = message;
        request.title = title;
        request.roles = roles;
        request.dataSetName = dataSetName;
        request.primaryKey = primaryKey;
        request.navigationID = navigationID;
        return sendPushNotification(request, applicationAPIKey, appID);
    }

    public CompletionStage<PushNotificationResponse> sendPushBasedOnStatuses(List<String> statuses, String title, String message, @NotNull String applicationAPIKey, @NotNull String appID) {
        PushRequest request = new PushRequest();
        request.message = message;
        request.title = title;
        request.statuses = statuses;
        return sendPushNotification(request, applicationAPIKey, appID);
    }

    public CompletionStage<PushNotificationResponse> sendPushBasedOnStatusesFromDataSet(List<String> statuses, String title, String message, String dataSetName, String primaryKey, String navigationID, @NotNull String applicationAPIKey, @NotNull String appID) {
        PushRequest request = new PushRequest();
        request.message = message;
        request.title = title;
        request.statuses = statuses;
        request.dataSetName = dataSetName;
        request.primaryKey = primaryKey;
        request.navigationID = navigationID;
        return sendPushNotification(request, applicationAPIKey, appID);
    }

    public CompletionStage<PushNotificationResponse> sendPushBasedOnRolesAndOrStatus(List<String> statuses, List<String> roles, boolean useAndOperator, String title, String message, @NotNull String applicationAPIKey, @NotNull String appID) {
        PushRequest request = new PushRequest();
        request.message = message;
        request.title = title;
        request.statuses = statuses;
        request.roles = roles;
        request.operator = useAndOperator ? "AND" : "OR";
        return sendPushNotification(request, applicationAPIKey, appID);
    }

    public CompletionStage<PushNotificationResponse> sendPushBasedOnRolesAndOrStatusFromDataSet(List<String> statuses, List<String> roles, boolean useAndOperator, String title, String message, String dataSetName, String primaryKey, String navigationID, @NotNull String applicationAPIKey, @NotNull String appID) {
        PushRequest request = new PushRequest();
        request.message = message;
        request.title = title;
        request.statuses = statuses;
        request.roles = roles;
        request.operator = useAndOperator ? "AND" : "OR";
        request.dataSetName = dataSetName;
        request.primaryKey = primaryKey;
        request.navigationID = navigationID;
        return sendPushNotification(request, applicationAPIKey, appID);
    }
}
