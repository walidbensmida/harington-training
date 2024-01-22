package org.example.chapitre1.exception;

public class OperationNotFoundException extends Exception {
    public OperationNotFoundException() {
        super("Operation not found");
    }
}
