package sdk.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import play.mvc.Result;
import sdk.exceptions.AuthorizationException;
import sdk.exceptions.PrimaryObjectNotFoundException;

/**
 * Created by Matthew Smith on 5/12/16.
 * Copyright AppTree Software, Inc.
 */
public class ResponseExceptionHandler {
    public static Result handleException(Throwable throwable) {
        return handleException(throwable, false);
    }

    public static Result handleException(Throwable throwable, boolean async) {
        throwable = findRootCause(throwable);
        throwable.printStackTrace();
        if ( throwable instanceof PrimaryObjectNotFoundException) {
            if ( throwable.getMessage() != null ) {
                return Controller.notFound(JsonUtils.toJson(Response.fromException(throwable, async)));
            }
            return Controller.notFound();
        } else if ( throwable instanceof AuthorizationException) {
            return Controller.unauthorized();
        }
        return Controller.ok(JsonUtils.toJson(Response.fromException(throwable, async)));
    }

    public static void updateCallbackWithException(WSRequest request, Throwable throwable) {
        String message = "";
        if ( throwable instanceof PrimaryObjectNotFoundException ) {
            if ( throwable.getMessage() != null ) {
                message = Response.fromException(throwable, true).getMessage();
            } else {
                message = "The data you are trying to access can not be found";
            }
        } else if ( throwable instanceof AuthorizationException ) {
            message = "Authorization Failed";
        } else {
            message = Response.fromException(throwable, true).getMessage();
        }
        request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_ERROR);
        ObjectNode json = Json.newObject();
        json.put(Constants.CORE_CALLBACK_MESSAGE, message);
        request.setBody(json);
    }

    public static Throwable findRootCause(Throwable throwable) {
        Throwable childCause = throwable;
        while ( childCause.getCause() != null ) {
            childCause = childCause.getCause();
        }
        return childCause;
    }
}
