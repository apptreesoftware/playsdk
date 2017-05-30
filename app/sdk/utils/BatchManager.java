package sdk.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Logger;
import play.libs.Json;
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
        ObjectNode json;
        if (dataSet.isSuccess()) {
            request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_SUCCESS);
        } else {
            request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_ERROR);
            json = Json.newObject();
            json.put(Constants.CORE_CALLBACK_MESSAGE, dataSet.getMessage() != null ? dataSet.getMessage() : "");
            request.setBody(json);
        }
        json = dataSet.toJSON();
        request.post(json)
                .whenComplete((wsResponse, throwable) -> {
                    logCallbackInfo(wsResponse, throwable, callbackURL);
                });
    }
}
