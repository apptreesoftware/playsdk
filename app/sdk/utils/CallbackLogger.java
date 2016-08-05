package sdk.utils;

import play.Logger;
import play.libs.ws.WSResponse;

/**
 * Created by Matthew Smith on 8/5/16.
 * Copyright AppTree Software, Inc.
 */
public class CallbackLogger {
    public static void logExceptionCallback(WSResponse response, Throwable dataSetThrowable, Throwable callbackThrowable, String callbackURL) {
        if (callbackThrowable != null) {
            Logger.error(String.format("FAILURE CALLBACK COULD NOT BE SENT to %s. Network Exception Occurred - %s", callbackURL, callbackThrowable.toString()), dataSetThrowable);
        } else {
            if (response.getStatus() == 200) {
                Logger.error(String.format("FAILURE CALLBACK SENT to %s successfully", callbackURL), dataSetThrowable);
            } else {
                Logger.error(String.format("FAILURE CALLBACK COULD NOT BE SENT to %s. Reason: %s : %s\n%s", callbackURL, response.getStatus(), response.getStatusText(), response.getBody()), dataSetThrowable);
            }
        }
    }

    public static void logCallbackInfo(WSResponse response, Throwable callbackThrowable, String callbackURL) {
        if (callbackThrowable != null) {
            Logger.error(String.format("CALLBACK COULD NOT BE SENT to %s. Network Exception Occurred - %s", callbackURL, callbackThrowable.toString()), callbackThrowable);
        } else {
            if (response.getStatus() == 200) {
                Logger.debug(String.format("CALLBACK SENT to %s successfully", callbackURL));
            } else {
                Logger.error(String.format("CALLBACK COULD NOT BE SENT to %s. Reason: %s : %s\n%s", callbackURL, response.getStatus(), response.getStatusText(), response.getBody()));
            }
        }
    }
}
