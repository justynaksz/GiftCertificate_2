package com.epam.esm.exceptions;

/**
 * Should be thrown in case of invalid input.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
