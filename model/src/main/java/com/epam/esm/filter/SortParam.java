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
        SortParam sort = null;
        if(sortParam != null) {
            var sortParamPrep = sortParam.trim().toUpperCase();
            if (NAME.toString().equals(sortParamPrep)) {
                sort = NAME;
            } else {
                sort = DATE;
            }
        } else {
            sort = DATE;
        }
        return sort;
    }
}
