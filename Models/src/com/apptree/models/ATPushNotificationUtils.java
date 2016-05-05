package com.apptree.models;

import com.squareup.okhttp.*;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alexis on 2/11/16.
 */
public class ATPushNotificationUtils {
    private String coreURL;
    OkHttpClient mHttpClient = null;
    ExecutorService mExecutorService;
    private static String defaultCoreURL = "https://revolution.apptreesoftware.com";
    private static ATPushNotificationUtils instance;

    public static void setDefaultNotificationCoreURL(String url) {
        defaultCoreURL = url;
    }

    public static ATPushNotificationUtils getDefaultInstance() {
        if ( instance == null ) {
            instance = new ATPushNotificationUtils(defaultCoreURL);
        }
        return instance;
    }

    public static ATPushNotificationUtils getNewInstance(String url) {
        return new ATPushNotificationUtils(url);
    }

    private ATPushNotificationUtils(String url) {
        coreURL = url;
        mHttpClient = new OkHttpClient();
        mExecutorService = Executors.newFixedThreadPool(20);
    }

    private JSONObject sendPushNotification(String userID, String statusName, String roleName, boolean bothCriteria, String applicationAPIKey, String appID, String title, String message, String primaryKey, String dataSetName, String navigationID) throws Exception {
        JSONObject requestObject;
        RequestBody body;
        Request request;
        Response response;
        String responseString;
        JSONObject responseObject;

        requestObject = new JSONObject();
        requestObject.putOpt("userID", userID);
        requestObject.putOpt("status", statusName);
        requestObject.putOpt("role", roleName);
        requestObject.putOpt("operator", bothCriteria ? "AND" : "OR");
        requestObject.putOpt("dataSetName", dataSetName);
        requestObject.putOpt("primaryKey", primaryKey);
        requestObject.putOpt("message", message);
        requestObject.putOpt("navigationID", navigationID);
        requestObject.putOpt("title", title);

        body = RequestBody.create(MediaType.parse("application/json"), requestObject.toString());
        request = new Request.Builder()
                .post(body)
                .url(coreURL + "/apptree/push/connectorPush")
                .addHeader(Constants.APPLICATION_ID_HEADER, appID)
                .addHeader(Constants.APIKEY_HEADER, applicationAPIKey)
                .build();
        try {
            response = mHttpClient.newCall(request).execute();
            if ( !response.isSuccessful() ) {
                throw new Exception("Invalid response received");
            }
            responseString = response.body().string();
            responseObject = new JSONObject(responseString);
            return responseObject;
        } catch (Exception e ) {
            throw new Exception("There was an exception processing this request");
        }
    }

    public JSONObject sendPushNotificationToUserFromDataSet(String userID, String dataSetName, String primaryKey, String applicationAPIKey, String appID, String message, String title, String navigationID) throws Exception {
        return sendPushNotification(userID, null, null, false, applicationAPIKey, appID, title, message, primaryKey, dataSetName, navigationID);
    }

    public JSONObject sendPushNotificationToUser(String userID, String applicationAPIKey, String appID, String message, String title) throws Exception {
        return sendPushNotificationToUserFromDataSet(userID, null, null, applicationAPIKey, appID, message, title, null);
    }

    public JSONObject sendPushNotificationBasedOnRoleAndORStatusFromDataSet(String statusName, String roleName, boolean useAndOperator, String applicationAPIKey, String appID, String title, String message, String primaryKey, String dataSetName, String navigationID) throws Exception {
        return sendPushNotification(null, statusName, roleName, useAndOperator, applicationAPIKey, appID, title, message, primaryKey, dataSetName, navigationID);
    }

    public JSONObject sendPushNotificationBasedOnRoleAndORStatus(String statusName, String roleName, boolean useAndOperator, String applicationAPIKey, String appID, String title, String message) throws Exception {
        return sendPushNotification(null, statusName, roleName, useAndOperator, applicationAPIKey, appID, title, message, null, null, null);
    }

    public JSONObject sendPushNotificationBasedOnRoleFromDataSet(String roleName, String applicationAPIKey, String appID, String title, String message, String primaryKey, String dataSetName, String navigationID) throws Exception {
        return sendPushNotification(null, null, roleName, false, applicationAPIKey, appID, title, message, primaryKey, dataSetName, navigationID);
    }

    public JSONObject sendPushNotificationBasedOnRole(String roleName, String applicationAPIKey, String appID, String title, String message) throws Exception {
        return sendPushNotification(null, null, roleName, false, applicationAPIKey, appID, title, message, null, null, null);
    }

    public JSONObject sendPushNotificationBasedOnStatusFromDataSet(String statusName, String applicationAPIKey, String appID, String title, String message, String primaryKey, String dataSetName, String navigationID) throws Exception {
        return sendPushNotification(null, statusName, null, false, applicationAPIKey, appID, title, message, primaryKey, dataSetName, navigationID);
    }

    public JSONObject sendPushNotificationBasedOnStatus(String statusName, String applicationAPIKey, String appID, String title, String message) throws Exception {
        return sendPushNotification(null, statusName, null, false, applicationAPIKey, appID, title, message, null, null, null);
    }
}
