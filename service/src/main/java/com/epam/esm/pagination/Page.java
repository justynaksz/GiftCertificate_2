package com.epam.esm.pagination;

import com.epam.esm.dto.DTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Representative for DTOs page in searching operation.
 */
public class Page<T extends DTO> {

    private final List<T> content;

    @JsonCreator
    public Page(int page, int size, int entitiesCount, @JsonProperty List<T> content) {
        this.content = content;
    }

    /**
     * Gets the content of page.
     */
    public List<T> getContent() {
        return new ArrayList<>(content);
    }
}
