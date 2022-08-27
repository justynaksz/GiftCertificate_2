package com.epam.esm.exceptions;

/**
 * Exception that should be thrown in case of invalid input.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
