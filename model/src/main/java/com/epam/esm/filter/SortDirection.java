package com.epam.esm.filter;

/**
 * Representation of possible sorting directions.
 */
public enum SortDirection {
    ASC, DESC;

    /**
     * Converts String into enum or null.
     *
     * @param sortDirection given String to convert
     * @return sortDirectionEnum
     */
    public static SortDirection convertString(String sortDirection) {
        SortDirection direction = null;
        if (sortDirection != null) {
            var sortDirectionPrep = sortDirection.trim().toUpperCase();
            if (DESC.toString().equals(sortDirectionPrep)) {
                direction = DESC;
            } else {
                direction = ASC;
            }
        } else {
            direction = ASC;
        }
        return direction;
    }
}
