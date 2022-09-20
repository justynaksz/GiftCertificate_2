package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Component;

/**
 * Mapper class to transform {@code Tag} and {@code TagDTO} types.
 */
@Component
public class TagMapper {

    /**
     * Migrate {@code TagDTO} object to {@code Tag} type.
     *
     * @param tagDTO object to transform into tag
     * @return tag         transformed tag
     */
    public Tag toModel(TagDTO tagDTO) {
        var id = 0;
        if (tagDTO.getId() != null) {
            id = tagDTO.getId();
        }
        return new Tag(id, tagDTO.getName());
    }

    /**
     * Migrate {@code Tag} object to {@code TagDTO} type.
     *
     * @param tag object to transform into tagDTO
     * @return tagDTO   transformed tagDTO
     */
    public TagDTO toDTO(Tag tag) {
        var id = 0;
        if (tag.getId() != null) {
            id = tag.getId();
        }
        return new TagDTO(id, tag.getName());
    }
}
