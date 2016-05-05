package com.apptree.models;

/**
 * Created by matthew on 1/6/15.
 */
public class ATInvalidPrimaryKeyException extends Exception {

    /**
     * Creates an invalid primary key exception
     */
    public ATInvalidPrimaryKeyException() {
    }

    /**
     * Creates an invalid primary key exception
     * @param message The exception message
     */
    public ATInvalidPrimaryKeyException(String message) {
        super(message);
    }

    /**
     * Creates an invalid primary key exception
     * @param message The exception message
     * @param cause The throwable cause
     */
    public ATInvalidPrimaryKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an invalid primary key exception
     * @param cause The throwable cause
     */
    public ATInvalidPrimaryKeyException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates an invalid primary key exception
     * @param message The exception message
     * @param cause The throwable cause
     * @param enableSuppression A boolean indicating whether suppression is enabled
     * @param writableStackTrace a boolean indicating whether the stack trace is writeable
     */
    public ATInvalidPrimaryKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}