package edu.mongo10web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionsHandler {
    @ExceptionHandler(NotFoundInRepositoryException.class)
    public ResponseEntity<?> handleNotFound(NotFoundInRepositoryException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(InvalidDataFormatException.class)
    public ResponseEntity<?> handleInvalidFormat(InvalidDataFormatException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }

    @ExceptionHandler(ConflictDataException.class)
    public ResponseEntity<?> handleConflict(ConflictDataException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
