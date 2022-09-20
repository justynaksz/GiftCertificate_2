package com.epam.esm.exception;

/**
 * Exception that should be thrown when the requested item was not found in db.
 */
public class NotFoundException extends Exception {
    public NotFoundException() {
        super("Requested item not found");
    }
}
