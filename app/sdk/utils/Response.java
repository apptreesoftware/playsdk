package sdk.utils;

import sdk.AppTree;

/**
 * Created by alexis on 5/3/16.
 */
public class Response {
    protected boolean success = true;
    protected String message;
    protected boolean showMessageAsAlert;
    protected boolean async = false;

    public Response setFailedWithMessage(String message) {
        setSuccess(false);
        setMessage(message);
        return this;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(boolean success, String message, boolean async) {
        this.success = success;
        this.message = message;
        this.async = async;
    }

    public static Response fromException(Throwable throwable, boolean async) {
        String message = throwable.getMessage();
        if ( message == null ) {
            if ( throwable instanceof NullPointerException ) {
                message = "Connector threw NullPointer exception";
            }
        }
        return new Response(false, message, async);
    }
    public static Response success() {
        return new Response(true, null);
    }
    public static Response asyncSuccess() { return new Response(true, null, true); }
    public static Response success(String message) {
        return new Response(true, message);
    }

    public Response() {

    }

    /**
     *
     * @return A boolean indicating the success of the call
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the boolean indicating the success of the call
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     *
     * @return A String message about the call
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets a String message about the call
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return A boolean indicating whether the message should be shown as an alert on the device
     */
    public boolean isShowMessageAsAlert() {
        return showMessageAsAlert;
    }

    /**
     * Sets a boolean indicating whether the message should be shown as an alert on the device
     * @param showMessageAsAlert
     */
    public void setShowMessageAsAlert(boolean showMessageAsAlert) {
        this.showMessageAsAlert = showMessageAsAlert;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
