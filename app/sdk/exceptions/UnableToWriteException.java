package sdk.exceptions;

/**
 * Created by Orozco on 7/26/17.
 */
public class UnableToWriteException extends Exception {

    public UnableToWriteException(String className, int index, String writeType, String message) {
        super(String.format("Unable to write %s at index %s for class %s.  Details: %s", writeType, index, className, message));
    }
}
