package com.epam.esm.filter;

/**
 * Possible enums for sorting direction.
 */
public enum SortDirection {
    ASC, DESC;

    /**
     * Converts String into enum or null.
     * @param sortDirection given String to convert
     * @return sortDirectionEnum
     */
    public static SortDirection convertString(String sortDirection) {
        if (sortDirection != null) {
            var sortDirectionPrep = sortDirection.trim().toUpperCase();
            if (SortDirection.valueOf(sortDirectionPrep).equals(ASC)) {
                return ASC;
            } else if (SortDirection.valueOf(sortDirectionPrep).equals(DESC)) {
                return DESC;
            } else {
                return null;
            }
        }
        return null;
    }
}
