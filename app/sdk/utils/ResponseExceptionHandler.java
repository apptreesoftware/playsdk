package sdk.utils;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sdk.exceptions.PrimaryObjectNotFoundException;

/**
 * Created by Matthew Smith on 5/12/16.
 * Copyright AppTree Software, Inc.
 */
public class ResponseExceptionHandler {
    public static Result handleException(Throwable throwable) {
        throwable = findRootCause(throwable);
        throwable.printStackTrace();
        if ( throwable instanceof PrimaryObjectNotFoundException) {
            if ( throwable.getMessage() != null ) {
                return Controller.notFound(Json.toJson(Response.fromException(throwable)));
            }
            return Controller.notFound();
        }
        return Controller.ok(Json.toJson(Response.fromException(throwable)));
    }

    private static Throwable findRootCause(Throwable throwable) {
        Throwable childCause = throwable;
        while ( childCause.getCause() != null ) {
            childCause = childCause.getCause();
        }
        return childCause;
    }
}
