package edu.mongo10web.exception;

public class InvalidDataFormatException extends RuntimeException{
    public InvalidDataFormatException() {
    }

    public InvalidDataFormatException(String message) {
        super(message);
    }

    public InvalidDataFormatException(Throwable cause) {
        super(cause);
    }

    public InvalidDataFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
