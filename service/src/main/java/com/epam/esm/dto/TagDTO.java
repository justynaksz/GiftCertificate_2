package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

/**
 * DTO class fo Tag.
 */
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagDTO extends RepresentationModel<TagDTO> implements DTO {

    private final Integer id;
    private final String name;

    public TagDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var tagDTO = (TagDTO) o;
        return Objects.equals(id, tagDTO.id) && Objects.equals(name, tagDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
