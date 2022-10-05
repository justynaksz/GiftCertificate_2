package com.epam.esm.exception;

/**
 * Should be thrown when the requested item was not found in the database.
 */
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }
}
