package edu.mongo10web.exception;

public class NotFoundInRepositoryException extends RuntimeException {
    public NotFoundInRepositoryException(String message) {
        super(message);
    }

    public NotFoundInRepositoryException() {
    }

    public NotFoundInRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundInRepositoryException(Throwable cause) {
        super(cause);
    }
}
