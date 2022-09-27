package com.epam.esm.filter;

/**
 * Represents possible sorting parameters.
 */
public enum SortParam {
    NAME, DATE;

    /**
     * Converts String into enum or null.
     * @param sortParam given String to convert
     * @return sortParamEnum
     */
    public static SortParam convertString(String sortParam) {
        if(sortParam != null) {
            var sortParamPrep = sortParam.trim().toUpperCase();
            if (SortParam.valueOf(sortParamPrep).equals(NAME)) {
                return NAME;
            } else if (SortParam.valueOf(sortParamPrep).equals(DATE)) {
                return DATE;
            } else {
                return null;
            }
        }
        return null;
    }
}
