package sdk.data;

/**
 * Created by matthew on 5/5/16.
 */
public class InvalidPrimaryKeyException extends RuntimeException {

    /**
     * Creates an invalid primary key exception
     */
    public InvalidPrimaryKeyException() {
    }

    /**
     * Creates an invalid primary key exception
     * @param message The exception message
     */
    public InvalidPrimaryKeyException(String message) {
        super(message);
    }

    /**
     * Creates an invalid primary key exception
     * @param message The exception message
     * @param cause The throwable cause
     */
    public InvalidPrimaryKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an invalid primary key exception
     * @param cause The throwable cause
     */
    public InvalidPrimaryKeyException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates an invalid primary key exception
     * @param message The exception message
     * @param cause The throwable cause
     * @param enableSuppression A boolean indicating whether suppression is enabled
     * @param writableStackTrace a boolean indicating whether the stack trace is writeable
     */
    public InvalidPrimaryKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}