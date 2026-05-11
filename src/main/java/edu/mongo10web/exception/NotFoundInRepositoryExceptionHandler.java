package edu.mongo10web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundInRepositoryExceptionHandler {
    @ExceptionHandler(NotFoundInRepositoryException.class)
    public ResponseEntity<?> handleNotFound(NotFoundInRepositoryException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
