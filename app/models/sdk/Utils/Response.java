package models.sdk.Utils;

/**
 * Created by alexis on 5/3/16.
 */
public class Response {
    protected boolean success;
    protected String message;
    protected boolean showMessageAsAlert;
    public boolean authorizationError;

    /**
     *
     * @param success A boolean indicating whether or not the call was successful
     * @param message A message about the call optional
     */
    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     *
     * @return A response indicating that the call failed because it was unable to authenticate
     */
    public static Response withInvalidAuthentication() {
        return new Response(false,"Invalid Authentication",true);
    }

    public void setFailedWithMessage(String message) {
        setSuccess(false);
        setMessage(message);
    }
    /**
     *
     * @param success A boolean indincating the success of the call
     * @param message A message about the call
     * @param authorizationError A boolean indicating whether there was an authorization error
     */
    public Response(boolean success, String message, boolean authorizationError) {
        this.success = success;
        this.message = message;
        this.authorizationError = authorizationError;
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
     * @return A boolean indicating whether there was an authorization error
     */
    public boolean isAuthorizationError() {
        return authorizationError;
    }

    /**
     * Sets a boolean indicating whether there was an authorization error
     * @param authorizationError
     */
    public void setAuthorizationError(boolean authorizationError) {
        this.authorizationError = authorizationError;
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
}
