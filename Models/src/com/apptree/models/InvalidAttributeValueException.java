package com.apptree.models;

/**
 * Created by Matthew Smith on 9/29/15.
 * Copyright AppTree Software, Inc.
 */
public class InvalidAttributeValueException extends Exception {
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
