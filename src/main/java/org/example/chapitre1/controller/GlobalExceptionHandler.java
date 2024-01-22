package org.example.chapitre1.controller;

import org.example.chapitre1.exception.AccountNotFoundException;
import org.example.chapitre1.exception.OperationNotFoundException;
import org.example.chapitre1.exception.UnsupportedOperationTypeException;
import org.example.chapitre1.exception.UserNotFoundException;
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

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handlAccountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OperationNotFoundException.class)
    public ResponseEntity<String> handlOperationNotFoundException(OperationNotFoundException ex) {
        return new ResponseEntity<>("Operation not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedOperationTypeException.class)
    public ResponseEntity<String> handlUnsupportedOperationTypeException(UnsupportedOperationTypeException ex) {
        return new ResponseEntity<>("Unsupported operation type", HttpStatus.NOT_FOUND);
    }
}
