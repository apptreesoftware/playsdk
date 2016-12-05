package sdk.emailNotifications;

import play.libs.ws.WSRequest;
import sdk.AppTreeSource;
import sdk.utils.Constants;
import sdk.utils.JsonUtils;

/**
 * Created by alexis on 12/5/16.
 */
public class EmailUtils implements AppTreeSource {
    private String coreURL;
    private static String defaultCoreURL = "https://services1.apptreesoftware.com";
    private static EmailUtils instance;

    private EmailUtils(String url) {
        coreURL = url;
    }

    public static void setDefaultCoreURL(String url) {
        defaultCoreURL = url;
    }

    public static EmailUtils getDefaultInstance() {
        if ( instance == null ) {
            instance = new EmailUtils(defaultCoreURL);
        }
        return instance;
    }

    public static EmailUtils getNewInstance(String url) {
        return new EmailUtils(url);
    }

    private void sendEmail(EmailRequest emailRequest, String appID, String appAPIKey) {
        WSRequest request = getWSClient().url(String.format("%s/emailNotification", coreURL)).setRequestTimeout(300000);
        request.setHeader(Constants.APP_ID_HEADER, appID);
        request.setHeader(Constants.APPLICATION_API_KEY_HEADER, appAPIKey);
        request.setMethod("POST");
        request.setBody(JsonUtils.toJson(emailRequest));
        request.execute()
                .thenApply(wsResponse -> {
                    if ( wsResponse.getStatus() != 200 ) throw new RuntimeException("Core returned response code " + wsResponse.getStatus());
                    return 0;
                });
    }
}
