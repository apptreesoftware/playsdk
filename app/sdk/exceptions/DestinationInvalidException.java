package sdk.exceptions;

public class DestinationInvalidException extends RuntimeException{

    public DestinationInvalidException(){
        super("Destination object is invalid.");
    }
}
