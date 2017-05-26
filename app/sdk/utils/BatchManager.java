package sdk.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Logger;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import sdk.data.DataSet;

import static sdk.utils.CallbackLogger.logCallbackInfo;

/**
 * Created by alexisandreason on 5/26/17.
 */
public class BatchManager {
    private String callbackURL;
    private WSClient client;

    public BatchManager(String callbackURL, WSClient client) {
        this.callbackURL = callbackURL;
        this.client = client;
    }

    public void sendBatch(DataSet dataSet, boolean moreRecordsAvailable) {
        dataSet.setMoreRecordsAvailable(moreRecordsAvailable);
        WSRequest request = client.url(callbackURL);
        ObjectNode json = dataSet.toJSON();
        Logger.debug(String.format("Async response: %s\n%s", callbackURL, json.toString()));
        Logger.debug(json.toString());
        request.post(json)
                .whenComplete((wsResponse, throwable) -> {
                    logCallbackInfo(wsResponse, throwable, callbackURL);
                });
    }
}
