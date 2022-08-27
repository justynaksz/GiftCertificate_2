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
     * @param tagDTO       object to transform into tag
     * @return tag         transformed tag
     */
    public Tag toModel(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        return tag;
    }

    /**
     * Migrate {@code Tag} object to {@code TagDTO} type.
     * @param tag       object to transform into tagDTO
     * @return tagDTO   transformed tagDTO
     */
    public TagDTO toDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }
}
