package com.apptree.models;

import com.squareup.okhttp.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alexis on 3/15/16.
 */
public class ATEmailNotificationUtils {
    private String coreURL;
    OkHttpClient mHTTPClient = null;
    ExecutorService mExecutorService;
    private static String defaultCoreURL = "https://revolution.apptreesoftware.com";
    private static ATEmailNotificationUtils instance;

    public static void setDefaultNotificationCoreURL(String url) {
        defaultCoreURL = url;
    }

    public static ATEmailNotificationUtils getDefaultInstance() {
        if ( instance == null ) {
            instance = new ATEmailNotificationUtils(defaultCoreURL);
        }
        return instance;
    }

    public static ATEmailNotificationUtils getNewInstance(String url) {
        return new ATEmailNotificationUtils(url);
    }

    private ATEmailNotificationUtils(String url) {
        coreURL = url;
        mHTTPClient = new OkHttpClient();
        mExecutorService = Executors.newFixedThreadPool(20);
    }

    private JSONObject sendEmailNotification(String userID, String statusName, String roleName, boolean bothCriteria, String subject, String body, String from, JSONArray ccList, String replyTo, String applicationAPIKey, String appID) throws Exception {
        JSONObject requestObject;
        RequestBody requestBody;
        Request request;
        Response response;
        String responseString;
        JSONObject responseObject;

        if ( subject == null || body == null || from == null ) {
            throw new Exception("Invalid email configuration");
        }
        if ( applicationAPIKey == null || appID == null ) {
            throw new Exception("No app ID or application API key given");
        }
        requestObject = new JSONObject();
        requestObject.putOpt("userID", userID);
        requestObject.putOpt("status", statusName);
        requestObject.putOpt("role", roleName);
        requestObject.putOpt("operator", bothCriteria ? "AND" : "OR");
        requestObject.putOpt("subject", subject);
        requestObject.putOpt("body", body);
        requestObject.putOpt("from", from);
        requestObject.putOpt("cc", ccList);
        requestObject.putOpt("replyTo", replyTo);

        requestBody = RequestBody.create(MediaType.parse("application/json"), requestObject.toString());
        request = new Request.Builder()
                .post(requestBody)
                .url(coreURL + "/apptree/email/sendEmail")
                .addHeader(Constants.APPLICATION_ID_HEADER, appID)
                .addHeader(Constants.APIKEY_HEADER, applicationAPIKey)
                .build();

        try {
            response = mHTTPClient.newCall(request).execute();
            if ( !response.isSuccessful() ) {
                throw new Exception("Invalid response received");
            }
            responseString = response.body().string();
            responseObject = new JSONObject(responseString);
            return responseObject;
        } catch (Exception e) {
            throw new Exception("There was an exception processing this request");
        }
    }

    public JSONObject sendEmailNotificationToUser(String userID, String subject, String body, String from, JSONArray ccList, String replyTo, String applicationAPIKey, String appID) throws Exception {
        return sendEmailNotification(userID, null, null, false, subject, body, from, ccList, replyTo, applicationAPIKey, appID);
    }

    public JSONObject sendEmailNotificationBasedOnStatus(String statusName, String subject, String body, String from, JSONArray ccList, String replyTo, String applicationAPIKey, String appID) throws Exception {
        return sendEmailNotification(null, statusName, null, false, subject, body, from, ccList, replyTo, applicationAPIKey, appID);
    }

    public JSONObject sendEmailNotificationBasedOnRole(String roleName, String subject, String body, String from, JSONArray ccList, String replyTo, String applicationAPIKey, String appID) throws Exception {
        return sendEmailNotification(null, null, roleName, false, subject, body, from, ccList, replyTo, applicationAPIKey, appID);
    }

    public JSONObject sendEmailNotificationBasedOnStatusAndOrRole(String statusName, String roleName, boolean bothCriteria, String subject, String body, String from, JSONArray ccList, String replyTo, String applicationAPIKey, String appID) throws Exception {
        return sendEmailNotification(null, statusName, roleName, bothCriteria, subject, body, from, ccList, replyTo, applicationAPIKey, appID);
    }
}
