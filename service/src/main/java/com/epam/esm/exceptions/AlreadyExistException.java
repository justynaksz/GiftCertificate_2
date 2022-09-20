package com.epam.esm.exceptions;

/**
 * Exception that should be thrown in case of duplicate item in database.
 */
public class AlreadyExistException extends Exception {

    /**
     * Constructs exception containing message with specified name of tag
     * which is already present in database, but was tried to add once again.
     * @param tagName       name of tag which is already present in db
     */
    public AlreadyExistException(String tagName) {
        super("Tag of name \"" + tagName + "\" already exists in data base.");
    }
}
