package sdk.data;

/**
 * Created by alexis on 5/3/16.
 */
public class InvalidAttributeValueException extends RuntimeException {
    public InvalidAttributeValueException() {
    }

    public InvalidAttributeValueException(String message) {
        super(message);
    }

    public InvalidAttributeValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAttributeValueException(Throwable cause) {
        super(cause);
    }

    public InvalidAttributeValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
