package org.example.finalproject.exceptions;

public class UniquenessViolationException extends RuntimeException {
    public UniquenessViolationException() {
        super();
        //Default constructor
    }

    public UniquenessViolationException(String message) {
        super(message);
    }
}
