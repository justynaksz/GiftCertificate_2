package com.epam.esm.pagination;

import com.epam.esm.dto.DTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representation of DTOs page in searching operation.
 */
public class Page<T extends DTO> {

    private final List<T> content;
    private final int pageIndex;
    private final int size;
    private final int entitiesCount;


    @JsonCreator
    public Page(int pageIndex, int size, int entitiesCount, @JsonProperty List<T> content) {
        this.pageIndex = pageIndex;
        this.size = size;
        this.entitiesCount = entitiesCount;
        this.content = content;
    }

    /**
     * Gets the content of page.
     * @return list of items on the page
     */
    public List<T> getContent() {
        return new ArrayList<>(content);
    }

    /**
     * Gets current page number.
     * @return pageIndex
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * Gets page max size.
     * @return pageSize
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets last page number.
     * @return lastPage
     */
    public int getLastPageIndex() {
        if (size == 0) {
            return 1;
        } else {
            return (int) Math.ceil((double)entitiesCount / (double)size);
        }
    }

    /**
     * Checks if there is previous page available.
     */
    public boolean hasPrevious() {
        return pageIndex > 1;
    }

    /**
     * Checks if there is next page available.
     */
    public boolean hasNext() {
        return pageIndex != getLastPageIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> page = (Page<?>) o;
        return Objects.equals(content, page.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
