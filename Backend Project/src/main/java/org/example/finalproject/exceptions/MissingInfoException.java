package org.example.finalproject.exceptions;

public class MissingInfoException extends RuntimeException {
    public MissingInfoException() {
        //Default constructro
        super();
    }

    public MissingInfoException(String message) {
        super(message);
    }
}
