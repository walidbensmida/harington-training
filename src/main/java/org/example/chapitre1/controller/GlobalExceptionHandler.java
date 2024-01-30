package org.example.chapitre1.controller;

import org.example.chapitre1.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedFilterTypeException.class)
    public ResponseEntity<String> handleUnsupportedFilterTypeException(UnsupportedFilterTypeException ex) {
        return new ResponseEntity<>("Filter not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OperationNotFoundException.class)
    public ResponseEntity<String> handleOperationNotFoundException(OperationNotFoundException ex) {
        return new ResponseEntity<>("Operation not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedOperationTypeException.class)
    public ResponseEntity<String> handleUnsupportedOperationTypeException(UnsupportedOperationTypeException ex) {
        return new ResponseEntity<>("Unsupported operation type", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedGlobalOperationFilterTypeException.class)
    public ResponseEntity<String> handleUnsupportedGlobalOperationFilterTypeException(UnsupportedOperationTypeException ex) {
        return new ResponseEntity<>("global operator must be OR or AND", HttpStatus.NOT_FOUND);
    }
}
