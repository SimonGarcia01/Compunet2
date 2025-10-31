package org.example.finalproject.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super();
        //Default constructor
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}