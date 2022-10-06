package com.epam.esm.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mapper to transform {@link Tag} and {@link TagDTO} types.
 */
@Component
public class TagMapper extends Mapper<Tag, TagDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag toModel(TagDTO tagDTO) {
        var id = 0;
        if (tagDTO.getId() != null) {
            id = tagDTO.getId();
        }
        LocalDateTime createDate = null;
        if (tagDTO.getCreateDate() != null) {
            createDate = LocalDateTime.parse(tagDTO.getCreateDate());
        }
        return new Tag(id, tagDTO.getName(), createDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagDTO toDTO(Tag tag) {
        var id = 0;
        if (tag.getId() != null) {
            id = tag.getId();
        }
        String createDate = null;
        if (tag.getCreateDate() != null) {
            createDate = constructISOFormatDate(tag.getCreateDate());
        }
        return new TagDTO(id, tag.getName(), createDate);
    }
}
